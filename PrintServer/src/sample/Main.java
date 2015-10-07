package sample;

import com.sun.javafx.print.Units;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.print.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(final Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        /*final WebView view = new WebView();
        view.getEngine().load("http://www.bg-online.com.cn:9587/");
        view.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (!event.getCharacter().equals("\r")) {
                    return;
                }
                System.out.println(event.getCode());
                SnapshotParameters parameters = new SnapshotParameters();
                // 包贴大小 width 283 height 226
                WritableImage image = new WritableImage(340, 226);
                view.snapshot(parameters, image);
                try {
                    ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", new File("D:/2.png"));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                PrinterJob job = PrinterJob.createPrinterJob();
//                Paper paper = new Paper("包贴", 100.0, 80.0, Units.MM);
                job.getPrinter().createPageLayout(Paper.NA_LETTER, PageOrientation.PORTRAIT, javafx.print.Printer.MarginType.DEFAULT);
                JobSettings settings = job.getJobSettings();
                job.printPage(view);
                job.getPrinter();
                job.endJob();
//                view.getEngine().print(job);
            }
        });*/

        /*SnapshotParameters parameters = new SnapshotParameters();
        // 包贴大小 width 283 height 226
        WritableImage image = new WritableImage(283, 226);
        view.snapshot(parameters, image);
        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", new File("D:/1.png"));*/
        WmClient client = new WmClient();

        primaryStage.setScene(new Scene(client, 1500, 800));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
