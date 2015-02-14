package com.metaui.core.ui.layout.property;

import com.metaui.core.dict.DictCategory;
import com.metaui.core.dict.DictManager;
import com.metaui.core.dict.EnumAlign;
import com.metaui.core.meta.DisplayStyle;
import com.metaui.core.meta.MetaDataType;
import com.metaui.core.meta.annotation.MetaElement;
import com.metaui.core.meta.annotation.MetaFieldElement;
import com.metaui.core.meta.model.MetaField;
import com.metaui.core.ui.layout.LayoutManager;
import com.metaui.core.ui.model.View;
import com.metaui.core.ui.model.ViewProperty;
import com.metaui.core.util.UString;
import com.metaui.fxbase.ui.view.MUDialog;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 表格字段属性
 *
 * @author wei_jc
 * @since 1.0.0
 */
@MetaElement(displayName = "表格字段配置")
public class TableFieldProperty extends BaseProperty {
    private TableProperty tableProperty;
    private String name;
    private StringProperty displayName = new SimpleStringProperty();
    private MetaDataType dataType;
    private IntegerProperty width = new SimpleIntegerProperty();
    private BooleanProperty isDisplay = new SimpleBooleanProperty(true);
    private BooleanProperty isPk = new SimpleBooleanProperty(false);
    private BooleanProperty isFk = new SimpleBooleanProperty(false);
    private DisplayStyle displayStyle;
    private DictCategory dict;
    private ObjectProperty<EnumAlign> align = new SimpleObjectProperty<EnumAlign>();
    private IntegerProperty sortNum = new SimpleIntegerProperty(0);

    public TableFieldProperty() {}

    public TableFieldProperty(String name, String displayName, DictCategory dict, boolean isDisplay) {
        setName(name);
        setDisplayName(displayName);
        setDict(dict);
        setDisplay(isDisplay);
        if (dict != null) {
            setDisplayStyle(DisplayStyle.COMBO_BOX);
        }
    }

    public TableFieldProperty(String name, String displayName) {
        this(name, displayName, 80);
    }

    public TableFieldProperty(String name, String displayName, int width) {
        this(name, displayName, width, null);
    }

    public TableFieldProperty(String name, String displayName, int width, DictCategory dict) {
        this(name, displayName, width, dict, EnumAlign.LEFT);
    }

    public TableFieldProperty(String name, String displayName, int width, DictCategory dict, EnumAlign align) {
        setName(name);
        setDisplayName(displayName);
        setWidth(width);
        this.dict = dict;
        if (dict != null) {
            this.displayStyle = DisplayStyle.COMBO_BOX;
        }
        setAlign(align);
    }

    public TableFieldProperty(final TableProperty tableProperty, final MetaField field, Map<String, ViewProperty> propMap) {
        super(tableProperty.getView(), field, propMap);
        this.tableProperty = tableProperty;

        // 初始化默认值
        int defaultWidth = field.getMaxLength();
        if(defaultWidth <= 0) {
            defaultWidth = 80;
        }
        if ((field.isPk() || field.isFk()) && defaultWidth == 32) {
            defaultWidth = 250;
        }

        if(defaultWidth > 500) {
            defaultWidth = 200;
        }

        String defaultDisplayStyle = DisplayStyle.TEXT_FIELD.name();
        String defaultAlign = EnumAlign.LEFT.name();
        String dictId = field.getDictId();
        if (MetaDataType.BOOLEAN == field.getDataType()) {
            defaultWidth = 50;
            defaultDisplayStyle = DisplayStyle.BOOLEAN.name();
        } else if (MetaDataType.INTEGER == field.getDataType()) {
            defaultWidth = 60;
            defaultAlign = EnumAlign.CENTER.name();
        } else if (MetaDataType.DATE == field.getDataType()) {
            defaultWidth = 140;
        }
        if (UString.isNotEmpty(dictId)) {
            defaultDisplayStyle = DisplayStyle.COMBO_BOX.name();
        }

        this.name = field.getName();
        setDisplayName(getPropertyValue(TABLE_FIELD.DISPLAY_NAME, field.getDisplayName()));
        this.dataType = field.getDataType();
        setWidth(getIntPropertyValue(TABLE_FIELD.WIDTH, defaultWidth));
        setDisplay(getBooleanPropertyValue(TABLE_FIELD.IS_DISPLAY, true));
        this.displayStyle = DisplayStyle.getStyle(getPropertyValue(TABLE_FIELD.DISPLAY_STYLE, defaultDisplayStyle));
        this.dict = DictManager.getDict(getPropertyValue(TABLE_FIELD.DICT_ID, field.getDictId()));
        setAlign(EnumAlign.getAlign(getPropertyValue(TABLE_FIELD.ALIGN, defaultAlign)));
        setSortNum(getIntPropertyValue(TABLE_FIELD.SORT_NUM, field.getSortNum()));
        setPk(field.isPk());
        setFk(field.isFk());

        // 属性改变，保存到数据库
        widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (oldValue.intValue() != newValue.intValue()) {
                    saveOrUpdateViewProperty(TABLE_FIELD.WIDTH, newValue.toString());
                }
            }
        });
        sortNumProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (oldValue.intValue() != newValue.intValue()) {
                    saveOrUpdateViewProperty(TABLE_FIELD.SORT_NUM, newValue.toString());
                }
            }
        });

        // 初始化视图属性
        initViewProperties();
    }

    private void initViewProperties() {
        addViewProperty(TABLE_FIELD.NAME, getName());
        addViewProperty(TABLE_FIELD.DISPLAY_NAME, getDisplayName());
        addViewProperty(TABLE_FIELD.IS_DISPLAY, isDisplay() ? "true" : "false");
        addViewProperty(TABLE_FIELD.IS_PK, isPk() ? "true" : "false");
        addViewProperty(TABLE_FIELD.IS_FK, isFk() ? "true" : "false");
        addViewProperty(TABLE_FIELD.WIDTH, getWidth() + "");
        addViewProperty(TABLE_FIELD.DISPLAY_STYLE, getDisplayStyle().name());
        addViewProperty(TABLE_FIELD.ALIGN, getAlign().name());
        addViewProperty(TABLE_FIELD.DICT_ID, getDict() == null ? "" : getDict().getId());
        addViewProperty(TABLE_FIELD.SORT_NUM, getSortNum() + "");
    }

    private void saveOrUpdateViewProperty(String layoutPropId, String value) {
        ViewProperty viewProperty = getProperty(layoutPropId);
        try {
            if (viewProperty == null || UString.isEmpty(viewProperty.getId())) {
                viewProperty = new ViewProperty(tableProperty.getView(), LayoutManager.getLayoutPropById(layoutPropId), metaField, value);
                viewProperty.save();
                addViewProperty(viewProperty);
            } else {
                viewProperty.setValue(value);
                viewProperty.update();
            }
        } catch (Exception e) {
            MUDialog.showExceptionDialog(e);
        }
    }

    @MetaFieldElement(displayName = "名称", sortNum = 10)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @MetaFieldElement(displayName = "显示名", sortNum = 20)
    public String getDisplayName() {
        return displayName.get();
    }

    public void setDisplayName(String displayName) {
        this.displayName.set(displayName);
    }

    @MetaFieldElement(displayName = "数据类型", sortNum = 30, dictId = "MetaDataType", dataType = MetaDataType.DICT)
    public MetaDataType getDataType() {
        return dataType;
    }

    public void setDataType(MetaDataType dataType) {
        this.dataType = dataType;
    }

    @MetaFieldElement(displayName = "宽", sortNum = 40, dataType = MetaDataType.INTEGER)
    public int getWidth() {
        return width.get();
    }

    public void setWidth(int width) {
        this.width.set(width);
    }

    @MetaFieldElement(displayName = "是否显示", sortNum = 50, dataType = MetaDataType.BOOLEAN)
    public boolean isDisplay() {
        return isDisplay.get();
    }

    public void setDisplay(boolean isDisplay) {
        this.isDisplay.set(isDisplay);
    }

    @MetaFieldElement(displayName = "显示风格", sortNum = 60, dictId = "DisplayStyle", dataType = MetaDataType.DICT)
    public DisplayStyle getDisplayStyle() {
        return displayStyle;
    }

    public void setDisplayStyle(DisplayStyle displayStyle) {
        this.displayStyle = displayStyle;
    }

    @MetaFieldElement(displayName = "数据字典", sortNum = 70, dictId = "ROOT", dataType = MetaDataType.DICT)
    public DictCategory getDict() {
        return dict;
    }

    public void setDict(DictCategory dict) {
        this.dict = dict;
    }

    @MetaFieldElement(displayName = "对齐方式", sortNum = 80, dictId = "EnumAlign", dataType = MetaDataType.DICT)
    public EnumAlign getAlign() {
        return align.get();
    }

    public void setAlign(EnumAlign align) {
        this.align.set(align);
    }

    @MetaFieldElement(displayName = "排序号", sortNum = 90, dataType = MetaDataType.INTEGER)
    public int getSortNum() {
        return sortNum.get();
    }

    public void setSortNum(int sortNum) {
        this.sortNum.set(sortNum);
    }

    @MetaFieldElement(displayName = "是否主键", sortNum = 100, dataType = MetaDataType.BOOLEAN)
    public boolean isPk() {
        return isPk.get();
    }

    public void setPk(boolean isPk) {
        this.isPk.set(isPk);
    }

    @MetaFieldElement(displayName = "是否外键", sortNum = 110, dataType = MetaDataType.BOOLEAN)
    public boolean isFk() {
        return isFk.get();
    }

    public void setFk(boolean isFk) {
        this.isFk.set(isFk);
    }

    public IntegerProperty widthProperty() {
        return width;
    }

    public StringProperty displayNameProperty() {
        return displayName;
    }

    public ObjectProperty<EnumAlign> alignProperty() {
        return align;
    }

    public BooleanProperty displayProperty() {
        return isDisplay;
    }

    public BooleanProperty pkProperty() {
        return isPk;
    }

    public BooleanProperty fkProperty() {
        return isFk;
    }

    public IntegerProperty sortNumProperty() {
        return sortNum;
    }

    public static List<ViewProperty> getViewProperties(View view, MetaField field, boolean isDisplay) {
        List<ViewProperty> viewProperties = new ArrayList<ViewProperty>();
        viewProperties.add(new ViewProperty(view, LayoutManager.getLayoutPropById(TABLE_FIELD.NAME), field, field.getName()));
        viewProperties.add(new ViewProperty(view, LayoutManager.getLayoutPropById(TABLE_FIELD.DISPLAY_NAME), field, field.getDisplayName()));
        viewProperties.add(new ViewProperty(view, LayoutManager.getLayoutPropById(TABLE_FIELD.IS_DISPLAY), field, isDisplay ? "true" : "false"));
        viewProperties.add(new ViewProperty(view, LayoutManager.getLayoutPropById(TABLE_FIELD.IS_PK), field, field.isPk() ? "true" : "false"));
        viewProperties.add(new ViewProperty(view, LayoutManager.getLayoutPropById(TABLE_FIELD.IS_FK), field, field.isFk() ? "true" : "false"));

        int w = field.getMaxLength();
        if(w <= 0) {
            w = 80;
        }
        if ((field.isPk() || field.isFk()) && w == 32) {
            w = 250;
        }

        if(w > 500) {
            w = 200;
        }

        String width = w + "";
        String displayStyle = DisplayStyle.TEXT_FIELD.name();
        String align = EnumAlign.LEFT.name();
        String dictId = field.getDictId();
        if (MetaDataType.BOOLEAN == field.getDataType()) {
            width = "50";
            displayStyle = DisplayStyle.BOOLEAN.name();
        } else if (MetaDataType.INTEGER == field.getDataType()) {
            width = "60";
            align = EnumAlign.CENTER.name();
        } else if (MetaDataType.DATE == field.getDataType()) {
            width = "140";
        }
        if (UString.isNotEmpty(dictId)) {
            displayStyle = DisplayStyle.COMBO_BOX.name();
        }

        viewProperties.add(new ViewProperty(view, LayoutManager.getLayoutPropById(TABLE_FIELD.WIDTH), field, width));
        viewProperties.add(new ViewProperty(view, LayoutManager.getLayoutPropById(TABLE_FIELD.DISPLAY_STYLE), field, displayStyle));
        viewProperties.add(new ViewProperty(view, LayoutManager.getLayoutPropById(TABLE_FIELD.ALIGN), field, align));
        viewProperties.add(new ViewProperty(view, LayoutManager.getLayoutPropById(TABLE_FIELD.DICT_ID), field, dictId));
        viewProperties.add(new ViewProperty(view, LayoutManager.getLayoutPropById(TABLE_FIELD.SORT_NUM), field, field.getSortNum() + ""));

        return viewProperties;
    }

    @Override
    public String toString() {
        return displayName.get();
    }
}
