package com.metaui.swing.model;

import javax.swing.*;
import java.util.List;
import java.util.Vector;

/**
 * 下拉框数据模型
 *
 * @author wei_jc
 * @since 1.0.0
 * 2016/4/17.
 */
public class MUComboBoxModel extends DefaultComboBoxModel {
    public MUComboBoxModel() {
    }

    public MUComboBoxModel(List list) {
        super(list.toArray());
    }

    public MUComboBoxModel(Object[] items) {
        super(items);
    }

    public MUComboBoxModel(Vector v) {
        super(v);
    }
}
