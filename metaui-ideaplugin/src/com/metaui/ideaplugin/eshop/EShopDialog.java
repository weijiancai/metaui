package com.metaui.ideaplugin.eshop;

import com.intellij.find.EditorSearchSession;
import com.intellij.ide.highlighter.XmlHighlighterFactory;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.ex.EditorEx;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.wm.impl.IdeGlassPaneImpl;
import com.intellij.openapi.wm.impl.IdeRootPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EShopDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPanel editorPanel;
    private JButton btnAddAccount;
    private JButton btnModifyAccount;
    private ComboBox apiSiteCB;
    private ComboBox accountCB;
    private JRootPane rootPane;
    private IdeGlassPaneImpl ideGlassPane;

    private Project project;

    public EShopDialog(Project project) {
        this.project = project;

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

// call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

// call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        createEditor();
    }

    private void createEditor() {
        ideGlassPane = new IdeGlassPaneImpl(getRootPane());
        getRootPane().setGlassPane(ideGlassPane);

        EditorFactory editorFactory = EditorFactory.getInstance();
        Document doc = editorFactory.createDocument(getText());
        final EditorEx editor = (EditorEx)editorFactory.createEditor(doc);
        editor.setHighlighter(XmlHighlighterFactory.createXMLHighlighter(editor.getColorsScheme()));
        EditorSearchSession.start(editor, project);
        editorPanel.add(editor.getComponent(), BorderLayout.CENTER);
        editorPanel.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_F) {
                    System.out.println("open search");
                    EditorSearchSession.start(editor, project);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }

    private String getText() {
        return "<idea-plugin version=\"2\">\n" +
                "    <id>com.metaui.ideaplugin</id>\n" +
                "    <name>MetaUI for idea plugin</name>\n" +
                "    <version>1.0</version>\n" +
                "    <vendor email=\"support@metaui.com\" url=\"http://www.metaui.com\">MetaUI</vendor>\n" +
                "\n" +
                "    <description><![CDATA[\n" +
                "      MetaUI for idea plugin\n" +
                "    ]]></description>\n" +
                "\n" +
                "    <change-notes><![CDATA[\n" +
                "      Add change notes here.<br>\n" +
                "      <em>most HTML tags may be used</em>\n" +
                "    ]]>\n" +
                "    </change-notes>\n" +
                "\n" +
                "    <!-- please see http://www.jetbrains.org/intellij/sdk/docs/basics/getting_started/build_number_ranges.html for description -->\n" +
                "    <idea-version since-build=\"141.0\"/>\n" +
                "\n" +
                "    <!-- please see http://www.jetbrains.org/intellij/sdk/docs/basics/getting_started/plugin_compatibility.html\n" +
                "         on how to target different products -->\n" +
                "    <!-- uncomment to enable plugin in all products\n" +
                "    <depends>com.intellij.modules.lang</depends>\n" +
                "    -->\n" +
                "\n" +
                "    <extensions defaultExtensionNs=\"com.intellij\">\n" +
                "        <!-- Add your extensions here -->\n" +
                "    </extensions>\n" +
                "\n" +
                "    <actions>\n" +
                "        <!-- Add your actions here -->\n" +
                "        <group id=\"MetaUIPlugin.Menu\" text=\"_Meta UI\" description=\"Meta UI Menu\">\n" +
                "            <add-to-group group-id=\"MainMenu\" anchor=\"last\"/>\n" +
                "            <action id=\"MetaUIPlugin.EShopAction\" class=\"com.metaui.ideaplugin.eshop.EShopAction\" text=\"网店API\" description=\"网店API\"/>\n" +
                "        </group>\n" +
                "    </actions>\n" +
                "\n" +
                "    <application-components>\n" +
                "        <component>\n" +
                "            <implementation-class>com.metaui.ideaplugin.MetaUIPlugin</implementation-class>\n" +
                "        </component>\n" +
                "    </application-components>\n" +
                "</idea-plugin>";
    }

    private void onOK() {
// add your code here
        dispose();
    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        EShopDialog dialog = new EShopDialog(null);
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    private void createUIComponents() {
        rootPane = new JRootPane();
//        ideGlassPane = new IdeGlassPaneImpl(getRootPane());
//        getRootPane().setGlassPane(ideGlassPane);

//        createEditor();
    }
}
