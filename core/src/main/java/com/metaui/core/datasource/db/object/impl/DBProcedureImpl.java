package com.metaui.core.datasource.db.object.impl;

import com.metaui.core.datasource.db.object.DBProcedure;
import com.metaui.core.datasource.db.object.enums.DBMethodType;
import com.metaui.core.datasource.db.object.enums.DBObjectType;

/**
 * 存储过程实现类
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class DBProcedureImpl extends DBMethodImpl implements DBProcedure {
    public DBProcedureImpl() {
        setObjectType(DBObjectType.PROCEDURE);
    }

    @Override
    public DBMethodType getMethodType() {
        return DBMethodType.PROCEDURE;
    }
}
