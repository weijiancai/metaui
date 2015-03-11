package com.metaui.tools.socket.server;

import com.metaui.core.util.UString;
import com.metaui.tools.socket.transport.ServiceInfo;
import com.metaui.tools.socket.transport.ServiceTransport;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class ServiceHandler {
    private ServiceTransport transport;

    public ServiceHandler(ServiceTransport transport) {
        this.transport = transport;
    }

    public void handle() throws Exception {
        String cmd = transport.getSendInfo();
        if ("list".equals(cmd)) {
            String result = ExecCmd.execute("sc query type= service state= all");
            List<ServiceInfo> list = new ArrayList<>();
            transport.setServiceInfos(list);

            for (String str : result.split("\n\n")) {
                ServiceInfo info = new ServiceInfo();
                list.add(info);

                for (String line : str.split("\n")) {
                    String[] strs = line.split(":");
                    if (strs.length == 2) {
                        String key = UString.trim(strs[0]);
                        String value = strs[1];
                        if ("SERVICE_NAME".equals(key)) {
                            info.setName(value);
                        } else if ("DISPLAY_NAME".equals(key)) {
                            info.setDisplayName(value);
                        } else if ("STATE".equals(key)) {
                            if (value.contains("STOPPED")) {
                                info.setState("已停止");
                            } else if (value.contains("RUNNING")) {
                                info.setState("已启动");
                            }
                        }
                    }
                }
                System.out.println(str);
                System.out.println("-----------------------------------------------------------");
            }
        } else if ("start".equals(cmd)) {
            String result = ExecCmd.execute("net start " + transport.getServiceName());
            System.out.println(result);
            transport.setReceiveInfo(result);
        } else if ("stop".equals(cmd)) {
            String result = ExecCmd.execute("net stop " + transport.getServiceName());
            System.out.println(result);
            transport.setReceiveInfo(result);
        }
    }

    public static void main(String[] args) throws Exception {
        ServiceTransport transport = new ServiceTransport();
        transport.setSendInfo("list");
        ServiceHandler handler = new ServiceHandler(transport);
        handler.handle();
    }
}
