package com.metaui.tools.socket.server;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

/**
 * Socket 服务器
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class SocketServer {
    private int port;
    private ServerSocket server;
    private ObservableList<ClientConnect> connectList = FXCollections.observableArrayList();
    private ObservableList<String> logs = FXCollections.observableArrayList();
    private boolean isRunning = true;

    public SocketServer(int port) throws IOException {
        this.port = port;
        server = new ServerSocket(port);
    }

    /**
     * 启动服务器
     */
    public void start() {
        logs.add("服务器正在启动......");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    logs.add("启动成功。");
                    while (isRunning) {
                        System.out.println("1111");
                        Socket socket = server.accept();
                        try {
                            logs.add("有客户端进行连接：" + socket.getInetAddress());
                            connectList.add(new ClientConnect(socket, connectList, logs));
                        } catch (Exception e) {
                            e.printStackTrace();
                            socket.close();
                            logs.add("客户端连接异常：" + e.getMessage());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        server.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    /**
     * 停止服务器
     */
    public void stop() throws Exception {
        logs.add("正在停止服务器........");
        isRunning = false;
        server.close();
        logs.addAll("服务器已停止。");
    }

    public ObservableList<ClientConnect> getConnectList() {
        return connectList;
    }

    public ObservableList<String> getLogs() {
        return logs;
    }
}
