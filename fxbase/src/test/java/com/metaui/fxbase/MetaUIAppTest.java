package com.metaui.fxbase;

import com.metaui.core.dict.DictManager;
import com.metaui.core.dict.EnumBoolean;
import com.metaui.core.meta.DisplayStyle;
import com.metaui.fxbase.desktop.MUListViewTabsDesktop;
import com.metaui.fxbase.model.*;
import com.metaui.fxbase.view.MUForm;
import com.metaui.fxbase.win.ApkToolWin;
import com.metaui.fxbase.win.ConsoleWin;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
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

        FormModel formModel = getFormModel();
        MUForm form = new MUForm(formModel);

        // 导航菜单
        NavMenuModel menu1 = new NavMenuModel();
        menu1.setId("APK_TOOLS");
        menu1.setTitle("apk工具");
        menu1.setView(new ApkToolWin());

        NavMenuModel menu2 = new NavMenuModel();
        menu2.setId("FORM_TEST");
        menu2.setTitle("表单测试");
        menu2.setView(form);

        NavMenuModel console = new NavMenuModel();
        console.setId("CONSOLE");
        console.setTitle("控制台");
        console.setView(new ConsoleWin());

        viewModel.getNavMenus().addAll(menu1, menu2, console);

        MUListViewTabsDesktop desktop = new MUListViewTabsDesktop(viewModel);

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

        ActionModel add = ActionModel.builder()
                .displayName("新增")
                .callback((aVoid, obj) -> {
                    System.out.println("新增。。。。。。");
                })
                .build();

        List<ActionModel> actionList = new ArrayList<>();
        actionList.add(add);
        formModel.setActions(actionList);

        return formModel;
    }

    public static void main(String[] args) {
        launch(MetaUIAppTest.class);
    }
}
