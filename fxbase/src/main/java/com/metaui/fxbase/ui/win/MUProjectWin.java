package com.metaui.fxbase.ui.win;

import com.metaui.core.meta.MetaManager;
import com.metaui.core.model.ITreeNode;
import com.metaui.core.model.impl.BaseTreeNode;
import com.metaui.core.project.ProjectDefine;
import com.metaui.core.project.ProjectManager;
import com.metaui.core.ui.model.View;
import com.metaui.fxbase.ui.view.MUTable;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.BorderPane;

/**
 * 项目管理窗口
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MUProjectWin extends BorderPane {
    private TreeItem<ITreeNode> projectTree;

    public MUProjectWin() {
        initUI();
    }

    private void initUI() {
        this.setCenter(new MUTable(MetaManager.getMeta(ProjectDefine.class)));
        // 初始化项目树
        initProjectTree();
    }

    private void initProjectTree() {
        BaseTreeNode projectNode = new BaseTreeNode("项目管理");
        projectNode.setId("Project");
        projectNode.setView(View.createNodeView(this));

        projectTree = new TreeItem<ITreeNode>(projectNode);

        for (ProjectDefine project : ProjectManager.getProjects()) {
            TreeItem<ITreeNode> projectItem = new TreeItem<ITreeNode>(new BaseTreeNode(project.getDisplayName()));
            // 导航菜单
            BaseTreeNode navMenu = new BaseTreeNode("导航菜单");
            navMenu.setId(project.getName() + "_navMenu");
            navMenu.setPresentableText(project.getDisplayName() + " - 导航菜单");
            navMenu.setView(View.createNodeView(new MUNavMenuWin(project)));
            TreeItem<ITreeNode> navMenuItem = new TreeItem<ITreeNode>(navMenu);

            // 代码模板
            BaseTreeNode codeTpl = new BaseTreeNode("代码模板");
            codeTpl.setId(project.getName() + "_codeTpl");
            codeTpl.setPresentableText(project.getDisplayName() + " - 代码模板");
            codeTpl.setView(View.createNodeView(new MUCodeTplWin(project)));
            TreeItem<ITreeNode> codeTplItem = new TreeItem<ITreeNode>(codeTpl);

            projectItem.getChildren().add(navMenuItem);
            projectItem.getChildren().add(codeTplItem);
            projectTree.getChildren().add(projectItem);
        }
    }

    public TreeItem<ITreeNode> getProjectTree() {
        return projectTree;
    }
}
