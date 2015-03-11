package com.metaui.tools.socket.server;

import com.metaui.tools.socket.server.javafx.FxServer;
import org.junit.Test;

import static org.junit.Assert.*;

public class SocketServerTest {

    @Test
    public void testStart() throws Exception {
        FxServer server = new FxServer(9999);
        server.start();
        Thread.sleep(100000);
    }
}