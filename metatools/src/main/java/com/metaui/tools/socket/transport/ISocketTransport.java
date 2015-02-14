package com.metaui.tools.socket.transport;

import java.io.Serializable;

/**
 * 客户端、服务器之间传输信息
 *
 * @author wei_jc
 * @since 1.0.0
 */
public interface ISocketTransport extends Serializable {
    String getSendInfo();

    String getReceiveInfo();
}
