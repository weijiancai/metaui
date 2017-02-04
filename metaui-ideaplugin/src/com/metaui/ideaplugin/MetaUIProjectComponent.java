package com.metaui.ideaplugin;

import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowManager;
import com.metaui.ideaplugin.share.ShareToolWin;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

/**
 * @author wei_jc
 * @since 1.0.0
 * 2017/1/26.
 */
public class MetaUIProjectComponent implements ProjectComponent {
    public static final String TOOL_WINDOW_ID = "MetaUIToolWindow";
    private final Project project;

    public MetaUIProjectComponent(Project project) {
        this.project = project;
    }

    @Override
    public void initComponent() {
        // TODO: insert component initialization logic here
    }

    @Override
    public void disposeComponent() {
        // TODO: insert component disposal logic here
    }

    @Override
    @NotNull
    public String getComponentName() {
        return "MetaUIProjectComponent";
    }

    @Override
    public void projectOpened() {
        // called when project is opened
        ToolWindowManager toolWindowManager = ToolWindowManager.getInstance(project);
        JPanel myContentPanel = new JPanel(new BorderLayout());
        ShareToolWin toolWin = new ShareToolWin();
        myContentPanel.add(toolWin.getRootPanel(), BorderLayout.CENTER);
        ToolWindow toolWindow = toolWindowManager.registerToolWindow(TOOL_WINDOW_ID, false, ToolWindowAnchor.LEFT);
        toolWindow.getComponent().add(myContentPanel);
    }

    @Override
    public void projectClosed() {
        // called when project is being closed
        ToolWindowManager toolWindowManager = ToolWindowManager.getInstance(project);
        toolWindowManager.unregisterToolWindow(TOOL_WINDOW_ID);
    }
}
