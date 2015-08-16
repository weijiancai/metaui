package com.metaui.core.meta;

import com.metaui.core.dict.annotation.DictElement;

/**
 * 显示样式
 *
 * @author wei_jc
 * @since 1.0.0
 */
@DictElement(categoryName = "显示样式")
public enum DisplayStyle {
    /**
     * 0. 文本框
     */
    TEXT_FIELD("文本框"),
    /**
     * 1. 文本域
     */
    TEXT_AREA("文本域"),
    /**
     * 2. 密码
     */
    PASSWORD("密码"),
    /**
     * 3. 下拉框
     */
    COMBO_BOX("下拉框"),
    /**
     * 4. 数据源
     */
    DATA_SOURCE("数据源"),
    /**
     * 5. Boolean
     */
    BOOLEAN("是/否"),
    /**
     * 6. 日期
     */
    DATE("日期"),
    /**
     * 7. 文件选择
     */
    FILE_CHOOSER("文件选择"),
    /**
     * 8. 目录选择
     */
    DIRECTORY_CHOOSER("目录选择"),
    /**
     * 9. 文本
     */
    TEXT("文本")
    ;

    private String displayName;

    DisplayStyle(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static DisplayStyle getStyle(String styleStr) {
        if (TEXT_FIELD.name().equalsIgnoreCase(styleStr)) {
            return TEXT_FIELD;
        } else if (TEXT_AREA.name().equalsIgnoreCase(styleStr)) {
            return TEXT_AREA;
        } else if (PASSWORD.name().equalsIgnoreCase(styleStr)) {
            return PASSWORD;
        } else if (COMBO_BOX.name().equalsIgnoreCase(styleStr)) {
            return COMBO_BOX;
        } else if (DATA_SOURCE.name().equalsIgnoreCase(styleStr)) {
            return DATA_SOURCE;
        } else if (BOOLEAN.name().equalsIgnoreCase(styleStr)) {
            return BOOLEAN;
        } else if (DATE.name().equalsIgnoreCase(styleStr)) {
            return DATE;
        } else if (FILE_CHOOSER.name().equalsIgnoreCase(styleStr)) {
            return FILE_CHOOSER;
        }

        return TEXT_FIELD;
    }
}
