package com.metaui.core.util;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;
import org.hsqldb.Server;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wei_jc
 * @version 1.0
 */
public class HSqlDBServer {
    private static Logger log = Logger.getLogger(HSqlDBServer.class);
    private static HSqlDBServer instance;

    private Server server;
    private Map<String, String> filePathMap = new HashMap<String, String>();

    private HSqlDBServer() {
        server = new Server();
        server.setTrace(true);
    }

    public static HSqlDBServer getInstance() {
        if (instance == null) {
            instance = new HSqlDBServer();
        }

        return instance;
    }

    public void addDbFile(String dbName, String dbFilePath) {
        filePathMap.put(dbName, dbFilePath);
    }

    /**
     * 启动数据库
     */
    public void start() {
        log.info("启动内存数据库HsqlDB......");
        Server.main(new String[]{
                "-help"
        });

        int i = 0;
        for (Map.Entry<String, String> entry : filePathMap.entrySet()) {
            System.out.println(String.format("启动数据库【%s】 : %s", entry.getKey(), entry.getValue()));

            server.setDatabaseName(i, entry.getKey());
            server.setDatabasePath(i, entry.getValue());

            i++;
        }

        if (i > 0) {
            server.start();
            log.info("启动完成.");
        } else {
            log.info("没有要启动的数据库文件!");
        }
    }

    /**
     * 停止数据库
     */
    public void stop() {
        server.stop();
    }
}
