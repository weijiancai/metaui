package com.metaui.fxbase.ui.win;

import com.metaui.core.datasource.DataMap;
import com.metaui.core.meta.MetaManager;
import com.metaui.core.ui.ViewManager;
import com.metaui.core.ui.layout.property.FormProperty;
import com.metaui.fxbase.MuEventHandler;
import com.metaui.fxbase.ui.component.guide.MetaCreateGuide;
import com.metaui.fxbase.ui.design.MUFormDesigner;
import com.metaui.fxbase.ui.view.MUDialog;
import com.metaui.fxbase.ui.view.MUTable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

/**
 * 视图管理窗口
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MUViewWin extends BorderPane {
    private MUTable viewTable;
    private Button btnDesign;
    private Button btnViewCreate;

    public MUViewWin() {
        initUI();
    }

    private void initUI() {
        btnDesign = new Button("设计视图");
        btnViewCreate = new Button("创建视图");

        viewTable = new MUTable();
        viewTable.initUI(MetaManager.getMeta("View"));
        viewTable.getTableToolBar().getItems().addAll(btnDesign, btnViewCreate);


        this.setCenter(viewTable);
        // 注册按钮
        registButtonEvents();
    }

    private void registButtonEvents() {
        btnDesign.setOnAction(new MuEventHandler<ActionEvent>() {
            @Override
            public void doHandler(ActionEvent event) throws Exception {
                DataMap data = viewTable.getSelectedItem();
                if (data == null) {
                    MUDialog.showInformation("请选择行！");
                    return;
                }
                if (data.getString("name").endsWith("FormView") || data.getString("name").endsWith("QueryView")) {
                    FormProperty formConfig = new FormProperty(ViewManager.getViewById(data.getString("id")));
                    MUFormDesigner designer = new MUFormDesigner(formConfig);
                    MUDialog.showCustomDialog(null, "设计视图", designer, new Callback<Void, Void>() {
                        @Override
                        public Void call(Void param) {
                            return null;
                        }
                    });
                }
            }
        });
        btnViewCreate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                MetaCreateGuide guide = new MetaCreateGuide();
                MUDialog.showCustomDialog(null, "创建元数据向导", guide, null);
            }
        });
    }
}
