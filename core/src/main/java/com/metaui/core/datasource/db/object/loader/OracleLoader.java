package com.metaui.core.datasource.db.object.loader;

import com.metaui.core.datasource.db.DatabaseType;
import com.metaui.core.datasource.db.object.impl.DBConnectionImpl;
import com.metaui.core.datasource.db.sql.SqlBuilder;
import com.metaui.core.util.UString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * SqlServer 加载器
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class OracleLoader extends BaseDBLoader {
    public OracleLoader(DBConnectionImpl conn) throws Exception {
        super(conn);
    }

    @Override
    protected String getUserSql() {
        return "select\n" +
                "                name as USER_NAME,\n" +
                "                'N' as IS_EXPIRED,\n" +
                "                'N' as IS_LOCKED\n" +
                "            from sys.schemas\n" +
                "            order by schema_id asc";
    }

    @Override
    protected String getPrivilegesSql() {
        return "select distinct\n" +
                "                permission_name as PRIVILEGE_NAME\n" +
                "            from fn_builtin_permissions(default)\n" +
                "            order by permission_name asc";
    }

    @Override
    protected String getCharsetsSql() {
        return "select\n" +
                "                name as CHARSET_NAME,\n" +
                "                0 as MAX_LENGTH\n" +
                "            from sys.syscharsets\n" +
                "            order by name asc";
    }

    @Override
    protected String getSchemaSql() {
        return "select\n" +
                "                name as SCHEMA_NAME,\n" +
                "                'N' as IS_PUBLIC,\n" +
                "                (case when database_id > 3 then 'N' else 'Y' end) as IS_SYSTEM\n" +
                "            from sys.databases\n" +
                "            order by name asc";
    }

    @Override
    protected String getTableSql() {
        return "select\n" +
                "                 TABLE_NAME,\n" +
                "                (select COMMENTS from USER_TAB_COMMENTS where USER_TAB_COMMENTS.TABLE_NAME=t.TABLE_NAME) TABLE_COMMENT,\n" +
                "                NUM_ROWS,\n" +
                "                'N' as IS_TEMPORARY\n" +
                "            from  user_tables t\n" +
                "            order by TABLE_NAME asc";
    }

    @Override
    protected String getViewSql() {
        return "select\n" +
                "                table_name as VIEW_NAME,\n" +
                "                null as VIEW_TYPE_OWNER,\n" +
                "                null as VIEW_TYPE,\n" +
                "                'N' as IS_SYSTEM_VIEW\n" +
                "            from [%s].INFORMATION_SCHEMA.views\n" +
                "            order by table_name asc";
    }

    @Override
    protected String getColumnSql() {
        return "select\n" +
                "                '%2$s' as DATASET_NAME,\n" +
                "                COLUMN_NAME,\n" +
                "                '' COLUMN_COMMENT,\n" +
                "                col.column_id as POSITION,\n" +
                "                data_type as DATA_TYPE_NAME,\n" +
                "                null as DATA_TYPE_OWNER,\n" +
                "                null as DATA_TYPE_PACKAGE,\n" +
                "                col.data_length as DATA_LENGTH,\n" +
                "                col.data_PRECISION as DATA_PRECISION,\n" +
                "                col.data_SCALE as DATA_SCALE,\n" +
                "                nullable as IS_NULLABLE,\n" +
                "                'N' as IS_HIDDEN,\n" +
                "                'N' as IS_PRIMARY_KEY,\n" +
                "                'N' as IS_FOREIGN_KEY\n" +
                "            from USER_TAB_COLUMNS col\n" +
                "            where\n" +
                "                col.table_name = '%2$s'\n" +
                "            order by col.COLUMN_NAME asc";
    }

    @Override
    protected String getConstraintsSql() {
        return "select\n" +
                "                a.TABLE_NAME as DATASET_NAME,\n" +
                "                a.constraint_name as CONSTRAINT_NAME,\n" +
                "                a.CONSTRAINT_TYPE,\n" +
                "                '' as FK_CONSTRAINT_OWNER,\n" +
                "                '' as FK_CONSTRAINT_NAME,\n" +
                "                'Y' as IS_ENABLED,\n" +
                "                null as CHECK_CONDITION\n" +
                "                from\n" +
                "                INFORMATION_SCHEMA.TABLE_CONSTRAINTS a,\n" +
                "                INFORMATION_SCHEMA.KEY_COLUMN_USAGE b\n" +
                "            where\n" +
                "                a.table_name = b.table_name and\n" +
                "                a.table_catalog = b.table_catalog and\n" +
                "                a.constraint_name = b.constraint_name and\n" +
                "                a.TABLE_catalog = '%1$s' and\n" +
                "                a.table_name = '%2$s'\n" +
                "            order by\n" +
                "                a.TABLE_NAME,\n" +
                "                a.CONSTRAINT_NAME asc";
    }

    @Override
    protected String getIndexesSql() {
        return "select\n" +
                "                name as INDEX_NAME,\n" +
                "                (select name from dbo.sysobjects where id=object_id) as TABLE_NAME,\n" +
                "                '' as COLUMN_NAME,\n" +
                "                (case when is_unique = 0 then 'N' else 'Y' end) as IS_UNIQUE,\n" +
                "                'Y' as IS_ASC,\n" +
                "                (case when is_disabled = 0 then 'N' else 'Y' end) as IS_VALID\n" +
                "            from [%1$s].sys.indexes\n" +
                "            where\n" +
                "                name is not null\n" +
                "            order by name asc";
    }

    @Override
    protected String getIndexesSql(String schema, String table) {
        return "select\n" +
                "                name as INDEX_NAME,\n" +
                "                (select name from dbo.sysobjects where id=object_id) as TABLE_NAME,\n" +
                "                '' as COLUMN_NAME,\n" +
                "                (case when is_unique = 0 then 'N' else 'Y' end) as IS_UNIQUE,\n" +
                "                'Y' as IS_ASC,\n" +
                "                (case when is_disabled = 0 then 'N' else 'Y' end) as IS_VALID\n" +
                "            from [" + schema + "].sys.indexes\n" +
                "            where\n" +
                "                name is not null and\n" +
                "                object_id = (select id from [" + schema + "].dbo.sysobjects where name='" + table + "' and xtype='U')\n" +
                "            order by name asc";
    }

    @Override
    protected String getIndexSql(String schema, String tableName, String indexName) {
        return null;
    }

    @Override
    protected String getTriggersSql() {
        return "select\n" +
                "                (select top 1 name from [%1$s].sys.tables where object_id = t.parent_id) as DATASET_NAME,\n" +
                "                t.name TRIGGER_NAME,\n" +
                "                '' as TRIGGER_TYPE,\n" +
                "                '' as TRIGGERING_EVENT,\n" +
                "                'Y' as IS_ENABLED,\n" +
                "                'Y' as IS_VALID,\n" +
                "                'N' as IS_DEBUG,\n" +
                "                'Y' as IS_FOR_EACH_ROW\n" +
                "            from [%1$s].sys.TRIGGERS t\n" +
                "            order by parent_id, name asc";
    }

    @Override
    protected String getTriggersSql(String schema, String table) {
        return "select\n" +
                "                (select top 1 name from [" + schema + "].sys.tables where object_id = t.parent_id) as DATASET_NAME,\n" +
                "                t.name TRIGGER_NAME,\n" +
                "                '' as TRIGGER_TYPE,\n" +
                "                '' as TRIGGERING_EVENT,\n" +
                "                'Y' as IS_ENABLED,\n" +
                "                'Y' as IS_VALID,\n" +
                "                'N' as IS_DEBUG,\n" +
                "                'Y' as IS_FOR_EACH_ROW\n" +
                "            from [" + schema + "].sys.TRIGGERS t\n" +
                "            where\n" +
                "               object_id = (select id from [" + schema + "].dbo.sysobjects where name='" + table + "' and xtype='U')\n " +
                "            order by parent_id, name asc";
    }

    @Override
    protected String getProceduresSql() {
        return "select\n" +
                "                ROUTINE_NAME as PROCEDURE_NAME,\n" +
                "                'Y' as IS_VALID,\n" +
                "                'N' as IS_DEBUG,\n" +
                "                'N' as IS_DETERMINISTIC\n" +
                "            from [%1$s].INFORMATION_SCHEMA.ROUTINES\n" +
                "            where\n" +
                "                ROUTINE_CATALOG = '%1$s' and\n" +
                "                ROUTINE_TYPE = 'PROCEDURE'\n" +
                "            order by ROUTINE_NAME asc";
    }

    @Override
    protected String getFunctionsSql() {
        return "select\n" +
                "                ROUTINE_NAME as FUNCTION_NAME,\n" +
                "                'Y' as IS_VALID,\n" +
                "                'N' as IS_DEBUG,\n" +
                "                'N' as IS_DETERMINISTIC\n" +
                "            from [%1$s].INFORMATION_SCHEMA.ROUTINES\n" +
                "            where\n" +
                "                ROUTINE_CATALOG = '%1$s' and\n" +
                "                ROUTINE_TYPE = 'FUNCTION'\n" +
                "            order by ROUTINE_NAME asc";
    }

    @Override
    protected String getParametersSql() {
        return "select\n" +
                "                a.PARAMETER_NAME as ARGUMENT_NAME,\n" +
                "                null as PROGRAM_NAME,\n" +
                "                a.SPECIFIC_NAME as METHOD_NAME,\n" +
                "                b.ROUTINE_TYPE as METHOD_TYPE,\n" +
                "                0 as OVERLOAD,\n" +
                "                a.ORDINAL_POSITION as POSITION,\n" +
                "                a.ORDINAL_POSITION as SEQUENCE,\n" +
                "                (case when a.PARAMETER_MODE is null then 'OUT' else a.PARAMETER_MODE end) as IN_OUT,\n" +
                "                null as DATA_TYPE_OWNER,\n" +
                "                null as DATA_TYPE_PACKAGE,\n" +
                "                a.DATA_TYPE as DATA_TYPE_NAME,\n" +
                "                a.CHARACTER_MAXIMUM_LENGTH  as DATA_LENGTH,\n" +
                "                a.NUMERIC_PRECISION as DATA_PRECISION,\n" +
                "                a.NUMERIC_SCALE as DATA_SCALE\n" +
                "            from [%1$s].INFORMATION_SCHEMA.PARAMETERS a, [%1$s].INFORMATION_SCHEMA.ROUTINES b\n" +
                "            where\n" +
                "                a.SPECIFIC_CATALOG = b.ROUTINE_CATALOG and\n" +
                "                a.SPECIFIC_NAME = b.SPECIFIC_NAME and\n" +
                "                a.SPECIFIC_CATALOG = '%1$s'\n" +
                "            order by\n" +
                "                a.SPECIFIC_NAME,\n" +
                "                a.ORDINAL_POSITION asc";
    }

    @Override
    protected String getFKConstraintsColumnsSql() {
        return "SELECT\n" +
                "  (select name from dbo.sysobjects where id=constid) constraint_name,\n" +
                "  (select name from dbo.sysobjects where id=fkeyid) table_name,\n" +
                "  (select name from sys.columns where OBJECT_ID= fkeyid and column_id=fkey) column_name,\n" +
                "  (select name from dbo.sysobjects where id=rkeyid) referenced_table_name,\n" +
                "  (select name from sys.columns where OBJECT_ID= rkeyid and column_id=rkey) referenced_column_name\n" +
                "FROM SYSFOREIGNKEYS b";
    }

    @Override
    protected String getSearchSql(String value, String[] schemas, String filter) {
        String sql = "";
        for(int i = 0; i < schemas.length; i++) {
            String schema = schemas[i];
            SqlBuilder objSql = SqlBuilder.create()
                    .query("'" + schema + "' id, name, '' comment, (case when (type in ('AF','FN', 'FS', 'FT', 'IF', 'TF')) then 'FUNCTION' when (type in ('F', 'PK', 'UQ')) then 'CONSTRAINT' when (type in ('IT', 'S', 'U', 'TT')) then 'TABLE' when (type in ('P', 'PC', 'X')) then 'PROCEDURE' when (type in ('TA', 'TR')) then 'TRIGGER' when type='V' then 'VIEW' else '' end) dbType")
                    .from("[" + schema + "].sys.objects");
            if(filter != null) {
//                objSql.where();
                String[] array = filter.split(",");
                List<String> list = new ArrayList<String>();
                if(Arrays.binarySearch(array, "TABLE") > -1) {
                    list.add("type in ('IT', 'S', 'U', 'TT')");
                }
                if(Arrays.binarySearch(array, "VIEW") > -1) {
                    list.add("type in ('V')");
                }
                if(Arrays.binarySearch(array, "TRIGGER") > -1) {
                    list.add("type in ('TA', 'TR')");
                }
                if(Arrays.binarySearch(array, "PROCEDURE") > -1) {
                    list.add("type in ('P', 'PC', 'X')");
                }
                if(Arrays.binarySearch(array, "FUNCTION") > -1) {
                    list.add("type in ('AF','FN', 'FS', 'FT', 'IF', 'TF')");
                }
                if(Arrays.binarySearch(array, "CONSTRAINT") > -1 || Arrays.binarySearch(array, "INDEX") > -1) {
                    list.add("type in ('F', 'PK', 'UQ')");
                }
                if (list.size() > 0) {
                    objSql.and(String.format("(%s)", UString.convert(list, " OR ")));
                }
            } else {
                objSql.where("type in ('IT', 'S', 'U', 'TT', 'V', 'TA', 'TR', 'P', 'PC', 'X', 'AF', 'FN', 'FS', 'FT', 'IF', 'TF', 'F', 'PK', 'UQ')");
            }
            objSql.like("name", value);
            sql += objSql.build(DatabaseType.SQLSERVER);

            if(filter == null || (Arrays.binarySearch(filter.split(","), "'COLUMN'") > -1)) {
                String columnSql = SqlBuilder.create()
                        .query("'" + schema + "'+'.'+(select name from dbo.sysobjects where id=col.object_id) id,col.name as name,(select top 1 convert(varchar, value) from [" + schema + "].sys.extended_properties where major_id = col.object_id and minor_id = col.column_id) comment, 'COLUMN' dbType")
                        .from("[" + schema + "].sys.columns col")
                        .like("col.name", value)
                        .build(DatabaseType.SQLSERVER);

                sql += "\n UNION ALL \n" + columnSql;
            }

            if(i < schemas.length - 1) {
                sql += "\n UNION ALL \n";
            }
        }
        return "SELECT * FROM (" + sql + ") t ORDER BY t.dbType desc";
    }
}
