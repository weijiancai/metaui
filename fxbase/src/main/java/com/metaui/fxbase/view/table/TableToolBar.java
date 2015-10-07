package com.metaui.fxbase.view.table;

import com.metaui.core.datasource.DataMap;
import com.metaui.core.datasource.db.QueryResult;
import com.metaui.core.dict.DictManager;
import com.metaui.core.dict.EnumAlign;
import com.metaui.core.dict.EnumBoolean;
import com.metaui.core.meta.DisplayStyle;
import com.metaui.core.meta.MetaDataType;
import com.metaui.core.meta.model.Meta;
import com.metaui.fxbase.MuEventHandler;
import com.metaui.fxbase.ui.event.DataChangeEvent;
import com.metaui.fxbase.view.dialog.MUDialog;
import com.metaui.fxbase.view.table.model.TableFieldModel;
import com.metaui.fxbase.view.table.model.TableModel;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;

/**
 * 表格工具条
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class TableToolBar extends ToolBar {
    private TableModel model;
    private MUTable table;

    public TableToolBar(MUTable table) {
        this.table = table;
        this.model = table.getModel();
        initUI();
    }

    private void initUI() {
        // 列信息
        Button btnCol = new Button("列信息");
        btnCol.setOnAction(new TableColumnEventHandler());
        // 分组
        Button btnGroup = new Button("分组");
//        btnGroup.setOnAction(new TableColumnGroupEventHandler());
        // 增加
        Button btnAdd = new Button("增加");
//        btnAdd.setOnAction(new FormAddEventHandler());
        // 删除
        Button btnDelete = new Button("删除");
//        btnDelete.setOnAction(new TableDeleteEventHandler());
        // 查看
        Button btnLook = new Button("查看");
//        btnLook.setOnAction(new TableLookEventHandler());
        // 导出数据
        Button btnExport = new Button("导出");
//        btnExport.setOnAction(new TableExportEventHandler());
        // 复制行数据
        Button btnCopy = new Button("复制行");
//        btnCopy.setOnAction(new TableRowCopyEventHandler());
        // 复制列数据
        Button btnCopyCol = new Button("复制列");
//        btnCopyCol.setOnAction(new TableColumnCopyEventHandler());

        this.getItems().addAll(btnCol, btnGroup, btnAdd, btnDelete, btnLook, btnExport, btnCopy, btnCopyCol);
        if (model.getShowQueryForm()) {
            // 查询
            Button btnQuery = new Button("查询");
            btnQuery.setOnAction(new TableQueryEventHandler());
            this.getItems().add(btnQuery);
        }
    }

    class TableColumnEventHandler extends MuEventHandler<ActionEvent> {

        @Override
        public void doHandler(ActionEvent event) throws Exception {
            BorderPane root = new BorderPane();
            root.setPrefSize(900, 400);
            TabPane tabPane = new TabPane();

            Tab main = new Tab("列信息");
            main.setClosable(false);
            main.setContent(getColInfoTable(model.getTableFields()));
            tabPane.getTabs().addAll(main);
            root.setCenter(tabPane);

            /*Meta mainMeta = view.getMeta();
            for (MetaReference ref : mainMeta.getReferences()) {
                Meta pkMeta = ref.getPkMeta();
                Tab tab = new Tab(pkMeta.getDisplayName());
                tab.setClosable(false);
                View pkTableView = pkMeta.getTableView();
                TableProperty tableProperty = new TableProperty(pkTableView);
                tab.setContent(getColInfoTable(ViewManager.getViewByName("TableFieldPropertyTableView"), tableProperty.getFieldProperties()));
                tabPane.getTabs().addAll(tab);
            }*/

            MUDialog.showCustomDialog("列信息", root, new Callback<Void, Void>() {
                @Override
                public Void call(Void param) {

                    return null;
                }
            });
        }

        public MUTable getColInfoTable(List<TableFieldModel> fieldModels) {
            TableModel model = new TableModel();
            model.setShowToolbar(false);
            model.setEditable(true);
            model.setShowPaging(false);

            model.getTableFields().add(TableFieldModel.builder().name("name").displayName("名称").sortNum(10).editable(false).width(100).build());
            model.getTableFields().add(TableFieldModel.builder().name("displayName").displayName("显示名").sortNum(20).width(150).build());
            model.getTableFields().add(TableFieldModel.builder().name("dataType").displayName("数据类型").sortNum(30).dict(DictManager.getDict(MetaDataType.class)).build());
            model.getTableFields().add(TableFieldModel.builder().name("width").displayName("宽").sortNum(40).dataType(MetaDataType.INTEGER).build());
            model.getTableFields().add(TableFieldModel.builder().name("isDisplay").displayName("显示").sortNum(50).width(20).displayStyle(DisplayStyle.BOOLEAN).build());
            model.getTableFields().add(TableFieldModel.builder().name("displayStyle").displayName("显示风格").sortNum(60).dict(DictManager.getDict(DisplayStyle.class)).build());
            model.getTableFields().add(TableFieldModel.builder().name("dict").displayName("数据字典").sortNum(70).dict(DictManager.getRoot()).width(100).build());
            model.getTableFields().add(TableFieldModel.builder().name("align").displayName("对齐方式").sortNum(80).dict(DictManager.getDict(EnumAlign.class)).build());
            model.getTableFields().add(TableFieldModel.builder().name("sortNum").displayName("排序号").sortNum(90).dataType(MetaDataType.INTEGER).build());
            model.getTableFields().add(TableFieldModel.builder().name("isPk").displayName("主键").sortNum(100).width(20).displayStyle(DisplayStyle.BOOLEAN).build());
            model.getTableFields().add(TableFieldModel.builder().name("isFk").displayName("外键").sortNum(110).width(20).displayStyle(DisplayStyle.BOOLEAN).build());

            MUTable table = new MUTable(model);
            List<DataMap> list = new ArrayList<DataMap>();
            for (TableFieldModel field : fieldModels) {
                DataMap data = new DataMap();
                data.put("name", field.getName());
                data.put("displayName", field.getDisplayName());
                data.put("dataType", field.getDataType().name()); // 数据类型
                data.put("width", field.getWidth()); // 数据类型
                data.put("isDisplay", field.isDisplay());
                data.put("displayStyle", field.getDisplayStyle());
                data.put("dict", field.getDict() == null ? "" : field.getDict().getId());
                data.put("align", field.getAlign().name());
                data.put("sortNum", field.getSortNum());
                data.put("isPk", field.isPk());
                data.put("isFk", field.isFk());
                list.add(data);
            }
            table.addEventHandler(DataChangeEvent.EVENT_TYPE, new MuEventHandler<DataChangeEvent>() {
                @Override
                public void doHandler(DataChangeEvent event) throws Exception {
                    /*String fieldName = event.getRowData().getString("name");
                    MetaField field = config.getView().getMeta().getFieldByName(fieldName.toLowerCase());
                    ViewProperty viewProperty = config.getView().getViewProperty(field, event.getName());
                    viewProperty.setValue(event.getNewValue());
                    config.setPropertyValue(field, event.getName(), event.getNewValue());
                    // 保存到数据库
                    viewProperty.update();*/
                }
            });
            table.getItems().addAll(list);

            return table;
        }
    }

    class TableQueryEventHandler extends MuEventHandler<ActionEvent> {
        @Override
        public void doHandler(ActionEvent event) throws Exception {
            // 清空数据
            if (table.getItems() != null) {
                table.getItems().clear();
            }
            // 查询数据
            Meta meta = table.getMeta();
            if (meta != null) {
                QueryResult<DataMap> queryResult = meta.query(table.getQueryForm().getQueryList(), 0, model.getPageRows());
                table.getItems().addAll(queryResult.getRows());
                model.setPageCount(meta.getPageCount());
                model.setTotal(meta.getTotalRows());
            }
        }
    }
}
