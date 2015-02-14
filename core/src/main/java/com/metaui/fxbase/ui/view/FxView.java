package com.metaui.fxbase.ui.view;

import com.metaui.core.ui.IView;
import com.metaui.core.ui.IViewConfig;
import com.metaui.fxbase.BaseApp;
import com.metaui.fxbase.ui.layout.FxLayoutFactory;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * JavaFx 视图
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class FxView implements IView<FxPane> {
    protected IViewConfig viewConfig;
    protected FxPane pane;
    protected boolean isDesign; // 是否设计模式
    protected ContextMenu contextMenu = new ContextMenu();

    public FxView(IViewConfig viewConfig, boolean isDesign) {
        this.viewConfig = viewConfig;
        this.isDesign = isDesign;

        pane = FxLayoutFactory.create(viewConfig, true);
        initUI();
    }

    public FxView(IViewConfig viewConfig) {
        this(viewConfig, false);
    }

    @Override
    public void initUI() {
        MenuItem designMenu = new MenuItem("设计视图");
        designMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FxViewDesigner designer = new FxViewDesigner(viewConfig);
                MUDialog.showCustomDialog(BaseApp.getInstance().getStage(), "设计视图", designer, null);
            }
        });
        contextMenu.getItems().addAll(designMenu);

        pane.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!isDesign && event.getButton() == MouseButton.SECONDARY) { // 非设计视图，弹出右键菜单
                    contextMenu.show(pane, event.getScreenX(), event.getScreenY());
                } else {
                    contextMenu.hide();
                }
            }
        });
    }

    @Override
    public IViewConfig getViewConfig() {
        return viewConfig;
    }

    @Override
    public FxPane layout() {
        return pane;
    }
}
