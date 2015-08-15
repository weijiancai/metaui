package com.metaui.fxbase;

import com.metaui.core.dict.DictManager;
import com.metaui.core.dict.EnumBoolean;
import com.metaui.core.meta.DisplayStyle;
import com.metaui.fxbase.desktop.MUListViewTabsDesktop;
import com.metaui.fxbase.model.AppModel;
import com.metaui.fxbase.model.FormFieldModel;
import com.metaui.fxbase.model.FormModel;
import com.metaui.fxbase.model.NavMenuModel;
import com.metaui.fxbase.view.MUForm;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class MetaUIAppTest extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        AppModel viewModel = new AppModel();
        primaryStage.titleProperty().bind(viewModel.titleProperty());

        // 导航菜单
        NavMenuModel menu1 = new NavMenuModel();
        menu1.setTitle("apk工具");
        viewModel.getNavMenuList().add(menu1);

        MUListViewTabsDesktop desktop = new MUListViewTabsDesktop(viewModel);

        FormModel formModel = getFormModel();
        MUForm form = new MUForm(formModel);
        desktop.setTop(form);
        primaryStage.setScene(new Scene(desktop));

        primaryStage.show();
    }

    public FormModel getFormModel() {
        FormModel formModel = new FormModel();
        List<FormFieldModel> formFieldList = formModel.getFormFields();
        FormFieldModel name = FormFieldModel.builder()
                .displayName("名称")
                .require(true)
                .value("魏建才")
                .build();
        FormFieldModel age = FormFieldModel.builder()
                .displayName("年龄")
                .defaultValue("23")
                .build();
        FormFieldModel password = FormFieldModel.builder()
                .displayName("密码")
                .displayStyle(DisplayStyle.PASSWORD)
                .build();
        FormFieldModel atWork = FormFieldModel.builder()
                .displayName("在岗")
                .displayStyle(DisplayStyle.COMBO_BOX)
                .dict(DictManager.getDict(EnumBoolean.class))
                .build();
        FormFieldModel memo = FormFieldModel.builder()
                .displayName("备注")
                .displayStyle(DisplayStyle.TEXT_AREA)
                .singleLine(true)
                .build();

        formFieldList.add(name);
        formFieldList.add(age);
        formFieldList.add(password);
        formFieldList.add(atWork);
        formFieldList.add(memo);

        return formModel;
    }

    public static void main(String[] args) {
        launch(MetaUIAppTest.class);
    }
}
