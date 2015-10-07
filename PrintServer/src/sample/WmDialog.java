package sample;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Modality;
import javafx.util.Callback;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.function.Consumer;

/**
 * 对话框
 *
 * @author wei_jc
 * @since 1.0
 */
public class WmDialog {
    private Alert createAlert(Alert.AlertType type) {
        Alert alert = new Alert(type, "");
        alert.initModality(Modality.APPLICATION_MODAL);
//        alert.initOwner(BaseApplication.getInstance().getStage());
        alert.getDialogPane().setContentText(type + " text.");
        alert.getDialogPane().setHeaderText(null);
        alert.showAndWait()
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response -> System.out.println("The alert was approved"));
        return alert;
    }

    public static Dialog<ButtonType> showException(Throwable th) {
        Dialog<ButtonType> dialog = new Dialog<ButtonType>();
        dialog.setTitle("异常信息");

        final DialogPane dialogPane = dialog.getDialogPane();
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

    public static Dialog<ButtonType> showCustomDialog(Node node, String title, Callback<Void, Void> callback) {
        Dialog<ButtonType> dialog = new Dialog<ButtonType>();
        dialog.setTitle(title);
        final DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.setContent(node);
        dialogPane.getButtonTypes().addAll(ButtonType.OK);

        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.showAndWait()
                .filter(response -> response == ButtonType.OK)
                .ifPresent(new Consumer<ButtonType>() {
                    @Override
                    public void accept(ButtonType buttonType) {
                        if (callback != null) {
                            callback.call(null);
                        }
                    }
                });

        return dialog;
    }
}
