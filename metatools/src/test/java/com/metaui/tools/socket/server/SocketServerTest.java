package com.metaui.tools.socket.server;

import org.junit.Test;

import static org.junit.Assert.*;

public class SocketServerTest {

    @Test
    public void testStart() throws Exception {
        SocketServer server = new SocketServer(9999);
        server.start();
        Thread.sleep(100000);
    }
}