package com.metaui.ideaplugin.eshop;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.wm.impl.IdeGlassPaneImpl;
import com.metaui.core.moudle.ModuleManager;
import com.metaui.core.sys.SystemManager;
import com.metaui.eshop.EShopApp;
import com.metaui.eshop.moudle.EShopModule;
import com.metaui.ideaplugin.eshop.form.EShopApiForm;

import javax.swing.*;
import java.awt.*;

/**
 * @author wei_jc
 * @since 1.0.0
 * 2016/4/16.
 */
public class EShopAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getData(PlatformDataKeys.PROJECT);
        /*String userName = askForName(project);
        sayHello(project, userName);*/
        /*try {
            EShopDialog dialog = new EShopDialog(project);
            dialog.pack();
            dialog.setVisible(true);
        } catch (Exception e1) {
            e1.printStackTrace();
        }*/
        try {
            startFrame(project);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    private void startFrame(final Project project) throws Exception {
        // 注册模块
        ModuleManager.getInstance().regist(new EShopModule());
        // 系统初始化
        SystemManager.getInstance().init();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame();
                frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
//                frame.setLocationRelativeTo(null);
                frame.setTitle("网店API");
                int width = 1280;
                int height = 800;
                frame.setSize(width, height);
                // 居中
                int w = (Toolkit.getDefaultToolkit().getScreenSize().width - width) / 2;
                int h = (Toolkit.getDefaultToolkit().getScreenSize().height - height) / 2;
                frame.setLocation(w, h);

                Container contentPane = frame.getContentPane();
                contentPane.setLayout(new BorderLayout());
                EShopApiForm form = new EShopApiForm(project);
                contentPane.add(form.getRootPane(), BorderLayout.CENTER);

                IdeGlassPaneImpl ideGlassPane = new IdeGlassPaneImpl(frame.getRootPane());
                frame.getRootPane().setGlassPane(ideGlassPane);

                frame.setVisible(true);
            }
        });
    }

    private String askForName(Project project) {
        return Messages.showInputDialog(project,
                "What is your name?", "Input Your Name",
                Messages.getQuestionIcon());
    }

    private void sayHello(Project project, String userName) {
        Messages.showMessageDialog(project,
                String.format("Hello, %s!\n Welcome to PubEditor.", userName), "Information",
                Messages.getInformationIcon());
    }
}
