package com.metaui.fxbase.win.db;

import com.metaui.core.datasource.DataMap;
import com.metaui.core.datasource.db.object.DBColumn;
import com.metaui.core.datasource.db.object.impl.DBColumnImpl;
import com.metaui.core.datasource.db.object.impl.DBTableImpl;
import com.metaui.core.meta.DisplayStyle;
import com.metaui.fxbase.model.FormFieldModel;
import com.metaui.fxbase.model.FormModel;
import com.metaui.fxbase.view.MUForm;
import com.metaui.fxbase.view.dialog.MUDialog;
import com.metaui.fxbase.view.table.MUTable;
import com.metaui.fxbase.view.table.model.TableFieldModel;
import com.metaui.fxbase.view.table.model.TableModel;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;

/**
 * 添加表对话框
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class AddTableDialog extends BorderPane {
    private MUForm form;
    private MUTable table;

    public AddTableDialog() {
        form = new MUForm(createFormModel());
        table = new MUTable(createTableModel());

        this.setTop(form);
        this.setCenter(table);
    }

    private FormModel createFormModel() {
        FormModel model = new FormModel();
        model.addAll(
                FormFieldModel.builder().name("name").displayName("名称").build(),
                FormFieldModel.builder().name("comment").displayName("注释").build()
        );
        return model;
    }

    private TableModel createTableModel() {
        TableModel model = new TableModel();
        model.setShowToolbar(false);
        model.setShowPaging(false);
        model.setEditable(true);

        model.addAll(
                TableFieldModel.builder().name("name").displayName("名称").width(80).build(),
                TableFieldModel.builder().name("comment").displayName("注释").width(120).build(),
                TableFieldModel.builder().name("dataType").displayName("数据类型").width(60).build(),
                TableFieldModel.builder().name("isPk").displayName("是否主键").width(40).displayStyle(DisplayStyle.BOOLEAN).build()
        );
        return model;
    }

    public MUForm getForm() {
        return form;
    }

    public MUTable getTable() {
        return table;
    }

    public static void show() {
        AddTableDialog dialog = new AddTableDialog();
        MUDialog.showCustomDialog("新增表", dialog, new Callback<Void, Void>() {
            @Override
            public Void call(Void param) {
                FormModel formModel = dialog.getForm().getModel();
                TableModel tableModel = dialog.getTable().getModel();
                DBTableImpl table = new DBTableImpl();
                table.setName(formModel.getFieldValue("name"));
                table.setComment(formModel.getFieldValue("comment"));

                List<DBColumn> columns = new ArrayList<>();
                for (DataMap dataMap : tableModel.getValues()) {
                    columns.add(new DBColumnImpl(dataMap.getString("name"), dataMap.getString("comment"), dataMap.getString("dataType"), dataMap.getBoolean("isPk")));
                }
                table.setColumns(columns);

                return null;
            }
        });
    }
}
