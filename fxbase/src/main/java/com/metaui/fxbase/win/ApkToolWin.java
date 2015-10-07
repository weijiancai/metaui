package com.metaui.fxbase.win;

import com.metaui.core.meta.DisplayStyle;
import com.metaui.core.ui.IView;
import com.metaui.core.util.apk.ApkTool;
import com.metaui.fxbase.model.ActionModel;
import com.metaui.fxbase.model.FormFieldModel;
import com.metaui.fxbase.model.FormModel;
import com.metaui.fxbase.view.MUForm;
import javafx.scene.layout.BorderPane;

import java.io.File;
import java.util.List;

/**
 * Apk工具窗口
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class ApkToolWin extends BorderPane implements IView {
    private MUForm form;
    private ApkTool apkTool;

    public ApkToolWin() {
        apkTool = new ApkTool();

        initUI();
    }

    @Override
    public void initUI() {
        form = new MUForm(getFormModel());
        this.setCenter(form);
    }

    public FormModel getFormModel() {
        FormModel model = new FormModel();

        FormFieldModel apkToolVersion = FormFieldModel.builder()
                .formModel(model)
                .displayName("apktool版本")
                .displayStyle(DisplayStyle.TEXT)
                .value(apkTool.getVersion())
                .build();

        FormFieldModel apkFile = FormFieldModel.builder()
                .formModel(model)
                .displayName("选择apk文件")
                .name("apkFilePath")
                .displayStyle(DisplayStyle.FILE_CHOOSER)
                .singleLine(true)
                .build();

        FormFieldModel outputDir = FormFieldModel.builder()
                .formModel(model)
                .displayName("选择输出目录")
                .displayStyle(DisplayStyle.DIRECTORY_CHOOSER)
                .singleLine(true)
                .placeholder("默认当前apk文件目录")
                .build();

        List<FormFieldModel> fieldList = model.getFormFields();
        fieldList.add(apkToolVersion);
        fieldList.add(apkFile);
        fieldList.add(outputDir);

        ActionModel decode = ActionModel.builder()
                .displayName("反编译")
                .callback((aVoid, obj) -> {
                    FormFieldModel apkFilePath = form.getModel().getFieldByName("apkFilePath");
                    String message = ApkTool.decode(new File(apkFilePath.getValue()));
                    System.out.println(message);
                })
                .build();

        model.getActions().add(decode);

        return model;
    }
}
