package com.metaui.core.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * IO工具类
 *
 * @author wei_jc
 * @version 1.0.0
 */
public class UIO {
    public static enum FROM {
        /**
         * ClassPath，类路径
         */
        CP,
        /**
         * FileSystem，文件系统
         */
        FS,
        /**
         * URL, HTTP URL
         */
        URL
    }

    /**
     * 获得InputStream
     *
     * @param path 文件路径
     * @param from 文件来源
     * @return 返回InputStream
     * @throws FileNotFoundException
     */
    public static InputStream getInputStream(String path, FROM from) throws IOException {
        if(FROM.CP == from) {
            return UIO.class.getResourceAsStream(path);
        } else if (FROM.FS == from) {
            return new FileInputStream(path);
        } else if (FROM.URL == from) {
            HttpURLConnection httpConn = (HttpURLConnection) new URL(path).openConnection();
            return httpConn.getInputStream();
        }

        return null;
    }

    public static void main(String[] args) {
        try {
            Runtime.getRuntime().exec("C:\\Users\\wei_jc\\node_modules\\.bin\\jsdoc.cmd");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
