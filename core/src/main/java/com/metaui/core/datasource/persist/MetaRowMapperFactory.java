package com.metaui.core.datasource.persist;

import com.metaui.core.config.ProfileSetting;
import com.metaui.core.datasource.*;
import com.metaui.core.datasource.db.*;
import com.metaui.core.datasource.db.object.enums.DBObjectType;
import com.metaui.core.dict.DictCategory;
import com.metaui.core.dict.DictCode;
import com.metaui.core.meta.MetaDataType;
import com.metaui.core.meta.MetaManager;
import com.metaui.core.meta.model.Meta;
import com.metaui.core.meta.model.MetaField;
import com.metaui.core.meta.model.MetaItem;
import com.metaui.core.meta.model.MetaReference;
import com.metaui.core.project.NavMenu;
import com.metaui.core.project.ProjectDefine;
import com.metaui.core.project.tpl.CodeTpl;
import com.metaui.core.ui.layout.LayoutManager;
import com.metaui.core.ui.layout.LayoutType;
import com.metaui.core.ui.layout.PropertyType;
import com.metaui.core.ui.model.*;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class MetaRowMapperFactory {
    public static RowMapper<DataSource> getDataSource() {
        return new RowMapper<DataSource>() {
            @Override
            public DataSource mapRow(ResultSet rs) throws SQLException {
                DataSource dataSource;
                DataSourceType type = DataSourceType.get(rs.getString("type"));
                String url = rs.getString("url");

                if (DataSourceType.DATABASE == type) {
                    dataSource = new DBDataSource();
                } else {
                    dataSource = new DefaultDataSource();
                }

                dataSource.setId(rs.getString("id"));
                dataSource.setName(rs.getString("name"));
                dataSource.setDisplayName(rs.getString("display_name"));
                dataSource.setDescription(rs.getString("description"));
                dataSource.setValid("T".equals(rs.getString("is_valid")));
                dataSource.setInputDate(rs.getDate("input_date"));
                dataSource.setSortNum(rs.getInt("sort_num"));
                dataSource.setHost(rs.getString("host"));
                dataSource.setPort(rs.getInt("port"));
                dataSource.setUserName(rs.getString("user_name"));
                dataSource.setPwd(rs.getString("pwd"));
                dataSource.setType(type);
                dataSource.setUrl(url);

                return dataSource;
            }
        };
    }

    public static RowMapper<ProfileSetting> getProfileSetting() {
        return new RowMapper<ProfileSetting>() {
            @Override
            public ProfileSetting mapRow(ResultSet rs) throws SQLException {
                ProfileSetting setting = new ProfileSetting();
                setting.setConfSection(rs.getString("conf_section"));
                setting.setConfKey(rs.getString("conf_key"));
                setting.setConfValue(rs.getString("conf_value"));
                setting.setSortNum(rs.getInt("sort_num"));
                setting.setMemo(rs.getString("memo"));

                return setting;
            }
        };
    }

    public static RowMapper<Meta> getMeta() {
        return new RowMapper<Meta>() {
            @Override
            public Meta mapRow(ResultSet rs) throws SQLException {
                Meta meta = new Meta();

                meta.setId(rs.getString("id"));
                meta.setName(rs.getString("name"));
                meta.setDisplayName(rs.getString("display_name"));
                meta.setDescription(rs.getString("description"));
                meta.setValid("T".equals(rs.getString("is_valid")));
                meta.setInputDate(rs.getDate("input_date"));
                meta.setSortNum(rs.getInt("sort_num"));
                meta.setSqlText(rs.getString("sql_text"));
                String rsId = rs.getString("rs_id");
                meta.setRsId(rsId);
                meta.setResource(ResourceManager.getResourceById(rsId));

                return meta;
            }
        };
    }

    public static RowMapper<MetaField> getMetaField(final Meta meta) {
        return new RowMapper<MetaField>() {
            @Override
            public MetaField mapRow(ResultSet rs) throws SQLException {
                MetaField field = new MetaField();

                field.setMeta(meta);
                field.setId(rs.getString("id"));
                field.setName(rs.getString("name"));
                field.setDisplayName(rs.getString("display_name"));
                field.setDataType(MetaDataType.getDataType(rs.getString("data_type")));
                field.setDefaultValue(rs.getString("default_value"));
                field.setDescription(rs.getString("description"));
                field.setDictId(rs.getString("dict_id"));
                field.setValid("T".equals(rs.getString("is_valid")));
                field.setInputDate(rs.getDate("input_date"));
                field.setSortNum(rs.getInt("sort_num"));
                field.setMetaId(rs.getString("meta_id"));

                field.setOriginalName(rs.getString("original_name"));
                field.setMaxLength(rs.getInt("max_length"));
                field.setPk("T".equals(rs.getString("is_pk")));
                field.setFk("T".equals(rs.getString("is_fk")));
                field.setRequire("T".equals(rs.getString("is_require")));

                return field;
            }
        };
    }

    public static RowMapper<MetaItem> getMetaItem() {
        return new RowMapper<MetaItem>() {
            @Override
            public MetaItem mapRow(ResultSet rs) throws SQLException {
                MetaItem field = new MetaItem();

                field.setId(rs.getString("id"));
                field.setName(rs.getString("name"));
                field.setDisplayName(rs.getString("display_name"));
                field.setDataType(MetaDataType.getDataType(rs.getString("data_type")));
                field.setDescription(rs.getString("description"));
                field.setCategory(rs.getString("category"));
                field.setValid("T".equals(rs.getString("is_valid")));
                field.setInputDate(rs.getDate("input_date"));
                field.setSortNum(rs.getInt("sort_num"));
                field.setMaxLength(rs.getInt("max_length"));

                return field;
            }
        };
    }

    public static RowMapper<MetaReference> getMetaReference() {
        return new RowMapper<MetaReference>() {
            @Override
            public MetaReference mapRow(ResultSet rs) throws SQLException {
                MetaReference metaRef = new MetaReference();

                metaRef.setId(rs.getString("id"));
                metaRef.setPkMetaId(rs.getString("pk_meta_id"));
                metaRef.setPkMetaFieldId(rs.getString("pk_meta_field_id"));
                metaRef.setFkMetaId(rs.getString("fk_meta_id"));
                metaRef.setFkMetaFieldId(rs.getString("fk_meta_field_id"));
                metaRef.setPkMeta(MetaManager.getMetaById(rs.getString("pk_meta_id")));
                metaRef.setPkMetaField(MetaManager.getMetaField(rs.getString("pk_meta_field_id")));
                metaRef.setFkMeta(MetaManager.getMetaById(rs.getString("fk_meta_id")));
                metaRef.setFkMetaField(MetaManager.getMetaField(rs.getString("fk_meta_field_id")));

                return metaRef;
            }
        };
    }

    public static RowMapper<Layout> getLayout() {
        return new RowMapper<Layout>() {
            @Override
            public Layout mapRow(ResultSet rs) throws SQLException {
                Layout layout = new Layout();

                layout.setId(rs.getString("id"));
                layout.setPid(rs.getString("pid"));
                layout.setName(rs.getString("name"));
                layout.setDisplayName(rs.getString("display_name"));
                layout.setDescription(rs.getString("description"));
                layout.setRefId(rs.getString("ref_id"));
                layout.setValid("T".equals(rs.getString("is_valid")));
                layout.setInputDate(rs.getDate("input_date"));
                layout.setSortNum(rs.getInt("sort_num"));

                return layout;
            }
        };
    }

    public static RowMapper<LayoutProperty> getLayoutProperty(final Layout layout) {
        return new RowMapper<LayoutProperty>() {
            @Override
            public LayoutProperty mapRow(ResultSet rs) throws SQLException {
                LayoutProperty prop = new LayoutProperty();

                prop.setLayout(layout);
                prop.setId(rs.getString("id"));
                prop.setName(rs.getString("name"));
                prop.setDisplayName(rs.getString("display_name"));
                prop.setDefaultValue(rs.getString("default_value"));
                prop.setPropType(PropertyType.getType(rs.getString("prop_type")));
                prop.setDescription(rs.getString("description"));
                prop.setSortNum(rs.getInt("sort_num"));
                prop.setLayoutType(LayoutType.valueOf(rs.getString("layout_type")));

                return prop;
            }
        };
    }

    public static RowMapper<DictCategory> getDictCategory() {
        return new RowMapper<DictCategory>() {
            @Override
            public DictCategory mapRow(ResultSet rs) throws SQLException {
                DictCategory category = new DictCategory();

                category.setId(rs.getString("id"));
                category.setName(rs.getString("name"));
                category.setSystem("T".equals(rs.getString("is_system")));
                category.setDescription(rs.getString("description"));
                category.setPid(rs.getString("pid"));
                category.setValid("T".equals(rs.getString("is_valid")));
                category.setInputDate(rs.getDate("input_date"));
                category.setSortNum(rs.getInt("sort_num"));

                return category;
            }
        };
    }

    public static RowMapper<DictCode> getDictCode(final DictCategory category) {
        return new RowMapper<DictCode>() {
            @Override
            public DictCode mapRow(ResultSet rs) throws SQLException {
                DictCode code = new DictCode();

                code.setCategory(category);
                code.setId(rs.getString("id"));
                code.setName(rs.getString("name"));
                code.setDisplayName(rs.getString("display_name"));
                code.setCategoryId(rs.getString("category_id"));
                code.setDescription(rs.getString("description"));
                code.setValid("T".equals(rs.getString("is_valid")));
                code.setInputDate(rs.getDate("input_date"));
                code.setSortNum(rs.getInt("sort_num"));

                return code;
            }
        };
    }

    public static RowMapper<View> getView() {
        return new RowMapper<View>() {
            @Override
            public View mapRow(ResultSet rs) throws SQLException {
                View view = new View();

                view.setId(rs.getString("id"));
                view.setName(rs.getString("name"));
                view.setDisplayName(rs.getString("display_name"));
                view.setDescription(rs.getString("description"));
                view.setMeta(MetaManager.getMetaById(rs.getString("meta_id")));
                view.setMetaId(rs.getString("meta_id"));
                view.setValid("T".equals(rs.getString("is_valid")));
                view.setInputDate(rs.getDate("input_date"));
                view.setSortNum(rs.getInt("sort_num"));

                return view;
            }
        };
    }

    public static RowMapper<ViewLayout> getViewLayout(final View view) {
        return new RowMapper<ViewLayout>() {
            @Override
            public ViewLayout mapRow(ResultSet rs) throws SQLException {
                ViewLayout viewLayout = new ViewLayout();

                viewLayout.setId(rs.getString("id"));
                viewLayout.setView(view);
                viewLayout.setLayout(LayoutManager.getLayoutById(rs.getString("layout_id")));
                viewLayout.setMeta(MetaManager.getMetaById(rs.getString("meta_id")));

                return viewLayout;
            }
        };
    }

    public static RowMapper<ViewConfig> getViewConfig(final ViewLayout viewLayout) {
        return new RowMapper<ViewConfig>() {
            @Override
            public ViewConfig mapRow(ResultSet rs) throws SQLException {
                ViewConfig config = new ViewConfig();

                config.setId(rs.getString("id"));
                config.setViewLayout(viewLayout);
                config.setProperty(LayoutManager.getLayoutPropById(rs.getString("prop_id")));
                config.setField(MetaManager.getMetaField(rs.getString("meta_field_id")));
                config.setValue(rs.getString("value"));

                return config;
            }
        };
    }

    public static RowMapper<ViewProperty> getViewProperty(final View view) {
        return new RowMapper<ViewProperty>() {
            @Override
            public ViewProperty mapRow(ResultSet rs) throws SQLException {
                ViewProperty property = new ViewProperty();

                property.setId(rs.getString("id"));
                property.setView(view);
                property.setViewId(rs.getString("view_id"));
                property.setLayoutPropId(rs.getString("layout_prop_id"));
                property.setMetaFieldId(rs.getString("meta_field_id"));
                property.setValue(rs.getString("value"));
                property.setProperty(LayoutManager.getLayoutPropById(rs.getString("layout_prop_id")));
                property.setField(MetaManager.getMetaField(rs.getString("meta_field_id")));

                return property;
            }
        };
    }

    public static RowMapper<ProjectDefine> getProjectDefine() {
        return new RowMapper<ProjectDefine>() {
            @Override
            public ProjectDefine mapRow(ResultSet rs) throws SQLException {
                ProjectDefine project = new ProjectDefine();

                project.setId(rs.getString("id"));
                project.setName(rs.getString("name"));
                project.setDisplayName(rs.getString("display_name"));
                project.setDescription(rs.getString("description"));
                project.setPackageName(rs.getString("package_name"));
                project.setProjectUrl(rs.getString("project_url"));
                project.setValid("T".equals(rs.getString("is_valid")));
                project.setInputDate(rs.getDate("input_date"));
                project.setSortNum(rs.getInt("sort_num"));

                return project;
            }
        };
    }

    public static RowMapper<NavMenu> getNavMenu() {
        return new RowMapper<NavMenu>() {
            @Override
            public NavMenu mapRow(ResultSet rs) throws SQLException {
                NavMenu nav = new NavMenu();

                nav.setId(rs.getString("id"));
                nav.setName(rs.getString("name"));
                nav.setDisplayName(rs.getString("display_name"));
                nav.setIcon(rs.getString("icon"));
                nav.setUrl(rs.getString("url"));
                nav.setPid(rs.getString("pid"));
                nav.setLevel(rs.getInt("level"));
                nav.setProjectId(rs.getString("project_id"));
                nav.setValid("T".equals(rs.getString("is_valid")));
                nav.setInputDate(rs.getDate("input_date"));
                nav.setSortNum(rs.getInt("sort_num"));

                return nav;
            }
        };
    }

    public static RowMapper<CodeTpl> getCodeTpl() {
        return new RowMapper<CodeTpl>() {
            @Override
            public CodeTpl mapRow(ResultSet rs) throws SQLException {
                CodeTpl codeTpl = new CodeTpl();

                codeTpl.setId(rs.getString("id"));
                codeTpl.setName(rs.getString("name"));
                codeTpl.setDisplayName(rs.getString("display_name"));
                codeTpl.setDescription(rs.getString("description"));
                codeTpl.setProjectId(rs.getString("project_id"));
                codeTpl.setFileName(rs.getString("file_name"));
                codeTpl.setFilePath(rs.getString("file_path"));
                codeTpl.setTplContent(rs.getString("tpl_content"));
                codeTpl.setValid("T".equals(rs.getString("is_valid")));
                codeTpl.setSortNum(rs.getInt("sort_num"));
                codeTpl.setInputDate(rs.getDate("input_date"));
                codeTpl.setTplFile(rs.getString("tpl_file"));

                return codeTpl;
            }
        };
    }

    public static RowMapper<DbmsObject> getDbmsObject() {
        return new RowMapper<DbmsObject>() {
            @Override
            public DbmsObject mapRow(ResultSet rs) throws SQLException {
                DbmsObject dbmsObject = new DbmsObject();

                dbmsObject.setId(rs.getString("id"));
                dbmsObject.setName(rs.getString("name"));
                dbmsObject.setDbComment(rs.getString("db_comment"));
                dbmsObject.setDbObjType(DBObjectType.valueOf(rs.getString("db_obj_type")));
                dbmsObject.setPid(rs.getString("pid"));
                dbmsObject.setDataType(rs.getString("data_type"));
                dbmsObject.setPosition(rs.getInt("position"));
                dbmsObject.setDefaultValue(rs.getString("default_value"));
                dbmsObject.setNullable("T".equals(rs.getString("is_nullable")));
                dbmsObject.setMaxLength(rs.getInt("max_length"));
                dbmsObject.setNumericPrecision(rs.getInt("numeric_precision"));
                dbmsObject.setNumericScale(rs.getInt("numeric_scale"));
                dbmsObject.setPk("T".equals(rs.getString("is_pk")));
                dbmsObject.setFk("T".equals(rs.getString("is_fk")));
                dbmsObject.setFkColumnId(rs.getString("fk_column_id"));
                dbmsObject.setInputDate(rs.getDate("input_date"));

                return dbmsObject;
            }
        };
    }
}
