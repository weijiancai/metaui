package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class PrintServiceModel {
    private ObservableList<String> printServices = FXCollections.observableArrayList();
    private String url;
    private String packPrintServiceName;
    private String sendPrintServiceName;

    private Preferences prefs;
    private PrintService[] services;
    private static PrintServiceModel instance;

    private PrintServiceModel() {
        List<String> list = new ArrayList<>();
        services = PrintServiceLookup.lookupPrintServices(null, null);
        for (PrintService service : services) {
            list.add(service.getName());
        }
        printServices.setAll(list);

        prefs = Preferences.userRoot().node(this.getClass().getName());
        load();
    }

    public ObservableList<String> getPrintServices() {
        return printServices;
    }

    public void load() {
        url = prefs.get("url", "");
        packPrintServiceName = prefs.get("packPrintServiceName", "");
        sendPrintServiceName = prefs.get("sendPrintServiceName", "");
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
        prefs.put("url", url);
    }

    public String getPackPrintServiceName() {
        return packPrintServiceName;
    }

    public void setPackPrintServiceName(String packPrintServiceName) {
        this.packPrintServiceName = packPrintServiceName;
        prefs.put("packPrintServiceName", packPrintServiceName);
    }

    public String getSendPrintServiceName() {
        return sendPrintServiceName;
    }

    public void setSendPrintServiceName(String sendPrintServiceName) {
        this.sendPrintServiceName = sendPrintServiceName;
        prefs.put("sendPrintServiceName", sendPrintServiceName);
    }

    public static PrintServiceModel getInstance() {
        if (instance == null) {
            instance = new PrintServiceModel();
        }
        return instance;
    }

    public PrintService getPackPrintService() {
        for (PrintService service : services) {
            if (service.getName().equals(packPrintServiceName)) {
                return service;
            }
        }
        return null;
    }
}
