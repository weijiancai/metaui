package com.metaui.ideaplugin.eshop.form;

import com.intellij.find.EditorSearchSession;
import com.intellij.ide.highlighter.XmlHighlighterFactory;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.ex.EditorEx;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.ui.JBColor;
import com.intellij.ui.components.JBLabel;
import com.intellij.util.ui.UIUtil;
import com.metaui.core.util.UString;
import com.metaui.eshop.api.ApiFactory;
import com.metaui.eshop.api.ApiTester;
import com.metaui.eshop.api.domain.Account;
import com.metaui.eshop.api.domain.ApiInfo;
import com.metaui.eshop.api.domain.ParamInfo;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javax.swing.*;
import java.awt.*;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wei_jc
 * @since 1.0.0
 * 2016/4/17.
 */
public class ApiTestForm {
    private JPanel rootPane;
    private JTabbedPane tabbedPane;
    private JPanel testPanel;
    private JButton btnTest;
    private JPanel webPanel;
    private JPanel resultPanel;
    private JPanel appParamPanel;
    private WebView webView;

    private Document doc;
    private ApiInfo info;
    private EShopApiForm apiForm;
    private Map<JComponent, ParamInfo> componentList = new HashMap<>();

    public ApiTestForm(Project project, ApiInfo info, EShopApiForm apiForm) {
        this.info = info;
        this.apiForm = apiForm;

        // 删除已有参数
        appParamPanel.removeAll();
        /*for (JComponent component : componentList) {
            appParamPanel.remove(component);
        }*/

        Box vBox = Box.createVerticalBox();
        // 初始化请求参数
        for (ParamInfo paramInfo : info.getAppParams()) {
            Box hBox = Box.createHorizontalBox();

            JBLabel label = new JBLabel();
            label.setText(paramInfo.getId());
            label.setHorizontalAlignment(SwingConstants.LEFT);
            if (paramInfo.isRequire()) {
                label.setForeground(JBColor.RED);
            }
            label.setPreferredSize(new Dimension(100, 22));
            hBox.add(label);

            JTextField textField = new JTextField();
            textField.setToolTipText(paramInfo.getExample());
            hBox.add(textField);
            componentList.put(textField, paramInfo);

            vBox.add(hBox);
        }
        appParamPanel.add(vBox);

        // 设置返回结果
        EditorFactory editorFactory = EditorFactory.getInstance();
        doc = editorFactory.createDocument("");
        final EditorEx editor = (EditorEx)editorFactory.createEditor(doc);
        editor.setHighlighter(XmlHighlighterFactory.createXMLHighlighter(editor.getColorsScheme()));
        EditorSearchSession.start(editor, project);
        resultPanel.add(editor.getComponent(), BorderLayout.CENTER);

        // 创建WebView
        createWebView();

        // 测试
        btnTest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    test();
                } catch (Exception e1) {
                    e1.printStackTrace();
                    doc.setText("异常：" + e1.getMessage());
                }
            }
        });
    }

    private void test() throws Exception {
        Account account = apiForm.getAccount();
        if (account == null) {
            Messages.showMessageDialog("请选择账号！", "消息", Messages.getInformationIcon());
            return;
        }
        doc.setText("");
        ApiTester tester = ApiFactory.getTester(apiForm.getApiSiteName());
        String result = tester.test(account, info, getParams());
        doc.setText(result);
    }

    private Map<String,String> getParams() {
        Map<String, String> params = new HashMap<>();
        for (JComponent node : componentList.keySet()) {
            if (node instanceof JTextField) {
                JTextField tf = (JTextField) node;
                ParamInfo param = componentList.get(tf);
                String value = tf.getText();
                if (UString.isNotEmpty(value)) {
                    params.put(param.getId(), value);
                }
            }
        }
        return params;
    }

    private void createWebView() {
        final JFXPanel jfxPanel = new JFXPanel();
        webPanel.add(jfxPanel, BorderLayout.CENTER);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                WebView view = new WebView();
                WebEngine eng = view.getEngine();
                eng.load(info.getUrl());

                Scene scene = new Scene(view, rootPane.getWidth(), rootPane.getHeight());
                jfxPanel.setScene(scene);
            }

        });
    }

    public JPanel getRootPane() {
        return rootPane;
    }

    public void setResult(String text) {
        doc.setText(text);
    }
}
