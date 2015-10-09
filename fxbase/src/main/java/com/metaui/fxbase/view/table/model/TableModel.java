package com.metaui.fxbase.view.table.model;

import com.metaui.core.datasource.DataMap;
import com.metaui.core.datasource.DataMapMetaData;
import com.metaui.core.dict.EnumAlign;
import com.metaui.core.meta.model.Meta;
import com.metaui.core.util.UList;
import com.metaui.fxbase.model.ActionModel;
import com.metaui.fxbase.model.ModelFactory;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class TableModel {
    private BooleanProperty editable = new SimpleBooleanProperty();
    private BooleanProperty showToolbar = new SimpleBooleanProperty(true);
    private BooleanProperty ShowPaging = new SimpleBooleanProperty(true);
    private BooleanProperty ShowQueryForm = new SimpleBooleanProperty(true);
    private IntegerProperty pageRows = new SimpleIntegerProperty(15);
    private IntegerProperty total = new SimpleIntegerProperty();
    private IntegerProperty pageCount = new SimpleIntegerProperty(); // 页数
    private BooleanProperty colFromData = new SimpleBooleanProperty(); // 是否从数据中创建列信息

    private ObjectProperty<ObservableList<TableFieldModel>> tableFields = new SimpleObjectProperty<>(FXCollections.observableList(new ArrayList<>()));
    private ObservableList<ActionModel> actions = FXCollections.observableArrayList(new ArrayList<>());
    private ObjectProperty<List<DataMap>> values = new SimpleObjectProperty<>();

    private Meta meta;

    public TableModel() {
        init();
    }

    public TableModel(Meta meta) {
        this.meta = meta;
        ModelFactory.convert(meta, this);

        init();
    }

    private void init() {

    }

    public boolean getEditable() {
        return editable.get();
    }

    public BooleanProperty editableProperty() {
        return editable;
    }

    public void setEditable(boolean isEditable) {
        this.editable.set(isEditable);
    }

    public boolean getShowToolbar() {
        return showToolbar.get();
    }

    public BooleanProperty showToolbarProperty() {
        return showToolbar;
    }

    public TableModel setShowToolbar(boolean showToolbar) {
        this.showToolbar.set(showToolbar);
        return this;
    }

    public boolean getShowPaging() {
        return ShowPaging.get();
    }

    public BooleanProperty showPagingProperty() {
        return ShowPaging;
    }

    public void setShowPaging(boolean showPaging) {
        this.ShowPaging.set(showPaging);
    }

    public boolean getShowQueryForm() {
        return ShowQueryForm.get();
    }

    public BooleanProperty showQueryFormProperty() {
        return ShowQueryForm;
    }

    public void setShowQueryForm(boolean showQueryForm) {
        this.ShowQueryForm.set(showQueryForm);
    }

    public int getPageRows() {
        return pageRows.get();
    }

    public IntegerProperty pageRowsProperty() {
        return pageRows;
    }

    public void setPageRows(int pageRows) {
        this.pageRows.set(pageRows);
    }

    public int getTotal() {
        return total.get();
    }

    public IntegerProperty totalProperty() {
        return total;
    }

    public void setTotal(int total) {
        this.total.set(total);
    }

    public int getPageCount() {
        return pageCount.get();
    }

    public IntegerProperty pageCountProperty() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount.set(pageCount);
    }

    public boolean getColFromData() {
        return colFromData.get();
    }

    public BooleanProperty colFromDataProperty() {
        return colFromData;
    }

    public void setColFromData(boolean colFromData) {
        this.colFromData.set(colFromData);
    }

    public ObservableList<TableFieldModel> getTableFields() {
        return tableFields.get();
    }

    public ObjectProperty<ObservableList<TableFieldModel>> tableFieldsProperty() {
        return tableFields;
    }

    public void setTableFields(ObservableList<TableFieldModel> tableFields) {
        // 排序
        Collections.sort(tableFields, (o1, o2) -> o1.getSortNum() - o2.getSortNum());
        this.tableFields.set(tableFields);
    }

    public ObservableList<ActionModel> getActions() {
        return actions;
    }

    public void setActions(List<ActionModel> actions) {
        this.actions.setAll(actions);
    }

    public List<DataMap> getValues() {
        return values.get();
    }

    public ObjectProperty<List<DataMap>> valuesProperty() {
        return values;
    }

    public void setValues(List<DataMap> values) {
        if (getColFromData()) {
            createTableFieldModel(values);
        }
        this.values.set(values);
    }

    private void createTableFieldModel(List<DataMap> list) {
        if (UList.isEmpty(list)) {
            return;
        }
        List<TableFieldModel> fieldModelList = new ArrayList<>();

        DataMap dataMap = list.get(0);
        DataMapMetaData dmMd = dataMap.getMetaData();
        if (dmMd != null) {
            for (int i = 0; i < dmMd.getColumnCount(); i++) {
                TableFieldModel fieldModel = new TableFieldModel();
                fieldModel.setName(dmMd.getColumnLabel(i));
                fieldModel.setDisplayName(dmMd.getColumnLabel(i));
                int w = dmMd.getColumnDisplaySize(i);
                if(w <= 80) {
                    w = 80;
                }

                if(w > 500) {
                    w = 200;
                }
                fieldModel.setWidth(w);

                switch (dmMd.getColumnType(i)) {
                    case Types.TIMESTAMP:
                    case Types.DATE: {
                        fieldModel.setWidth(140);
                        break;
                    }
                    case Types.INTEGER:
                    case Types.BIGINT:
                    case Types.CHAR:
                    case Types.DOUBLE:
                    case Types.FLOAT:
                    case Types.NUMERIC:
                    case Types.TINYINT: {
                        fieldModel.setWidth(60);
                        fieldModel.setAlign(EnumAlign.CENTER);
                        break;
                    }
                }
                // boolean 类型
                if (Types.CHAR == dmMd.getColumnType(i) && dmMd.getColumnName(i).toLowerCase().startsWith("is")) {
                    fieldModel.setWidth(50);
                    fieldModel.setAlign(EnumAlign.CENTER);
                }

                fieldModelList.add(fieldModel);
            }
        } else {
            for (String key : dataMap.keySet()) {
                TableFieldModel fieldModel = new TableFieldModel();
                fieldModel.setName(key);
                fieldModel.setDisplayName(key);
                fieldModelList.add(fieldModel);
            }
        }

        setTableFields(FXCollections.observableArrayList(fieldModelList));
    }

    public void addAll(TableFieldModel... fieldModels) {
        for (TableFieldModel field : fieldModels) {
            field.setTableModel(this);
        }
        getTableFields().addAll(fieldModels);
    }
}
