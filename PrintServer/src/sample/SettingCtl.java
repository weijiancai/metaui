package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class SettingCtl implements Initializable {
    @FXML
    private TextField tfUrl;
    @FXML
    private ChoiceBox<String> cbPack;
    @FXML
    private ChoiceBox<String> cbSend;

    private PrintServiceModel model = PrintServiceModel.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cbPack.setItems(model.getPrintServices());
        cbSend.setItems(model.getPrintServices());
        // 监听事件
        tfUrl.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                model.setUrl(newValue);
            }
        });
        cbPack.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                model.setPackPrintServiceName(newValue);
            }
        });
        cbSend.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                model.setSendPrintServiceName(newValue);
            }
        });

        // 初始化值
        tfUrl.setText(model.getUrl());
        cbPack.getSelectionModel().select(model.getPackPrintServiceName());
        cbSend.getSelectionModel().select(model.getSendPrintServiceName());
    }
}
