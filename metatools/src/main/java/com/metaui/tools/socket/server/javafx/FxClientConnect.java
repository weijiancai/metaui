package com.metaui.tools.socket.server.javafx;

import com.metaui.tools.socket.server.BaseClientConnect;
import com.metaui.tools.socket.server.BaseServer;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.IOException;
import java.net.Socket;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class FxClientConnect extends Service {
    private BaseClientConnect connect;

    public FxClientConnect(Socket socket, BaseServer server) throws IOException {
        connect = new BaseClientConnect(socket, server);
        this.start();
    }

    @Override
    protected Task createTask() {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                connect.connect();
                return null;
            }
        };
    }

    public BaseClientConnect getConnect() {
        return connect;
    }
}
