package com.metaui.core.datasource.db;

import com.metaui.core.datasource.DataMap;
import com.metaui.core.datasource.db.object.DBColumn;
import com.metaui.core.datasource.db.object.DBSchema;
import com.metaui.core.datasource.db.object.DBTable;
import com.metaui.core.datasource.db.sql.SqlBuilder;
import com.metaui.core.datasource.db.util.JdbcTemplate;
import com.metaui.core.util.Callback;

import java.util.ArrayList;
import java.util.List;

/**
 * 两个数据库之间复制
 *
 * @author wei_jc
 * @since 1.0
 */
public class DBCopy {
    private DBDataSource source;
    private DBDataSource target;

    private List<DataMap> dataMaps = new ArrayList<DataMap>();

    public DBCopy(DBDataSource source, DBDataSource target) throws Exception {
        this.source = source;
        this.target = target;

        // 查询源数据库中所有表
        /*List<DBTable> sourceTables = getSourceTables();
        DBSchema targetSchema = target.getDbConnection().getSchema();
        for (DBTable sourceTable : sourceTables) {
            DataMap dataMap = new DataMap();
            dataMap.put("sourceTableName", sourceTable.getName());
            dataMap.put("sourceTableComment", sourceTable.getComment());
            dataMap.put("sourceNumRows", sourceTable.getNumRows());

            final DBTable targetTable = targetSchema.getTable(sourceTable.getName());
            if (targetTable != null) {
                dataMap.put("targetNumRows", targetTable.getNumRows());
                dataMap.put("isImported", "false");
                dataMap.put("importedRows", 0); // 已导入行

                final List<DBColumn> columns = targetTable.getColumns();
                System.out.println(columns);

                // 查询数据
                String sql = "select * from " + sourceTable.getName();
                JdbcTemplate template = new JdbcTemplate(source);
                final JdbcTemplate targetTemplate = new JdbcTemplate(target);
                int count = 0;
                template.query(sql, new Callback<DataMap>() {
                    @Override
                    public void call(DataMap dataMap, Object... obj) throws Exception {
                        System.out.println(dataMap);
                        List<Object> values = new ArrayList<Object>();
                        for (DBColumn column : columns) {
                            values.add(dataMap.get(column.getName()));
                        }
                        // 插入
                        String sql = SqlBuilder.create().insert(targetTable.getName(), columns).build();
                        targetTemplate.save(sql, values);
//                        count++;
                    }
                }, 0, 0);
            }
            break;
        }*/
    }

    public void copyTable(String tableName) throws Exception {
        copyTable(tableName, "");
    }

    public void copyTable(String tableName, String appendSql) throws Exception {
        final DBTable sourceTable = source.getDbConnection().getSchema().getTable(tableName);
        if (sourceTable == null) {
            throw new Exception("表【" + tableName + "】不存在！");
        }
        DBSchema targetSchema = target.getDbConnection().getSchema();
        final DBTable targetTable = targetSchema.getTable(sourceTable.getName());
        if (targetTable != null) {
            final List<DBColumn> columns = targetTable.getColumns();
            System.out.println(columns);

            // 查询数据
            String sql = "select * from " + sourceTable.getName() + " " + appendSql;
            System.out.println(sql);
            JdbcTemplate template = new JdbcTemplate(source);
            final JdbcTemplate targetTemplate = new JdbcTemplate(target);
            template.query(sql, new Callback<DataMap, Void>() {
                @Override
                public Void call(DataMap dataMap, Object... obj) throws Exception {
                    System.out.println(dataMap);
                    List<Object> values = new ArrayList<Object>();
                    for (DBColumn column : columns) {
                        values.add(dataMap.get(column.getName()));
                    }
                    // 插入
                    String sql = SqlBuilder.create().insert(targetTable.getName(), columns).build();
                    targetTemplate.save(sql, values);

                    return null;
                }
            }, 0, 0);
            // 提交
            targetTemplate.commit();
        }
    }

    public void copyAllData() throws Exception {
        // 查询源数据库中所有表
        List<DBTable> sourceTables = getSourceTables();

        // 查询目标数据库所有表
        DBSchema targetSchema = target.getDbConnection().getSchema();
        for (DBTable sourceTable : sourceTables) {
            System.out.println(sourceTable);
            final DBTable targetTable = targetSchema.getTable(sourceTable.getName());
            if (targetTable != null) {
                final List<DBColumn> columns = targetTable.getColumns();
                System.out.println(columns);

                // 查询数据
                String sql = "select * from " + sourceTable.getName();
                JdbcTemplate template = new JdbcTemplate(source);
                template.query(sql, new Callback<DataMap, Void>() {
                    @Override
                    public Void call(DataMap dataMap, Object... obj) throws Exception {
                        System.out.println(dataMap);
                        List<Object> values = new ArrayList<Object>();
                        for (DBColumn column : columns) {
                            values.add(dataMap.get(column.getName()));
                        }
                        // 插入
                        String sql = SqlBuilder.create().insert(targetTable.getName(), columns).build();
                        JdbcTemplate.save(target, sql, values);

                        return null;
                    }
                }, 0, 0);
            }
            break;
        }
    }

    public List<DBTable> getSourceTables() throws Exception {
        return source.getDbConnection().getSchema().getTables();
    }
}
