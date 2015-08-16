package com.metaui.fxbase.win;

import com.metaui.core.ui.IView;
import com.metaui.fxbase.model.ConsoleModel;
import javafx.collections.ListChangeListener;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;

import java.util.List;

/**
 * 控制台输出窗口
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class ConsoleWin extends BorderPane implements IView {
    private ToolBar toolBar = new ToolBar();
    private TextArea textArea = new TextArea();
    private ConsoleModel model = new ConsoleModel();

    public ConsoleWin() {
        initUI();
    }

    @Override
    public void initUI() {
        this.setStyle("-fx-padding: 10");

        this.setTop(toolBar);
        this.setCenter(textArea);

        createToolbar();
    }

    private void createToolbar() {
        CheckBox checkBox = new CheckBox("重定向输出流");
        checkBox.selectedProperty().bindBidirectional(model.isResetSystemOutProperty());
        toolBar.getItems().add(checkBox);

        model.getMessages().addListener((ListChangeListener<String>) change -> {
            if (change.next()) {
                if (change.wasAdded()) {
                    List<? extends String> list = change.getAddedSubList();
                    list.forEach(textArea::appendText);
                }
            }
        });
    }
}
