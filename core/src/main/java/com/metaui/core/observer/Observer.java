package com.metaui.core.observer;

/**
 * 观察者
 *
 * @author wei_jc
 * @since 1.0.0
 */
public interface Observer<D extends EventData> {
    void update(D data);
}
