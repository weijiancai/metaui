package com.metaui.tools.socket.client;

import com.metaui.tools.socket.transport.CmdTransport;
import org.junit.Test;

import java.net.Socket;

import static org.junit.Assert.*;

public class SocketClientTest {

    @Test
    public void testCreateConnect() throws Exception {
        SocketClient client = new SocketClient();
        Socket socket = client.createConnect("127.0.0.1", 9999);
        CmdTransport transport = new CmdTransport();
        transport.setCmdInfo("dir");
        client.send(socket, transport);
        System.out.println(transport.getReceiveInfo());
    }
}