package com.metaui.core.backup;

import com.metaui.core.config.ProfileSetting;
import com.metaui.core.datasource.DataSource;
import com.metaui.core.datasource.DefaultDataSource;
import com.metaui.core.dict.DictCategory;
import com.metaui.core.meta.model.Meta;
import com.metaui.core.meta.model.MetaItem;
import com.metaui.core.meta.model.MetaReference;
import com.metaui.core.project.ProjectDefine;
import com.metaui.core.ui.model.View;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * 备份信息
 *
 * @author wei_jc
 * @version 1.0.0
 */
@XmlRootElement
public class BackupInfo {
    private List<ProfileSetting> settingList;
    private DictCategory dictCategory;
    private List<ProjectDefine> projectList;
    private List<MetaItem> metaItemList;
    private List<Meta> metaList;
    private List<MetaReference> metaReferenceList;
    private List<View> viewList;
    private List<DataSource> dataSourceList;

    @XmlElementWrapper(name = "ProfileSettings")
    @XmlElement(name = "Setting")
    public List<ProfileSetting> getSettingList() {
        if (settingList == null) {
            settingList = new ArrayList<ProfileSetting>();
        }
        return settingList;
    }

    public void setSettingList(List<ProfileSetting> settingList) {
        this.settingList = settingList;
    }

    @XmlElement(name = "DictCategory")
    public DictCategory getDictCategory() {
        return dictCategory;
    }

    public void setDictCategory(DictCategory dictCategory) {
        this.dictCategory = dictCategory;
    }

    @XmlElementWrapper(name = "Projects")
    @XmlElement(name = "Project")
    public List<ProjectDefine> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<ProjectDefine> projectList) {
        this.projectList = projectList;
    }

    @XmlElementWrapper(name = "MetaItems")
    @XmlElement(name = "MetaItem")
    public List<MetaItem> getMetaItemList() {
        if (metaItemList == null) {
            metaItemList = new ArrayList<MetaItem>();
        }
        return metaItemList;
    }

    public void setMetaItemList(List<MetaItem> metaItemList) {
        this.metaItemList = metaItemList;
    }

    @XmlElementWrapper(name = "Metas")
    @XmlElement(name = "Meta")
    public List<Meta> getMetaList() {
        if (metaList == null) {
            metaList = new ArrayList<Meta>();
        }
        return metaList;
    }

    public void setMetaList(List<Meta> metaList) {
        this.metaList = metaList;
    }

    @XmlElementWrapper(name = "MetaReferences")
    @XmlElement(name = "MetaReference")
    public List<MetaReference> getMetaReferenceList() {
        if (metaReferenceList == null) {
            metaReferenceList = new ArrayList<MetaReference>();
        }
        return metaReferenceList;
    }

    public void setMetaReferenceList(List<MetaReference> metaReferenceList) {
        this.metaReferenceList = metaReferenceList;
    }

    @XmlElementWrapper(name = "Views")
    @XmlElement(name = "View")
    public List<View> getViewList() {
        if (viewList == null) {
            viewList = new ArrayList<View>();
        }
        return viewList;
    }

    public void setViewList(List<View> viewList) {
        this.viewList = viewList;
    }

    @XmlElementWrapper(name = "DataSources")
    @XmlElement(name = "DataSource", type = DefaultDataSource.class)
    public List<DataSource> getDataSourceList() {
        return dataSourceList;
    }

    public void setDataSourceList(List<DataSource> dataSourceList) {
        this.dataSourceList = dataSourceList;
    }
}
