package com.metaui.core.web.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.metaui.core.datasource.DataMap;
import com.metaui.core.datasource.db.JdbcDrivers;
import com.metaui.core.datasource.db.util.JdbcTemplate;
import com.metaui.core.util.UString;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class DBAction extends BaseAction {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        String host = request.getParameter("host");
        String port = request.getParameter("port");
        String user = request.getParameter("user");
        String password = request.getParameter("password");
        String sql = request.getParameter("sql");
        String countSql = request.getParameter("countSql");
        String database = request.getParameter("database");

        Connection connection;
        List<DataMap> list = new ArrayList<DataMap>();
        Map<String, Object> map = new HashMap<String, Object>();
        JdbcTemplate template = null;
        try {
            Class.forName(JdbcDrivers.SQL_SERVER);
            connection = DriverManager.getConnection(String.format("jdbc:sqlserver://%s:%s;databaseName=%s", host, port, database), user, password);
            template = new JdbcTemplate(connection);
            if(sql.trim().toLowerCase().startsWith("update") || sql.trim().toLowerCase().startsWith("delete") || sql.trim().toLowerCase().startsWith("insert")) {
                template.update(sql);
                template.commit();
            } else {
                list = template.queryForList(sql);

                if (UString.isNotEmpty(countSql)) {
                    int total = template.queryForInt(countSql, null);
                    map.put("recordsTotal", total);
                    map.put("recordsFiltered", total);
                    map.put("data", list);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (template != null) {
                template.close();
            }
        }

        String json;
        if (map.size() > 0) {
            json = JSON.toJSONString(map, SerializerFeature.PrettyFormat, SerializerFeature.WriteDateUseDateFormat);
        } else {
            json = JSON.toJSONString(list, SerializerFeature.PrettyFormat, SerializerFeature.WriteDateUseDateFormat);
        }
        response.getWriter().println(json);
        response.getWriter().flush();
        response.getWriter().close();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doPost(req, res);
    }
}
