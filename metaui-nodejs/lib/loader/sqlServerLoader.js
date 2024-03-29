var Connection = require('tedious').Connection;
var Request = require('tedious').Request;
var async = require('async');
var underscore = require('underscore');
var util = require('../util');
var sqlBuilder = require('../sqlBuilder');

var config, connection, _dataSource;

function setDataSource(dataSource) {
    _dataSource = dataSource;
    config = {
        userName: dataSource.user,
        password: dataSource.password,
        server: dataSource.host,
        // If you're on Windows Azure, you will need this:
        options: {
            encrypt: true,
            port: dataSource.port,
            database: dataSource.database,
            connectTimeout: 25000,
            requestTimeout: 25000,
            tdsVersion: '7_3_A'
        }
    };
    /*connection = new Connection(config);
    connection.on('connect', function(err) {
            console.log(err);
            console.log('connected.......')
        }
    );*/
}

function query(sql, callback) {
    console.log(sql);
    if('T' === _dataSource.isVpn) {
        vpnQuery(sql, callback);
        return;
    }
    connection = new Connection(config);
    connection.on('connect', function(err) {
            if(err) {
                console.log(err);
                return;
            }
            var result = [];

            var request = new Request(sql, function(err, rowCount) {
                if (err) {
                    console.log(err);
                }
                callback(result);
            });

            request.on('row', function(columns) {
                var obj = {};
                columns.forEach(function(column) {
                    var key = column.metadata.colName;
                    obj[key]  = column.value ? column.value : '';
                });
                result.push(obj);
            });

            connection.execSql(request);
        }
    ).on('error', function(err) {
            console.log('Error: \n');
            console.log(err);
        }
    );

    //connection.close();
}

function queryByBuilder(builder, start, length, callback, tableName, pkColName) {
    queryByPage(builder.build(), start, length, callback, tableName, pkColName);
}

function queryByPage(sql, start, length, callback, tableName, pkColName, orderBy) {
    sql = sql.toLowerCase();
    if(_dataSource.isVpn) {
        vpnQueryByPage(sql, start, length, callback, tableName, pkColName, orderBy);
        return;
    }

    var countSql = getCountSql(sql);

    async.parallel({
        totalCount: function(callback) {
            if(!tableName || !pkColName) {
                callback(null, length);
                return;
            }
            console.log(countSql);
            connection.on('connect', function(err) {
                    if(err) {
                        console.log(err);
                        return;
                    }
                    var result = [];

                    var request = new Request(sql, function(err, rowCount) {
                        if (err) {
                            console.log(err);
                        }
                        callback(null, result[0].count);
                    });

                    request.on('row', function(columns) {
                        var obj = {};
                        columns.forEach(function(column) {
                            var key = column.metadata.colName;
                            obj[key]  = column.value ? column.value : '';
                        });
                        result.push(obj);
                    });

                    connection.execSql(request);
                }
            );
        },
        rows: function(callback) {
            sql = getPageSql(sql, tableName, pkColName, start, length, orderBy);
            console.log(sql);
            connection = new Connection(config);
            connection.on('connect', function(err) {
                    if(err) {
                        console.log(err);
                        return;
                    }
                    var result = [];

                    var request = new Request(sql, function(err, rowCount) {
                        if (err) {
                            console.log(err);
                        }
                        callback(null, result);
                    });

                    request.on('row', function(columns) {
                        var obj = {};
                        columns.forEach(function(column) {
                            var key = column.metadata.colName.toLowerCase();
                            var value = column.value;

                            if(value && underscore._.isDate(value)) {
                                obj[key] = util.utilDate.format(value, 'yyyy-MM-dd hh:mm:ss');
                            } else {
                                obj[key]  = value ? value : '';
                            }
                        });
                        result.push(obj);
                    });

                    connection.execSql(request);
                }
            );
        }
    }, function(err, results) {
        var result = {draw: 1, recordsTotal: results.totalCount, recordsFiltered: results.totalCount, data: results.rows};
        callback(result);
    })
}

function getCountSql(sql) {
    var idx = sql.lastIndexOf('from');
    return 'select count(1) count ' + sql.substr(idx);
}

function getPageSql(sql_, tableName, pkColName, start, length, orderBy) {
    var sql;
    if(tableName && pkColName) {
        sql = "select top %length * from (select row_number() over(order by %pkColName) as n, * from (%sql) tt) t where t.n > %start";
        sql = util.utilString.replaceAll(sql, '%length', length);
        sql = util.utilString.replaceAll(sql, '%pkColName', pkColName);
        sql = util.utilString.replaceAll(sql, '%sql', sql_);
        sql = util.utilString.replaceAll(sql, '%start', start);
        if(orderBy) {
            sql += " order by " + orderBy;
        }
    } else {
        sql = "SELECT top " + length + " * FROM (" + sql_ + ") t";
    }

    return sql;
}

function vpnQuery(sql, callback) {
    console.log(sql);
    var request = require('request');
    request.post({url: 'http://localhost:8080/db', form: {host: _dataSource.host, port: _dataSource.port, user: _dataSource.user, password: _dataSource.password, sql: sql, database: _dataSource.database}}, function (error, response, body) {
        if (!error && response.statusCode == 200) {
            callback(JSON.parse(body));
        } else {
            console.log(error);
            callback();
        }
    });
}

function vpnQueryByPage(sql, start, length, callback, tableName, pkColName, orderBy) {
    var countSql = getCountSql(sql, tableName);
    console.log(countSql);
    var pageSql = getPageSql(sql, tableName, pkColName, start, length, orderBy);
    console.log(pageSql);
    var request = require('request');
    request.post({url: 'http://localhost:8080/db', form: {host: _dataSource.host, port: _dataSource.port, user: _dataSource.user, password: _dataSource.password, sql: pageSql, countSql: countSql, database: _dataSource.database}}, function (error, response, body) {
        if (!error && response.statusCode == 200) {
            callback(JSON.parse(body));
        } else {
            console.log(error);
            callback({});
        }
    });
}

function getUsers(callback) {
    query(getUserSql(), callback);
}

function getPrivileges(callback) {
    query(getPrivilegesSql(), callback);
}

function getCharsets(callback) {
    query(getCharsetsSql(), callback);
}

function getSchemas(callback) {
    query(getSchemaSql(), callback);
}

function getTables(schema, callback) {
    query(getTableSql(schema), callback);
}

function getViews(schema, callback) {
    query(getViewSql(schema), callback);
}

function getFunctions(schema, callback) {
    query(getFunctionsSql(schema), callback);
}

function getProcedures(schema, callback) {
    query(getProceduresSql(schema), callback);
}

function getColumns(schema, table, callback) {
    query(getColumnSql(schema, table), callback);
}

function getConstraints(schema, table, callback) {
    query(getConstraintsSql(schema, table), callback);
}

function getIndexes(schema, table, callback) {
    query(getIndexesSql(schema, table), callback);
}

function getTriggers(schema, table, callback) {
    query(getTriggersSql(schema, table), callback);
}

function getParameters(schema, methodName, callback) {
    query(getParametersSql(schema, methodName), callback);
}

function getFKConstraintsColumns(schema,callback, conditions) {
    query(getFKConstraintsColumnsSql(schema, conditions), callback);
}

module.exports = {
    setDataSource: setDataSource,
    query: query,
    queryByPage: queryByPage,
    queryByBuilder: queryByBuilder,
    getUsers: getUsers,
    getPrivileges: getPrivileges,
    getCharsets: getCharsets,
    getSchemas: getSchemas,
    getTables: getTables,
    getViews: getViews,
    getFunctions: getFunctions,
    getProcedures: getProcedures,
    getColumns: getColumns,
    getConstraints: getConstraints,
    getIndexes: getIndexes,
    getTriggers: getTriggers,
    getParameters: getParameters,
    getFKConstraintsColumns: getFKConstraintsColumns,
    getSearchSql: getSearchSql
};

function getUserSql() {
    return "select\n" +
        "                name as name,\n" +
        "                name as displayName,\n" +
        "                'N' as isExpired,\n" +
        "                'N' as isLocked\n" +
        "            from sys.schemas\n" +
        "            order by schema_id asc";
}

function getPrivilegesSql() {
    return "select distinct\n" +
        "                permission_name as name,\n" +
        "                permission_name as displayName\n" +
        "            from fn_builtin_permissions(default)\n" +
        "            order by permission_name asc";
}

function getCharsetsSql() {
    return "select\n" +
        "                name as name,\n" +
        "                name as displayName,\n" +
        "                0 as maxLength\n" +
        "            from sys.syscharsets\n" +
        "            order by name asc";
}

function getSchemaSql() {
    return "select\n" +
        "                name as name,\n" +
        "                'N' as isPublic,\n" +
        "                (case when database_id > 3 then 'N' else 'Y' end) as isSystem\n" +
        "            from sys.databases\n" +
        "            order by name asc";
}

function getTableSql(schema) {
    return "select\n" +
        "                name name,\n" +
        "                (select top 1 convert(varchar, value) from [" + schema + "].sys.extended_properties where major_id = object_id) displayName,\n" +
        "                'N' as isTemporary\n" +
        "            from  [" + schema + "].sys.TABLES\n" +
        "            order by name asc";
}

function getViewSql(schema) {
    return "select\n" +
        "                table_name as name,\n" +
        "                table_name as displayName,\n" +
        "                null as viewTypeOwner,\n" +
        "                null as viewType,\n" +
        "                'N' as isSystemView\n" +
        "            from [" + schema + "].INFORMATION_SCHEMA.views\n" +
        "            order by table_name asc";
}

function getProceduresSql(schema) {
    return "select\n" +
        "                ROUTINE_NAME as name,\n" +
        "                ROUTINE_NAME as displayName,\n" +
        "                'Y' as isValid,\n" +
        "                'N' as isDebug,\n" +
        "                'N' as isDeterministic\n" +
        "            from [" + schema + "].INFORMATION_SCHEMA.ROUTINES\n" +
        "            where\n" +
        "                ROUTINE_CATALOG = '" + schema + "' and\n" +
        "                ROUTINE_TYPE = 'PROCEDURE'\n" +
        "            order by ROUTINE_NAME asc";
}

function getColumnSql(schema, table) {
    return "select\n" +
        "                col.name as name,\n" +
        "                (select top 1 convert(varchar, value) from [" + schema + "].sys.extended_properties where major_id = col.object_id and minor_id = col.column_id) displayName,\n" +
        "                col.column_id as position,\n" +
        "                (select top 1 name from [" + schema + "].sys.types where system_type_id = col.system_type_id) as dataType,\n" +
        "                null as dataTypeOwner,\n" +
        "                null as dataTypePackage,\n" +
        "                col.max_length as maxLength,\n" +
        "                col.PRECISION as numPrecision,\n" +
        "                col.SCALE as numScale,\n" +
        "                (case when col.IS_NULLABLE = 0 then 'N' else 'Y' end) as isNullable,\n" +
        "                'N' as isHidden,\n" +
        "                ISNULL((SELECT top 1 'Y' FROM sysobjects where xtype='PK' and  parent_obj=col.object_id and name in (SELECT name  FROM sysindexes WHERE indid in(SELECT indid FROM sysindexkeys WHERE id = col.object_id AND colid=col.column_id))), 'N') as isPrimaryKey,\n" +
        "                isnull((select top 1 'Y' from SYSFOREIGNKEYS where fkeyid=col.object_id and fkey=col.column_id), 'N') as isForeignKey\n" +
        "            from [" + schema + "].sys.columns col\n" +
        "            where\n" +
        "                col.object_id = (select object_id from [" + schema + "].sys.tables where name='" + table + "') or \n" +
        "                col.object_id = (select object_id from [" + schema + "].sys.views where name='" + table + "')\n" +
        "            order by col.column_id asc";
}

function getFunctionsSql(schema) {
    return "select\n" +
        "                ROUTINE_NAME as name,\n" +
        "                ROUTINE_NAME as displayName,\n" +
        "                'Y' as isValid,\n" +
        "                'N' as isDebug,\n" +
        "                'N' as isDeterministic\n" +
        "            from [" + schema + "].INFORMATION_SCHEMA.ROUTINES\n" +
        "            where\n" +
        "                ROUTINE_CATALOG = '" + schema + "' and\n" +
        "                ROUTINE_TYPE = 'FUNCTION'\n" +
        "            order by ROUTINE_NAME asc";
}

function getParametersSql(schema, methodName) {
    return "select\n" +
        "                a.PARAMETER_NAME as name,\n" +
        "                a.PARAMETER_NAME as displayName,\n" +
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
        "            from [" + schema + "].INFORMATION_SCHEMA.PARAMETERS a, [" + schema + "].INFORMATION_SCHEMA.ROUTINES b\n" +
        "            where\n" +
        "                a.SPECIFIC_CATALOG = b.ROUTINE_CATALOG and\n" +
        "                a.SPECIFIC_NAME = b.SPECIFIC_NAME and\n" +
        "                a.SPECIFIC_CATALOG = '" + schema + "' and\n" +
        "                a.SPECIFIC_NAME = '" + methodName + "'\n" +
        "            order by\n" +
        "                a.SPECIFIC_NAME,\n" +
        "                a.ORDINAL_POSITION asc";
}

function getConstraintsSql(schema, table) {
    return "select\n" +
        "                a.constraint_name as name,\n" +
        "                a.CONSTRAINT_TYPE constraintType,\n" +
        "                '' as fkConstraintOwner,\n" +
        "                '' as fkConstraintName,\n" +
        "                'Y' as isEnabled,\n" +
        "                null as checkCondition\n" +
        "                from\n" +
        "                INFORMATION_SCHEMA.TABLE_CONSTRAINTS a,\n" +
        "                INFORMATION_SCHEMA.KEY_COLUMN_USAGE b\n" +
        "            where\n" +
        "                a.table_name = b.table_name and\n" +
        "                a.table_catalog = b.table_catalog and\n" +
        "                a.constraint_name = b.constraint_name and\n" +
        "                a.TABLE_catalog = '" + schema + "' and\n" +
        "                a.table_name = '" + table + "'\n" +
        "            order by\n" +
        "                a.TABLE_NAME,\n" +
        "                a.CONSTRAINT_NAME asc";
}

function getIndexesSql(schema, table) {
    return "select\n" +
        "                name as name,\n" +
        "                (select name from dbo.sysobjects where id=object_id) as tableName,\n" +
        "                '' as columnName,\n" +
        "                (case when is_unique = 0 then 'N' else 'Y' end) as isUnique,\n" +
        "                'Y' as isAsc,\n" +
        "                (case when is_disabled = 0 then 'N' else 'Y' end) as isValid\n" +
        "            from [" + schema + "].sys.indexes\n" +
        "            where\n" +
        "                name is not null and\n" +
        "                object_id = (select id from [" + schema + "].dbo.sysobjects where name='" + table + "' and xtype='U')\n" +
        "            order by name asc";
}

function getTriggersSql(schema, table) {
    return "select\n" +
        "                t.name name,\n" +
        "                '' as triggerType,\n" +
        "                '' as triggeringEvent,\n" +
        "                'Y' as isEnabled,\n" +
        "                'Y' as isValid,\n" +
        "                'N' as isDebug,\n" +
        "                'Y' as isForEachRow\n" +
        "            from [" + schema + "].sys.TRIGGERS t\n" +
        "            where\n" +
        "               object_id = (select id from [" + schema + "].dbo.sysobjects where name='" + table + "' and xtype='U')\n " +
        "            order by parent_id, name asc";
}

function getFKConstraintsColumnsSql(schema, conditions) {
    var sql = "SELECT\n" +
        "  (select name from dbo.sysobjects where id=constid) constraint_name,\n" +
        "  (select name from dbo.sysobjects where id=fkeyid) table_name,\n" +
        "  (select name from sys.columns where OBJECT_ID= fkeyid and column_id=fkey) column_name,\n" +
        "  (select name from dbo.sysobjects where id=rkeyid) referenced_table_name,\n" +
        "  (select name from sys.columns where OBJECT_ID= rkeyid and column_id=rkey) referenced_column_name\n" +
        "FROM [" + schema + "].sys.SYSFOREIGNKEYS b";

    if(conditions) {
        var tableName = conditions.tableName;
        var columnName = conditions.columnName;
        var isAnd;
        var where = ' WHERE ';
        if(tableName) {
            where += "fkeyid = (select id from dbo.sysobjects where name='" + tableName + "')";
            isAnd = true;
        }
        if(columnName) {
            if(isAnd) {
                where += ' AND ';
            }
            where += "fkey = (select column_id from sys.columns where OBJECT_ID= fkeyid and name='" + columnName + "')";
            isAnd = true;
        }

        sql += where;
    }

    return sql;
}

function getSearchSql(value, schemas, filter) {
    var sql = '';
    for(var i = 0; i < schemas.length; i++) {
        var schema = schemas[i];
        var objSql = sqlBuilder.create()
            .query("'" + schema.name + "' id, name, '' comment, (case when (type in ('AF','FN', 'FS', 'FT', 'IF', 'TF')) then 'FUNCTION' when (type in ('F', 'PK', 'UQ')) then 'CONSTRAINT' when (type in ('IT', 'S', 'U', 'TT')) then 'TABLE' when (type in ('P', 'PC', 'X')) then 'PROCEDURE' when (type in ('TA', 'TR')) then 'TRIGGER' when type='V' then 'VIEW' else '' end) dbType")
            .from("[" + schema.name + "].sys.objects");
        if(filter) {
            objSql.where();
            var array = filter.split(',');
            if(underscore._.indexOf(array, 'TABLE') > -1) {
                objSql.ands("type in ('IT', 'S', 'U', 'TT')");
            }
            if(underscore._.indexOf(array, 'VIEW') > -1) {
                objSql.ands("type in ('V')");
            }
            if(underscore._.indexOf(array, 'CONSTRAINT') > -1 || underscore._.indexOf(array, 'INDEX') > -1) {
                objSql.ands("type in ('F', 'PK', 'UQ')");
            }
            if(underscore._.indexOf(array, 'TRIGGER') > -1) {
                objSql.ands("type in ('TA', 'TR')");
            }
            if(underscore._.indexOf(array, 'PROCEDURE') > -1) {
                objSql.ands("type in ('P', 'PC', 'X')");
            }
            if(underscore._.indexOf(array, 'FUNCTION') > -1) {
                objSql.ands("type in ('AF','FN', 'FS', 'FT', 'IF', 'TF')");
            }
        } else {
            objSql.where("type in ('AF', 'F', 'FN', 'FS', 'FT', 'IF', 'IT', 'P', 'PC', 'PK', 'S', 'U', 'TA', 'TR', 'TF', 'TT', 'UQ', 'V', 'X')");
        }
        objSql.like("name", value);
        sql += objSql.build();

        if(!filter || (filter && underscore._.indexOf(filter.split(','), 'COLUMN') > -1)) {
            var columnSql = sqlBuilder.create()
                .query("'" + schema.name + "'+'.'+(select name from dbo.sysobjects where id=col.object_id) id,col.name as name,(select top 1 convert(varchar, value) from [" + schema.name + "].sys.extended_properties where major_id = col.object_id and minor_id = col.column_id) comment, 'COLUMN' dbType")
                .from("[" + schema.name + "].sys.columns col")
                .where()
                .build();

            sql += '\n UNION ALL \n' + columnSql;
        }

        if(i < schemas.length - 1) {
            sql += '\n UNION ALL \n';
        }
    }
    return "SELECT * FROM (" + sql + ") t ORDER BY t.dbType";
}