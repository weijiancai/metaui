package com.metaui.core.datasource;

import com.metaui.core.datasource.db.object.DBColumn;
import com.metaui.core.util.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据库查询结果集,key会转换成小写
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class DataMap extends HashMap<String,Object> {
    /**
     * 状态
     */
    public static enum STATUS {
        NEW, // 新增
        MODIFY, // 修改
        NOT_MODIFY // 未修改
    }

    private STATUS status = STATUS.NOT_MODIFY;
    private String uid; // 标识此DataMap的唯一值
    private DataMapMetaData metaData;
    private boolean isUpperKey; // 是否使用大写key值

    public DataMap() {
        uid = UUIDUtil.getUUID();
    }

    public DataMap(Map<? extends String, ?> map) {
        super(map);
        uid = UUIDUtil.getUUID();
    }

    public STATUS getStatus() {
        return status;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }

    public String getUid() {
        return uid;
    }

    public boolean isUpperKey() {
        return isUpperKey;
    }

    public void setUpperKey(boolean upperKey) {
        isUpperKey = upperKey;
    }

    /**
     * 获得key对应的字符串值
     *
     * @param key Key
     * @return 返回key对应的字符串值
     * @since 1.0.0
     */
    public String getString(String key) {
        return UObject.toString(get(key));
    }

    /**
     * 将key，value添加到结果集中，key会转成小写
     *
     * @param key key
     * @param value key对应的值
     * @return 返回key对应的值
     * @since 1.0.0
     */
    @Override
    public Object put(String key, Object value) {
        if (UString.isEmpty(key)) {
            return null;
        }
        if (isUpperKey) {
            key = key.toUpperCase();
        }
        return super.put(key, value);
    }


    /**
     * 根据key查找值,key会转成小写
     *
     * @param key key
     * @return 返回key对应的值
     * @since 1.0.0
     */
    public Object get(String key) {
        if (isUpperKey) {
            key = key.toUpperCase();
        }
        return super.get(key);
    }

    /**
     * 根据数据库列查找值
     *
     * @param column 数据库列
     * @return 返回key对应的值
     * @since 1.0.0
     */
    public Object get(DBColumn column) {
        return get(column.getName());
    }

    /**
     * 获得key对应的整数值
     *
     * @param key Key
     * @return 返回key对应的整数值
     * @since 1.0.0
     */
    public int getInt(String key) {
        return UNumber.toInt(getString(key));
    }

    public long getLong(String key) {
        return UNumber.toLong(getString(key));
    }

    /**
     * 获得key对应的Boolean值
     *
     * @param key Key
     * @return 返回key对应的Boolean值
     * @since 1.0.0
     */
    public boolean getBoolean(String key) {
        return UString.toBoolean(getString(key));
    }

    @Override
    public void putAll(Map<? extends String, ?> map) {
        for (Map.Entry<? extends String, ?> entry : map.entrySet()) {
            this.put(entry.getKey(), entry.getValue());
        }
    }

    public <T> T toClass(Class<T> clazz) {
        return UClass.convert(clazz, this);
    }

    public DataMapMetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(DataMapMetaData metaData) {
        this.metaData = metaData;
    }

    public static DataMap insert() {
        DataMap dataMap = new DataMap();
        dataMap.setStatus(STATUS.NEW);
        return dataMap;
    }
}
