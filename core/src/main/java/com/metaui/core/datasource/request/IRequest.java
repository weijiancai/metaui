package com.metaui.core.datasource.request;

import com.metaui.core.datasource.QueryCondition;
import com.metaui.core.rest.PathHandler;

import java.util.List;
import java.util.Map;

/**
 * 资源请求接口
 *
 * @author wei_jc
 * @since 1.0.0
 */
public interface IRequest {
    PathHandler getPathHandler();

    Map<String, String> getParams();

    List<QueryCondition> getConditions();

    IResponse getResponse();
}
