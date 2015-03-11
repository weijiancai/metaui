package com.metaui.tools.socket.client;

import com.metaui.core.util.UString;
import com.metaui.tools.socket.transport.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class ServerTabCtrl implements Initializable {
    public Button btnCreateConnect;
    public Button btnStop;
    public Button btnSendCmd;
    public TextField cmdTF;
    public ListView<String> cmdLogListView;
    public TableView<ServiceInfo> serviceTableView;
    public Button btnRefresh;
    public TabPane tabPane;
    public TableColumn<ServiceInfo, String> nameCol;
    public TableColumn<ServiceInfo, String> displayNameCol;
    public TableColumn<ServiceInfo, String> stateCol;

    private ServerInfo serverInfo;
    private SocketClient client;
    private ObservableList<String> cmdLogList = FXCollections.observableList(new ArrayList<>());

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cmdLogListView.setItems(cmdLogList);
    }

    public void createConnect(ActionEvent event) throws Exception {
        cmdLogList.add("创建与客户端" + serverInfo.toString() + "连接......");
        if (client == null) {
            client = new SocketClient();
            client.createConnect(serverInfo.getIp(), serverInfo.getPort());
            client.setOnTransport(new ITransportEvent() {
                @Override
                public void onTransport(ISocketTransport transport) {
                    if (transport instanceof CmdTransport) {
                        CmdTransport cmdTransport = (CmdTransport) transport;
                        String response = cmdTransport.getReceiveInfo();
                        cmdLogList.add(response);
                    } else if (transport instanceof ServiceTransport) {
                        ServiceTransport serviceTransport = (ServiceTransport) transport;
                        String cmd = serviceTransport.getSendInfo();
                        if ("list".equals(cmd)) {
                            List<ServiceInfo> list = serviceTransport.getServiceList();
                            serviceTableView.setItems(FXCollections.observableArrayList(list));
                        } else if ("start".equals(cmd) || "stop".equals(cmd)) {
                            try {
                                cmdLogList.add(transport.getReceiveInfo());
                                refreshService(null);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }
            });
            cmdLogList.add("创建连接成功！");
        }

        // 切换到服务，查询服务器的所有服务
        tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
                if (newValue.getText().equals("服务")) {
                    try {
                        refreshService(null);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        // 表格
        nameCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ServiceInfo, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ServiceInfo, String> param) {
                return new SimpleStringProperty(param.getValue().getName());
            }
        });
        displayNameCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ServiceInfo, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ServiceInfo, String> param) {
                return new SimpleStringProperty(param.getValue().getDisplayName());
            }
        });
        stateCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ServiceInfo, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ServiceInfo, String> param) {
                return new SimpleStringProperty(param.getValue().getState());
            }
        });
    }

    public void stopServer(ActionEvent event) throws IOException {
        cmdLogList.add("正在停止与服务器连接......");
        client.stop();
        cmdLogList.add("客户端已停止。");
    }

    public void sendCmd(ActionEvent event) throws IOException {
        String cmd = cmdTF.getText();
        if (UString.isNotEmpty(cmd)) {
            cmdLogList.add(cmd + "\n");
            CmdTransport transport = new CmdTransport();
            transport.setCmdInfo(cmd);
            client.send(transport);
        }
    }

    public void setServerInfo(ServerInfo serverInfo) {
        this.serverInfo = serverInfo;
    }

    public void refreshService(ActionEvent event) throws IOException {
        ServiceTransport transport = new ServiceTransport();
        transport.setSendInfo("list");
        client.send(transport);
    }

    public void startService(ActionEvent event) throws IOException {
        ServiceInfo info = serviceTableView.getSelectionModel().getSelectedItem();
        if (info != null) {
            cmdLogList.add("启动服务：" + info.getDisplayName() + "......");
            ServiceTransport transport = new ServiceTransport();
            transport.setSendInfo("start");
            transport.setServiceName(info.getName());
            client.send(transport);
        }
    }

    public void stopService(ActionEvent event) throws IOException {
        ServiceInfo info = serviceTableView.getSelectionModel().getSelectedItem();
        if (info != null) {
            cmdLogList.add("停止服务：" + info.getDisplayName() + "......");
            ServiceTransport transport = new ServiceTransport();
            transport.setSendInfo("stop");
            transport.setServiceName(info.getName());
            client.send(transport);
        }
    }
}
