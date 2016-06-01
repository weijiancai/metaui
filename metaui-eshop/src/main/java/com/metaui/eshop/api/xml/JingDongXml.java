package com.metaui.eshop.api.xml;

import com.metaui.core.util.jaxb.JAXBUtil;
import com.metaui.eshop.api.ApiSiteName;
import com.metaui.eshop.api.ApiXml;
import com.metaui.eshop.api.domain.Category;
import com.metaui.eshop.moudle.EShopModule;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.util.List;

/**
 * 京东Api接口Xml存储
 *
 * @author wei_jc
 * @since 1.0.0
 * 2016/4/9.
 */
@XmlRootElement(name = "root")
public class JingDongXml implements ApiXml {
    private List<Category> categories;
    private File file;

    public JingDongXml() {
        file = new File(EShopModule.getPath(), ApiSiteName.JING_DONG.name() + ".xml");
    }

    @XmlElementWrapper(name = "categories")
    @XmlElement(name = "category")
    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    @Override
    public void save() throws Exception {
        JAXBUtil.marshalToFile(this, file, JingDongXml.class, Category.class);
    }

    @Override
    public void load() throws Exception {
        JingDongXml xml = JAXBUtil.unmarshal(file, JingDongXml.class);
        if (xml != null) {
            categories = xml.getCategories();
        }
    }
}
