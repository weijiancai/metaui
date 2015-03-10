package com.metaui.dbtool;

import com.metaui.core.config.ProjectConfig;
import com.metaui.core.config.SystemManager;
import com.metaui.core.datasource.DataSourceManager;
import com.metaui.core.datasource.ResourceTreeAdapter;
import com.metaui.core.facade.impl.BaseFacade;
import com.metaui.core.model.ITreeNode;
import com.metaui.fxbase.ui.IDesktop;
import com.metaui.fxbase.ui.view.MUTabsDesktop;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import javafx.util.Callback;

/**
 * 数据库工具Facade
 *
 * @author wei_jc
 * @since  1.0.0
 */
public class DBToolFacade extends BaseFacade {
    /**
     * 项目名称
     */
    public static final String PROJECT_NAME = "DBTool";
    private static DBToolFacade instance;

    private ProjectConfig projectConfig;
    private MUTabsDesktop desktop;

    private DBToolFacade() {

    }

    @Override
    protected void initProjectConfig() throws Exception {
        projectConfig = SystemManager.getInstance().getProjectConfig(PROJECT_NAME);
        if (projectConfig == null) {
            projectConfig = SystemManager.getInstance().createProjectConfig(PROJECT_NAME);
        }
    }

    public static DBToolFacade getInstance() {
        if (instance == null) {
            instance = new DBToolFacade();
        }
        return instance;
    }

    @Override
    public ProjectConfig getProjectConfig() throws Exception {
        return projectConfig;
    }

    @Override
    public void initAfter() throws Exception {

    }

    @Override
    public IDesktop getDesktop() throws Exception {
        if (desktop == null) {
//            desktop = new MUTabsDesktop(DataSourceManager.getNavTree());
            desktop = new MUTabsDesktop(new ResourceTreeAdapter(DataSourceManager.getSysDataSource().getRootResource()));
            desktop.getNavTree().setCellFactory(new Callback<TreeView<ITreeNode>, TreeCell<ITreeNode>>() {
                @Override
                public TreeCell<ITreeNode> call(TreeView<ITreeNode> param) {
                    return new DBNavTreeCell();
                }
            });
        }
        return desktop;
    }
}
