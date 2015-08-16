package com.metaui.fxbase.view.form;

import com.metaui.core.meta.DisplayStyle;
import com.metaui.core.meta.MetaDataType;
import com.metaui.core.util.UString;
import com.metaui.fxbase.model.FormFieldModel;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public abstract class BaseFormField extends HBox {
    protected FormFieldModel model;

    private HBox labelBox = new HBox();

    public BaseFormField(FormFieldModel model) {
        this.model = model;
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
        // 默认值
        if (UString.isEmpty(model.getValue()) && UString.isNotEmpty(model.getDefaultValue())) {
            setValue(model.getDefaultValue());
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
        } else if (DisplayStyle.DATE == displayStyle) {
            if (formConfig.getFormType() == FormType.QUERY) {
                node = new MuDateRange(field);
            } else {
                node = new MuDate(field);
            }
        } else {
            node = new MuTextField(field);
        }*/
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
}
