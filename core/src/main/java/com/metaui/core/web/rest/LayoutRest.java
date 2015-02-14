package com.metaui.core.web.rest;

import com.metaui.core.config.SystemManager;
import com.metaui.core.ui.config.LayoutConfig;
import com.metaui.core.web.action.BaseAction;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class LayoutRest extends BaseAction {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String uri = req.getRequestURI();
        if(uri.endsWith("/layout")) {
            List<LayoutConfig> list = new ArrayList<LayoutConfig>();
            list.add(SystemManager.getInstance().getLayoutConfig());
            writeJsonObject(res, list);
        }
    }
}
