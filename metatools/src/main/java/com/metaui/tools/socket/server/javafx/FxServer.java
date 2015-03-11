package com.metaui.tools.socket.server.javafx;

import com.metaui.tools.socket.server.BaseClientConnect;
import com.metaui.tools.socket.server.BaseServer;

import java.io.IOException;
import java.net.Socket;

/**
 * JavaFx 服务器
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class FxServer extends BaseServer {
    private FxClientConnect connect;
    public FxServer(int port) throws IOException {
        super(port);
    }

    @Override
    protected BaseClientConnect getClientConnect(Socket socket) throws IOException {
        if (connect == null) {
            connect = new FxClientConnect(socket, this);
        }

        return connect.getConnect();
    }
}
