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
import javafx.stage.DirectoryChooser;

import java.io.File;

/**
 * MetaUI 目录选择
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MUDirectoryChooser extends BaseFormField {
    private TextField textField;
    private DirectoryChooser dirChooser;

    public MUDirectoryChooser(FormFieldModel model) {
        super(model);
    }

    @Override
    protected void initPrep() {
        dirChooser = new DirectoryChooser();

        textField = new TextField();
        Button button = new Button("选择");

        HBox.setHgrow(textField, Priority.ALWAYS);
        button.setOnAction(new MuEventHandler<ActionEvent>() {
            @Override
            public void doHandler(ActionEvent event) throws Exception {
                // 初始目录
                String initDir = PreferenceSettings.getInstance().getObject("FILE_CHOOSER");
                if (UString.isNotEmpty(initDir)) {
                    dirChooser.setInitialDirectory(new File(initDir));
                }
                // 打开文件选择窗口
                File dir = dirChooser.showDialog(null);
                if (dir != null) {
                    textField.setText(dir.getAbsolutePath());
                    PreferenceSettings.getInstance().put("FILE_CHOOSER", dir.getAbsolutePath());
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
