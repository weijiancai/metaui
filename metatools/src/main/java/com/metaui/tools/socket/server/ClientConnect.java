package com.metaui.tools.socket.server;

import com.metaui.tools.socket.transport.ISocketTransport;

import java.net.Socket;

/**
 * �������˴�����ͻ��˵�����
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
     * ���������Ϣ
     */
    public void handle(ISocketTransport transport) {

    }
}
