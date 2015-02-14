package com.metaui.core.ui;

import com.metaui.core.config.SystemInfo;
import com.metaui.core.config.SystemManager;
import com.metaui.core.datasource.DataSourceManager;
import com.metaui.core.datasource.db.DBDataSource;
import com.metaui.core.datasource.db.util.JdbcTemplate;
import com.metaui.core.datasource.persist.MetaPDBFactory;
import com.metaui.core.datasource.persist.MetaRowMapperFactory;
import com.metaui.core.meta.model.Meta;
import com.metaui.core.meta.model.MetaField;
import com.metaui.core.ui.layout.property.FormProperty;
import com.metaui.core.ui.layout.property.TableProperty;
import com.metaui.core.ui.model.*;
import com.metaui.core.util.UString;
import com.metaui.core.util.UUIDUtil;

import java.util.*;

/**
 * 视图管理器
 *
 * @author wei_jc
 * @since  1.0.0
 */
public class ViewManager {
    private static List<View> viewList = new ArrayList<View>();
    private static Map<String, View> viewIdMap = new HashMap<String, View>();
    private static Map<String, View> viewNameMap = new HashMap<String, View>();
    private static Map<String, ViewLayout> viewLayoutMap = new HashMap<String, ViewLayout>();
    private static Map<String, ViewConfig> configMap = new HashMap<String, ViewConfig>();
    private static Map<String, ViewProperty> propertyMap = new HashMap<String, ViewProperty>();

    public static void load() throws Exception {
        SystemInfo sysInfo = SystemManager.getSystemInfo();
        DBDataSource dataSource = DataSourceManager.getSysDataSource();
        JdbcTemplate template = new JdbcTemplate(dataSource);

        // 从数据库中加载视图
        if (sysInfo.isViewInit()) {
            String sql = "SELECT * FROM mu_view";
            viewList = template.query(sql, MetaRowMapperFactory.getView());
            for (final View view : viewList) {
                addCache(view);
                // 查询视图布局
                /*sql = "SELECT * FROM mu_view_layout WHERE view_id=?";
                List<ViewLayout> viewLayoutList = template.query(sql, MetaRowMapperFactory.getViewLayout(view), view.getId());
                view.setLayoutList(viewLayoutList);
                for (ViewLayout viewLayout : viewLayoutList) {
                    viewLayoutMap.put(viewLayout.getId(), viewLayout);

                    // 查询视图配置
                    sql = "SELECT * FROM mu_view_config WHERE view_layout_id=?";
                    List<ViewConfig> configList = template.query(sql, MetaRowMapperFactory.getViewConfig(viewLayout), viewLayout.getId());
                    viewLayout.setConfigs(configList);
                    for (ViewConfig config : configList) {
                        configMap.put(config.getId(), config);
                        view.addConfig(config);
                    }
                }*/

                // 查询视图属性
                /*sql = "SELECT * FROM mu_view_prop WHERE view_id=?";
                List<ViewProperty> configList = template.query(sql, MetaRowMapperFactory.getViewProperty(view), view.getId());
                view.setViewProperties(configList);
                for (ViewProperty config : configList) {
                    propertyMap.put(config.getId(), config);
                }*/
            }

            sql = "SELECT * FROM mu_view_prop";
            List<ViewProperty> configList = template.query(sql, MetaRowMapperFactory.getViewProperty(null));
            for (ViewProperty config : configList) {
                View view = viewIdMap.get(config.getViewId());
                view.addViewProperty(config);
                config.setView(view);
                propertyMap.put(config.getId(), config);
            }
        } else {
            // 暂时不需要初始化
        }

        template.close();
    }

    public static void addCache(View view) throws Exception {
        // 如果缓存里已有，则先删除原来的视图
        View oldView = getViewByName(view.getName());
        if (oldView != null) {
            deleteCache(oldView);
        }
        viewIdMap.put(view.getId(), view);
        viewNameMap.put(view.getName(), view);
    }

    public static void deleteCache(View view) {
        viewIdMap.remove(view.getId());
        viewNameMap.remove(view.getName());
    }

    /**
     * 根据元数据和布局创建视图
     *
     * @param meta 元数据
     * @since 1.0.0
     */
    public static void createViews(Meta meta, JdbcTemplate template) throws Exception {
        System.out.println(String.format("创建Meta = 【%s, %s】视图", meta.getName(), meta.getDisplayName()));
        System.out.println(String.format("创建%sFormView", meta.getName()));
        View formView = FormProperty.createFormView(meta, false);
        formView.setMeta(meta);
        addView(formView, template);

        for (ViewProperty property : formView.getViewProperties()) {
//            template.save(MetaPDBFactory.getViewProperty(property));
        }

        System.out.println(String.format("创建%sTableView", meta.getName()));
        View tableView = TableProperty.createTableView(meta);
        tableView.setMeta(meta);
        addView(tableView, template);

        for (ViewProperty property : tableView.getViewProperties()) {
//            template.save(MetaPDBFactory.getViewProperty(property));
        }

        System.out.println(String.format("创建%sQueryView", meta.getName()));
        View queryView = FormProperty.createFormView(meta, true);
        queryView.setMeta(meta);
        addView(queryView, template);

        for (ViewProperty property : queryView.getViewProperties()) {
//            template.save(MetaPDBFactory.getViewProperty(property));
        }

        /*System.out.println(String.format("创建%sCrudView", meta.getName()));
        View crudView = CrudProperty.createCrudView(meta, formView, tableView, queryView);
        crudView.setMeta(meta);
        addView(crudView, template);

        for (ViewProperty property : crudView.getViewProperties()) {
//            template.save(MetaPDBFactory.getViewProperty(property));
        }*/

        System.out.println(String.format("创建视图完成"));
        System.out.println("--------------------------------------------------------------------------------");
    }

    private static void addView(View view, JdbcTemplate template) throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("name", view.getName());
        template.delete(params, "mu_view");
        template.save(MetaPDBFactory.getView(view));
        addCache(view);
    }

    /**
     * 根据元数据创建视图
     *
     * @param meta 元数据
     * @since 1.0.0
     */
    public static void createView(Meta meta) throws Exception {
        JdbcTemplate template = new JdbcTemplate();
        try {
            createViews(meta, template);
            template.commit();
        } finally {
            template.close();
        }
    }

    public static ViewLayout createViewLayout(Meta meta, Layout layout) {
        ViewLayout viewLayout = new ViewLayout();
        viewLayout.setId(UUIDUtil.getUUID());
        viewLayout.setMeta(meta);
        viewLayout.setLayout(layout);

        List<ViewConfig> configList = new ArrayList<ViewConfig>();

        // 创建属性配置
        if (layout.getProperties() != null) {
            for (LayoutProperty property : layout.getProperties()) {
                switch (property.getPropType()) {
                    case MP: {
                        configList.add(createViewConfig(viewLayout, property, null, null));
                        break;
                    }
                    case IP: {
                        for (MetaField field : meta.getFields()) {
                            configList.add(createViewConfig(viewLayout, property, field, null));
                        }
                    }
                }
            }
        }
        viewLayout.setConfigs(configList);

        return viewLayout;
    }

    public static ViewConfig createViewConfig(ViewLayout viewLayout, LayoutProperty property, MetaField field, String value) {
        ViewConfig config = new ViewConfig();
        config.setId(UUIDUtil.getUUID());
        if (field != null) {
            config.setField(field);
        }
        config.setProperty(property);
        config.setViewLayout(viewLayout);
        config.setValue(UString.isEmpty(value) ? property.getDefaultValue() : value);

        return config;
    }

    /**
     * 根据视图ID，获得视图信息
     *
     * @param viewId 视图ID
     * @return 返回视图信息
     * @since 1.0.0
     */
    public static View getViewById(String viewId) {
        return viewIdMap.get(viewId);
    }

    /**
     * 根据视图名称，获得视图信息
     *
     * @param viewName 视图名称
     * @return 返回视图信息
     * @since 1.0.0
     */
    public static View getViewByName(String viewName) {
        return viewNameMap.get(viewName);
    }

    /**
     * 根据元数据，获得表单视图信息
     *
     * @param meta 元数据
     * @return 返回视图信息
     * @since 1.0.0
     */
    public static View getFormView(Meta meta) {
        return getViewByName(meta.getName() + "FormView");
    }

    /**
     * 获得所有视图列表
     *
     * @return 返回视图列表
     */
    public static List<View> getViewList() {
        return viewList;
    }

    /**
     * 获得系统视图列表
     *
     * @return 返回视图列表
     */
    public static List<View> getSystemViewList() {
        List<View> list = new ArrayList<View>();
        for (String viewName : SystemManager.getSystemProperty("viewList").split(",")) {
            list.add(getViewByName(viewName));
        }

        return list;
    }
}
