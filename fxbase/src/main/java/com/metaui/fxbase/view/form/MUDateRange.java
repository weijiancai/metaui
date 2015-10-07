package com.metaui.fxbase.view.form;

import com.metaui.core.dict.QueryModel;
import com.metaui.core.meta.MetaDataType;
import com.metaui.core.util.UString;
import com.metaui.fxbase.model.FormFieldModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;

/**
 * 日期范围 控件
 *
 * @author weijiancai
 * @since 1.0.0
 */
public class MUDateRange extends BaseFormField {
    private MUDate startField;
    private MUDate endField;
    private StringProperty value;

    public MUDateRange(FormFieldModel model) {
        super(model);
    }

    @Override
    protected void initPrep() {
        value = new SimpleStringProperty();

        startField = new MUDate(model);
        endField = new MUDate(model);

        startField.prefWidthProperty().bind(this.prefWidthProperty().divide(2).add(-15));
        endField.prefWidthProperty().bind(this.prefWidthProperty().divide(2).add(-15));

        this.getChildren().addAll(startField, new Label("至"), endField);
    }

    @Override
    public void setValue(String value) {
    }

    @Override
    protected StringProperty valueProperty() {
        return value;
    }

    @Override
    public List<Condition> getConditions() {
        List<Condition> list = new ArrayList<Condition>();

        if (UString.isNotEmpty(startField.getDate())) {
            list.add(new Condition(model.getQueryName(), QueryModel.GREATER_EQUAL, startField.getDate(), MetaDataType.DATE));
        }
        if (UString.isNotEmpty(endField.getDate())) {
            list.add(new Condition(model.getQueryName(), QueryModel.LESS_THAN, endField.getDate(), MetaDataType.DATE));
        }

        return list;
    }
}
