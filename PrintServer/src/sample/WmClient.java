package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;
import javafx.util.Callback;
import org.xhtmlrenderer.simple.*;

import javax.print.PrintService;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.IOException;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class WmClient extends BorderPane {
    private ToolBar toolBar;
    private WebView webView;
    private PrintServiceModel model = PrintServiceModel.getInstance();

    public WmClient() {
        initUI();
    }

    private void initUI() {
        toolBar = new ToolBar();
        webView = new WebView();

        Button btnSetting = new Button("设置");
        btnSetting.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("setting.fxml"));
                    WmDialog.showCustomDialog(root, "设置", new Callback<Void, Void>() {
                        @Override
                        public Void call(Void param) {
                            webView.getEngine().load(model.getUrl());
                            return null;
                        }
                    });
                } catch (IOException e) {
                    WmDialog.showException(e);
                }
            }
        });
        toolBar.getItems().add(btnSetting);
        // 加载url
        webView.getEngine().load(PrintServiceModel.getInstance().getUrl());

        webView.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCharacter().equals("+")) {
                    Object packPrintUrl = webView.getEngine().executeScript("packageVerify.getPackPrintUrl();");
                    if (packPrintUrl != null && packPrintUrl.toString().length() > 0) {
//                        printURL(model.getPackPrintService(), packPrintUrl.toString(), 291, 226);
                        new WmPrinter(model.getPackPrintService(), packPrintUrl.toString());
                    }
                }
            }
        });

        this.setTop(toolBar);
        this.setCenter(webView);
    }

    private void printURL(PrintService printService, String url, int width, int height) {
        XHTMLPanel panel = new XHTMLPanel();
        panel.getSharedContext().setPrint(true);
        panel.getSharedContext().setInteractive(false);
        panel.getSharedContext().setBaseURL(model.getUrl());
        panel.getSharedContext().getTextRenderer();

        try {
            panel.setDocument(url);
        } catch (Exception e) {
            WmDialog.showException(e);
        }

        PrinterJob printJob = PrinterJob.getPrinterJob();

        PageFormat pf = new PageFormat();
        pf.setOrientation(PageFormat.PORTRAIT);
        // 通过Paper设置页面的空白边距和可打印区域。必须与实际打印纸张大小相符。
        Paper p = new Paper();
        p.setSize(width, height); // 纸张大小
        p.setImageableArea(0, 0, width, height); // A4(595 * 842) 设置打印区域，其实0，0应该是72，72，因为A4纸的默认X,Y边距是72
        pf.setPaper(p);

        printJob.setPrintable(new XHTMLPrintable(panel), pf);

        try {
            printJob.print();
        } catch (PrinterException e) {
            WmDialog.showException(e);
        }
    }
}
