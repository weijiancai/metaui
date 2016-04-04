package com.metaui.excel;

import org.apache.poi.ss.usermodel.CellStyle;

/**
 * excel表格头部数据模型
 *
 * @author wei_jc
 * @since 1.0.0
 * 2016/3/29.
 */
public class TableHeader {
    /** 名称 */
    private String name;
    /** 显示名 */
    private String displayName;
    /** 数据类型 */
    private Class<?> type;
    /** 单元格样式 */
    private CellStyle style;
    /** 单元格宽度 */
    private int width;
    /** 是否求和 */
    private boolean isSum;

    public TableHeader() {
    }

    public TableHeader(String name, String displayName) {
        this.name = name;
        this.displayName = displayName;
    }

    public TableHeader(String name, String displayName, int width) {
        this.name = name;
        this.displayName = displayName;
        this.width = width;
    }

    public TableHeader(String name, String displayName, Class<?> type) {
        this.name = name;
        this.displayName = displayName;
        this.type = type;
    }

    public TableHeader(String name, String displayName, Class<?> type, int width) {
        this.name = name;
        this.displayName = displayName;
        this.type = type;
        this.width = width;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public CellStyle getStyle() {
        return style;
    }

    public void setStyle(CellStyle style) {
        this.style = style;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public boolean isSum() {
        return isSum;
    }

    public void setSum(boolean sum) {
        isSum = sum;
    }
}
