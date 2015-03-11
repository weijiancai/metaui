package com.metaui.core.event;

import java.util.EventObject;

/**
 * 事件对象
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MEvent extends EventObject {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public MEvent(Object source) {
        super(source);
    }
}
