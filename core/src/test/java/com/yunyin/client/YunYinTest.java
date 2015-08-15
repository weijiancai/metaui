package com.yunyin.client;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class YunYinTest {
    public static void main(String[] args) {
        YunYinWebService service = new YunYinWebService();
        String result = service.getYunYinWebServiceOutImplPort().wsCargo(getXml());
        System.out.println(result);
    }

    public static String getXml() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" ?> \n" +
                "<root>\n" +
                "  <head>\n" +
                "  \t<publisher>5086</publisher> \n" +
                "  </head>\n" +
                "  <deals id=\"20110321151320\" type=\"100\" operation=\"0\">\n" +
                "  \t<deal>\n" +
                "  \t<result code=\"0\">³É¹¦</result> \n" +
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
