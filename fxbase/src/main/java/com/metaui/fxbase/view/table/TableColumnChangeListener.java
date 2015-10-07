package com.metaui.fxbase.view.table;

import com.metaui.core.datasource.DataMap;
import com.metaui.fxbase.ui.component.table.column.BaseTableColumn;
import com.metaui.fxbase.view.table.model.TableFieldModel;
import com.metaui.fxbase.view.table.model.TableModel;
import javafx.collections.ListChangeListener;
import javafx.scene.control.TableColumn;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class TableColumnChangeListener implements ListChangeListener<TableColumn<DataMap, ?>> {
    private TableModel model;

    public TableColumnChangeListener(TableModel model) {
        this.model = model;
    }

    @Override
    public void onChanged(Change<? extends TableColumn<DataMap, ?>> change) {
        if (change.next() && !change.wasRemoved()) {
            Map<TableFieldModel, Integer> map = new HashMap<>();
            for (int i = 0; i < model.getTableFields().size(); i++) {
                TableFieldModel field = model.getTableFields().get(i);
                BaseTableColumn cur = (BaseTableColumn) change.getList().get(i + 1);
                if (field.getSortNum() != cur.getProperty().getSortNum()) {
                    map.put(field, cur.getProperty().getSortNum());
                }
            }
            // 更新序号
            for (Map.Entry<TableFieldModel, Integer> entry : map.entrySet()) {
                entry.getKey().setSortNum(entry.getValue());
            }
        }
    }
}
