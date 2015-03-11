package com.metaui.tools.socket.transport;

import java.io.Serializable;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class ServiceInfo implements Serializable {
    private String name;
    private String displayName;
    private String desc;
    private String state;
    private String startType;

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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStartType() {
        return startType;
    }

    public void setStartType(String startType) {
        this.startType = startType;
    }
}
