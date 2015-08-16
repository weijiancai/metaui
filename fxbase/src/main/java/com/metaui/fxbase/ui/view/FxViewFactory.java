package com.metaui.fxbase.ui.view;

import com.metaui.core.ui.IView;
import com.metaui.core.ui.IViewConfig;

import static com.metaui.core.ui.ConfigConst.*;

/**
 * @author wei_jc
 * @version 1.0.0
 */
public class FxViewFactory {
    public static IView getView(IViewConfig config) {
        if (LAYOUT_FORM.equals(config.getLayoutConfig().getName())) {
            return new FxFormView(config);
        }
        return null;
    }
}
