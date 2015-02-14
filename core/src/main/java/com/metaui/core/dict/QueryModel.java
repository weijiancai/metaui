package com.metaui.core.dict;

import com.metaui.core.dict.annotation.DictElement;
import com.metaui.core.util.UString;

/**
 * 查询模式
 *
 * @author wei_jc
 * @since 1.0.0
 */
@DictElement(categoryName = "查询模式")
public enum QueryModel {
    /**
     * 等于 =
     */
    EQUAL("等于"),
    /**
     * 不等于 !=
     */
    NOT_EQUAL("不等于"),
    /**
     * 小于 <
     */
    LESS_THAN("小于"),
    /**
     * 小于等于 <=
     */
    LESS_EQUAL("小于等于"),
    /**
     * 大于 >
     */
    GREATER_THAN("大于"),
    /**
     * 大于等于 >=
     */
    GREATER_EQUAL("大于等于"),
    /**
     * 全Like like
     */
    LIKE("包含"),
    /**
     * 左Like like a%
     */
    LEFT_LIKE("左Like"),
    /**
     * 右Like like %a
     */
    RIGHT_LIKE("右Like"),
    /**
     * NULL
     */
    NULL("NULL值"),
    /**
     * NOT NULL
     */
    NOT_NULL("非NULL值")
    ;

    private String displayName;

    private QueryModel(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static QueryModel convert(String queryModelStr) {
        if (UString.isEmpty(queryModelStr)) {
            return EQUAL;
        }
        return QueryModel.valueOf(queryModelStr);
    }

    public String toSqlModel() {
        switch (this) {
            case EQUAL: {
                return " = ";
            }
            case NOT_EQUAL: {
                return " != ";
            }
            case LESS_THAN: {
                return " < ";
            }
            case LESS_EQUAL: {
                return " <= ";
            }
            case GREATER_THAN: {
                return " > ";
            }
            case GREATER_EQUAL: {
                return " >= ";
            }
            case LIKE: {
                return "%%";
            }
            case LEFT_LIKE: {
                return "*%";
            }
            case RIGHT_LIKE: {
                return "%*";
            }
            case NULL:
                return "null";
            case NOT_NULL:
                return "not null";
        }

        return "=";
    }
}
