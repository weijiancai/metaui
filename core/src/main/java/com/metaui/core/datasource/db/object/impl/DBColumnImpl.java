package com.metaui.core.datasource.db.object.impl;

import com.metaui.core.datasource.db.DBIcons;
import com.metaui.core.datasource.db.DatabaseType;
import com.metaui.core.datasource.db.object.DBColumn;
import com.metaui.core.datasource.db.object.DBDataset;
import com.metaui.core.datasource.db.object.enums.DBObjectType;
import com.metaui.core.meta.MetaDataType;
import com.metaui.core.util.UString;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 数据库列信息实现类
 *
 * @author wei_jc
 * @since 1.0.0
 */
@XmlRootElement(name = "Column")
public class DBColumnImpl extends DBObjectImpl implements DBColumn {
    private int maxLength;
    private boolean isPk;
    private boolean isFk;
    private boolean isNullable;
    private String dbDataType;
    private int precision;
    private int scale;
    private MetaDataType dataType;
    private DBColumn refColumn;
    private DBDataset dataset;

    private String dataTypeString;

    public DBColumnImpl() {
        setObjectType(DBObjectType.COLUMN);
    }

    @Override
    public MetaDataType getDataType() {
        if (dataType == null) {
            dataType = MetaDataType.getDataType(getDbDataType());
        }
        return dataType;
    }

    public boolean isPk() {
        return isPk;
    }

    public void setPk(boolean isPk) {
        this.isPk = isPk;
    }

    public boolean isFk() {
        return isFk;
    }

    @Override
    public boolean isNullable() {
        return isNullable;
    }

    public void setFk(boolean isFk) {
        this.isFk = isFk;
    }

    public int getMaxLength() {
        return maxLength;
    }

    @Override
    public DBColumn getRefColumn() {
        return refColumn;
    }

    @Override
    public DBDataset getDataset() {
        return dataset;
    }

    public void setDataTypeString(String dataTypeString) {
        this.dataTypeString = dataTypeString;
    }

    @Override
    public String getDataTypeString() {
        if(UString.isEmpty(dataTypeString)) {
            StringBuilder sb =  new StringBuilder();
            sb.append(dbDataType.toLowerCase());
            if (maxLength > 0) {
                sb.append("(").append(maxLength).append(")");
            } else if (precision > 0) {
                sb.append("(").append(precision);
                if (scale > 0) {
                    sb.append(",").append(scale);
                }
                sb.append(")");
            }
            dataTypeString = sb.toString();
        }

        return dataTypeString;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public String getDbDataType() {
        return dbDataType;
    }

    @Override
    public String getDbDataType(DatabaseType type) {
        MetaDataType metaDataType = getDataType();
        switch (metaDataType) {
            case STRING: {
                if (getMaxLength() > 0) {
                    return getDbDataType() + "(" + getMaxLength() + ")";
                } else {
                    return getDbDataType();
                }
            }
            case DATE: {
                return "date";
            }
            case INTEGER: {
                return "int";
            }
            case TEXT: {
                return "clob";
            }
        }
        return "";
    }

    public void setDbDataType(String dbDataType) {
        this.dbDataType = dbDataType;
    }

    @Override
    public int getPrecision() {
        return precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    @Override
    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public void setRefColumn(DBColumn refColumn) {
        this.refColumn = refColumn;
    }

    public void setDataset(DBDataset dataset) {
        this.dataset = dataset;
    }

    public void setNullable(boolean isNullable) {
        this.isNullable = isNullable;
    }

    @Override
    public String getIcon() {
        if (isPk && isFk) {
            return DBIcons.DBO_COLUMN_PKFK;
        } else if (isPk) {
            return DBIcons.DBO_COLUMN_PK;
        } else if (isFk) {
            return DBIcons.DBO_COLUMN_FK;
        }
        return super.getIcon();
    }

    @Override
    public String getPresentableText() {
        StringBuilder sb =  new StringBuilder();
        sb.append(" - ").append(dbDataType.toLowerCase());
        if (maxLength > 0) {
            sb.append("(").append(maxLength).append(")");
        } else if (precision > 0) {
            sb.append("(").append(precision);
            if (scale > 0) {
                sb.append(",").append(scale);
            }
            sb.append(")");
        }
        if (UString.isNotEmpty(getComment()) && !getSchema().getName().equalsIgnoreCase("information_schema")) {
            sb.append(" ").append(getComment());
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return getName() + getPresentableText();
    }

    @Override
    public String getFullName() {
        if (getDataset() == null) {
            return getName();
        }
        return getDataset().getFullName() + "." + getName();
    }
}
