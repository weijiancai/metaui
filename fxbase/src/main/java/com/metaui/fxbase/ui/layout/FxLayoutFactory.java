package com.metaui.fxbase.ui.layout;

import com.metaui.core.ui.IViewConfig;
import com.metaui.fxbase.ui.component.FxFormPane;
import com.metaui.fxbase.ui.view.FxPane;

import static com.metaui.core.ui.ConfigConst.LAYOUT_FORM;
/**
 * @author wei_jc
 * @version 1.0.0
 */
public class FxLayoutFactory {
    public static FxPane create(IViewConfig viewConfig, boolean isDesign) {
        if (LAYOUT_FORM.equals(viewConfig.getLayoutConfig().getName())) {
            return new FxFormPane(viewConfig.getLayoutConfig());
        }

        return null;
    }
}
