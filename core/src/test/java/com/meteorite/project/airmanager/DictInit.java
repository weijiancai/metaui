package com.meteorite.project.airmanager;

import com.metaui.core.datasource.db.util.JdbcTemplate;
import com.metaui.core.datasource.persist.MetaPDBFactory;
import com.metaui.core.dict.DictCategory;
import com.metaui.core.dict.DictCode;
import com.metaui.core.dict.DictManager;
import com.metaui.core.meta.MetaItemCategory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class DictInit {
    public static void init(JdbcTemplate template) throws Exception {
        Map<String, Object> conditionMap = new HashMap<String, Object>();
        conditionMap.put("id", "AirManager");
        template.delete(conditionMap, "mu_dz_category");

        DictCategory root = new DictCategory();
        root.setId("AirManager");
        root.setName("航空货运系统");
        root.setSystem(false);
        root.setPid("ROOT");

        template.save(MetaPDBFactory.getDictCategory(root));

        // 元数据项分类
        conditionMap.clear();
        conditionMap.put("id", "code_AirManager");
        template.delete(conditionMap, "mu_dz_code");

        DictCode code = new DictCode();
        code.setCategory(DictManager.getDict(MetaItemCategory.class));
        code.setId("code_AirManager");
        code.setName("code_AirManager");
        code.setDisplayName("航空货物系统");

        template.save(MetaPDBFactory.getDictCode(code));
    }

    public static void main(String[] args) throws Exception {
        JdbcTemplate template = new JdbcTemplate();
        try {
            // 初始化数据字典
            DictInit.init(template);

            template.commit();
        } finally {
            template.close();
        }
    }
}
