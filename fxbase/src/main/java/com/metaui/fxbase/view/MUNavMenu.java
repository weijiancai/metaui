package com.metaui.fxbase.view;

import com.metaui.fxbase.model.NavMenuModel;
import javafx.scene.layout.BorderPane;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class MUNavMenu extends BorderPane {
    private NavMenuModel model;

    public MUNavMenu(NavMenuModel model) {
        this.model = model;
    }
}
