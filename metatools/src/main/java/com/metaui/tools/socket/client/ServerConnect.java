package com.metaui.tools.socket.client;

import com.metaui.tools.socket.transport.CmdTransport;
import com.metaui.tools.socket.transport.ISocketTransport;
import com.metaui.tools.socket.transport.ITransportEvent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * 服务器端创建与客户端的连接
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class ServerConnect extends Thread {
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private ITransportEvent event;

    public ServerConnect(Socket socket) throws IOException {
        this.socket = socket;
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());

        // 启动
        start();
    }

    @Override
    public void run() {
        try {
            while (true) {
                try {
                    Object object = in.readObject();
                    if (object instanceof ISocketTransport) {
                        ISocketTransport transport = (ISocketTransport) object;
                        handle(transport);
                    } else {
                        throw new RuntimeException("不能识别的对象！");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void send(ISocketTransport transport) throws IOException {
        out.writeObject(transport);
        out.flush();
    }

    /**
     * 处理接收信息
     */
    public void handle(ISocketTransport transport) throws Exception {
        if (transport instanceof CmdTransport) {
            CmdTransport cmdTransport = (CmdTransport) transport;
            String response = cmdTransport.getReceiveInfo();
            System.out.println(response);
        }

        if (event != null) {
            event.onTransport(transport);
        }
    }

    public void setOnTransport(ITransportEvent event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return socket.getInetAddress().getHostAddress();
    }
}
