package com.metaui.core.util.file;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class CutTxtFile {
    /**
     * 切割行数，默认一百万行
     */
    private long cutRows = 1000000;
    /**
     * 输入/输出缓冲区大小，默认为5M
     */
    public static int CACHE_SIZE = 10 * 1024 * 1024;

    // 文件路径
    private String filePath;
    // 字符集
    private String charset = "UTF-8";


    private long count = 0; // 行数/字符数计数器
    private int fileSerialNum = 1; // 文件流水号
    /**
     * 文件大小，默认100M
     */
    private long fileSize = 100 * 1024 * 1024;

    /**
     * 替换字符串map
     */
    private Map<String, String> replaceMap = new HashMap<String, String>();

    public CutTxtFile(String filePath) {
        this.filePath = filePath;
    }

    public void setCutRows(long cutRows) {
        this.cutRows = cutRows;
    }

    /**
     * 设置切割文件大小，单位：M
     *
     * @param fileSize 文件大小
     */
    public void setFileSize(long fileSize) {
        this.fileSize = fileSize * 1024 * 1024;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public Map<String, String> getReplaceMap() {
        return replaceMap;
    }

    public void setReplaceMap(Map<String, String> replaceMap) {
        this.replaceMap = replaceMap;
    }

    /**
     * 按行切割文件，切割后的文件已数字序列命名，存储在与要切割的文件同一个目录
     */
    public void cutByLine() throws IOException {
        BufferedReader br = null;
        PrintWriter pw = null;
        try {
            br = getReader();
            pw = getWriter(fileSerialNum);
            String line;
            while ((line = br.readLine()) != null) {
                // 替换字符串
                for (Map.Entry<String, String> entry : replaceMap.entrySet()) {
                    line = line.replace(entry.getKey(), entry.getValue());
                }
                // 计算行
                if (count++ >= cutRows) {
                    pw.write(line + "\n");
                    pw.flush();
                    pw.close();
                    count = 0;
                    pw = getWriter(++fileSerialNum);
                }
                pw.write(line + "\n");
            }
            pw.flush();
        } finally {
            if (br != null) {
                br.close();
            }
            if (pw != null) {
                pw.close();
            }
        }
    }

    private BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(new FileInputStream(filePath), charset), CACHE_SIZE);
    }

    private PrintWriter getWriter(int fileSerialNum) throws IOException {
        return new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(getSerialFile(fileSerialNum)), charset), CACHE_SIZE));
    }

    public String getSerialFile(int fileSerialNum) {
        int idx = filePath.lastIndexOf(".");
        if (idx >= 0) {
            return filePath.substring(0, idx) + "_" + fileSerialNum + filePath.substring(idx);
        } else {
            return filePath + fileSerialNum;
        }
    }
}
