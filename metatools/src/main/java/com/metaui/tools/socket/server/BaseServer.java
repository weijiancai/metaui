package com.metaui.tools.socket.server;

import com.metaui.core.event.MEventHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * 服务器
 *
 * @author wei_jc
 * @since 1.0.0
 */
public abstract class BaseServer {
    private ServerSocket server;
    private MEventHandler<String> messageEventHandler;
    private MEventHandler<BaseClientConnect> clientConnectChangeEventHandler; // 客户端连接有改变时的事件处理
    private List<BaseClientConnect> connectList = new ArrayList<>();
    private boolean isRunning = true;

    public BaseServer(int port) throws IOException {
        server = new ServerSocket(port);
    }

    /**
     * 启动服务器
     */
    public void start() throws IOException {
        try {
            printMessage("启动成功。");
            isRunning = true;
            while (isRunning) {
                Socket socket = server.accept();
                try {
                    printMessage("有客户端进行连接：" + socket.getInetAddress());
                    BaseClientConnect connect = getClientConnect(socket);
                    connectList.add(connect);
                    // 发送通知
                    notifyClientConnectChange(connect);
                } catch (Exception e) {
                    e.printStackTrace();
                    socket.close();
                    printMessage("客户端连接异常：" + e.getMessage());
                }
            }
        } finally {
            server.close();
        }
    }

    /**
     * 停止服务器
     */
    public void stop() {
        printMessage("正在停止服务器........");
        isRunning = false;
        try {
            if (!server.isClosed()) {
                server.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        printMessage("服务器已停止。");
    }

    /**
     * 获得客户端连接
     *
     * @param socket 连接Socket
     * @return 返回客户端连接
     */
    protected abstract BaseClientConnect getClientConnect(Socket socket) throws IOException;

    /**
     * 移除客户端连接
     *
     * @param clientConnect 客户端连接
     * @throws IOException
     */
    public void removeClient(BaseClientConnect clientConnect) throws IOException {
        Socket socket = clientConnect.getSocket();
        printMessage("客户端：" + socket.getInetAddress().getHostAddress() + "断开连接！");
        if (!socket.isClosed()) {
            socket.close();
        }
        connectList.remove(clientConnect);
        // 发送通知
        notifyClientConnectChange(clientConnect);
    }

    public void printMessage(String message) {
        if (messageEventHandler != null) {
            messageEventHandler.handle(message);
        }
    }

    /**
     * 当有客户端连接、或断开连接时，通知事件
     *
     * @param connect 客户端连接
     */
    public void notifyClientConnectChange(BaseClientConnect connect) {
        if (clientConnectChangeEventHandler != null) {
            clientConnectChangeEventHandler.handle(connect);
        }
    }

    public void setOnMessage(MEventHandler<String> handler) {
        messageEventHandler = handler;
    }

    public void setOnClientConnectChange(MEventHandler<BaseClientConnect> handler) {
        this.clientConnectChangeEventHandler = handler;
    }

    public List<BaseClientConnect> getConnectList() {
        return connectList;
    }
}
