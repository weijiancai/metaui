package com.metaui.tools.socket.server;

import com.metaui.tools.socket.transport.ISocketTransport;

import java.net.Socket;

/**
 * 服务器端创建与客户端的连接
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class ClientConnect implements Runnable {
    private Socket socket;

    public ClientConnect(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

    }

    public void send(ISocketTransport transport) {

    }

    /**
     * 处理接收信息
     */
    public void handle(ISocketTransport transport) {

    }
}
