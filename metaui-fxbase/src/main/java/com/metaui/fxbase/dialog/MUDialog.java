package com.metaui.fxbase.dialog;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Modality;
import javafx.util.Callback;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 对话框
 *
 * @author wei_jc
 * @since 1.0
 */
public class MUDialog {
    public static void showMessage(String message) {
        createAlert(Alert.AlertType.INFORMATION, message);
    }

    public static Alert createAlert(Alert.AlertType type, String content) {
        Alert alert = new Alert(type, content);
        alert.initModality(Modality.APPLICATION_MODAL);
//        alert.initOwner(BaseApplication.getInstance().getStage());
//        alert.getDialogPane().setContentText(type + " text.");
        alert.getDialogPane().setHeaderText(null);
        alert.showAndWait()
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response -> System.out.println("The alert was approved"));
        return alert;
    }

    public static Dialog<ButtonType> showException(Throwable th) {
        th.printStackTrace();

        Dialog<ButtonType> dialog = new Dialog<ButtonType>();
        dialog.setTitle("程序异常");

        final DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.setContentText("Details of the problem:");
        dialogPane.getButtonTypes().addAll(ButtonType.OK);
        dialogPane.setContentText(th.getMessage());
        dialog.initModality(Modality.APPLICATION_MODAL);

        Label label = new Label("异常堆栈:");
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        th.printStackTrace(pw);
        pw.close();

        TextArea textArea = new TextArea(sw.toString());
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane root = new GridPane();
        root.setVisible(false);
        root.setMaxWidth(Double.MAX_VALUE);
        root.add(label, 0, 0);
        root.add(textArea, 0, 1);
        dialogPane.setExpandableContent(root);
        dialog.showAndWait()
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response -> System.out.println("The exception was approved"));
        return dialog;
    }

    /**
     * 显示自定义对话框
     *
     * @param title 对话框标题
     * @param content 对话框内容节点
     */
    public static void showCustomDialog(String title, Node content, final Callback<Void, Void> callback) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setGraphic(null);
        alert.getDialogPane().setContent(content);
        alert.showAndWait()
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response -> {
                    if(callback != null) callback.call(null);
                });
    }
}
