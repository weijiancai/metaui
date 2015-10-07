package com.metaui.core.config.preference;

import com.metaui.core.config.PathManager;
import com.metaui.core.util.jaxb.JAXBUtil;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import org.apache.log4j.Logger;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Preference存储配置信息
 *
 * @author wei_jc
 * @since 1.0.0
 */
@XmlRootElement
public class Preferences {
    private static Logger log = Logger.getLogger(Preferences.class);
    /**
     * 跟节点配置信息，如果没有section，则都存储在这里
     */
    public static final String ROOT = "ROOT";
    public static final String FILE_NAME_PREFERENCE = "Preference.xml";
    private static final File PREFERENCE_FILE = new File(PathManager.getConfigPath(), FILE_NAME_PREFERENCE);
    private List<Preference> preferenceList = new ArrayList<Preference>();
    private static Preferences preferences;

    private Preferences() {

    }

    public static Preferences getInstance() {
        if (preferences == null) {
            preferences = new Preferences();
            try {
                List<Preference> list = null;
                if (PREFERENCE_FILE.exists()) {
                    Preferences pfs = JAXBUtil.unmarshal(new FileInputStream(PREFERENCE_FILE), Preferences.class);
                    preferences.setPreferenceList(pfs.getPreferenceList());
                }
                list = new ArrayList<Preference>();
            } catch (Exception e) {
                log.error("加载Preferences信息出错", e);
            }
        }

        return preferences;
    }

    @XmlElement(name = "Preference")
    public List<Preference> getPreferenceList() {
        return preferenceList;
    }

    public void setPreferenceList(List<Preference> preferenceList) {
        this.preferenceList = preferenceList;
    }

    public void put(String key, String value) {
        put(ROOT, key, value);
    }

    public void put(Class<?> clazz, String key, String value) {
        put(clazz.getName(), key, value);
    }

    public void put(String section, String key, String value) {
        // 检查值是否已经存在，如果存在并相等，则不保存
        for (Preference preference : preferenceList) {
            if (preference.getSection().equals(section) && preference.getKey().equals(key)) {
                if (preference.getValue().equals(value)) {
                    return ;
                } else { // 修改值
                    preference.setValue(value);
                    store();
                    return;
                }
            }
        }
        // 新增
        preferenceList.add(new Preference(section, key, value));
        store();
    }

    /**
     * 存储对象信息
     *
     * @param key   键
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
        put(key, new String(bos.getBytes()));
    }

    public String get(Class<?> clazz, String key) {
        return get(clazz.getName(), key);
    }

    public String get(String section, String key) {
        for (Preference preference : preferenceList) {
            if (preference.getSection().equals(section) && preference.getKey().equals(key)) {
                return preference.getValue();
            }
        }

        return "";
    }

    public String get(String key) {
        return get(ROOT, key);
    }

    /**
     * 根据键获得存储的对象信息
     *
     * @param key 键
     * @return 返回存储的对象
     */
    @SuppressWarnings("unchecked")
    public <T> T getObject(String key) {
        byte[] bytes = get(key).getBytes();
        if (bytes.length == 0) {
            return null;
        }
        ByteInputStream bis = new ByteInputStream(bytes, bytes.length);
        try {
            ObjectInputStream ois = new ObjectInputStream(bis);

            return (T) ois.readObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将类对象序列化为xml文件
     */
    public void store() {
        try {
            JAXBUtil.marshalToFile(this, PREFERENCE_FILE, Preferences.class, Preference.class);
        } catch (Exception e) {
            log.error("保存Preferences信息出错", e);
        }
    }
}
