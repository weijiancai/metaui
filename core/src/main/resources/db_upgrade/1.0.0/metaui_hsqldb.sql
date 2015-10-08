drop table if exists mu_code_tpl;
drop table if exists mu_dbms_object;
drop table if exists mu_db_datasource;
drop table if exists mu_db_mobile_number;
drop table if exists mu_db_version;
drop table if exists mu_dz_category;
drop table if exists mu_dz_code;
drop table if exists mu_layout;
drop table if exists mu_layout_prop;
drop table if exists mu_meta;
drop table if exists mu_meta_field;
drop table if exists mu_meta_item;
drop table if exists mu_meta_obj;
drop table if exists mu_meta_obj_value;
drop table if exists mu_meta_reference;
drop table if exists mu_meta_sql;
drop table if exists mu_module;
drop table if exists mu_nav_menu;
drop table if exists mu_pm_user;
drop table if exists mu_profile_setting;
drop table if exists mu_project_define;
drop table if exists mu_view;
drop table if exists mu_view_config;
drop table if exists mu_view_prop;

drop index iux_dzCategory_name if exists;
drop index idx_view_config_prop if exists;
drop index iux_view_prop if exists;

/*==============================================================*/
/* Table: mu_code_tpl                                      */
/*==============================================================*/
create table mu_code_tpl
(
    id                               varchar(32)     not null,
    name                             varchar(64)     not null,
    display_name                     varchar(128)    not null,
    description                      varchar(1024)   ,
    project_id                       varchar(32)     not null,
    file_name                        varchar(128)    not null,
    file_path                        varchar(256)    ,
    tpl_file                         varchar(256)    ,
    tpl_content                      clob            ,
    is_valid                         char(1)         not null,
    sort_num                         int             not null,
    input_date                       date            not null,
    primary key (id)
);
comment on table mu_code_tpl is 'mu_code_tpl';
comment on column mu_code_tpl.id is 'id';
comment on column mu_code_tpl.name is 'name';
comment on column mu_code_tpl.display_name is 'display_name';
comment on column mu_code_tpl.description is 'description';
comment on column mu_code_tpl.project_id is 'project_id';
comment on column mu_code_tpl.file_name is 'file_name';
comment on column mu_code_tpl.file_path is 'file_path';
comment on column mu_code_tpl.tpl_file is 'tpl_file';
comment on column mu_code_tpl.tpl_content is 'tpl_content';
comment on column mu_code_tpl.is_valid is 'is_valid';
comment on column mu_code_tpl.sort_num is 'sort_num';
comment on column mu_code_tpl.input_date is 'input_date';

/*==============================================================*/
/* Table: mu_dbms_object                                      */
/*==============================================================*/
create table mu_dbms_object
(
    id                               varchar(32)     not null,
    name                             varchar(128)    not null,
    db_comment                       varchar(1024)   ,
    db_obj_type                      varchar(64)     not null,
    pid                              varchar(32)     ,
    data_type                        varchar(64)     ,
    position                         int             ,
    default_value                    varchar(128)    ,
    is_nullable                      char(1)         ,
    max_length                       int             ,
    numeric_precision                int             ,
    numeric_scale                    int             ,
    is_pk                            char(1)         not null,
    is_fk                            char(1)         not null,
    fk_column_id                     varchar(32)     ,
    input_date                       date            not null,
    primary key (id)
);
comment on table mu_dbms_object is 'mu_dbms_object';
comment on column mu_dbms_object.id is 'id';
comment on column mu_dbms_object.name is 'name';
comment on column mu_dbms_object.db_comment is 'db_comment';
comment on column mu_dbms_object.db_obj_type is 'db_obj_type';
comment on column mu_dbms_object.pid is 'pid';
comment on column mu_dbms_object.data_type is 'data_type';
comment on column mu_dbms_object.position is 'position';
comment on column mu_dbms_object.default_value is 'default_value';
comment on column mu_dbms_object.is_nullable is 'is_nullable';
comment on column mu_dbms_object.max_length is 'max_length';
comment on column mu_dbms_object.numeric_precision is 'numeric_precision';
comment on column mu_dbms_object.numeric_scale is 'numeric_scale';
comment on column mu_dbms_object.is_pk is 'is_pk';
comment on column mu_dbms_object.is_fk is 'is_fk';
comment on column mu_dbms_object.fk_column_id is 'fk_column_id';
comment on column mu_dbms_object.input_date is 'input_date';

/*==============================================================*/
/* Table: mu_db_datasource                                      */
/*==============================================================*/
create table mu_db_datasource
(
    id                               varchar(32)     not null,
    name                             varchar(128)    not null,
    display_name                     varchar(128)    ,
    type                             varchar(32)     not null,
    description                      varchar(1024)   ,
    url                              varchar(1024)   ,
    host                             varchar(64)     ,
    port                             int             ,
    user_name                        varchar(64)     ,
    pwd                              varchar(64)     ,
    is_valid                         char(1)         not null,
    sort_num                         int             not null,
    input_date                       date            not null,
    primary key (id)
);
comment on table mu_db_datasource is 'mu_db_datasource';
comment on column mu_db_datasource.id is 'id';
comment on column mu_db_datasource.name is 'name';
comment on column mu_db_datasource.display_name is 'display_name';
comment on column mu_db_datasource.type is 'type';
comment on column mu_db_datasource.description is 'description';
comment on column mu_db_datasource.url is 'url';
comment on column mu_db_datasource.host is 'host';
comment on column mu_db_datasource.port is 'port';
comment on column mu_db_datasource.user_name is 'user_name';
comment on column mu_db_datasource.pwd is 'pwd';
comment on column mu_db_datasource.is_valid is 'is_valid';
comment on column mu_db_datasource.sort_num is 'sort_num';
comment on column mu_db_datasource.input_date is 'input_date';

/*==============================================================*/
/* Table: mu_db_mobile_number                                      */
/*==============================================================*/
create table mu_db_mobile_number
(
    code                             char(11)        not null,
    province                         varchar(64)     ,
    city                             varchar(64)     ,
    card_type                        varchar(64)     ,
    operators                        varchar(64)     ,
    code_segment                     varchar(11)     ,
    primary key (code)
);
comment on table mu_db_mobile_number is 'mu_db_mobile_number';
comment on column mu_db_mobile_number.code is 'code';
comment on column mu_db_mobile_number.province is 'province';
comment on column mu_db_mobile_number.city is 'city';
comment on column mu_db_mobile_number.card_type is 'card_type';
comment on column mu_db_mobile_number.operators is 'operators';
comment on column mu_db_mobile_number.code_segment is 'code_segment';

/*==============================================================*/
/* Table: mu_db_version                                      */
/*==============================================================*/
create table mu_db_version
(
    sys_name                         varchar(64)     not null,
    db_version                       varchar(11)     ,
    input_date                       date            not null,
    memo                             varchar(1024)   ,
    primary key (sys_name)
);
comment on table mu_db_version is 'mu_db_version';
comment on column mu_db_version.sys_name is 'sys_name';
comment on column mu_db_version.db_version is 'db_version';
comment on column mu_db_version.input_date is 'input_date';
comment on column mu_db_version.memo is 'memo';

/*==============================================================*/
/* Table: mu_dz_category                                      */
/*==============================================================*/
create table mu_dz_category
(
    id                               varchar(32)     not null,
    name                             varchar(64)     not null,
    description                      varchar(1024)   ,
    pid                              varchar(32)     ,
    db_table                         varchar(128)    ,
    table_id_col                     varchar(64)     ,
    table_name_col                   varchar(64)     ,
    sql_text                         varchar(1024)   ,
    is_system                        char(1)         not null,
    is_valid                         char(1)         not null,
    sort_num                         int             not null,
    input_date                       date            not null,
    primary key (id)
);
comment on table mu_dz_category is 'mu_dz_category';
comment on column mu_dz_category.id is 'id';
comment on column mu_dz_category.name is 'name';
comment on column mu_dz_category.description is 'description';
comment on column mu_dz_category.pid is 'pid';
comment on column mu_dz_category.db_table is 'db_table';
comment on column mu_dz_category.table_id_col is 'table_id_col';
comment on column mu_dz_category.table_name_col is 'table_name_col';
comment on column mu_dz_category.sql_text is 'sql_text';
comment on column mu_dz_category.is_system is 'is_system';
comment on column mu_dz_category.is_valid is 'is_valid';
comment on column mu_dz_category.sort_num is 'sort_num';
comment on column mu_dz_category.input_date is 'input_date';

/*==============================================================*/
/* Table: mu_dz_code                                      */
/*==============================================================*/
create table mu_dz_code
(
    id                               varchar(32)     not null,
    category_id                      varchar(32)     not null,
    name                             varchar(128)    not null,
    display_name                     varchar(128)    not null,
    description                      varchar(1024)   ,
    is_valid                         char(1)         not null,
    input_date                       date            not null,
    sort_num                         int             not null,
    primary key (id)
);
comment on table mu_dz_code is 'mu_dz_code';
comment on column mu_dz_code.id is 'id';
comment on column mu_dz_code.category_id is 'category_id';
comment on column mu_dz_code.name is 'name';
comment on column mu_dz_code.display_name is 'display_name';
comment on column mu_dz_code.description is 'description';
comment on column mu_dz_code.is_valid is 'is_valid';
comment on column mu_dz_code.input_date is 'input_date';
comment on column mu_dz_code.sort_num is 'sort_num';

/*==============================================================*/
/* Table: mu_layout                                      */
/*==============================================================*/
create table mu_layout
(
    id                               varchar(32)     not null,
    pid                              varchar(32)     ,
    name                             varchar(64)     not null,
    display_name                     varchar(128)    not null,
    description                      varchar(1024)   ,
    ref_id                           varchar(32)     ,
    is_valid                         char(1)         not null,
    input_date                       date            not null,
    sort_num                         int             not null,
    primary key (id)
);
comment on table mu_layout is 'mu_layout';
comment on column mu_layout.id is 'id';
comment on column mu_layout.pid is 'pid';
comment on column mu_layout.name is 'name';
comment on column mu_layout.display_name is 'display_name';
comment on column mu_layout.description is 'description';
comment on column mu_layout.ref_id is 'ref_id';
comment on column mu_layout.is_valid is 'is_valid';
comment on column mu_layout.input_date is 'input_date';
comment on column mu_layout.sort_num is 'sort_num';

/*==============================================================*/
/* Table: mu_layout_prop                                      */
/*==============================================================*/
create table mu_layout_prop
(
    id                               varchar(32)     not null,
    layout_type                      varchar(32)     not null,
    layout_id                        varchar(32)     ,
    name                             varchar(64)     not null,
    display_name                     varchar(128)    not null,
    default_value                    varchar(128)    ,
    prop_type                        char(2)         not null,
    description                      varchar(1024)   ,
    sort_num                         int             not null,
    primary key (id)
);
comment on table mu_layout_prop is 'mu_layout_prop';
comment on column mu_layout_prop.id is 'id';
comment on column mu_layout_prop.layout_type is 'layout_type';
comment on column mu_layout_prop.layout_id is 'layout_id';
comment on column mu_layout_prop.name is 'name';
comment on column mu_layout_prop.display_name is 'display_name';
comment on column mu_layout_prop.default_value is 'default_value';
comment on column mu_layout_prop.prop_type is 'prop_type';
comment on column mu_layout_prop.description is 'description';
comment on column mu_layout_prop.sort_num is 'sort_num';

/*==============================================================*/
/* Table: mu_meta                                      */
/*==============================================================*/
create table mu_meta
(
    id                               varchar(32)     not null,
    name                             varchar(128)    not null,
    display_name                     varchar(128)    ,
    description                      varchar(1024)   ,
    is_valid                         char(1)         not null,
    input_date                       date            not null,
    sort_num                         int             not null,
    rs_id                            varchar(128)    ,
    primary key (id)
);
comment on table mu_meta is 'mu_meta';
comment on column mu_meta.id is 'id';
comment on column mu_meta.name is 'name';
comment on column mu_meta.display_name is 'display_name';
comment on column mu_meta.description is 'description';
comment on column mu_meta.is_valid is 'is_valid';
comment on column mu_meta.input_date is 'input_date';
comment on column mu_meta.sort_num is 'sort_num';
comment on column mu_meta.rs_id is 'rs_id';

/*==============================================================*/
/* Table: mu_meta_field                                      */
/*==============================================================*/
create table mu_meta_field
(
    id                               varchar(32)     not null,
    meta_id                          varchar(32)     not null,
    meta_item_id                     varchar(32)     ,
    name                             varchar(128)    not null,
    display_name                     varchar(64)     ,
    data_type                        varchar(64)     not null,
    dict_id                          varchar(32)     ,
    original_name                    varchar(128)    ,
    max_length                       int             ,
    is_pk                            char(1)         not null,
    is_fk                            char(1)         not null,
    is_require                       char(1)         not null,
    description                      varchar(1024)   ,
    default_value                    varchar(256)    ,
    is_valid                         char(1)         not null,
    sort_num                         int             not null,
    input_date                       date            not null,
    primary key (id)
);
comment on table mu_meta_field is 'mu_meta_field';
comment on column mu_meta_field.id is 'id';
comment on column mu_meta_field.meta_id is 'meta_id';
comment on column mu_meta_field.meta_item_id is 'meta_item_id';
comment on column mu_meta_field.name is 'name';
comment on column mu_meta_field.display_name is 'display_name';
comment on column mu_meta_field.data_type is 'data_type';
comment on column mu_meta_field.dict_id is 'dict_id';
comment on column mu_meta_field.original_name is 'original_name';
comment on column mu_meta_field.max_length is 'max_length';
comment on column mu_meta_field.is_pk is 'is_pk';
comment on column mu_meta_field.is_fk is 'is_fk';
comment on column mu_meta_field.is_require is 'is_require';
comment on column mu_meta_field.description is 'description';
comment on column mu_meta_field.default_value is 'default_value';
comment on column mu_meta_field.is_valid is 'is_valid';
comment on column mu_meta_field.sort_num is 'sort_num';
comment on column mu_meta_field.input_date is 'input_date';

/*==============================================================*/
/* Table: mu_meta_item                                      */
/*==============================================================*/
create table mu_meta_item
(
    id                               varchar(32)     not null,
    name                             varchar(128)    not null,
    display_name                     varchar(64)     not null,
    data_type                        varchar(64)     not null,
    category                         varchar(32)     ,
    max_length                       int             ,
    description                      varchar(1024)   ,
    is_valid                         char(1)         not null,
    sort_num                         int             not null,
    input_date                       date            not null,
    primary key (id)
);
comment on table mu_meta_item is 'mu_meta_item';
comment on column mu_meta_item.id is 'id';
comment on column mu_meta_item.name is 'name';
comment on column mu_meta_item.display_name is 'display_name';
comment on column mu_meta_item.data_type is 'data_type';
comment on column mu_meta_item.category is 'category';
comment on column mu_meta_item.max_length is 'max_length';
comment on column mu_meta_item.description is 'description';
comment on column mu_meta_item.is_valid is 'is_valid';
comment on column mu_meta_item.sort_num is 'sort_num';
comment on column mu_meta_item.input_date is 'input_date';

/*==============================================================*/
/* Table: mu_meta_obj                                      */
/*==============================================================*/
create table mu_meta_obj
(
    id                               varchar(32)     not null,
    meta_id                          varchar(32)     not null,
    primary key (id)
);
comment on table mu_meta_obj is 'mu_meta_obj';
comment on column mu_meta_obj.id is 'id';
comment on column mu_meta_obj.meta_id is 'meta_id';

/*==============================================================*/
/* Table: mu_meta_obj_value                                      */
/*==============================================================*/
create table mu_meta_obj_value
(
    meta_obj_id                      varchar(32)     not null,
    meta_field_id                    varchar(32)     not null,
    value                            varchar(1024)   
);
comment on table mu_meta_obj_value is 'mu_meta_obj_value';
comment on column mu_meta_obj_value.meta_obj_id is 'meta_obj_id';
comment on column mu_meta_obj_value.meta_field_id is 'meta_field_id';
comment on column mu_meta_obj_value.value is 'value';

/*==============================================================*/
/* Table: mu_meta_reference                                      */
/*==============================================================*/
create table mu_meta_reference
(
    id                               varchar(32)     not null,
    pk_meta_id                       varchar(32)     not null,
    pk_meta_field_id                 varchar(32)     not null,
    fk_meta_id                       varchar(32)     not null,
    fk_meta_field_id                 varchar(32)     not null,
    primary key (id)
);
comment on table mu_meta_reference is 'mu_meta_reference';
comment on column mu_meta_reference.id is 'id';
comment on column mu_meta_reference.pk_meta_id is 'pk_meta_id';
comment on column mu_meta_reference.pk_meta_field_id is 'pk_meta_field_id';
comment on column mu_meta_reference.fk_meta_id is 'fk_meta_id';
comment on column mu_meta_reference.fk_meta_field_id is 'fk_meta_field_id';

/*==============================================================*/
/* Table: mu_meta_sql                                      */
/*==============================================================*/
create table mu_meta_sql
(
    meta_id                          varchar(32)     not null,
    sql_text                         varchar(8000)   not null,
    primary key (meta_id)
);
comment on table mu_meta_sql is 'mu_meta_sql';
comment on column mu_meta_sql.meta_id is 'meta_id';
comment on column mu_meta_sql.sql_text is 'sql_text';

/*==============================================================*/
/* Table: mu_module                                      */
/*==============================================================*/
create table mu_module
(
    id                               varchar(32)     not null,
    name                             varchar(64)     not null,
    display_name                     varchar(64)     not null,
    pid                              varchar(32)     ,
    description                      varchar(1024)   ,
    view_id                          varchar(32)     ,
    is_valid                         char(1)         not null,
    sort_num                         int             not null,
    input_date                       date            not null,
    primary key (id)
);
comment on table mu_module is 'mu_module';
comment on column mu_module.id is 'id';
comment on column mu_module.name is 'name';
comment on column mu_module.display_name is 'display_name';
comment on column mu_module.pid is 'pid';
comment on column mu_module.description is 'description';
comment on column mu_module.view_id is 'view_id';
comment on column mu_module.is_valid is 'is_valid';
comment on column mu_module.sort_num is 'sort_num';
comment on column mu_module.input_date is 'input_date';

/*==============================================================*/
/* Table: mu_nav_menu                                      */
/*==============================================================*/
create table mu_nav_menu
(
    id                               varchar(32)     not null,
    name                             varchar(64)     not null,
    display_name                     varchar(128)    not null,
    icon                             varchar(1024)   ,
    url                              varchar(1024)   ,
    pid                              varchar(32)     not null,
    level                            int             not null,
    project_id                       varchar(32)     not null,
    is_valid                         char(1)         not null,
    sort_num                         int             not null,
    input_date                       date            not null,
    primary key (id)
);
comment on table mu_nav_menu is 'mu_nav_menu';
comment on column mu_nav_menu.id is 'id';
comment on column mu_nav_menu.name is 'name';
comment on column mu_nav_menu.display_name is 'display_name';
comment on column mu_nav_menu.icon is 'icon';
comment on column mu_nav_menu.url is 'url';
comment on column mu_nav_menu.pid is 'pid';
comment on column mu_nav_menu.level is 'level';
comment on column mu_nav_menu.project_id is 'project_id';
comment on column mu_nav_menu.is_valid is 'is_valid';
comment on column mu_nav_menu.sort_num is 'sort_num';
comment on column mu_nav_menu.input_date is 'input_date';

/*==============================================================*/
/* Table: mu_pm_user                                      */
/*==============================================================*/
create table mu_pm_user
(
    id                               varchar(32)     not null,
    name                             varchar(64)     not null,
    display_name                     varchar(128)    ,
    pwd                              varchar(64)     ,
    email                            varchar(64)     ,
    mobile_number                    varchar(64)     ,
    is_valid                         char(1)         not null,
    input_date                       date            not null,
    primary key (id)
);
comment on table mu_pm_user is 'mu_pm_user';
comment on column mu_pm_user.id is 'id';
comment on column mu_pm_user.name is 'name';
comment on column mu_pm_user.display_name is 'display_name';
comment on column mu_pm_user.pwd is 'pwd';
comment on column mu_pm_user.email is 'email';
comment on column mu_pm_user.mobile_number is 'mobile_number';
comment on column mu_pm_user.is_valid is 'is_valid';
comment on column mu_pm_user.input_date is 'input_date';

/*==============================================================*/
/* Table: mu_profile_setting                                      */
/*==============================================================*/
create table mu_profile_setting
(
    conf_section                     varchar(64)     not null,
    conf_key                         varchar(64)     not null,
    conf_value                       varchar(128)    ,
    sort_num                         int             ,
    memo                             varchar(1024)   ,
    primary key (conf_section,conf_key)
);
comment on table mu_profile_setting is 'mu_profile_setting';
comment on column mu_profile_setting.conf_section is 'conf_section';
comment on column mu_profile_setting.conf_key is 'conf_key';
comment on column mu_profile_setting.conf_value is 'conf_value';
comment on column mu_profile_setting.sort_num is 'sort_num';
comment on column mu_profile_setting.memo is 'memo';

/*==============================================================*/
/* Table: mu_project_define                                      */
/*==============================================================*/
create table mu_project_define
(
    id                               varchar(32)     not null,
    name                             varchar(64)     not null,
    display_name                     varchar(64)     not null,
    description                      varchar(1024)   ,
    package_name                     varchar(64)     ,
    is_valid                         char(1)         not null,
    sort_num                         int             not null,
    input_date                       date            not null,
    project_url                      varchar(1024)   ,
    primary key (id)
);
comment on table mu_project_define is 'mu_project_define';
comment on column mu_project_define.id is 'id';
comment on column mu_project_define.name is 'name';
comment on column mu_project_define.display_name is 'display_name';
comment on column mu_project_define.description is 'description';
comment on column mu_project_define.package_name is 'package_name';
comment on column mu_project_define.is_valid is 'is_valid';
comment on column mu_project_define.sort_num is 'sort_num';
comment on column mu_project_define.input_date is 'input_date';
comment on column mu_project_define.project_url is 'project_url';

/*==============================================================*/
/* Table: mu_view                                      */
/*==============================================================*/
create table mu_view
(
    id                               varchar(32)     not null,
    name                             varchar(64)     not null,
    display_name                     varchar(128)    ,
    description                      varchar(1024)   ,
    is_valid                         char(1)         not null,
    input_date                       date            not null,
    sort_num                         int             not null,
    meta_id                          varchar(32)     not null,
    primary key (id)
);
comment on table mu_view is 'mu_view';
comment on column mu_view.id is 'id';
comment on column mu_view.name is 'name';
comment on column mu_view.display_name is 'display_name';
comment on column mu_view.description is 'description';
comment on column mu_view.is_valid is 'is_valid';
comment on column mu_view.input_date is 'input_date';
comment on column mu_view.sort_num is 'sort_num';
comment on column mu_view.meta_id is 'meta_id';

/*==============================================================*/
/* Table: mu_view_config                                      */
/*==============================================================*/
create table mu_view_config
(
    id                               varchar(32)     not null,
    prop_id                          varchar(32)     not null,
    meta_field_id                    varchar(32)     ,
    value                            varchar(128)    ,
    primary key (id)
);
comment on table mu_view_config is 'mu_view_config';
comment on column mu_view_config.id is 'id';
comment on column mu_view_config.prop_id is 'prop_id';
comment on column mu_view_config.meta_field_id is 'meta_field_id';
comment on column mu_view_config.value is 'value';

/*==============================================================*/
/* Table: mu_view_prop                                      */
/*==============================================================*/
create table mu_view_prop
(
    id                               varchar(32)     not null,
    view_id                          varchar(32)     not null,
    layout_prop_id                   varchar(32)     not null,
    meta_field_id                    varchar(32)     ,
    value                            varchar(128)    ,
    primary key (id)
);
comment on table mu_view_prop is 'mu_view_prop';
comment on column mu_view_prop.id is 'id';
comment on column mu_view_prop.view_id is 'view_id';
comment on column mu_view_prop.layout_prop_id is 'layout_prop_id';
comment on column mu_view_prop.meta_field_id is 'meta_field_id';
comment on column mu_view_prop.value is 'value';


alter table mu_code_tpl add constraint FK_code_tpl_projectId foreign key (project_id)
    references mu_project_define (id) on delete cascade on update cascade;

alter table mu_dz_code add constraint FK_code_categoryId foreign key (category_id)
    references mu_dz_category (id) on delete cascade on update cascade;

alter table mu_layout_prop add constraint FK_layout_prop_layoutId foreign key (layout_id)
    references mu_layout (id) on delete cascade on update cascade;

alter table mu_meta_field add constraint FK_meta_field_metaId foreign key (meta_id)
    references mu_meta (id) on delete cascade on update cascade;

alter table mu_meta_field add constraint FK_metaField_metaItem foreign key (meta_item_id)
    references mu_meta_item (id) on delete cascade on update cascade;

alter table mu_meta_obj add constraint FK_meta_obj_metaId foreign key (meta_id)
    references mu_meta (id) on delete cascade on update cascade;

alter table mu_meta_obj_value add constraint FK_meta_obj_value_metaObjId foreign key (meta_obj_id)
    references mu_meta_obj (id) on delete cascade on update cascade;

alter table mu_meta_obj_value add constraint FK_meta_field_value_metaField foreign key (meta_field_id)
    references mu_meta_field (id) on delete cascade on update cascade;

alter table mu_meta_reference add constraint FK_meta_reference_pkMetaId foreign key (pk_meta_id)
    references mu_meta (id) on delete cascade on update cascade;

alter table mu_meta_reference add constraint FK_meta_reference_fkMetaFieldId foreign key (fk_meta_field_id)
    references mu_meta_field (id) on delete cascade on update cascade;

alter table mu_meta_reference add constraint FK_meta_reference_fkMetaId foreign key (fk_meta_id)
    references mu_meta (id) on delete cascade on update cascade;

alter table mu_meta_reference add constraint FK_meta_reference_pkMetaFieldId foreign key (pk_meta_field_id)
    references mu_meta_field (id) on delete cascade on update cascade;

alter table mu_meta_sql add constraint FK_meta_sql_metaId foreign key (meta_id)
    references mu_meta (id) on delete cascade on update cascade;

alter table mu_module add constraint FK_module_viewId foreign key (view_id)
    references mu_view (id) on delete cascade on update cascade;

alter table mu_nav_menu add constraint FK_nav_menu_projectId foreign key (project_id)
    references mu_project_define (id) on delete cascade on update cascade;

alter table mu_view add constraint FK_view_metaId foreign key (meta_id)
    references mu_meta (id) on delete cascade on update cascade;

alter table mu_view_config add constraint FK_view_config_metaFieldId foreign key (meta_field_id)
    references mu_meta_field (id) on delete cascade on update cascade;

alter table mu_view_config add constraint FK_view_config_layoutPropId foreign key (prop_id)
    references mu_layout_prop (id) on delete cascade on update cascade;

alter table mu_view_prop add constraint FK_view_prop_viewId foreign key (view_id)
    references mu_view (id) on delete cascade on update cascade;

alter table mu_view_prop add constraint FK_view_prop_layoutPropId foreign key (layout_prop_id)
    references mu_layout_prop (id) on delete cascade on update cascade;

alter table mu_view_prop add constraint FK_view_prop_metaFieldId foreign key (meta_field_id)
    references mu_meta_field (id) on delete cascade on update cascade;


create  index iux_dzCategory_name on mu_dz_category
(
    name
);
create  index idx_view_config_prop on mu_view_config
(
    meta_field_id,
    prop_id
);
create  index iux_view_prop on mu_view_prop
(
    layout_prop_id,
    meta_field_id,
    view_id
);
