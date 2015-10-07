package com.metaui.fxbase.view.table;

import com.metaui.core.datasource.DataMap;
import com.metaui.core.datasource.db.QueryResult;
import com.metaui.core.meta.model.Meta;
import com.metaui.fxbase.view.table.model.TableModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/**
 * 表格分页
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class TablePaging extends HBox {
    public static final String TOTAL_FORMAT = "总共%s条";
    private TableModel model;
    private Hyperlink totalLink;
    private Pagination pagination;
    private TextField pageRowsTF;

    public TablePaging(TableModel model) {
        this.model = model;
        initUI();
    }

    public void initUI() {
        this.setAlignment(Pos.CENTER_LEFT);

        totalLink = new Hyperlink(String.format(TOTAL_FORMAT, 0));
        pagination = new Pagination(1);
        pagination.currentPageIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                /*Meta meta = config.getMeta();
                try {
                    if (queryForm == null) {
                        return;
                    }
                    QueryResult<DataMap> queryResult = meta.query(queryForm.getQueryList(), newValue.intValue(), getPageRows());
                    table.getItems().clear();
                    table.getItems().addAll(queryResult.getRows());
                    pagination.setPageCount(meta.getPageCount());
                    setTotalRows(meta.getTotalRows());
                } catch (Exception e) {
                    e.printStackTrace();
                }*/
            }
        });

        Label pageRowsLabel = new Label("，每页显示");
        pageRowsTF = new TextField("15");
        this.getChildren().addAll(totalLink, pageRowsLabel, pageRowsTF, new Label("条"), pagination);

        // 绑定值
        model.pageRowsProperty().addListener((observable, oldValue, newValue) -> {
            pageRowsTF.setText(newValue.toString());
        });
        model.totalProperty().addListener((observable, oldValue, newValue) -> {
            totalLink.setText(String.format(TOTAL_FORMAT, newValue));
        });
    }
}
