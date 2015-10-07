package sample;

import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class PrintTest1 {
    public static void main(String[] args) throws PrintException, FileNotFoundException, MalformedURLException {
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
        for (PrintService service : services) {
            System.out.println(service.getName());
        }
        PrintService ps = PrintServiceLookup.lookupDefaultPrintService();

        FileInputStream fis = new FileInputStream("D:/1.html");
//        Doc doc = new SimpleDoc(new URL("http://115.29.163.55:9587/html/base/page/default_login.html") ,DocFlavor.URL.TEXT_HTML_HOST, null);
        Doc doc = new SimpleDoc(fis, DocFlavor.INPUT_STREAM.TEXT_HTML_HOST, null);

        DocPrintJob job = ps.createPrintJob();

        //获得打印属性
        PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
        //每一次默认打印一页
        pras.add(new Copies(1));
        job.print(doc, pras);
    }
}
