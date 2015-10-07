package com.metaui.fxbase.view.form;

import com.metaui.core.dict.FormType;
import com.metaui.core.dict.QueryModel;
import com.metaui.core.meta.DisplayStyle;
import com.metaui.core.meta.MetaDataType;
import com.metaui.core.ui.ICanQuery;
import com.metaui.core.util.UDate;
import com.metaui.core.util.UString;
import com.metaui.core.util.UUIDUtil;
import com.metaui.fxbase.MuEventHandler;
import com.metaui.fxbase.model.FormFieldModel;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public abstract class BaseFormField extends HBox implements ICanQuery {
    protected FormFieldModel model;
    protected boolean isQuery;
    private List<Condition> list = new ArrayList<>();

    private HBox labelBox = new HBox();
    private Hyperlink btnQueryModel;

    public BaseFormField() {
    }

    public BaseFormField(FormFieldModel model) {
        this.model = model;
        this.isQuery = model.getFormModel().getFormType() == FormType.QUERY;
        init();
    }

    /**
     * 初始化之前调用
     */
    protected abstract void initPrep();

    /**
     * 设置控件值
     *
     * @param value 值
     */
    protected abstract void setValue(String value);

    /**
     * 值属性
     *
     * @return
     */
    protected abstract StringProperty valueProperty();

    /**
     * 初始化
     */
    protected void init() {
        initPrep();

        this.prefWidthProperty().bind(model.widthProperty());
        this.prefHeightProperty().bind(model.heightProperty());
        this.setAlignment(Pos.CENTER_LEFT);
        this.setSpacing(5);

        // label
        labelBox.setAlignment(Pos.CENTER_LEFT);
        labelBox.setSpacing(3);
        Label label = new Label();
        label.textProperty().bindBidirectional(model.displayNameProperty());
        labelBox.getChildren().add(label);
        // 是否必须
        model.requireProperty().addListener((observable, oldValue, newValue) -> {
            setLabelRequire(newValue);
        });
        setLabelRequire(model.isRequire());
        // 设置默认值
        String defaultValue = model.getDefaultValue();
        if ("GUID()".equals(defaultValue)) {
            if(isQuery) {
                this.setValue("");
            } else {
                this.setValue(UUIDUtil.getUUID());
            }
        } else if ("SYSDATE()".equals(defaultValue)) {
            if (isQuery) {
                this.setValue("");
            } else {
                this.setValue(UDate.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
            }
        } else if (UString.isEmpty(model.getValue())) {
            this.setValue(defaultValue);
        }
        // 绑定值
        model.valueProperty().bindBidirectional(valueProperty());

        initAfter();
    }

    protected void initAfter() {
        if(isQuery && !this.getClass().isAssignableFrom(MUDate.class) && !this.getClass().isAssignableFrom(MUDateRange.class)) {
            btnQueryModel = new Hyperlink("=");
            btnQueryModel.setBorder(null);
            btnQueryModel.setMinWidth(30);
            btnQueryModel.setOnAction(event -> {
                String text = btnQueryModel.getText().trim();
                switch (text) {
                    case "=":
                        setQueryModel("!=");
                        break;
                    case "!=":
                        setQueryModel(">");
                        break;
                    case ">":
                        setQueryModel(">=");
                        break;
                    case ">=":
                        setQueryModel("<");
                        break;
                    case "<":
                        setQueryModel("<=");
                        break;
                    case "<=":
                        setQueryModel("%%");
                        break;
                    case "%%":
                        setQueryModel("*%");
                        break;
                    case "*%":
                        setQueryModel("%*");
                        break;
                    case "%*":
                        setQueryModel("null");
                        break;
                    case "null":
                        setQueryModel("not null");
                        break;
                    case "not null":
                        setQueryModel("=");
                        break;
                }
            });

            // 查看查询模式上下文菜单
            MenuItem equal = new MenuItem("=");
            equal.setOnAction(new MuEventHandler<ActionEvent>() {
                @Override
                public void doHandler(ActionEvent event) throws Exception {
                    setQueryModel("=");
                }
            });
            MenuItem notEqual = new MenuItem("!=");
            notEqual.setOnAction(new MuEventHandler<ActionEvent>() {
                @Override
                public void doHandler(ActionEvent event) throws Exception{
                    setQueryModel("!=");
                }
            });
            MenuItem greater = new MenuItem(">");
            greater.setOnAction(new MuEventHandler<ActionEvent>() {
                @Override
                public void doHandler(ActionEvent event) throws Exception{
                    setQueryModel(">");
                }
            });
            MenuItem greaterEqual = new MenuItem(">=");
            greaterEqual.setOnAction(new MuEventHandler<ActionEvent>() {
                @Override
                public void doHandler(ActionEvent event) throws Exception {
                    setQueryModel(">=");
                }
            });
            MenuItem less = new MenuItem("<");
            less.setOnAction(new MuEventHandler<ActionEvent>() {
                @Override
                public void doHandler(ActionEvent event) throws Exception {
                    setQueryModel("<");
                }
            });
            MenuItem lessEqual = new MenuItem("<=");
            lessEqual.setOnAction(new MuEventHandler<ActionEvent>() {
                @Override
                public void doHandler(ActionEvent event) throws Exception {
                    setQueryModel("<=");
                }
            });
            MenuItem like = new MenuItem("%%");
            like.setOnAction(new MuEventHandler<ActionEvent>() {
                @Override
                public void doHandler(ActionEvent event) throws Exception {
                    setQueryModel("%%");
                }
            });
            MenuItem leftLike = new MenuItem("*%");
            leftLike.setOnAction(new MuEventHandler<ActionEvent>() {
                @Override
                public void doHandler(ActionEvent event) throws Exception {
                    setQueryModel("*%");
                }
            });
            MenuItem rightLike = new MenuItem("%*");
            rightLike.setOnAction(new MuEventHandler<ActionEvent>() {
                @Override
                public void doHandler(ActionEvent event) throws Exception {
                    setQueryModel("%*");
                }
            });
            MenuItem nullItem = new MenuItem("null");
            nullItem.setOnAction(new MuEventHandler<ActionEvent>() {
                @Override
                public void doHandler(ActionEvent event) throws Exception {
                    setQueryModel("null");
                }
            });
            MenuItem notNull = new MenuItem("not null");
            notNull.setOnAction(new MuEventHandler<ActionEvent>() {
                @Override
                public void doHandler(ActionEvent event) throws Exception {
                    setQueryModel("not null");
                }
            });
            ContextMenu contextMenu = new ContextMenu();
            contextMenu.getItems().addAll(equal, notEqual, greater, greaterEqual, less, lessEqual, like, leftLike, rightLike);
            btnQueryModel.setContextMenu(contextMenu);
            this.getChildren().add(btnQueryModel);

            // 设置查询模式
            setQueryModel(model.getQueryModel().toSqlModel());
        }
    }

    private void setQueryModel(String queryModel) {
        btnQueryModel.setText(queryModel);
        switch (queryModel) {
            case "=":
                model.setQueryModel(QueryModel.EQUAL);
                break;
            case "!=":
                model.setQueryModel(QueryModel.NOT_EQUAL);
                break;
            case ">":
                model.setQueryModel(QueryModel.GREATER_THAN);
                break;
            case ">=":
                model.setQueryModel(QueryModel.GREATER_EQUAL);
                break;
            case "<":
                model.setQueryModel(QueryModel.LESS_THAN);
                break;
            case "<=":
                model.setQueryModel(QueryModel.LESS_EQUAL);
                break;
            case "%%":
                model.setQueryModel(QueryModel.LIKE);
                break;
            case "*%":
                model.setQueryModel(QueryModel.LEFT_LIKE);
                break;
            case "%*":
                model.setQueryModel(QueryModel.RIGHT_LIKE);
                break;
            case "null":
                model.setQueryModel(QueryModel.NULL);
                break;
            case "not null":
                model.setQueryModel(QueryModel.NOT_NULL);
                break;
        }
    }

    public HBox getLabelNode() {
        return labelBox;
    }

    public static BaseFormField getInstance(FormFieldModel model) {
        DisplayStyle displayStyle = model.getDisplayStyle();
        if (DisplayStyle.TEXT_AREA == displayStyle) {
            return new MUTextArea(model);
        } else if (DisplayStyle.PASSWORD == displayStyle || MetaDataType.PASSWORD == model.getDataType()) {
            return new MUPasswordField(model);
        } else if (DisplayStyle.COMBO_BOX == displayStyle || DisplayStyle.BOOLEAN == displayStyle) {
            return new MUComboBox(model);
        } else if (DisplayStyle.FILE_CHOOSER == displayStyle) {
            return new MUFileChooser(model);
        } else if (DisplayStyle.DIRECTORY_CHOOSER == displayStyle) {
            return new MUDirectoryChooser(model);
        } else if (DisplayStyle.TEXT == displayStyle) {
            return new MUText(model);
        }
        /* else if (DisplayStyle.DATA_SOURCE == displayStyle) {
            node = new MuDataSource(field);
        }*/ else if (DisplayStyle.DATE == displayStyle) {
            if (model.getFormModel().getFormType() == FormType.QUERY) {
                return new MUDateRange(model);
            } else {
                return new MUDate(model);
            }
        }
        return new MUTextField(model);
    }

    public void setLabelRequire(boolean isRequire) {
        if (isRequire) {
            Text requireText = new Text("*");
            requireText.setFill(Color.RED);
            requireText.setFont(new Font(15));
            requireText.setTextAlignment(TextAlignment.CENTER);
            labelBox.getChildren().add(requireText);
        } else {
            if (labelBox.getChildren().size() == 2) {
                labelBox.getChildren().remove(1);
            }
        }
    }

    @Override
    public List<Condition> getConditions() {
        list.clear();
        if (UString.isNotEmpty(model.getValue())) {
            list.add(new Condition(model.getQueryName(), model.getQueryModel(), model.getValue(), MetaDataType.STRING));
        }

        return list;
    }
}
