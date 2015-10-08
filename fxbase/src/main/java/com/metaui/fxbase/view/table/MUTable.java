package com.metaui.fxbase.view.table;

import com.metaui.core.datasource.DataMap;
import com.metaui.core.datasource.db.object.DBDataset;
import com.metaui.core.meta.model.Meta;
import com.metaui.core.ui.IView;
import com.metaui.core.ui.ViewManager;
import com.metaui.core.ui.model.View;
import com.metaui.core.util.Callback;
import com.metaui.core.util.UClipboard;
import com.metaui.fxbase.MuEventHandler;
import com.metaui.fxbase.model.FormModel;
import com.metaui.fxbase.model.ModelFactory;
import com.metaui.fxbase.ui.component.table.event.TableCellRenderEvent;
import com.metaui.fxbase.view.MUForm;
import com.metaui.fxbase.view.table.cell.BaseTableCell;
import com.metaui.fxbase.view.table.cell.SortNumTableCell;
import com.metaui.fxbase.view.table.column.BaseTableColumn;
import com.metaui.fxbase.view.table.model.TableFieldModel;
import com.metaui.fxbase.view.table.model.TableModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class MUTable extends StackPane implements IView {
    private TableModel model;
    private FormModel queryFormModel;
    private Meta meta;

    private TableToolBar tableToolBar;
    private TablePaging paging;
    private TableView<DataMap> table = new TableView<>();
    private MUForm queryForm;

    private BorderPane tablePane = new BorderPane(); // 表格面板

    private TableColumnChangeListener tableColumnChangeListener;
    private Callback<TableCellRenderEvent> onCellRender;

    public MUTable() {
        this.model = new TableModel();
        initUI();
    }

    public MUTable(TableModel model) {
        this.model = model;
        initUI();
    }

    public MUTable(Meta meta) {
        this.meta = meta;
        // 表格
        this.model = new TableModel();
        View tableView = ViewManager.getTableView(meta);
        if (tableView != null) {
            ModelFactory.convert(tableView, model);
        } else {
            ModelFactory.convert(meta, model);
        }
        // 查询表单
        this.queryFormModel = new FormModel();
        View queryFormView = ViewManager.getQueryFormView(meta);
        if (queryFormView != null) {
            ModelFactory.convert(queryFormView, queryFormModel);
        } else {
            ModelFactory.convert(meta, queryFormModel);
        }

        initUI();
    }

    public MUTable(DBDataset dataset) {
        this.model = new TableModel();
        ModelFactory.convert(dataset, model);
        initUI();
    }

    @Override
    public void initUI() {
        // 初始化查询表单
        initQueryForm();
        // 初始化表格
        initTable();
    }

    private void initQueryForm() {
        if (queryFormModel == null) {
            return;
        }
        queryForm = new MUForm(queryFormModel);
        tablePane.setTop(queryForm);
    }

    private void initTable() {
        tableToolBar = new TableToolBar(this);
        paging = new TablePaging(model);

        createTableColumns();
        // 单元格选中模式
        table.getSelectionModel().setCellSelectionEnabled(true);
        ContextMenu menu = new ContextMenu();
        MenuItem copyItem = new MenuItem("复制");
        copyItem.setOnAction(new MuEventHandler<ActionEvent>() {
            @Override
            public void doHandler(ActionEvent event) throws Exception {
                BaseTableColumn column = (BaseTableColumn) table.getFocusModel().getFocusedCell().getTableColumn();
                DataMap dataMap = table.getSelectionModel().getSelectedItem();
                UClipboard.setContent(dataMap.getString(column.getModel().getName()));
            }
        });
        menu.getItems().add(copyItem);
        table.setContextMenu(menu);
        // 绑定模型
        table.editableProperty().bind(model.editableProperty());
        // 是否显示table工具条
        model.showToolbarProperty().addListener((observable, oldValue, newValue) -> {
            tablePane.setBottom(newValue ? tableToolBar : null);
        });
        // 是否显示分页条
        model.showPagingProperty().addListener((observable, oldValue, newValue) -> {
            tablePane.setBottom(newValue ? paging : null);
        });
        // 数据
        model.valuesProperty().addListener((observable, oldValue, newValue) -> {
            table.getItems().setAll(newValue);
        });
        // 列信息改变
        model.tableFieldsProperty().addListener((observable, oldValue, newValue) -> {
            createTableColumns();
        });

        BorderPane borderPane = new BorderPane();

        // 表格面板
        if (model.getShowToolbar()) {
            borderPane.setTop(tableToolBar);
        }
        if (model.getShowPaging()) {
            tablePane.setBottom(paging);
        }
        borderPane.setCenter(table);
        tablePane.setCenter(borderPane);
        // 添加到StackPane
        if (this.getChildren().size() == 0) {
            this.getChildren().addAll(tablePane);
        }
    }

    private void createTableColumns() {
        // 先删除监听
        if (tableColumnChangeListener != null) {
            table.getColumns().removeListener(tableColumnChangeListener);
        } else {
            tableColumnChangeListener = new TableColumnChangeListener(model);
        }
        // 删除所有列
        table.getColumns().remove(0, table.getColumns().size());
        // 创建序号列
        TableColumn<DataMap, String> sortNumCol = new TableColumn<DataMap, String>("序号");
        sortNumCol.setPrefWidth(50);
        sortNumCol.setCellFactory(param -> new SortNumTableCell());
        table.getColumns().add(sortNumCol);

        // 创建其他列
        for (TableFieldModel fieldModel : model.getTableFields()) {
            table.getColumns().add(new BaseTableColumn(this, fieldModel));
        }
        // 列顺序改变
        table.getColumns().addListener(tableColumnChangeListener);
    }

    public ObservableList<DataMap> getItems() {
        return table.getItems();
    }

    public TableModel getModel() {
        return model;
    }

    public Meta getMeta() {
        return meta;
    }

    public MUForm getQueryForm() {
        return queryForm;
    }

    public Callback<TableCellRenderEvent> getOnCellRender() {
        return onCellRender;
    }

    public void setOnCellRender(Callback<TableCellRenderEvent> onCellRender) {
        this.onCellRender = onCellRender;
    }
}
