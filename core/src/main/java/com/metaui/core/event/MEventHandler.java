package com.metaui.core.event;

/**
 * 事件处理者
 *
 * @author wei_jc
 * @since 1.0.0
 */
public interface MEventHandler<T> {
    /**
     * 处理事件
     *
     * @param event 所发生的事件
     */
    void handle(T event);
}
