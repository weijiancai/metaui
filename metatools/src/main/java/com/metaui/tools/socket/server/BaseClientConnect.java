package com.metaui.tools.socket.server;

import com.metaui.tools.socket.transport.CmdTransport;
import com.metaui.tools.socket.transport.ISocketTransport;
import com.metaui.tools.socket.transport.ServiceTransport;

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
public class BaseClientConnect {
    private Socket socket;
    private BaseServer server;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public BaseClientConnect(Socket socket, BaseServer server) throws IOException {
        this.socket = socket;
        this.server = server;

        in = new ObjectInputStream(socket.getInputStream());
        out = new ObjectOutputStream(socket.getOutputStream());
    }

    public void connect() throws IOException {
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
                server.removeClient(this);
                break;
            }
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
            String cmd = transport.getSendInfo();
            String response = ExecCmd.execute(cmd);
            System.out.println(response);
            cmdTransport.setReceiveInfo(response);
        } else if (transport instanceof ServiceTransport) {
            ServiceTransport serviceTransport = (ServiceTransport) transport;
            ServiceHandler handler = new ServiceHandler(serviceTransport);
            handler.handle();
        }

        // 发送返回信息
        send(transport);
    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public String toString() {
        return socket.getInetAddress().getHostAddress();
    }
}
