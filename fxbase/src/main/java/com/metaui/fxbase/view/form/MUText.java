package com.metaui.fxbase.view.form;

import com.metaui.fxbase.model.FormFieldModel;
import javafx.scene.text.Text;

/**
 *  MetaUI 文本显示
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MUText extends BaseFormField {
    private Text text;

    public MUText(FormFieldModel model) {
        super(model);
    }

    @Override
    protected void initPrep() {
        text = new Text();
        this.getChildren().add(text);

        // 双向绑定值
        text.textProperty().bindBidirectional(model.valueProperty());
    }

    @Override
    protected void setValue(String value) {
        text.setText(value);
    }
}
