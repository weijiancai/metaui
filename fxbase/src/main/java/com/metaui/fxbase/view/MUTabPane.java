package com.metaui.fxbase.view;

import com.metaui.fxbase.model.TabPaneModel;
import javafx.scene.control.TabPane;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class MUTabPane extends TabPane {
    private final TabPaneModel model;

    public MUTabPane(TabPaneModel model) {
        this.model = model;
    }
}
