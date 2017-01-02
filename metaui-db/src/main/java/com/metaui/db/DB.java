package com.metaui.db;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author wei_jc
 * @since 1.0.0
 * 2017/1/2.
 */
public class DB {
    private static DB db;
    private Map<String, DBConnection> connectionMap = new LinkedHashMap<>();
    private DBConnection connection; // 当前连接
    private String schema; // 当前schema;
    private String table; // 当前表

    private DB() {
    }

    public static DB add(String name, String url, String userName, String password) throws Exception {
        if (db == null) {
            db = new DB();
        }
        db.connection = new DBConnection(url, userName, password);
        db.schema = db.connection.getSchema();
        db.connectionMap.put(name, db.connection);

        return db;
    }

    public String getSchema() {
        return schema;
    }
}
