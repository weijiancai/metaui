package sample;

import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.print.*;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.transform.Scale;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * Demo to use JavaFX 8 Printer API.
 *
 * @author cdea
 */
public class PrintDemo extends Application {
    @Override
    public void start(Stage primaryStage) {

        final TextField urlTextField = new TextField();
        final Button printButton = new Button("Print");
        final WebView webPage = new WebView();
        final WebEngine webEngine = webPage.getEngine();
        webEngine.load("http://www.bg-online.com.cn:9587/");

        HBox hbox = new HBox();
        hbox.getChildren().addAll(urlTextField, printButton);
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(hbox);
        borderPane.setCenter(webPage);
        Scene scene = new Scene(borderPane, 300, 250);
        primaryStage.setTitle("Print Demo");
        primaryStage.setScene(scene);

        // print button pressed, page loaded
        final BooleanProperty printButtonClickedProperty = new SimpleBooleanProperty(false);
        final BooleanProperty pageLoadedProperty = new SimpleBooleanProperty(false);

        // when the a page is loaded and the button was pressed call the print() method.
        final BooleanProperty printActionProperty = new SimpleBooleanProperty(false);
        printActionProperty.bind(pageLoadedProperty.and(printButtonClickedProperty));

        // WebEngine updates flag when finished loading web page.
        webEngine.getLoadWorker()
                .stateProperty()
                .addListener(new ChangeListener<State>() {
                    @Override
                    public void changed(ObservableValue<? extends State> observable, State oldValue, State newValue) {
                        if (newValue == State.SUCCEEDED) {
                            pageLoadedProperty.set(true);
                        }
                    }
                });

        // When user enters a url and hits the enter key.
        urlTextField.setOnAction( aEvent ->  {
            pageLoadedProperty.set(false);
            printButtonClickedProperty.set(false);
            webEngine.load(urlTextField.getText());
        });

        // When the user clicks the print button the webview node is printed
        printButton.setOnAction( aEvent -> {
//            printButtonClickedProperty.set(true);
            print(webPage);
        });

        // Once the print action hears a true go print the WebView node.
        printActionProperty.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    print(webPage);
                }
            }
        });

        primaryStage.show();

    }

    /** Scales the node based on the standard letter, portrait paper to be printed.
     * @param node The scene node to be printed.
     */
    public void print(final Node node) {
        Printer printer = Printer.getDefaultPrinter();
        PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, 0, 0, 0, 0);
        /*double scaleX = pageLayout.getPrintableWidth() / node.getBoundsInParent().getWidth();
        double scaleY = pageLayout.getPrintableHeight() / node.getBoundsInParent().getHeight();
        node.getTransforms().add(new Scale(scaleX, scaleY));*/

        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null) {
            boolean success = job.printPage(node);
            if (success) {
                job.endJob();
            }
        }
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}