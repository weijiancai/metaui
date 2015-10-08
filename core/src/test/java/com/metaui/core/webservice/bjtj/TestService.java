package com.metaui.core.webservice.bjtj;

import com.yunyin.client.YunYinWebServiceOutImpl;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class TestService {
    public static void main(String[] args) throws MalformedURLException {
        URL url = new URL("http://117.34.87.29/wllgs/pr2lg.cfc?wsdl");
        // 第一个参数是服务的URI
        // 第二个参数是在WSDL发布的服务名
        QName qname = new QName("http://wllgs","pr2lgService");
        // 创建服务
        Service service = Service.create(url, qname);
        // 提取端点接口，服务“端口”。
        QName qName = null;
        Iterator<QName> list = service.getPorts();
        while (list.hasNext()) {
            qName = list.next();
            System.out.println(qName);
        }
        YYWebService eif = service.getPort(qName, YYWebService.class);
        System.out.println(eif.wsCargo(getXml()));

/*
        URL url = new URL("http://115.29.163.55:9186/service/yunying/wsCargo?wsdl");
        // 第一个参数是服务的URI
        // 第二个参数是在WSDL发布的服务名
        QName qname = new QName("http://www.ectongs.com","YunYinWebService");
        // 创建服务
        Service service = Service.create(url, qname);
        // 提取端点接口，服务“端口”。
        QName qName = null;
        Iterator<QName> list = service.getPorts();
        while (list.hasNext()) {
            qName = list.next();
            System.out.println(qName);
        }
        YunYinWebServiceOutImpl eif = service.getPort(qName, YunYinWebServiceOutImpl.class);
        System.out.println(eif.wsCargo(getParam(getXml())));*/
    }

    public static String getXml() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" ?> \n" +
                "<root>\n" +
                "  <head>\n" +
                "  \t<publisher>5086</publisher> \n" +
                "  </head>\n" +
                "  <deals id=\"20110321151320\" type=\"100\" operation=\"0\">\n" +
                "  \t<deal>\n" +
                "  \t<result code=\"0\">成功</result> \n" +
                "  \t</deal>\n" +
                "  </deals>\n" +
                "</root>";
    }

    public static String getParam(String xml) {
        return "<cfsavecontent \n" +
                "variable=\"sXml\"><cfinclude template=\"#sFiletTempl#\">\n" +
                "</cfsavecontent>\n" +
                "<cfscript>\n" +
                "\tsXml=FormatStr(" + xml + ");\n" +
                "</cfscript>";
    }
}
