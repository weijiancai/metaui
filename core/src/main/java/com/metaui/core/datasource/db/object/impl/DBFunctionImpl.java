package com.metaui.core.datasource.db.object.impl;

import com.metaui.core.datasource.db.object.DBArgument;
import com.metaui.core.datasource.db.object.DBFunction;
import com.metaui.core.datasource.db.object.enums.DBMethodType;
import com.metaui.core.datasource.db.object.enums.DBObjectType;

/**
 * 函数实现类
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class DBFunctionImpl extends DBMethodImpl implements DBFunction {
    private DBArgument returnArgument;

    public DBFunctionImpl() {
        setObjectType(DBObjectType.FUNCTION);
    }

    @Override
    public DBMethodType getMethodType() {
        return DBMethodType.FUNCTION;
    }

    @Override
    public DBArgument getReturnArgument() {
        return returnArgument;
    }

    public void setReturnArgument(DBArgument returnArgument) {
        this.returnArgument = returnArgument;
    }
}
