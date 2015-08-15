package com.metaui.fxbase.view.form;

import com.metaui.core.dict.DictCategory;
import com.metaui.core.dict.DictCode;
import com.metaui.core.util.UString;
import com.metaui.fxbase.BaseApp;
import com.metaui.fxbase.model.FormFieldModel;
import com.metaui.fxbase.ui.component.FxLookDictPane;
import com.metaui.fxbase.ui.view.MUDialog;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.util.List;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class MUComboBox extends BaseFormField {
    private DictCategory category;
    private ComboBox<DictCode> comboBox;

    public MUComboBox(FormFieldModel model) {
        super(model);
    }

    @Override
    protected void initPrep() {
        this.category = model.getDict();

        comboBox = new ComboBox<>();
        comboBox.setEditable(true);
        comboBox.setConverter(new DictCodeConverter());
        comboBox.setCellFactory(new Callback<ListView<DictCode>, ListCell<DictCode>>() {
            @Override
            public ListCell<DictCode> call(ListView<DictCode> param) {
                final ListCell<DictCode> cell = new ListCell<DictCode>() {

                    @Override
                    public void updateItem(DictCode item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.getDisplayName());
                        } else {
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        });

        /*if (config != null) {
            // 添加鼠标单击发布事件
            comboBox.getEditor().setOnMouseClicked(mouseClickEventHandler);
            comboBox.setOnMouseClicked(mouseClickEventHandler);
        }*/

        if (category != null) {
            List<DictCode> list = category.getCodeList();
            comboBox.setItems(FXCollections.observableArrayList(list));
            if (list.size() > 0) {
                comboBox.setPromptText(list.get(0).getDisplayName());
            }
        }

        // 查看数据字典上下文菜单
        MenuItem lookDictMenu = new MenuItem("查看数据字典");
        lookDictMenu.setOnAction(event -> MUDialog.showCustomDialog(BaseApp.getInstance().getStage(), category.getName(), new FxLookDictPane(category), null));
        ContextMenu contextMenu = new ContextMenu();
        contextMenu.getItems().add(lookDictMenu);
        comboBox.setContextMenu(contextMenu);

        comboBox.prefWidthProperty().bind(this.widthProperty());
        comboBox.valueProperty().addListener(new ChangeListener<DictCode>() {
            @Override
            public void changed(ObservableValue<? extends DictCode> observable, DictCode oldValue, DictCode newValue) {
                if (newValue != null) {
//                    valueProperty().set(newValue.getName());
                }
            }
        });

        this.getChildren().add(comboBox);
    }

    @Override
    protected void setValue(String value) {

    }

    class DictCodeConverter extends StringConverter<DictCode> {

        @Override
        public String toString(DictCode code) {
            if (code == null) {
                return null;
            }
            return code.getDisplayName();
        }

        @Override
        public DictCode fromString(String string) {
            if (UString.isNotEmpty(string)) {
                for (DictCode code : category.getCodeList()) {
                    if (code.getDisplayName().equals(string)) {
                        return code;
                    }
                }
            }
            return null;
        }
    }
}
