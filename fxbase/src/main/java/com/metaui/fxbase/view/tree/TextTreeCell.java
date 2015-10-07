package com.metaui.fxbase.view.tree;

import com.metaui.core.util.UString;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TreeCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class TextTreeCell extends TreeCell<TreeNodeModel> {
    private HBox hbox;
    private Label mainLabel;
    protected Label presentableLabel;

    public TextTreeCell() {

    }

    @Override
    protected void updateItem(TreeNodeModel item, boolean empty) {
        super.updateItem(item, empty);
        updateDisplay(item, empty);
    }

    void updateDisplay(TreeNodeModel model, boolean empty) {
//        System.out.println("model = " + (model == null ? "" : model.getDisplayName()) + ", isEmpty = " + empty);
        if (model == null || empty) {
            hbox = null;
            mainLabel = null;
            presentableLabel = null;
            setText(null);
            setGraphic(null);
        } else {
            if (hbox == null) {
                hbox = new HBox(3);
                hbox.setAlignment(Pos.CENTER_LEFT);
                mainLabel = new Label();
                presentableLabel = new Label();
                presentableLabel.setFont(Font.font(11));
                presentableLabel.setTextFill(Color.DARKRED);
                presentableLabel.setAlignment(Pos.BOTTOM_LEFT);
                hbox.getChildren().addAll(mainLabel, presentableLabel);

                setGraphic(hbox);
            }

            // 绑定值
            mainLabel.textProperty().bind(model.displayNameProperty());
            presentableLabel.textProperty().bind(model.presentableTextProperty());
            model.iconProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    if (UString.isNotEmpty(newValue)) {
                        Node node = new ImageView(new Image(getClass().getResourceAsStream(newValue)));
                        mainLabel.setGraphic(node);
                    }
                }
            });
            // 图标
            String icon = model.getIcon();
            if (UString.isNotEmpty(icon)) {
                Node node = new ImageView(new Image(getClass().getResourceAsStream(icon)));
                mainLabel.setGraphic(node);
            }
        }
    }
}
