package com.metaui.fxbase.win.db;

import com.metaui.core.ui.IView;
import com.metaui.fxbase.MuEventHandler;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;

/**
 * 导航视图
 *
 * @author wei_jc
 * @since 1.0
 */
public class DBNavView extends BorderPane implements IView {
    private ToolBar toolBar;
    private DBNavTree navTree;

    public DBNavView() {

    }

    public void initUI() {
        toolBar = new ToolBar();
        Button btnAdd = new Button("添加");
        btnAdd.setOnAction(new MuEventHandler<ActionEvent>() {
            @Override
            public void doHandler(ActionEvent event) throws Exception {

            }
        });
        toolBar.getItems().add(btnAdd);

        this.setTop(toolBar);

        navTree = new DBNavTree();
        this.setCenter(navTree);
        navTree.initUI();
    }
}
