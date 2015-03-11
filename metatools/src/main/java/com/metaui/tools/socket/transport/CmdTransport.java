package com.metaui.tools.socket.transport;

/**
 * 命令传输
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class CmdTransport implements ISocketTransport {
    private String cmdInfo;
    private String receiveInfo;

    public void setReceiveInfo(String receiveInfo) {
        this.receiveInfo = receiveInfo;
    }

    public void setCmdInfo(String cmdInfo) {
        this.cmdInfo = cmdInfo;
    }

    @Override
    public String getSendInfo() {
        return cmdInfo;
    }

    @Override
    public String getReceiveInfo() {
        return receiveInfo;
    }
}
