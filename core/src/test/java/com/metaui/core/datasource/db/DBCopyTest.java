package com.metaui.core.datasource.db;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author wei_jc
 * @since 1.0
 */
public class DBCopyTest {

    @Test
    public void testCopyAllData() throws Exception {
        DBDataSource source = new DBDataSource("ecargo", JdbcDrivers.ORACLE, "jdbc:oracle:thin:@192.168.3.10:1521:orcl", "ecargo", "ecargo", null);
        DBDataSource target = new DBDataSource("test", JdbcDrivers.ORACLE, "jdbc:oracle:thin:@192.168.3.10:1521:orcl", "ecargo", "ecargo", null);
        DBCopy db = new DBCopy(source, target);
        db.copyAllData();
    }
}