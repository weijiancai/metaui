package com.metaui.core.datasource.db.object;

import com.metaui.core.loader.ILoader;

import java.util.List;

/**
 * 数据库加载器
 *
 * @author wei_jc
 * @version 0.0.1
 */
public interface DBLoader extends ILoader {
    void load() throws Exception;

    List<DBUser> loadUsers() throws Exception;

    List<DBObject> loadPrivileges();

    List<DBObject> loadCharsets();

    List<DBSchema> loadSchemas() throws Exception;

    List<DBIndex> loadIndexes(DBSchema schema) throws Exception;

    List<DBIndex> loadIndexes(DBSchema schema, DBTable table) throws Exception;

    List<DBTrigger> loadTriggers(DBSchema schema) throws Exception;

    List<DBTrigger> loadTriggers(DBSchema schema, DBTable table) throws Exception;

    List<DBProcedure> loadProcedures(DBSchema schema);

    List<DBFunction> loadFunctions(DBSchema schema);

    List<DBTable> loadTables(DBSchema schema) throws Exception;

    List<DBView> loadViews(DBSchema schema) throws Exception;

    List<DBColumn> loadColumns(DBDataset table) throws Exception;

    List<DBConstraint> loadConstraint(DBDataset table) throws Exception;

    List<DBConstraint> loadFkConstraints(DBSchema schema) throws Exception;

    DBTable getTable(String tableName) throws Exception;

    DBColumn getColumn(String tableName, String columnName) throws Exception;

    void deleteIndex(String tableName, String indexName) throws Exception;

    /**
     * 删除表，如果此表有外键约束，则删除此表关联的外键约束
     * @param tableName 表名
     * @throws Exception
     */
    void dropTable(String tableName) throws Exception;

    /**
     * 删除外键约束
     *
     * @param tableName 表名
     * @param referenceName 外键约束名
     * @throws Exception
     */
    void dropForeignKey(String tableName, String referenceName) throws Exception;

    List<DBConstraint> getExportedKeys(String table) throws Exception;

    void updateColumnNullable(String table, String column, boolean nullable) throws Exception;

    /**
     * 重命名表
     *
     * @param oldName 旧表名
     * @param newName 新表名
     * @throws Exception
     */
    void renameTable(String oldName, String newName) throws Exception;

    /**
     * 搜索数据库对象
     *
     * @param value 对象名
     * @param schemas schema
     * @param filter 过滤器，例如Table View Column等，以逗号分隔
     * @return
     */
    List<DBObject> search(String value, String[] schemas, String filter) throws Exception;
}
