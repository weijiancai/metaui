package com.metaui.fxbase.view;

import com.metaui.core.view.IView;
import com.metaui.core.view.config.FormConfig;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

import java.util.ArrayList;
import java.util.List;

/**
 * MetaUI 表单
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MUForm extends BorderPane implements IView {
    private FormConfig config;
    private GridPane gridPane = new GridPane();
    private ToolBar toolBar = new ToolBar();

    public MUForm(FormConfig config) {
        this.config = config;

        init();
    }

    public FormConfig getConfig() {
        return config;
    }

    @Override
    public void init() {
        layoutUI();
    }

    @Override
    public void layoutUI() {
        /*if (config.getActions().size() > 0) {
            this.setTop(toolBar);
        }*/
        this.setCenter(gridPane);
        this.setStyle("-fx-padding: 10");

        createToolBar();
        createCenter();
    }

    private void createToolBar() {
        /*for (ActionModel action : config.getActions()) {
            Button button = new Button();
            button.textProperty().bindBidirectional(action.displayNameProperty());
            button.idProperty().bindBidirectional(action.idProperty());
            button.setOnAction(new MuEventHandler<ActionEvent>() {
                @Override
                public void doHandler(ActionEvent event) throws Exception {
                    action.getCallback().call(null);
                }
            });
            toolBar.getItems().add(button);
        }*/
    }

    private Node createCenter() {
//        gridPane.setGridLinesVisible(true);
        gridPane.setHgap(config.getHgap());
        gridPane.setVgap(config.getVgap());
        gridPane.setAlignment(Pos.TOP_LEFT);
        gridPane.setPadding(new Insets(5, 0, 0, 0));

        Region labelGap;
        /*BaseFormField formField;
        Region fieldGap;
        int idxRow = 0;
        int idxCol = 0;

        for (FormFieldModel field : config.getFormFields()) {
            *//*if (onlyShowHidden) { // 只显示隐藏的
                if (field.isDisplay()) {
                    continue;
                }
            } else if (!field.isDisplay()) { // 不显示
                continue;
            }*//*

            formField = BaseFormField.getInstance(field);
            queryList.add(formField);
            // 查询表单
            if (FormType.QUERY == config.getFormType()) {
                // TextArea不显示
                if ((formField instanceof MUTextArea || field.getMaxLength() > 200)) {
                    continue;
                }
                // 查询表单，查询条件，至显示3行
                if (idxRow > 3) {
                    break;
                }
            }

            // 单行
            if (field.isSingleLine()) {
                idxRow++;
                gridPane.add(formField.getLabelNode(), 0, idxRow);

                labelGap = new Region();
                labelGap.setPrefWidth(config.getLabelGap());
                gridPane.add(labelGap, 1, idxRow);

                gridPane.add(formField, 2, idxRow, config.getColCount() * 4 - 2, 1);

                GridPane.setHgrow(formField, Priority.ALWAYS);
                idxCol = 0;
                idxRow++;

                continue;
            }

            gridPane.add(formField.getLabelNode(), idxCol++, idxRow);

            labelGap = new Region();
            labelGap.setPrefWidth(config.getLabelGap());
            gridPane.add(labelGap, idxCol++, idxRow);

            gridPane.add(formField, idxCol++, idxRow);

            if (config.getColCount() == 1) { // 单列
                idxCol = 0;
                idxRow++;
            } else { // 多列
                if (idxCol == config.getColCount() * 4 - 1) {
                    idxCol = 0;
                    idxRow++;
                } else {
                    fieldGap = new Region();
                    fieldGap.setPrefWidth(config.getFieldGap());
                    gridPane.add(fieldGap, idxCol++, idxRow);
                }
            }
        }*/

        return gridPane;
    }
}
