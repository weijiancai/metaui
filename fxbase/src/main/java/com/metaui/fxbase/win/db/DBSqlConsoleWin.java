package com.metaui.fxbase.win.db;

import com.metaui.core.datasource.DataMap;
import com.metaui.core.datasource.DataSourceManager;
import com.metaui.core.datasource.QueryBuilder;
import com.metaui.core.datasource.db.DBDataSource;
import com.metaui.core.datasource.db.QueryResult;
import com.metaui.core.datasource.db.object.DBConnection;
import com.metaui.core.datasource.db.object.DBSchema;
import com.metaui.core.datasource.db.object.DBTable;
import com.metaui.core.datasource.db.sql.SqlFormat;
import com.metaui.core.datasource.eventdata.SqlExecuteEventData;
import com.metaui.core.dict.DictCode;
import com.metaui.core.dict.DictManager;
import com.metaui.core.dict.EnumAlign;
import com.metaui.core.model.ITreeNode;
import com.metaui.core.model.impl.BaseTreeNode;
import com.metaui.core.observer.Observer;
import com.metaui.core.ui.IView;
import com.metaui.core.ui.model.View;
import com.metaui.core.util.UFile;
import com.metaui.core.util.UString;
import com.metaui.fxbase.MuEventHandler;
import com.metaui.fxbase.ui.view.MUDialog;
import com.metaui.fxbase.view.form.MUComboBox;
import com.metaui.fxbase.view.table.MUTable;
import com.metaui.fxbase.view.table.column.BaseTableColumn;
import com.metaui.fxbase.view.table.model.TableFieldModel;
import com.metaui.fxbase.view.table.model.TableModel;
import com.sun.javafx.webkit.Accessor;
import com.sun.webkit.WebPage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;
import org.controlsfx.control.MasterDetailPane;

import java.io.IOException;
import java.util.List;

/**
 * SQL控制台
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class DBSqlConsoleWin extends BorderPane implements IView {
    private TreeItem<ITreeNode> sqlConsoleTreeItem;
    private TabPane tabPane;
    private MUComboBox dataSource;
    private ChoiceBox<DBSchema> schemas;
    private WebPage webPage;
    private TextArea messageTA;
    private MUTable formatTable;
    private MUTable resultTable;
    private MasterDetailPane masterDetailPane;

    private String codeMirrorJs;
    private String codeMirrorCss;
    private String sqlModeJs;
    private String fullScreenJs;
    private String fullScreenCss;
    private String showHintJs;
    private String sqlHintJs;
    private String showHintCss;

    public DBSqlConsoleWin() {
        initUI();
    }

    @Override
    public void initUI() {
        masterDetailPane = new MasterDetailPane(Side.BOTTOM);
        masterDetailPane.setDividerPosition(0.6);
        this.setCenter(masterDetailPane);

        BaseTreeNode node = new BaseTreeNode("SQL控制台");
        node.setId("SqlConsoleView");
        node.setView(View.createNodeView(this));
        sqlConsoleTreeItem = new TreeItem<>(node);

        try {
            codeMirrorJs = UFile.readStringFromCP("/codemirror/lib/codemirror.js");
            codeMirrorCss = UFile.readStringFromCP("/codemirror/lib/codemirror.css");
            sqlModeJs = UFile.readStringFromCP("/codemirror/mode/sql/sql.js");
            fullScreenJs = UFile.readStringFromCP("/codemirror/addon/display/fullscreen.js");
            fullScreenCss = UFile.readStringFromCP("/codemirror/addon/display/fullscreen.css");
            showHintJs = UFile.readStringFromCP("/codemirror/addon/hint/show-hint.js");
            sqlHintJs = UFile.readStringFromCP("/codemirror/addon/hint/sql-hint.js");
            showHintCss = UFile.readStringFromCP("/codemirror/addon/hint/show-hint.css");
        } catch (IOException e) {
            MUDialog.showExceptionDialog(e);
        }

        createTopBar();
        createCenter();
        createBottom();

        registEvent();
    }

    private void createTopBar() {
        ToolBar toolBar = new ToolBar();
        // 数据源

        dataSource = new MUComboBox(DictManager.getDict(DictManager.DICT_DB_DATA_SOURCE));
        dataSource.setPrefWidth(120);
        dataSource.setEditable(false);
        // 数据库
        schemas = new ChoiceBox<DBSchema>();
        schemas.setPrefWidth(80);
        // 执行Sql
        Button btnExec = new Button("执行");
        btnExec.setOnAction(new ExecSqlEventHandler());
        // 格式化
        Button btnFormat = new Button("格式化");
        btnFormat.setOnAction(new SqlFormatEventHandler());

        toolBar.getItems().addAll(new Label("数据源"), dataSource, schemas, btnExec, btnFormat);
        this.setTop(toolBar);
    }

    private void createCenter() {
        final WebView webView = new WebView();
        webView.setStyle("-fx-border-color: #e02222");
        webPage = Accessor.getPageFor(webView.getEngine());
        setSql("");
        masterDetailPane.setMasterNode(webView);
    }

    private void createBottom() {
        tabPane = new TabPane();
        // 消息Tab
        Tab messageTab = new Tab("消息");
        messageTA = new TextArea();
        messageTab.setClosable(false);
        messageTab.setContent(messageTA);
        // 查询结果
        Tab resultTab = new Tab("查询结果");
        resultTab.setClosable(false);
        TableModel resultTableModel = new TableModel();
        resultTableModel.setShowToolbar(false).setColFromData(true);
        resultTable = new MUTable(resultTableModel);
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(resultTable);
        resultTab.setContent(borderPane);
        // 格式化
        Tab formatTab = new Tab("格式化");
        formatTab.setClosable(false);
        formatTable = new MUTable(getSqlFormatTableModel());
        formatTable.setOnCellRender((event, obj) -> {
            BaseTableColumn column = event.getColumn();
            DataMap rowData = event.getRowData();
            String type = rowData.getString(SqlFormat.DB_DATA_TYPE).toLowerCase();
            String value = rowData.getString(SqlFormat.VALUE);

            if (SqlFormat.VALUE_LENGTH.equals(column.getId())) {
                int length = rowData.getInt(SqlFormat.VALUE_LENGTH);
                int maxLength = rowData.getInt(SqlFormat.MAX_LENGTH);
                // 检查长度是否超出最大限度
                if (length > 0 && maxLength > 0 && length > maxLength && !"NULL".equalsIgnoreCase(value) && !type.contains("date")) {
                    event.getCell().setStyle("-fx-padding: 3;-fx-background-color:#ff0000;-fx-text-fill: #ffffff");
                } else {
                    event.getCell().setStyle(null);
                }

            } else if (SqlFormat.VALUE.equals(column.getId())) {
                event.getCell().setStyle(null);

                // 检查数据类型
                if(UString.isNotEmpty(value)) {
                    if(type.contains("int")) {
                        try {
                            Integer.parseInt(value);
                        } catch (Exception e) {
                            event.getCell().setStyle("-fx-padding: 3;-fx-background-color:#ff0000;-fx-text-fill: #ffffff");
                        }
                    } else if (type.contains("decimal")) {
                        try {
                            Double.parseDouble(value);
                        } catch (Exception e) {
                            event.getCell().setStyle("-fx-padding: 3;-fx-background-color:#ff0000;-fx-text-fill: #ffffff");
                        }
                    }
                }

            }

        });
        formatTab.setContent(formatTable);

        tabPane.getTabs().addAll(messageTab, resultTab, formatTab);
        masterDetailPane.setDetailNode(tabPane);
    }

    private void registEvent() {
        dataSource.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue != null && schemas != null) {
                    DictCode code = dataSource.getSelectedItem();
                    DBDataSource dbDataSource = (DBDataSource) DataSourceManager.getDataSource(code.getId());
                    try {
                        List<DBSchema> schemaList = dbDataSource.getSchemas();
                        schemas.setItems(FXCollections.observableArrayList(schemaList));
                        schemas.getSelectionModel().select(0);
                    } catch (Exception e) {
                        MUDialog.showExceptionDialog(e);
                    }
                }
            }
        });

        schemas.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<DBSchema>() {
            @Override
            public void changed(ObservableValue<? extends DBSchema> observable, DBSchema oldValue, DBSchema newValue) {
                setSql(getSql());
            }
        });
        dataSource.getSelectionModel().select(0);
    }

    private void setSql(String sql) {
        webPage.load(webPage.getMainFrame(), convertToHtml(sql), "text/html");
    }

    private String getSql() {
        return webPage.getInnerText(webPage.getMainFrame());
    }

    private String convertToHtml(String source) {
        StringBuilder html = new StringBuilder();
        html.append("<html>\n");
        html.append("    <head>\n");
        html.append("    <script type=\"text/javascript\">\n");
        html.append(codeMirrorJs);
        html.append('\n');
        html.append(sqlModeJs);
        html.append("\n");
        html.append(fullScreenJs);
        html.append("\n");
        html.append(showHintJs);
        html.append("\n");
        html.append(sqlHintJs);
        html.append("    </script>\n");
        html.append("    <style>\n");
        html.append(codeMirrorCss);
        html.append('\n');
        html.append(fullScreenCss);
        html.append('\n');
        html.append(showHintCss);
        html.append('\n');

        html.append("        body {background-color: #f4f4f4;}\n");
        html.append("    </style>\n");
        html.append("    </head>\n");
        html.append("<body contenteditable=\"true\">\n");
        html.append("\n");
        html.append("    <script type=\"text/javascript\"> " +
                "CodeMirror.commands.autocomplete = function(cm) {\n" +
                "    CodeMirror.showHint(cm, CodeMirror.hint.sql, " + getSqlHintOptions() + ");\n" +
                "};" +
                "var myCodeMirror = CodeMirror(document.body, {mode:'text/x-sql',fullScreen:true,extraKeys: {'Alt-/': 'autocomplete'}}); " +
                (UString.isEmpty(source) ? "" : String.format("myCodeMirror.setValue(\"%s\");", source.replace("\n", "\\n"))) +
                "</script>\n");
        html.append("</body>\n");
        html.append("</html>\n");

        try {
            UFile.write(html.toString(), "d:/test/1.html");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return html.toString();
    }

    public TreeItem<ITreeNode> getSqlConsoleTreeItem() {
        return sqlConsoleTreeItem;
    }

    private TableModel getSqlFormatTableModel() {
        TableModel model = new TableModel();
        model.setShowToolbar(false);
        model.setShowPaging(false);

        TableFieldModel colNameProp = new TableFieldModel(SqlFormat.COL_NAME, "列名", 200, 10);
        TableFieldModel valueProp = new TableFieldModel(SqlFormat.VALUE, "值", 400, 20);
        TableFieldModel valueLengthProp = new TableFieldModel(SqlFormat.VALUE_LENGTH, "长度", 30, 30);
        valueLengthProp.setAlign(EnumAlign.CENTER);
        TableFieldModel dataTypeProp = new TableFieldModel(SqlFormat.DB_DATA_TYPE, "数据类型", 120, 40);
        TableFieldModel maxLengthProp = new TableFieldModel(SqlFormat.MAX_LENGTH, "最大长度", 50, 50);
        maxLengthProp.setAlign(EnumAlign.CENTER);
        model.getTableFields().addAll(colNameProp, valueProp, valueLengthProp, dataTypeProp, maxLengthProp);

        return model;
    }

    public String getSqlHintOptions() {
        DBSchema schema = schemas.getValue();
        if (schema == null) {
            return "{}";
        }

        StringBuilder sb = new StringBuilder("{tables:{");
        // tables
        try {
            for (DBTable table : schema.getTables()) {
                sb.append(table.getName()).append(":[");
                /*for (DBColumn column : table.getColumns()) {
                    sb.append("'").append(column.getName()).append("',");
                }
                if (sb.charAt(sb.length() - 1) == ',') {
                    sb.deleteCharAt(sb.length() - 1);
                }*/
                sb.append("],");
            }
            if (sb.charAt(sb.length() - 1) == ',') {
                sb.deleteCharAt(sb.length() - 1);
            }
        } catch (Exception e) {
            MUDialog.showExceptionDialog(e);
        }

        sb.append("}}");
        return sb.toString();
    }

    public void printException(String title, Exception e) {
        // 选中消息Tab
        tabPane.getSelectionModel().select(0);

        StringBuilder sb = new StringBuilder();
        sb.append(title).append(e.getMessage()).append("\n");
        for(StackTraceElement element : e.getStackTrace()) {
            sb.append(element.toString()).append("\r\n");
        }
        printMessage(sb.toString());
    }

    public void printMessage(String message) {
        messageTA.appendText(message);
    }

    class ExecSqlEventHandler extends MuEventHandler<ActionEvent> {

        @Override
        public void doHandler(final ActionEvent event) throws Exception {
            messageTA.setText(""); // 清空消息

            String sql = getSql();
            if (UString.isEmpty(sql)) {
                return;
            }
            sql = sql.trim().replace('\u200B', ' ').replace('\n', ' ');
            DictCode code = dataSource.getSelectedItem();
            if (code == null) {
                MUDialog.showInformation("请选择数据源！");
                return;
            }

            DBDataSource dbDataSource = (DBDataSource) DataSourceManager.getDataSource(code.getId());
            DBSchema schema = schemas.getValue();
            dbDataSource.getDbConnection().setCurrentSchema(schema);

            if (sql.toLowerCase().startsWith("select")) { // 查询
                // 选中查询结果Tab
                tabPane.getSelectionModel().select(1);

                try {
                    QueryBuilder builder = QueryBuilder.create(sql, dbDataSource.getDatabaseType());
                    QueryResult<DataMap> list = dbDataSource.retrieve(builder, -1, resultTable.getModel().getPageRows());
                    resultTable.getModel().setValues(list.getRows());
                    resultTable.getModel().setTotal(list.getTotal());
                } catch (Exception e) {
                    printException("查询失败：", e);
                }
            } else {
                DBConnection connection = dbDataSource.getDbConnection();
                connection.getSqlExecuteSubject().registerObserver(new Observer<SqlExecuteEventData>() {
                    @Override
                    public void update(SqlExecuteEventData data) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("==============================================================================================\r\n");
                        sb.append(data.getSql()).append("\r\n");
                        sb.append(data.isSuccess() ? "执行成功！" : "执行失败");
                        if (!data.isSuccess() && data.getException() != null) {
                            for(StackTraceElement element : data.getException().getStackTrace()) {
                                sb.append(element.toString());
                            }
                        }
                        printMessage(sb.toString());
                    }
                });
                connection.execSqlScript(sql, ";");
                // 选中查询结果Tab
                tabPane.getSelectionModel().select(0);
            }
        }
    }

    class SqlFormatEventHandler extends MuEventHandler<ActionEvent> {

        @Override
        public void doHandler(ActionEvent event) throws Exception {
            messageTA.setText(""); // 清空消息

            String sql = getSql();
            if (UString.isEmpty(sql)) {
                return;
            }
            sql = sql.trim().replace('\u200B', ' ').replace('\n', ' ');
            DictCode code = dataSource.getSelectedItem();
            if (code == null) {
                MUDialog.showInformation("请选择数据源！");
                return;
            }
            // 选中格式化Tab
            tabPane.getSelectionModel().select(2);

            try {
                DBDataSource dbDataSource = (DBDataSource) DataSourceManager.getDataSource(code.getId());
                DBSchema schema = schemas.getValue();
                dbDataSource.getDbConnection().setCurrentSchema(schema);
                SqlFormat format = new SqlFormat(sql, dbDataSource);
                setSql(format.format(schema));
                formatTable.getModel().setValues(format.getDataList());
            } catch (Exception e) {
                printException("格式化代码失败：", e);
            }
        }
    }
}
