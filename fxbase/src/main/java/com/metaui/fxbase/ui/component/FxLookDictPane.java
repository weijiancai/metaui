package com.metaui.fxbase.ui.component;

import com.metaui.core.dict.DictCategory;
import com.metaui.core.dict.DictCode;
import javafx.scene.control.Label;
import javafx.scene.layout.TilePane;

/**
 * 查看数据字典面板
 * 
 * @author wei_jc
 * @since 1.0.0
 */
public class FxLookDictPane extends TilePane {
    public FxLookDictPane(DictCategory dictCategory) {
        this.setPrefColumns(2);
        for (DictCode code : dictCategory.getCodeList()) {
            this.getChildren().addAll(new Label(code.getName()), new Label(code.getDisplayName()));
        }
    }
}
