package com.metaui.db;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author wei_jc
 * @since 1.0.0
 * 2017/1/2.
 */
public class DBTest {
    private DB db;

    @Test
    public void testAdd() throws Exception {
        // 添加数据源
        db = DB.add("wy", "jdbc:sqlserver://weiyi1998.com:18888;databaseName=yhbis", "sa", "123!@#qwe");
//        db = DB.add("lhcbs", "jdbc:sqlserver://123.56.42.198:1433;databaseName=lhcbs", "ectwms", "ectongs2015$");
//        assertEquals("lhcbs", db.getSchema());
        assertEquals("yhbis", db.getSchema());

    }

    @Test public void testUse() {
//        DB.use();
        db.table("yhbis.dbo.wm_op_order", "")
    }
}