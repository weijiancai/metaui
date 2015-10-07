package com.meteorite.core.meta;

import com.alibaba.fastjson.JSON;
import com.metaui.core.config.ProjectConfig;
import com.metaui.core.config.SystemManager;
import com.metaui.core.datasource.db.util.JdbcTemplate;
import com.metaui.core.meta.MetaManager;
import com.metaui.core.meta.model.Meta;
import com.metaui.core.meta.model.MetaForm;
import com.metaui.core.ui.layout.property.TableFieldProperty;
import com.metaui.core.util.jaxb.JAXBUtil;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * @author wei_jc
 * @version 1.0.0
 */
public class MetaManagerTest {
    @Test
    public void testToMeta() throws Exception {
        JdbcTemplate template = new JdbcTemplate();
        try {
            MetaManager.toMeta(String.class, template);
        } catch (Exception e) {
            assertThat(e.getMessage(), equalTo("不能将非MetaElement的对象【java.lang.String】转化为Meta对象！"));
        }

        Meta meta = MetaManager.toMeta(TableFieldProperty.class, template);
        /*assertThat(meta, notNullValue());
        assertThat(meta.getName(), equalTo("ProjectConfig"));
        assertThat(meta.getDisplayName(), equalTo("项目配置"));
        assertThat(meta.getDesc(), equalTo(""));
        assertThat(meta.isValid(), equalTo(true));
        assertThat(meta.getFields().size(), equalTo(3));*/

        String metaXml = JAXBUtil.marshalToString(meta, Meta.class);
        System.out.println(metaXml);
        System.out.println(JSON.toJSONString(meta));
    }

    @Test
    public void testToForm() throws Exception {
        ProjectConfig projectConfig = SystemManager.getInstance().createProjectConfig("taobao");
        Meta meta = MetaManager.getMeta(ProjectConfig.class);
        MetaForm metaForm = MetaManager.toForm(meta);

        String metaXml = JAXBUtil.marshalToString(metaForm, MetaForm.class);
        System.out.println(metaXml);
    }

    @Test
    public void testMetaToJson() throws Exception {
        Meta meta = MetaManager.getMeta(ProjectConfig.class);
        System.out.println(JSON.toJSONString(meta));
    }
}
