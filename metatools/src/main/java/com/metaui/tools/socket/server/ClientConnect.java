package com.metaui.tools.socket.server;

import com.metaui.tools.socket.transport.CmdTransport;
import com.metaui.tools.socket.transport.ISocketTransport;
import javafx.collections.ObservableList;

import java.io.*;
import java.net.Socket;

/**
 * 服务器端创建与客户端的连接
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class ClientConnect extends Thread {
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private ObservableList<ClientConnect> connectList;
    private ObservableList<String> logs;

    public ClientConnect(Socket socket, ObservableList<ClientConnect> connectList, ObservableList<String> logs) throws IOException {
        this.socket = socket;
        this.connectList = connectList;
        this.logs = logs;
        // 启动
        start();
    }

    @Override
    public void run() {
        try {
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());

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
                    logs.add("客户端：" + socket.getInetAddress().getHostAddress() + "断开连接！");
                    connectList.remove(this);
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
            String cmd = transport.getSendInfo();
            String response = ExecCmd.execute(cmd);
            System.out.println(response);
            cmdTransport.setReceiveInfo(response);
        }

        // 发送返回信息
        send(transport);
    }

    @Override
    public String toString() {
        return socket.getInetAddress().getHostAddress();
    }
}
