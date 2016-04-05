package com.metaui.core.util.file;

import com.metaui.core.util.UString;

import java.io.*;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class CutXmlFile {
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
    // xml文件的根标签名
    private String rootTag;
    // 切割文件的头部信息的标签名
    private String headerTag;
    // 分割文件的标签名
    private String splitTag;

    private StringBuilder header = new StringBuilder(); // 头部信息
    private long count = 0; // 行数/字符数计数器
    private int fileSerialNum = 1; // 文件流水号
    /**
     * 文件大小，默认100M
     */
    private long fileSize = 100 * 1024 * 1024;

    public CutXmlFile(String filePath) {
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

    public void setRootTag(String rootTag) {
        this.rootTag = rootTag;
    }

    public void setHeaderTag(String headerTag) {
        this.headerTag = headerTag;
    }

    public void setSplitTag(String splitTag) {
        this.splitTag = splitTag;
    }

    public String getRootTag() {
        return rootTag;
    }

    public String getHeaderTag() {
        return headerTag;
    }

    public String getSplitTag() {
        return splitTag;
    }

    /**
     * 按行切割文件，切割后的文件已数字序列命名，存储在与要切割的文件同一个目录
     */
    public void cutByLine() throws IOException {
        BufferedReader br = null;
        PrintWriter pw = null;
        try {
            br = new BufferedReader(new FileReader(filePath), CACHE_SIZE);
            pw = new PrintWriter(new BufferedWriter(new FileWriter(getSerialFile(fileSerialNum)), CACHE_SIZE));
            boolean isHeadEnd = false; // 读取头部信息是否结束
            String line;
            while ((line = br.readLine()) != null) {
                if (String.format("</%s>", headerTag).equals(line.trim())) {
                    isHeadEnd = true;
                }
                if (!isHeadEnd) {
                    header.append(line).append("\n");
                    pw.write(line + "\n");
                } else {
                    if (count++ >= cutRows && String.format("</%s>", splitTag).equals(line.trim())) {
                        pw.write(line + "\n");
                        pw.write(String.format("</%s>", rootTag));
                        pw.flush();
                        pw.close();
                        count = 0;
                        pw = new PrintWriter(new BufferedWriter(new FileWriter(getSerialFile(++fileSerialNum)), CACHE_SIZE));
                        pw.write(header.toString());
                        pw.write(getHeaderTag() + "\n");
                    }
                    pw.write(line + "\n");
                }
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

    /**
     * 按字符切割文件，切割后的文件已数字序列命名，存储在与要切割的文件同一个目录
     */
    public void cutByChar() throws IOException {
        BufferedReader br = null;
        PrintWriter pw = null;
        try {
            br = new BufferedReader(new FileReader(filePath), CACHE_SIZE);
            pw = new PrintWriter(new BufferedWriter(new FileWriter(getSerialFile(fileSerialNum)), CACHE_SIZE));
            boolean isHeadEnd = false; // 读取头部信息是否结束
            if(UString.isEmpty(this.getHeaderTag())) {
            	//无head
            	isHeadEnd = true;
            }
            boolean isEndTag = false;  // 是否是结束标签
            StringBuilder tagName = new StringBuilder();
            int ch;
            while ((ch = br.read()) != -1) {
                count++;
                if (ch == '<') { // 标签开始
                    pw.write(ch);
                    if (!isHeadEnd) {
                        header.append((char)ch);
                    }

                    while (true) {
                    	count++;
                        ch = br.read();
                        pw.write(ch);
                        if (!isHeadEnd) {
                            header.append((char)ch);
                        }

                        if (ch == -1 || ch == '>' || (!isEndTag && ch == ' ')) {
                            break;
                        }

                        if (ch != ' ') {  // 去掉空格
                            if (ch == '/') {
                                isEndTag = true;
                            } else {
                                tagName.append((char)ch);
                            }
                        }
                    }
                    
                    if (isEndTag && getHeaderTag().equalsIgnoreCase(tagName.toString())) {
                        isHeadEnd = true;
                    }
                    
                    if (isHeadEnd && (count >= fileSize && isEndTag && getSplitTag().equalsIgnoreCase(tagName.toString()))) {
                        pw.write("\n</" + getRootTag() + ">");
                        pw.flush();
                        pw.close();
                        count = 0;
                        pw = new PrintWriter(new BufferedWriter(new FileWriter(getSerialFile(++fileSerialNum)), CACHE_SIZE));
                        pw.write(header.toString());
                    }

                    // 重置标识
                    isEndTag = false;
                    tagName.delete(0, tagName.length());
                } else {
                    pw.write(ch);
                    if (!isHeadEnd) {
                        header.append((char)ch);
                    }
                }
            }
            pw.flush();
        } finally {
            if (br != null) {
                br.close();
                br = null;
            }
            if (pw != null) {
                pw.close();
                pw = null;
            }
        }
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
