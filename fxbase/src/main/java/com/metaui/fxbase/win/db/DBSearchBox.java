package com.metaui.fxbase.win.db;

import com.metaui.core.config.preference.Preferences;
import com.metaui.core.datasource.DataSourceManager;
import com.metaui.core.datasource.db.DBDataSource;
import com.metaui.core.datasource.db.DBManager;
import com.metaui.core.datasource.db.object.*;
import com.metaui.core.datasource.db.object.enums.DBObjectType;
import com.metaui.core.datasource.db.object.impl.DBObjectImpl;
import com.metaui.core.datasource.db.object.impl.DBTableImpl;
import com.metaui.core.ui.model.View;
import com.metaui.core.util.UList;
import com.metaui.core.util.UString;
import com.metaui.fxbase.BaseApp;
import com.metaui.fxbase.BaseApplication;
import com.metaui.fxbase.MuEventHandler;
import com.metaui.fxbase.ui.view.MUTabsDesktop;
import com.metaui.fxbase.view.Popover;
import com.metaui.fxbase.view.dialog.MUDialog;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.util.Callback;
import org.controlsfx.control.CheckComboBox;

import java.util.*;

/**
 * 搜索框
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class DBSearchBox extends StackPane {
    public static final String[] FILTER_NAMES = new String[]{"TABLE", "VIEW", "TRIGGER", "PROCEDURE", "FUNCTION", "CONSTRAINT", "INDEX"};
    // 需要保存的配置参数
    public static final String PF_DATASOURCE = "dataSource";
    public static final String PF_SCHEMA = "schema";
    public static final String PF_FILTER = "filter";

    private TextField textField;
    private final Button clearButton = new Button();
    private final Region innerBackground = new Region();
    private final Region icon = new Region();
    private Popup popup = new Popup();
    private Popup filterPopup = new Popup();
    private CheckComboBox<String> filterCB = new CheckComboBox<>();
    private SearchService searchService = new SearchService();
    private DBDataSource dataSource;
    private String schema = Preferences.getInstance().get(DBSearchBox.class, PF_SCHEMA);

    private ListView<DBObject> listView = new ListView<>(FXCollections.<DBObject>observableArrayList());
    private VBox filterBox = new VBox();
    private Callback<View, Void> callback;

    public void setDataSource(DBDataSource dataSource) {
        this.dataSource = dataSource;
        Preferences.getInstance().put(DBSearchBox.class, PF_DATASOURCE, dataSource.getId());
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public DBSearchBox(Callback<View, Void> callback) {
        this.callback = callback;

        textField = new TextField() {
            @Override protected void layoutChildren() {
                super.layoutChildren();
                if (clearButton.getParent() != this) getChildren().add(clearButton);
                if (innerBackground.getParent() != this) getChildren().add(0,innerBackground);
                if (icon.getParent() != this) getChildren().add(icon);
                innerBackground.setLayoutX(0);
                innerBackground.setLayoutY(0);
                innerBackground.resize(getWidth(), getHeight());
                icon.setLayoutX(0);
                icon.setLayoutY(0);
                icon.resize(35,30);
                clearButton.setLayoutX(getWidth()-30);
                clearButton.setLayoutY(0);
                clearButton.resize(30, 30);
            }
        };

        textField.getStyleClass().addAll("search-box");
        textField.setMaxWidth(250);
        icon.getStyleClass().setAll("search-box-icon");
        innerBackground.getStyleClass().setAll("search-box-inner");
        textField.setPromptText("Search");
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                clearButton.setVisible(newValue.length() > 0);
//                updateResults(newValue);
            }
        });
        textField.setPrefHeight(30);
        clearButton.getStyleClass().setAll("search-clear-button");
        clearButton.setCursor(Cursor.DEFAULT);
        clearButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent t) {
                textField.setText("");
            }
        });
        clearButton.setVisible(false);
        clearButton.setManaged(false);
        innerBackground.setManaged(false);
        icon.setManaged(false);

        textField.addEventFilter(KeyEvent.ANY, new EventHandler<KeyEvent>() {
            @Override public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.DOWN
                        || t.getCode() == KeyCode.UP
                        || t.getCode() == KeyCode.PAGE_DOWN
                        || (t.getCode() == KeyCode.HOME && (t.isControlDown() || t.isMetaDown()))
                        || (t.getCode() == KeyCode.END && (t.isControlDown() || t.isMetaDown()))
                        || t.getCode() == KeyCode.PAGE_UP) {
                    listView.requestFocus();
                    listView.getSelectionModel().select(0);
                    t.consume();
                } else if (t.getCode() == KeyCode.ENTER) {
                    t.consume();
                    if (t.getEventType() == KeyEvent.KEY_PRESSED) {
                        try {
                            search(textField.getText());
                        } catch (Exception e) {
                            MUDialog.showException(e);
                        }
                    }
                }
            }
        });

        listView.setVisible(false);
        listView.setCellFactory(param -> new DBSearchResultListCell());
        listView.setOnMouseClicked(new MuEventHandler<MouseEvent>() {
            @Override
            public void doHandler(MouseEvent event) throws Exception {
                if (event.getClickCount() == 2) {
                    hide();
//                    tabsDesktop.openTab(listView.getSelectionModel().getSelectedItem());
                }
            }
        });
        listView.setOnKeyPressed(new MuEventHandler<KeyEvent>() {
            @Override
            public void doHandler(KeyEvent event) throws Exception {
                if (event.getCode() == KeyCode.ENTER) {
                    hide();
                    if (callback != null) {
                        DBObjectImpl object = (DBObjectImpl) listView.getSelectionModel().getSelectedItem();
//                        object.setDataSource(dataSource);
//                        object.setSchema(dataSource.getSchema(schema));
                        DBSchema dbSchema = dataSource.getSchema(schema);
                        /*if (object.getObjectType() == DBObjectType.TABLE || object.getObjectType() == DBObjectType.VIEW) {
                            View view = object.getView();
                            callback.call(view);
                        }*/
                        if (object.getObjectType() == DBObjectType.TABLE) {
                            DBTable table = dbSchema.getTable(object.getName());
                            callback.call(table.getView());
                        } else if (object.getObjectType() == DBObjectType.VIEW) {
                            DBView view = dbSchema.getView(object.getName());
                            callback.call(view.getView());
                        }
                    }
                }
            }
        });
        this.setMinWidth(800);

        HBox hBox = new HBox();
        hBox.setSpacing(10);
        Button btnFilter = new Button();
        btnFilter.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/img/filter.png"))));
        btnFilter.setOnAction(new MuEventHandler<ActionEvent>() {
            @Override
            public void doHandler(ActionEvent event) throws Exception {
                listView.setVisible(false);
//                filterBox.setVisible(!filterBox.isVisible());
                popup.hide();
                filterPopup.centerOnScreen();
                filterPopup.show(BaseApplication.getInstance().getStage());
            }
        });
        hBox.getChildren().addAll(textField, filterCB);
        // 初始化过滤器选择框
        initFilter();

        VBox vBox = new VBox();
        vBox.getChildren().addAll(hBox, listView);

        popup.getContent().add(this);
//        filterPopup.getContent().add(filterBox);

        // 添加esc按键监听，隐藏弹出窗口
        this.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ESCAPE) {
                    hide();
                }
            }
        });

        // 绑定service
        listView.itemsProperty().bind(searchService.valueProperty());
        searchService.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && newValue.size() > 0) {
                listView.setVisible(true);
            } else {
                listView.setVisible(false);
            }
        });

        this.getChildren().addAll(vBox);

        if (dataSource == null) {
            String dsId = Preferences.getInstance().get(DBSearchBox.class, PF_DATASOURCE);
            if (UString.isNotEmpty(dsId)) {
                dataSource = (DBDataSource) DataSourceManager.getDataSource(dsId);
            }
            if (dataSource == null) {
                dataSource = DataSourceManager.getSysDataSource();
            }
        }

        /*MenuItem wizPopup = new MenuItem();
        wizPopup.setGraphic(filterBox);
        MenuButton popupButton = new MenuButton("过滤器");
        popupButton.getItems().setAll(wizPopup);
        hBox.getChildren().add(popupButton);*/
    }

    private void initFilter() {
        filterCB.getItems().addAll("表", "视图", "函数", "触发器", "存储过程", "索引", "约束");
        filterBox.setSpacing(10);
//        filterBox.setVisible(false);
        CheckBox table = new CheckBox("表");
        CheckBox view = new CheckBox("视图");
        CheckBox function = new CheckBox("函数");
        CheckBox trigger = new CheckBox("触发器");
        CheckBox procedure = new CheckBox("存储过程");
        CheckBox index = new CheckBox("索引");
        CheckBox constraint = new CheckBox("约束");
        String filterStr = Preferences.getInstance().get(DBSearchBox.class, PF_FILTER);
        if (UString.isNotEmpty(filterStr)) {
            String[] strs = filterStr.split(",");
            if (Arrays.binarySearch(strs, "TABLE") > -1) {
                table.setSelected(true);
                filterCB.getCheckModel().check("表");
            }
            if (Arrays.binarySearch(strs, "VIEW") > -1) {
                view.setSelected(true);
                filterCB.getCheckModel().check("视图");
            }
            if (Arrays.binarySearch(strs, "FUNCTION") > -1) {
                function.setSelected(true);
                filterCB.getCheckModel().check("函数");
            }
            if (Arrays.binarySearch(strs, "TRIGGER") > -1) {
                trigger.setSelected(true);
                filterCB.getCheckModel().check("触发器");
            }
            if (Arrays.binarySearch(strs, "PROCEDURE") > -1) {
                procedure.setSelected(true);
                filterCB.getCheckModel().check("存储过程");
            }
            if (Arrays.binarySearch(strs, "INDEX") > -1) {
                index.setSelected(true);
                filterCB.getCheckModel().check("索引");
            }
            if (Arrays.binarySearch(strs, "CONSTRAINT") > -1) {
                constraint.setSelected(true);
                filterCB.getCheckModel().check("约束");
            }
        }

        filterBox.getChildren().addAll(table, view, function, trigger, procedure, index, constraint);
    }

    private void search(String searchText) throws Exception {
        if (UString.isEmpty(searchText)) {
            listView.setVisible(false);
            return;
        }

        searchService.reset();
        searchService.start();
    }

    public void reset() {
        textField.setText("");
        listView.setVisible(false);
        textField.requestFocus();
    }

    public void show() {
        popup.centerOnScreen();
        popup.show(BaseApplication.getInstance().getStage());
    }

    public void hide() {
        this.reset();
        popup.hide();
    }

    public String getFilterStr() {
        List<String> list = new ArrayList<>();
        /*for (Node node : filterBox.getChildren()) {
            CheckBox cb = (CheckBox) node;
            if (cb.isSelected()) {
                if ("表".equals(cb.getText())) {
                    list.add("TABLE");
                } else if ("视图".equals(cb.getText())) {
                    list.add("VIEW");
                } else if ("函数".equals(cb.getText())) {
                    list.add("FUNCTION");
                } else if ("触发器".equals(cb.getText())) {
                    list.add("TRIGGER");
                } else if ("存储过程".equals(cb.getText())) {
                    list.add("PROCEDURE");
                } else if ("索引".equals(cb.getText())) {
                    list.add("INDEX");
                } else if ("约束".equals(cb.getText())) {
                    list.add("CONSTRAINT");
                }
            }
        }*/
        for(String item : filterCB.getCheckModel().getCheckedItems()) {
            if ("表".equals(item)) {
                list.add("TABLE");
            } else if ("视图".equals(item)) {
                list.add("VIEW");
            } else if ("函数".equals(item)) {
                list.add("FUNCTION");
            } else if ("触发器".equals(item)) {
                list.add("TRIGGER");
            } else if ("存储过程".equals(item)) {
                list.add("PROCEDURE");
            } else if ("索引".equals(item)) {
                list.add("INDEX");
            } else if ("约束".equals(item)) {
                list.add("CONSTRAINT");
            }
        }
        return UString.convert(list);
    }

    class SearchService extends Service<ObservableList<DBObject>> {

        @Override
        protected Task<ObservableList<DBObject>> createTask() {
            return new Task<ObservableList<DBObject>>() {
                @Override
                protected ObservableList<DBObject> call() throws Exception {
                    ObservableList<DBObject> result = FXCollections.observableArrayList();
                    String searchText = textField.getText();
                    if (schema != null) {
                        String filterStr = getFilterStr();
                        Preferences.getInstance().put(DBSearchBox.class, PF_SCHEMA, schema);
                        Preferences.getInstance().put(DBSearchBox.class, PF_FILTER, filterStr);
                        result.setAll(dataSource.getDbConnection().getLoader().search(searchText, new String[]{schema}, filterStr));
                    } else {
                        result.setAll(dataSource.getDbConnection().getLoader().search(searchText, null, null));
                    }

                    // 排序
                    /*Collections.sort(result, (o1, o2) -> {
                        int i = o1.getObjectType().name().compareTo(o2.getObjectType().name());
                        if (i != 0) {
                            return i;
                        }
                        return o1.getName().compareTo(o2.getName());
                    });*/

                    return result;
                }
            };
        }
    }

    class DBSearchResultListCell extends ListCell<DBObject> {
        private HBox box;
        private ImageView icon;
        private Text searchPrepText;
        private Text searchText;
        private Text searchAfterText;
        private Label fullNameText;
        private Label commentText;
        private Text typeText;
        private Tooltip fullNameTooltip;
        private Tooltip commentTooltip;

        public DBSearchResultListCell() {
            box = new HBox();
            icon = new ImageView();
            searchPrepText = new Text();
            searchText = new Text();
            searchAfterText = new Text();
            fullNameText = new Label();
            commentText = new Label();
            typeText = new Text();
            fullNameTooltip = new Tooltip();
            commentTooltip = new Tooltip();

            fullNameTooltip.setFont(Font.font(15));
            commentTooltip.setFont(Font.font(15));
            Tooltip.install(fullNameText, fullNameTooltip);
            Tooltip.install(commentText, commentTooltip);

            HBox searchTextBox = new HBox();
            searchTextBox.getChildren().add(searchText);
            searchText.setFill(Color.BLUE);
            searchTextBox.setStyle("-fx-background-color: #ffff00");
            HBox commentBox = new HBox();
            commentBox.getChildren().addAll(fullNameText, commentText);
            commentBox.setMaxWidth(400);
            fullNameText.setTextFill(Color.GREY);
            fullNameText.setFont(Font.font(11));
            commentText.setFont(Font.font(11));

            typeText.setFont(Font.font(null, FontWeight.BOLD, 10));

            Region region = new Region();
            HBox.setHgrow(region, Priority.ALWAYS);
            box.getChildren().addAll(icon, searchPrepText, searchTextBox, searchAfterText, commentBox, region, typeText);
        }

        @Override
        protected void updateItem(DBObject object, boolean empty) {
            super.updateItem(object, empty);
            if (object != null) {
                String searchStr = textField.getText();
                if (object.getIcon() != null) {
                    icon.setImage(new Image(getClass().getResourceAsStream(object.getIcon())));
                }
                int idx = object.getName().toLowerCase().indexOf(searchStr.toLowerCase());
                if (idx > -1) {
                    searchPrepText.setText(" " + object.getName().substring(0, idx));
                    searchText.setText(object.getName().substring(idx, idx + searchStr.length()));
                    searchAfterText.setText(object.getName().substring(idx + searchStr.length()) + " ");
                } else {
                    return;
                }

                fullNameText.setText(String.format("(%s)", object.getFullName()));
                fullNameTooltip.setText(object.getFullName());
                if (UString.isNotEmpty(object.getComment())) {
                    commentText.setText(String.format(" - %s", object.getComment()));
                    commentTooltip.setText(object.getComment());
                }

                if (object.getObjectType() != null) {
                    typeText.setText(object.getObjectType().name());
                }

                this.setGraphic(box);
            } else {
                this.setGraphic(null);
            }
        }
    }
}
