package com.meteorite.core.datasource.ftp;

import com.metaui.core.datasource.ftp.FtpDataSource;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class FtpDataSourceTest {

    @Test public void testListFiles() throws Exception {
        FtpDataSource ds = FtpDataSource.getInstance("115.29.163.55", "wei_jc", "wjcectong2013#");
        ds.load();
    }
}