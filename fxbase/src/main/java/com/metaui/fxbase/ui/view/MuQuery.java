package com.metaui.fxbase.ui.view;

import com.metaui.core.datasource.DataMap;
import com.metaui.core.ui.layout.property.FormProperty;
import com.metaui.core.ui.IValue;
import com.metaui.fxbase.ui.layout.MUFormLayout;
import javafx.scene.layout.Pane;

import java.util.Map;

/**
 * MetaUI Form
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MuQuery extends Pane {
    private FormProperty formConfig;
    private MUFormLayout layout;

    public MuQuery(FormProperty property) {
        this.formConfig = property;
        layout = new MUFormLayout(property);
        this.getChildren().add(layout);
    }

    public void setValues(DataMap result) {
        if (result == null) {
            return;
        }
        for (Map.Entry<String, IValue> entry : layout.getValueMap().entrySet()) {
            entry.getValue().setValue(result.getString(entry.getKey()));
        }
    }
}
