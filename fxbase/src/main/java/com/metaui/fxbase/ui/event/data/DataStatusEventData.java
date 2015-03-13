package com.metaui.fxbase.ui.event.data;

import com.metaui.core.dict.EnumDataStatus;
import com.metaui.core.observer.EventData;
import com.metaui.fxbase.ui.view.MUForm;

/**
 * 数据状态事件数据
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class DataStatusEventData extends EventData {
    private EnumDataStatus dataStatus;
    private MUForm form;

    public DataStatusEventData(EnumDataStatus dataStatus, MUForm form) {
        this.dataStatus = dataStatus;
        this.form = form;
    }

    public EnumDataStatus getDataStatus() {
        return dataStatus;
    }

    public MUForm getForm() {
        return form;
    }
}
