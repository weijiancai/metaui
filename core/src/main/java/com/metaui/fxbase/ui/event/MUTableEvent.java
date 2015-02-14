package com.metaui.fxbase.ui.event;

import com.metaui.fxbase.ui.view.MUTable;
import javafx.event.ActionEvent;

/**
 * MetaUI Table事件
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MUTableEvent extends ActionEvent {
    private MUTable table;

    public MUTableEvent(MUTable table) {
        this.table = table;
    }

    public MUTable getTable() {
        return table;
    }
}
