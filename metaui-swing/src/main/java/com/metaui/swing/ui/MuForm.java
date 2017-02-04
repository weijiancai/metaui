package com.metaui.swing.ui;

import com.metaui.core.view.IView;
import com.metaui.core.view.config.FormConfig;
import com.metaui.core.view.config.FormFieldConfig;
import com.metaui.swing.ui.form.BaseFormField;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * @author wei_jc
 * @since 1.0.0
 * 2017/1/28.
 */
public class MuForm extends JPanel implements IView {
    private FormConfig config;

    public MuForm(FormConfig config) {
        this.config = config;
    }

    @Override
    public void init() {
        layoutUI();
//        this.setLayout(new FlowLayout());
//        this.add(BaseFormField.getInstance(config.getFields().get(0)));
    }

    @Override
    public void layoutUI() {
        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);
        this.setBorder(new LineBorder(Color.cyan));

        GridBagConstraints constraints = new GridBagConstraints();

        /*gridPane.setHgap(model.getHgap());
        gridPane.setVgap(model.getVgap());
        gridPane.setAlignment(Pos.TOP_LEFT);
        gridPane.setPadding(new Insets(5, 0, 0, 0));

        Region labelGap;
        BaseFormField formField;
        Region fieldGap;*/
        BaseFormField formField;
        int idxRow = 0;
        int idxCol = 0;

        for (FormFieldConfig field : config.getFields()) {
            if (!field.isDisplay()) { // 不显示
                continue;
            }

            /*if (onlyShowHidden) { // 只显示隐藏的
                if (field.isDisplay()) {
                    continue;
                }
            } else */

            /*formField = BaseFormField.getInstance(field);
            queryList.add(formField);
            // 查询表单
            if (FormType.QUERY == model.getFormType()) {
                // TextArea不显示
                if ((formField instanceof MUTextArea || field.getMaxLength() > 200)) {
                    continue;
                }
                // 查询表单，查询条件，至显示3行
                if (idxRow > 3) {
                    break;
                }
            }*/

            formField = BaseFormField.getInstance(field);

            // 单行
            if (field.isSingleLine()) {
                idxRow++;

                add(constraints, formField, 0, idxRow, config.getColCount());

                idxCol = 0;
                idxRow++;

                continue;
            }

            add(constraints, formField, idxCol++, idxRow, 1);

            if (config.getColCount() == 1) { // 单列
                idxCol = 0;
                idxRow++;
            } else { // 多列
                if (idxCol == config.getColCount()) {
                    idxCol = 0;
                    idxRow++;
                }
            }
        }
    }

    private void add(GridBagConstraints constraints, JComponent component, int x, int y, int w) {
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.gridwidth = w;
        constraints.gridheight = 1;
        constraints.weightx = 1;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        this.add(component, constraints);
    }
}
