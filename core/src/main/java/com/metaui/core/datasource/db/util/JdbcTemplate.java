package com.metaui.core.datasource.db.util;

import com.metaui.core.datasource.*;
import com.metaui.core.datasource.db.DBDataSource;
import com.metaui.core.datasource.db.DatabaseType;
import com.metaui.core.datasource.db.QueryResult;
import com.metaui.core.datasource.db.RowMapper;
import com.metaui.core.datasource.db.connection.ConnectionUtil;
import com.metaui.core.datasource.db.object.DBConnection;
import com.metaui.core.datasource.db.sql.SqlBuilder;
import com.metaui.core.datasource.persist.IPDB;
import com.metaui.core.meta.model.Meta;
import com.metaui.core.meta.model.MetaField;
import com.metaui.core.util.Callback;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.*;

/**
 * JDBC模板
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class JdbcTemplate {
    private static final Logger log = Logger.getLogger(JdbcTemplate.class);
    private DBDataSource dataSource;
    private Connection conn;
    private DatabaseType databaseType;

    public JdbcTemplate() {
        this(DataSourceManager.getSysDataSource());
    }

    public JdbcTemplate(Connection conn) {
        this.conn = conn;
        try {
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public JdbcTemplate(DBDataSource dataSource) {
        this.dataSource = dataSource;
        try {
            DBConnection dbConn = dataSource.getDbConnection();
            conn = dbConn.getConnection();
            conn.setAutoCommit(false);
            databaseType = dbConn.getDatabaseType();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public DBDataSource getDataSource() {
        return dataSource;
    }

    public List<DataMap> queryForList(String sql) throws Exception {
        return queryForList(sql, new HashMap<String, Object>());
    }

    public List<DataMap> queryForList(String sql, Map<String, Object> conditionMap) throws Exception {
        if (conditionMap == null) {
            conditionMap = new HashMap<String, Object>();
        }
        StringBuilder sb = new StringBuilder();
        if (!sql.toLowerCase().contains("where") && !sql.toLowerCase().contains("order by")) {
            sb.append(" WHERE 1=1");
        }
        List<String> conditionKeyList = new ArrayList<String>();
        for (String key : conditionMap.keySet()) {
            conditionKeyList.add(key);
            sb.append(" AND ").append(key).append("=?");
        }
        System.out.println(sql + sb.toString());
        PreparedStatement pstmt = conn.prepareStatement(sql + sb.toString());
        int i = 1;
        for (String key : conditionKeyList) {
            pstmt.setObject(i++, conditionMap.get(key));
        }
        ResultSet rs = pstmt.executeQuery();
        ResultSetMetaData md = rs.getMetaData();
        int columnCount = md.getColumnCount();
//        System.out.println("------------------------------------------------");
        List<DataMap> list = new ArrayList<DataMap>();
        DataMap map;
        while (rs.next()) {
            map = new DataMap();
            for (i = 1; i <= columnCount; i++) {
                Object obj = rs.getObject(i);
                map.put(md.getColumnLabel(i), obj);
//                System.out.println(obj + "  >> " + md.getColumnLabel(i) + " = " + md.getColumnName(i) + " = " + (obj == null ? "" : obj.getClass().toString()));
            }
//            System.out.println("-------------------------------------------");
            list.add(map);
        }
        rs.close();

        return list;
    }

    public List<DataMap> queryForList(QueryBuilder builder, int page, int rows) throws SQLException {
        System.out.println(builder.sql().toLog());
        SqlBuilder sqlBuilder = builder.sql();
        if (page >= 0) {
            Meta meta = builder.getMeta();
            List<MetaField> pkFields = new ArrayList<MetaField>();
            if (meta != null) {
                pkFields = meta.getPkFields();
            }
            return queryForList(sqlBuilder.getPageSql(page, rows, pkFields), builder.sql().getParamsValue());
        } else {
            return queryForList(sqlBuilder.getSql(), builder.sql().getParamsValue());
        }
    }

    public List<DataMap> queryForList(String sql, Object[] paramValues) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(sql);
        for (int i = 0; i < paramValues.length; i++) {
            pstmt.setObject(i + 1, paramValues[i]);
        }

        ResultSet rs = pstmt.executeQuery();
        ResultSetMetaData md = rs.getMetaData();
        int columnCount = md.getColumnCount();
        // 设置DataMap 元数据
        DataMapMetaData dmMd = new DataMapMetaData();
        dmMd.setColumnCount(columnCount);
        for (int i = 1; i <= columnCount; i++) {
            dmMd.add(md.getColumnName(i), md.getColumnLabel(i), md.getColumnDisplaySize(i), md.getColumnType(i), md.getColumnTypeName(i));
        }

        List<DataMap> list = new ArrayList<DataMap>();
        DataMap map;
        while (rs.next()) {
            map = new DataMap();
            for (int i = 1; i <= columnCount; i++) {
                Object obj = rs.getObject(i);
                if (databaseType != null && DatabaseType.HSQLDB == databaseType) {
                    map.setUpperKey(true);
                }
                map.put(md.getColumnLabel(i), obj);
            }
            list.add(map);
        }
        rs.close();
        if (list.size() > 0) {
            list.get(0).setMetaData(dmMd);
        }

        return list;
    }

    public int queryForInt(String sql, Object[] paramValues) throws SQLException {
        int result = 0;

        System.out.println(sql);
        PreparedStatement pstmt = conn.prepareStatement(sql);
        if (paramValues != null) {
            for (int i = 0; i < paramValues.length; i++) {
                pstmt.setObject(i + 1, paramValues[i]);
            }
        }
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            result = rs.getInt(1);
        }
        rs.close();
        pstmt.close();

        return result;
    }

    public Map<String, Object> queryForMap(String sql, Object... values) throws Exception {
        PreparedStatement pstmt = conn.prepareStatement(sql);
        if (null != values && values.length > 0) {
            int i = 1;
            for (Object obj : values) {
                pstmt.setObject(i++, obj);
            }
        }
        ResultSet rs = pstmt.executeQuery();
        Map<String, Object> map = new HashMap<String, Object>();
        while (rs.next()) {
            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                map.put(rs.getMetaData().getColumnLabel(i), rs.getObject(i));
            }
        }
        rs.close();
        pstmt.close();

        return map;
    }

    public void query(String sql, Callback<ResultSet> callback, Object... values) throws Exception {
        PreparedStatement pstmt = conn.prepareStatement(sql);
        if (null != values && values.length > 0) {
            int i = 1;
            for (Object obj : values) {
                pstmt.setObject(i++, obj);
            }
        }
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            callback.call(rs);
        }
        rs.close();
        pstmt.close();
    }

    public <T> List<T> query(String sql, RowMapper<T> rowMapper, Object... values) throws Exception {
        PreparedStatement pstmt = conn.prepareStatement(sql);
        if (null != values && values.length > 0) {
            int i = 1;
            for (Object obj : values) {
                pstmt.setObject(i++, obj);
            }
        }
        ResultSet rs = pstmt.executeQuery();
        List<T> list = new ArrayList<T>();
        T t;
        while (rs.next()) {
            t = rowMapper.mapRow(rs);
            list.add(t);
        }
        rs.close();
        pstmt.close();

        return list;
    }

    public void save(IPDB po) throws Exception {
        Map<String, ? extends Map<String, Object>> map = po.getPDBMap();
        for (String table : map.keySet()) {
            save(map.get(table), table);
        }
    }

    public void save(Map<String, Object> paramMap, String table)  throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            Object value = entry.getValue();
            if (value == null) {
                continue;
            }
            String key = entry.getKey();
            // 去掉表名前缀
            if (key.contains(".")) {
                key = key.split("\\.")[1];
            }
            params.put(key, value);
        }

        try {
            StringBuilder sql = new StringBuilder("INSERT INTO " + table + " (");

            List<String> keyList = new ArrayList<String>();

            String values = "";
            int i = 0;
            for (String key : params.keySet()) {
                Object obj = params.get(key);
                sql.append(key);
                if (obj != null && obj.toString().startsWith("$$")) { // 数据库函数，不解析
                    values += obj.toString().substring(2);
                } else {
                    values += "?";
                    keyList.add(key);
                }
                if (++i < params.size()) {
                    sql.append(",");
                    values += ",";
                }
            }
            sql.append(") VALUES (");
            // 打印输出sql语句
            String outSql = sql.toString();
            sql.append(values).append(")");

            Object obj;
            i = 0;
            for (String key : params.keySet()) {
                obj = params.get(key);
                outSql += (obj == null ? "' '" : "'" + obj.toString() + "'");
                if (i++ < params.size() - 1) {
                    outSql += ",";
                }
            }
            log.info(outSql + ")");
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            i = 1;
            for (String key : keyList) {
                pstmt.setObject(i++, params.get(key));
            }
            pstmt.executeUpdate();
            pstmt.close();

        } catch (Exception e) {
            e.printStackTrace();
            if (null != conn) {
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            throw e;
        }
    }

    public void delete(Map<String, Object> params, String table)  throws Exception {
        try {
            StringBuilder sql = new StringBuilder("DELETE FROM " + table + " WHERE ");

            List<String> keyList = new ArrayList<String>();

            int i = 0;
            for(String key : params.keySet()) {
                sql.append(key).append("=?");
                if(++i < params.size()) {
                    sql.append(" AND ");
                }
                keyList.add(key);
            }
            System.out.println(sql);
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            i = 1;
            for (String key : keyList) {
                pstmt.setObject(i++, params.get(key));
            }
            pstmt.executeUpdate();
            pstmt.close();

        } catch (Exception e) {
            e.printStackTrace();
            if(null != conn) {
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            throw e;
        }
    }

    public void close() {
        ConnectionUtil.closeConnection(conn);
    }

    public void clearTable(String... tables) throws SQLException {
        for (String table : tables) {
            String sql = "DELETE FROM " + table;
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
        }
    }

    public void update(Map<String, Object> valueMap, Map<String, Object> conditionMap, String tableName) throws Exception {
        try {
            StringBuilder sql = new StringBuilder("UPDATE " + tableName + " SET ");

            List<String> keyList = new ArrayList<String>();
            List<String> conditionKeyList = new ArrayList<String>();

            int i = 0;
            for(String key : valueMap.keySet()) {
                sql.append(key).append("=?");
                if(++i < valueMap.size()) {
                    sql.append(",");
                }
                keyList.add(key);
            }
            sql.append(" WHERE 1=1 ");
            for (String key : conditionMap.keySet()) {
                sql.append("AND ").append(key).append("=?");
                conditionKeyList.add(key);
            }
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            List<Object> objects = new ArrayList<Object>();
            i = 1;
            for (String key : keyList) {
                pstmt.setObject(i++, valueMap.get(key));
                objects.add(valueMap.get(key));
            }
            for (String key : conditionKeyList) {
                pstmt.setObject(i++, conditionMap.get(key));
                objects.add(conditionMap.get(key));
            }
            System.out.println(SqlUtil.toLog(sql.toString(), objects.toArray()));
            pstmt.executeUpdate();
            pstmt.close();

        } catch (Exception e) {
            e.printStackTrace();
            if(null != conn) {
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            throw e;
        }
    }

    public void commit() {
        if (null != conn) {
            try {
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void update(String sql) throws SQLException {
        try {
            System.out.println(sql);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            if(null != conn) {
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            throw e;
        }
    }

    public static QueryResult<DataMap> queryForResult(QueryBuilder builder, DBDataSource dataSource, int page, int rows) throws Exception {
        QueryResult<DataMap> queryResult = new QueryResult<DataMap>();
        queryResult.setPageRows(rows);

        builder.sql().build(dataSource.getDatabaseType());
        JdbcTemplate template = new JdbcTemplate(dataSource);
        List<DataMap> list = new ArrayList<DataMap>();
        try {
            // 查询rows
            list = template.queryForList(builder, page, rows);
            // 查询total rows
            if (page >= 0) {
                queryResult.setTotal(template.queryForInt(builder.sql().getCountSql(), builder.sql().getParamsValue()));
            } else {
                queryResult.setTotal(list.size());
                queryResult.setPageRows(list.size());
            }
        } finally {
            template.close();
        }

        queryResult.setRows(list);

        return queryResult;
    }

    public static void save(DBDataSource dataSource, IPDB pdb) throws Exception {
        JdbcTemplate template = new JdbcTemplate(dataSource);
        try {
            template.save(pdb);
            template.commit();
        } finally {
            template.close();
        }
    }

    public static void delete(DBDataSource dataSource, Map<String, Object> params, String table) throws Exception {
        JdbcTemplate template = new JdbcTemplate(dataSource);
        try {
            template.delete(params, table);
            template.commit();
        } finally {
            template.close();
        }
    }
}
