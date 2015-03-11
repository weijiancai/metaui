package com.metaui.tools.socket.server.javafx;

import com.metaui.core.event.MEventHandler;
import com.metaui.tools.socket.server.BaseServer;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.IOException;

/**
 * JavaFx服务器端Service
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class FxServerService extends Service {
    private BaseServer server;

    public FxServerService(int port) throws IOException {
        server = new FxServer(port);
    }

    @Override
    protected Task createTask() {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                server.setOnMessage(new MEventHandler<String>() {
                    @Override
                    public void handle(String message) {
                        updateMessage(message);
                    }
                });

                server.start();

                return null;
            }
        };
    }

    public BaseServer getServer() {
        return server;
    }

    @Override
    protected void cancelled() {
        super.cancelled();
        server.stop();
    }

    @Override
    protected void failed() {
        super.failed();
        server.stop();
    }
}
