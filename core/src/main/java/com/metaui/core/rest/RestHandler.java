package com.metaui.core.rest;

import com.metaui.core.datasource.VirtualResource;
import com.metaui.core.datasource.request.IRequest;
import com.metaui.core.datasource.request.IResponse;

/**
 * Reset Handler
 *
 * @author wei_jc
 * @since 1.0.0
 */
public interface RestHandler {
    VirtualResource get(IRequest request);

    void post(IRequest request);

    void put(IRequest request) throws Exception;

    void delete(IRequest request);


    /**
     * 导出资源
     */
    IResponse exp(IRequest request) throws Exception;

    /**
     * 导入资源
     */
    void imp(IRequest request) throws Exception;
}
