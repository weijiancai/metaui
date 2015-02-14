package com.metaui.fxbase.ui.design;

import com.metaui.core.ui.ConfigConst;
import com.metaui.core.ui.ILayoutConfig;
import com.metaui.fxbase.ui.IFormField;
import com.metaui.fxbase.ui.component.FxFormPane;
import com.metaui.fxbase.ui.config.FxFormFieldConfig;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * JavaFx 表单设计面板
 *
 * @author wei_jc
 * @since  1.0.0
 */
public class FxFormDesignPane extends FxFormPane {
    private FxFormFieldConfig fieldConfig;

    public FxFormDesignPane(ILayoutConfig formLayoutConfig, FxFormFieldConfig fieldConfig) {
        super(formLayoutConfig, false);
        this.fieldConfig = fieldConfig;

        initDesignPane();
    }

    private void initDesignPane() {
        for (final IFormField field : formFieldList) {
            field.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                    if(ConfigConst.FORM_FIELD_NAME.equals(field.getFormFieldConfig().getName())) {
                        fieldConfig.setName(newValue);
                    } else if (ConfigConst.FORM_FIELD_DISPLAY_NAME.equals(field.getFormFieldConfig().getName())) {
                        fieldConfig.setDisplayName(newValue);
                    }
                }
            });
        }
    }


}
