package com.metaui.core.util.format;

import com.metaui.core.util.UString;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.StringWriter;

/**
 * XML代码美化
 *
 * @author wei_jc
 * @since 1.0
 */
public class XmlFormat implements CodeFormat {
    @Override
    public String format(String code) throws Exception {
        if (UString.isEmpty(code)) {
            return code;
        }

        Document doc = DocumentHelper.parseText(code);
        StringWriter writer = new StringWriter();
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("UTF-8");
        XMLWriter xmlwriter = new XMLWriter(writer, format);
        xmlwriter.write(doc);

        return doc.asXML();
    }
}
