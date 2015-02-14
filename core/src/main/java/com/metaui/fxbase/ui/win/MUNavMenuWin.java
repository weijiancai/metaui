package com.metaui.fxbase.ui.win;

import com.metaui.core.meta.MetaManager;
import com.metaui.core.meta.model.Meta;
import com.metaui.core.model.ITreeNode;
import com.metaui.core.project.NavMenu;
import com.metaui.core.project.NavMenuTreeNode;
import com.metaui.core.project.ProjectDefine;
import com.metaui.fxbase.ui.IValue;

import java.util.Map;

/**
 * 导航树菜单
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MUNavMenuWin extends MUTreeTableWin {
    private ProjectDefine project;
    private NavMenuTreeNode rootNavMenu;

    public MUNavMenuWin(ProjectDefine project) {
        this.project = project;
        rootNavMenu = new NavMenuTreeNode(project.getRootNavMenu());

        initUI();
    }

    @Override
    public ITreeNode getRootTreeNode() {
        return rootNavMenu;
    }

    @Override
    public Meta getMainMeta() {
        return MetaManager.getMeta("NavMenu");
    }

    @Override
    public Meta getItemMeta() {
        return null;
    }

    @Override
    public String getParentIdColName() {
        return "pic";
    }

    @Override
    public String getItemFkColName() {
        return null;
    }

    @Override
    public String getItemFkDbName() {
        return null;
    }

    @Override
    public ITreeNode createNewTreeNode(Map<String, IValue> valueMap) {
        NavMenu menu = new NavMenu();
        menu.setId(valueMap.get("id").value());
        menu.setName(valueMap.get("name").value());
        menu.setDisplayName(valueMap.get("displayName").value());
        return new NavMenuTreeNode(menu);
    }
}
