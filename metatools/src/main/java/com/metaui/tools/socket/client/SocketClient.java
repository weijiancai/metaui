package com.metaui.tools.socket.client;

import com.metaui.tools.socket.transport.ISocketTransport;

import java.net.Socket;

/**
 * Socket客户端
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class SocketClient {
    /**
     * 创建与服务器的连接
     *
     * @param ip IP地址
     * @param port 端口
     * @return 返回Socket连接
     */
    public ServerConnect createConnect(String ip, int port) throws Exception {
        Socket socket = new Socket(ip, port);
        return new ServerConnect(socket);
    }

    public void send(Socket socket, ISocketTransport transport) {

    }
}
