package com.metaui.tools.socket.client;

import com.metaui.tools.socket.transport.ISocketTransport;
import com.metaui.tools.socket.transport.ITransportEvent;

import java.io.IOException;
import java.net.Socket;

/**
 * Socket客户端
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class SocketClient {
    private Socket socket;
    private ServerConnect connect;

    /**
     * 创建与服务器的连接
     *
     * @param ip IP地址
     * @param port 端口
     * @return 返回Socket连接
     */
    public ServerConnect createConnect(String ip, int port) throws Exception {
        socket = new Socket(ip, port);
        connect = new ServerConnect(socket);
        return connect;
    }

    public void send(ISocketTransport transport) throws IOException {
        connect.send(transport);

    }

    public void setOnTransport(ITransportEvent event) {
        connect.setOnTransport(event);
    }

    public void stop() throws IOException {
        if (socket != null) {
            socket.close();
        }
    }
}
