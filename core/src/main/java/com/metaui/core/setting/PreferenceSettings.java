package com.metaui.core.setting;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.prefs.Preferences;

/**
 * 使用Java Preference存储配置信息
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class PreferenceSettings {
    private static PreferenceSettings settings;
    private Preferences preferences;

    private PreferenceSettings() {

    }

    public static PreferenceSettings getInstance() {
        if (settings == null) {
            settings = new PreferenceSettings();
            settings.preferences = Preferences.userRoot();
        }

        return settings;
    }

    /**
     * 存储对象信息
     *
     * @param key 键
     * @param value 对象值
     */
    public void put(String key, Object value) {
        ByteOutputStream bos = new ByteOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(value);
            oos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        preferences.putByteArray(key, bos.getBytes());
    }

    /**
     * 根据键获得存储的对象信息
     *
     * @param key 键
     * @return 返回存储的对象
     */
    @SuppressWarnings("unchecked")
    public <T> T getObject(String key) {
        byte[] bytes = preferences.getByteArray(key, null);
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        ByteInputStream bis = new ByteInputStream(bytes, bytes.length);
        try {
            ObjectInputStream ois = new ObjectInputStream(bis);

            return (T)ois.readObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
