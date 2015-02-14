package com.metaui.core.datasource.db.object;

import com.metaui.core.datasource.db.DatabaseType;
import com.metaui.core.meta.MetaDataType;

/**
 * 数据库列
 *
 * @author wei_jc
 * @since 1.0.0
 */
public interface DBColumn extends DBObject {
    /**
     * 获得Meta数据类型(由数据库数据类型转化)
     *
     * @return 返回Meta数据类型
     * @since 1.0.0
     */
    MetaDataType getDataType();

    /**
     * 获得数据库数据类型
     *
     * @return 返回数据库数据类型
     * @since 1.0.0
     */
    String getDbDataType();

    /**
     * 转换为其他数据库的数据类型
     *
     * @param type 数据库类型
     * @return 返回其他数据库的数据类型
     */
    String getDbDataType(DatabaseType type);

    /**
     * 获得PRECISION，数值总长度
     *
     * @return 返回PRECISION
     * @since 1.0.0
     */
    int getPrecision();

    /**
     * 获得Scale，数值小数位数
     *
     * @return 返回Scale
     * @since 1.0.0
     */
    int getScale();

    /**
     * 是否主键
     *
     * @return 如果是主键 返回true
     */
    boolean isPk();

    /**
     * 是否外键
     *
     * @return 如果是外键，返回true
     */
    boolean isFk();

    /**
     * 是否可空
     *
     * @return 如果允许为null，则返回true，否则返回false
     * @since 1.0.0
     */
    boolean isNullable();

    /**
     * 获得最大长度
     *
     * @return 返回最大长度
     */
    int getMaxLength();

    /**
     * 获得引用的主键列
     *
     * @return 返回引用的主键列
     */
    DBColumn getRefColumn();

    /**
     * 获得列所在的表(视图)信息
     *
     * @return 返回列所在的表（视图）信息
     */
    DBDataset getDataset();

    /**
     * 获得数据类型的字符串表示，例如varchar(24)
     *
     * @return 返回数据类型字符串的表示
     */
    String getDataTypeString();
}
