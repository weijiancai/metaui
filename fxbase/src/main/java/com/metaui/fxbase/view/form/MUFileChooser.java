package com.metaui.fxbase.view.form;

import com.metaui.core.setting.PreferenceSettings;
import com.metaui.core.util.UString;
import com.metaui.fxbase.MuEventHandler;
import com.metaui.fxbase.model.FormFieldModel;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;

import java.io.File;

/**
 * MetaUI 文件选择
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MUFileChooser extends BaseFormField {
    private TextField textField;
    private FileChooser fileChooser;

    public MUFileChooser(FormFieldModel model) {
        super(model);
    }

    @Override
    protected void initPrep() {
        fileChooser = new FileChooser();

        textField = new TextField();
        Button button = new Button("选择");

        HBox.setHgrow(textField, Priority.ALWAYS);
        button.setOnAction(new MuEventHandler<ActionEvent>() {
            @Override
            public void doHandler(ActionEvent event) throws Exception {
                // 初始目录
                String initDir = PreferenceSettings.getInstance().getObject("FILE_CHOOSER");
                if (UString.isNotEmpty(initDir)) {
                    fileChooser.setInitialDirectory(new File(initDir));
                }
                // 打开文件选择窗口
                File file = fileChooser.showOpenDialog(null);
                if (file != null) {
                    textField.setText(file.getAbsolutePath());
                    PreferenceSettings.getInstance().put("FILE_CHOOSER", file.getParentFile().getAbsolutePath());
                }
            }
        });

        // 双向绑定值
        textField.promptTextProperty().bindBidirectional(model.placeholderProperty());

        this.getChildren().addAll(textField, button);
    }

    @Override
    protected void setValue(String value) {
        textField.setText(value);
    }

    @Override
    protected StringProperty valueProperty() {
        return textField.textProperty();
    }
}
