package com.metaui.core.datasource.db.object.impl;

import com.metaui.core.datasource.db.object.DBColumn;
import com.metaui.core.datasource.db.object.DBIndex;
import com.metaui.core.datasource.db.object.enums.DBObjectType;
import com.metaui.core.datasource.db.object.DBTable;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class DBIndexImpl extends DBObjectImpl implements DBIndex {
    private boolean isUnique;
    private boolean isAsc;
    private String tableName;
    private DBTable table;
    private List<String> columnNames;
    private List<DBColumn> columns;

    public DBIndexImpl() {
        setObjectType(DBObjectType.INDEX);
    }

    @Override
    public boolean isUnique() {
        return isUnique;
    }

    @Override
    public boolean isAsc() {
        return isAsc;
    }

    @Override
    public DBTable getTable() {
        return table;
    }

    @Override
    public List<DBColumn> getColumns() {
        return columns;
    }

    public void setUnique(boolean isUnique) {
        this.isUnique = isUnique;
    }

    public void setAsc(boolean isAsc) {
        this.isAsc = isAsc;
    }

    public String getTableName() {
        return tableName;
    }

    @Override
    public List<String> getColumnNames() {
        if (columnNames == null) {
            columnNames = new ArrayList<String>();
        }
        return columnNames;
    }

    public void setColumnNames(List<String> columnNames) {
        this.columnNames = columnNames;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setTable(DBTable table) {
        this.table = table;
    }

    public void setColumns(List<DBColumn> columns) {
        this.columns = columns;
    }

    @Override
    public String getFullName() {
        return getSchema().getFullName() + "." + getName();
    }
}
