package com.metaui.fxbase.ui.view;

import com.metaui.core.meta.MetaManager;
import com.metaui.core.meta.model.Meta;
import com.metaui.core.model.ITreeNode;
import com.metaui.core.model.impl.BaseTreeNode;
import com.metaui.core.ui.model.View;
import com.metaui.core.util.UString;
import com.metaui.fxbase.BaseApp;
import com.metaui.fxbase.MuEventHandler;
import com.metaui.fxbase.ui.IDesktop;
import com.metaui.fxbase.ui.component.search.MUSearchBox;
import com.metaui.fxbase.ui.win.*;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Popup;
import org.controlsfx.control.MasterDetailPane;

import java.util.*;

/**
 * Tabs 桌面
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MUTabsDesktop extends BorderPane implements IDesktop {
    private MUSearchBox searchBox;
    protected MUTree tree;
    protected MUTabPane tabPane;
    protected Map<String, Tab> tabCache = new HashMap<String, Tab>();
    protected ITreeNode navTree;
    protected Label messageLabel;
    private Popup popup = new Popup();

    public MUTabsDesktop() {
        this(null);
    }

    public MUTabsDesktop(ITreeNode navTree) {
        this.navTree = navTree;
        /*TreeView<File> fileTree = new TreeView<>();
        fileTree.setRoot(new FileTreeItem(new File("/")));
        this.setRight(fileTree);*/
        tree = new MUTree(navTree);
    }

    public void initUI() {
        ToolBar toolBar = new ToolBar();
        searchBox = new MUSearchBox(this);
        tabPane = new MUTabPane();
        popup.getContent().add(searchBox);

        this.setTop(toolBar);
        final MasterDetailPane sp = new MasterDetailPane(Side.LEFT);
        sp.setDividerPosition(0.8);
        sp.setMasterNode(tabPane);
        sp.setDetailNode(tree);
        this.setCenter(sp);

        Tab tab = new Tab("桌面");
        tab.setClosable(false);
        tabPane.getTabs().add(tab);

        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);
        searchBox.setPrefWidth(200);

        tree.setOnMouseClicked(new MuEventHandler<MouseEvent>() {
            @Override
            public void doHandler(MouseEvent event) throws Exception {
                if (event.getClickCount() == 2) {
                    ITreeNode node = tree.getSelected();
                    openTab(node);
                }
            }
        });

        tabPane.getTabs().addListener(new ListChangeListener<Tab>() {
            @Override
            public void onChanged(Change<? extends Tab> change) {
                if(change.next() && change.wasRemoved()) {
                    List<? extends Tab> removed = change.getRemoved();
                    if(removed.size() > 0) {
                        for (Tab tab : removed) {
                            tabCache.remove(tab.getId());
                        }
                    }
                }
            }
        });

        this.addEventHandler(KeyEvent.ANY, new MuEventHandler<KeyEvent>() {
            @Override
            public void doHandler(KeyEvent event) throws Exception {
                if (event.isControlDown() && event.getCode() == KeyCode.N) {
                    popup.centerOnScreen();
                    popup.show(BaseApp.getInstance().getStage());
                }
            }
        });
        tabPane.addEventHandler(MouseEvent.MOUSE_CLICKED, new MuEventHandler<MouseEvent>() {
            @Override
            public void doHandler(MouseEvent event) throws Exception {
                hideSearchBox();
            }
        });

        // 创建底部
        createBottom();

        initNavTree();
        initAfter();
    }

    @Override
    public void initAfter() {

    }

    private void initNavTree() {
        navTree = new BaseTreeNode("ROOT");
        final TreeItem<ITreeNode> navTreeItem = new TreeItem<ITreeNode>(navTree);
        navTreeItem.setExpanded(true);
        // 数据源管理
        MUDataSourceWin dataSourceWin = new MUDataSourceWin(messageLabel, tree);
        // 数据字典
        BaseTreeNode dictNode = new BaseTreeNode("数据字典");
        dictNode.setId("Dict");
        dictNode.setView(View.createNodeView(new MUDictWin()));
        TreeItem<ITreeNode> dictItem = new TreeItem<ITreeNode>(dictNode);
        // 元数据
        BaseTreeNode metaNode = new BaseTreeNode("元数据");
        metaNode.setId("Meta");
        metaNode.setView(View.createNodeView(new MUMetaWin()));
        TreeItem<ITreeNode> metaItem = new TreeItem<ITreeNode>(metaNode);
        // 视图管理
        BaseTreeNode viewNode = new BaseTreeNode("视图管理");
        viewNode.setId("ViewManager");
        viewNode.setView(View.createNodeView(new MUViewWin()));
        TreeItem<ITreeNode> viewItem = new TreeItem<ITreeNode>(viewNode);
        // 项目管理
        MUProjectWin projectWin = new MUProjectWin();
        TreeItem<ITreeNode> projectItem = projectWin.getProjectTree();
        // 参数配置
        BaseTreeNode profileSetting = new BaseTreeNode("参数配置");
        profileSetting.setId("ProfileSetting");
        profileSetting.setView(View.createNodeView(new MUTable(MetaManager.getMeta("ProfileSetting"))));
        TreeItem<ITreeNode> ProfileSettingItem = new TreeItem<ITreeNode>(profileSetting);
        // 备份恢复
        MUBackupWin backupWin = new MUBackupWin();
        // Sql控制台
        MUSqlConsoleWin sqlConsoleWin = new MUSqlConsoleWin();

        tree.setRoot(navTreeItem);
        tree.setShowRoot(false);
        navTreeItem.getChildren().add(dictItem);
        navTreeItem.getChildren().add(metaItem);
        navTreeItem.getChildren().add(viewItem);
        navTreeItem.getChildren().add(projectItem);
        navTreeItem.getChildren().add(dataSourceWin.getDataSourceItem());
        navTreeItem.getChildren().add(ProfileSettingItem);
        navTreeItem.getChildren().add(backupWin.getBackupTreeItem());
        navTreeItem.getChildren().add(sqlConsoleWin.getSqlConsoleTreeItem());
    }

    private void createBottom() {
        messageLabel = new Label("无消息");
        this.setBottom(messageLabel);
    }

    public void openTab(ITreeNode node) {
        if (node == null) {
            return;
        }

        // 展开数节点
        tree.expandTo(node);

        // 打开视图
        View view = node.getView();
        if (view != null) {
            String displayName = node.getPresentableText();
            if (UString.isEmpty(displayName)) {
                displayName = node.getDisplayName();
            }
            if (UString.isEmpty(displayName)) {
                displayName = node.getName();
            }
            Tab tab = tabCache.get(node.getId());
            if (tab == null) {
                tab = new Tab(displayName);
                tab.setId(node.getId());

                // 自定义视图
                Node nodeView = view.getNode();
                if (nodeView != null) {
                    tab.setContent(nodeView);
                } else {
                    // 底部数据库对象TabPane
                    MUTabPane dbObjTabPane = new MUTabPane();
                    dbObjTabPane.setSide(Side.BOTTOM);

                    Tab objDefTab = new Tab("对象定义");
                    objDefTab.setClosable(false);

                    Tab dataTab = new Tab("数据信息");
                    dataTab.setClosable(false);

                    Tab genCodeTab = getGenCodeTab(node);

                    dbObjTabPane.getTabs().addAll(objDefTab, dataTab, genCodeTab);
                    dbObjTabPane.getSelectionModel().select(dataTab);
                    MUTable table = new MUTable();
                    table.initUI(view.getMeta());
                    dataTab.setContent(table);

                    tab.setContent(dbObjTabPane);
                }

                tabPane.getTabs().add(tab);
                tabCache.put(node.getId(), tab);
            }
            tabPane.getSelectionModel().select(tab);
        }
    }

    public void hideSearchBox() {
        searchBox.reset();
        popup.hide();
    }

    private Tab getGenCodeTab(final ITreeNode node) {
        final Meta meta = node.getView().getMeta();

        Tab tab = new Tab("生成代码");
        tab.setClosable(false);

        BorderPane borderPane = new BorderPane();
        // 中央代码
        final TextArea codeTA = new TextArea();
        borderPane.setCenter(codeTA);
        // 头部工具条
        ToolBar toolBar = new ToolBar();
        // 模板choice
        final ChoiceBox<String> choiceBox = new ChoiceBox<String>();
        List<String> list = Arrays.asList("JavaBean", "RowMapper", "PDB");
        choiceBox.getItems().addAll(list);
        // 生成代码按钮
        Button btnGenCode = new Button("生成代码");
        btnGenCode.setOnAction(new MuEventHandler<ActionEvent>() {
            @Override
            public void doHandler(ActionEvent event) throws Exception {
                String choice = choiceBox.getValue();
                if (UString.isEmpty(choice)) {
                    MUDialog.showInformation("请选择模板！");
                    return;
                }
                if ("JavaBean".equals(choice)) {
                    codeTA.setText(meta.genJavaBeanCode());
                } else if ("RowMapper".equals(choice)) {
                    codeTA.setText(meta.genRowMapperCode());
                } else if ("PDB".equals(choice)) {
                    codeTA.setText(meta.getPDBCode());
                }
            }
        });
        toolBar.getItems().addAll(choiceBox, btnGenCode);
        borderPane.setTop(toolBar);

        tab.setContent(borderPane);

        return tab;
    }

    @Override
    public Parent getDesktop() {
        return this;
    }

    @Override
    public MUTree getNavTree() {
        return tree;
    }
}
