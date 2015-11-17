package com.metaui.fxbase;

import com.metaui.core.datasource.DataMap;
import com.metaui.core.datasource.db.DBDataSource;
import com.metaui.core.dict.DictManager;
import com.metaui.core.dict.EnumAlign;
import com.metaui.core.dict.EnumBoolean;
import com.metaui.core.meta.DisplayStyle;
import com.metaui.core.meta.MetaDataType;
import com.metaui.core.meta.MetaManager;
import com.metaui.core.meta.model.MetaField;
import com.metaui.core.ui.IView;
import com.metaui.fxbase.ui.IDesktop;
import com.metaui.fxbase.view.desktop.MUListViewTabsDesktop;
import com.metaui.fxbase.model.*;
import com.metaui.fxbase.view.MUForm;
import com.metaui.fxbase.view.desktop.MUTabsDesktop;
import com.metaui.fxbase.view.table.MUTable;
import com.metaui.fxbase.view.table.model.TableFieldModel;
import com.metaui.fxbase.view.table.model.TableModel;
import com.metaui.fxbase.view.tree.MUTree;
import com.metaui.fxbase.view.tree.TreeNodeModel;
import com.metaui.fxbase.win.ApkToolWin;
import com.metaui.fxbase.win.ConsoleWin;
import com.metaui.fxbase.win.db.DBCopyWin;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class MetaUIAppTest extends BaseApplication {
    private AppModel viewModel = new AppModel();

    public MetaUIAppTest() {
        super();

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

        NavMenuModel dataSource = new NavMenuModel();
        dataSource.setId("DATA_SOURCE");
        dataSource.setTitle("数据源");

        dataSource.setView(new MUForm(new FormModel(MetaManager.getMeta(DBDataSource.class))));

        NavMenuModel tree = new NavMenuModel();
        tree.setId("TREE");
        tree.setTitle("树测试");
        TreeNodeModel treeNodeModel = getTreeNodeModel();
        tree.setView(new MUTree(treeNodeModel));
        treeNodeModel.getChildren().add(new TreeNodeModel("yhbis_mobile", "database"));

        NavMenuModel table = new NavMenuModel();
        table.setId("TABLE");
        table.setTitle("表格测试");
        table.setView(createTable());

        NavMenuModel metaTable = new NavMenuModel();
        metaTable.setId("Meta_TABLE");
        metaTable.setTitle("Meta表格测试");
        metaTable.setView(createMetaTable());

        NavMenuModel dbCopy = new NavMenuModel();
        metaTable.setId("DB_COPY");
        metaTable.setTitle("数据库复制");
        metaTable.setView(new DBCopyWin());

        viewModel.getNavMenus().addAll(menu1, menu2, console, dataSource, tree, table, metaTable, dbCopy);

        setAppModel(viewModel);
    }

    @Override
    protected void onStart() {

    }

    @Override
    public IDesktop getDesktop() {
        return new MUListViewTabsDesktop(viewModel);
    }

    private IView createMetaTable() {
        return new MUTable(MetaManager.getMeta(MetaField.class));
    }

    private MUTable createTable() {
        TableModel model = new TableModel();
        model.setEditable(true);
        model.getTableFields().add(new TableFieldModel("name", "名称"));
        model.getTableFields().add(new TableFieldModel("age", "年龄", MetaDataType.INTEGER));
        model.getTableFields().add(new TableFieldModel("pwd", "密码", DisplayStyle.PASSWORD));
        model.getTableFields().add(new TableFieldModel("isWork", "在岗", DisplayStyle.BOOLEAN));
        model.getTableFields().add(new TableFieldModel("birthday", "出生日期", DisplayStyle.DATE));
        model.getTableFields().add(new TableFieldModel("dict", "数据字典", DictManager.getDict(EnumAlign.class)));

        MUTable table = new MUTable(model);
        DataMap data1 = new DataMap();
        data1.put("name", "张三");
        data1.put("age", 18);
        data1.put("pwd", "123456");
        data1.put("isWork", "T");
        data1.put("birthday", new Date());
        data1.put("dict", EnumAlign.LEFT);

        DataMap data2 = new DataMap();
        data2.put("name", "李四");
        data2.put("age", 25);
        data2.put("pwd", "222334");
        data2.put("isWork", "F");
        data2.put("birthday", new Date());
        data2.put("dict", EnumAlign.RIGHT);

        table.getItems().addAll(data1, data2);
        return table;
    }

    public FormModel getFormModel() {
        FormModel formModel = new FormModel();
        List<FormFieldModel> formFieldList = formModel.getFormFields();
        FormFieldModel name = FormFieldModel.builder()
                .formModel(formModel)
                .displayName("名称")
                .require(true)
                .value("魏建才")
                .build();
        FormFieldModel age = FormFieldModel.builder()
                .formModel(formModel)
                .displayName("年龄")
                .defaultValue("23")
                .build();
        FormFieldModel password = FormFieldModel.builder()
                .formModel(formModel)
                .displayName("密码")
                .displayStyle(DisplayStyle.PASSWORD)
                .build();
        FormFieldModel atWork = FormFieldModel.builder()
                .formModel(formModel)
                .displayName("在岗")
                .displayStyle(DisplayStyle.COMBO_BOX)
                .dict(DictManager.getDict(EnumBoolean.class))
                .build();
        FormFieldModel memo = FormFieldModel.builder()
                .formModel(formModel)
                .displayName("备注")
                .displayStyle(DisplayStyle.TEXT_AREA)
                .singleLine(true)
                .build();
        FormFieldModel birthday = FormFieldModel.builder()
                .formModel(formModel)
                .displayName("生日")
                .displayStyle(DisplayStyle.DATE)
                .singleLine(false)
                .build();

        formFieldList.add(name);
        formFieldList.add(age);
        formFieldList.add(password);
        formFieldList.add(atWork);
        formFieldList.add(birthday);
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

    public TreeNodeModel getTreeNodeModel() {
        TreeNodeModel model = new TreeNodeModel("根节点");
        model.getChildren().add(new TreeNodeModel("yhbis"));
        return model;
    }

    public static void main(String[] args) {
        launch(MetaUIAppTest.class);
    }
}
