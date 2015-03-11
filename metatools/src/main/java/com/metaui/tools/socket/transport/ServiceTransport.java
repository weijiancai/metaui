package com.metaui.tools.socket.transport;

import java.util.List;

/**
 * 服务Transport
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class ServiceTransport implements ISocketTransport {
    private String sendInfo;
    private String receiveInfo;
    private List<ServiceInfo> serviceInfos;
    private String serviceName;


    public void setSendInfo(String sendInfo) {
        this.sendInfo = sendInfo;
    }

    public void setReceiveInfo(String receiveInfo) {
        this.receiveInfo = receiveInfo;
    }

    @Override
    public String getSendInfo() {
        return sendInfo;
    }

    @Override
    public String getReceiveInfo() {
        return receiveInfo;
    }

    public List<ServiceInfo> getServiceList() {
        return serviceInfos;
    }

    public void setServiceInfos(List<ServiceInfo> serviceInfos) {
        this.serviceInfos = serviceInfos;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
