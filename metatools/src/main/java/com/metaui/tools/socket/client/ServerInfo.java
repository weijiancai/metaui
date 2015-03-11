package com.metaui.tools.socket.client;

import java.io.Serializable;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class ServerInfo implements Serializable {
    private String name;
    private String ip;
    private int port;

    public ServerInfo() {
    }

    public ServerInfo(String name, String ip, int port) {
        this.name = name;
        this.ip = ip;
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return String.format("%s(%s:%d)", name, ip, port);
    }
}
