package com.metaui.fxbase.view;

import com.metaui.fxbase.model.FormFieldModel;
import com.metaui.fxbase.model.FormModel;
import com.metaui.fxbase.view.form.BaseFormField;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class MUForm extends BorderPane {
    private FormModel model;
    private GridPane gridPane = new GridPane();

    public MUForm(FormModel model) {
        this.model = model;

        initUI();
    }

    private void initUI() {
        this.setCenter(createCenter());
        this.setStyle("-fx-padding: 10");
    }

    private Node createCenter() {
//        gridPane.setGridLinesVisible(true);
        gridPane.setHgap(model.getHgap());
        gridPane.setVgap(model.getVgap());
        gridPane.setAlignment(Pos.TOP_LEFT);

        Region labelGap;
        BaseFormField formField;
        Region fieldGap;
        int idxRow = 0;
        int idxCol = 0;

        for (FormFieldModel field : model.getFormFields()) {
            /*if (onlyShowHidden) { // 只显示隐藏的
                if (field.isDisplay()) {
                    continue;
                }
            } else if (!field.isDisplay()) { // 不显示
                continue;
            }*/

            formField = BaseFormField.getInstance(field);
            // 查询表单
            /*if (FormType.QUERY == model.getFormType()) {
                // TextArea不显示
                if ((formField instanceof MuTextArea || field.getMaxLength() > 200)) {
                    continue;
                }
                // 查询表单，查询条件，至显示3行
                if (idxRow > 3) {
                    break;
                }
            }*/

            // 单行
            if (field.isSingleLine()) {
                idxRow++;
//                label = new Label(field.getDisplayName());
                gridPane.add(formField.getLabelNode(), 0, idxRow);

                labelGap = new Region();
                labelGap.setPrefWidth(model.getLabelGap());
                gridPane.add(labelGap, 1, idxRow);

                gridPane.add(formField, 2, idxRow, model.getColCount() * 4 - 2, 1);

                GridPane.setHgrow(formField, Priority.ALWAYS);
                idxCol = 0;
                idxRow++;

                continue;
            }

//            label = new Label(field.getDisplayName());
            gridPane.add(formField.getLabelNode(), idxCol++, idxRow);

            labelGap = new Region();
            labelGap.setPrefWidth(model.getLabelGap());
            gridPane.add(labelGap, idxCol++, idxRow);

            gridPane.add(formField, idxCol++, idxRow);

            if (model.getColCount() == 1) { // 单列
                idxCol = 0;
                idxRow++;
            } else { // 多列
                if (idxCol == model.getColCount() * 4 - 1) {
                    idxCol = 0;
                    idxRow++;
                } else {
                    fieldGap = new Region();
                    fieldGap.setPrefWidth(model.getFieldGap());
                    gridPane.add(fieldGap, idxCol++, idxRow);
                }
            }
        }

        return gridPane;
    }
}
