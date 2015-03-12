package com.metaui.tools;

import com.metaui.core.util.UString;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class ViewDesignCtl implements Initializable {
    public ListView<String> tableList;
    public TextArea viewSql;
    public TableView viewTable;
    public TableColumn checkCol;
    public TableColumn nameCol;
    public TableColumn aliasNameCol;
    public Button genViewSql;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<String> tables = getTables();
        tableList.setItems(FXCollections.observableArrayList(tables));

        checkCol.setCellFactory(CheckBoxTableCell.forTableColumn(checkCol));
        checkCol.setCellValueFactory(new PropertyValueFactory<>("checked"));

        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        aliasNameCol.setCellValueFactory(new PropertyValueFactory<>("alias"));

        StringConverter<Object> sc = new StringConverter<Object>() {
            @Override
            public String toString(Object t) {
                return t == null ? null : t.toString();
            }

            @Override
            public Object fromString(String string) {
                return string;
            }
        };

        aliasNameCol.setCellFactory(TextFieldTableCell.forTableColumn(sc));

        tableList.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                String tableName = tableList.getSelectionModel().getSelectedItem();
                List<Column> columns = getColumns(tableName);
                viewTable.setItems(FXCollections.observableArrayList(columns));
            }
        });

        genViewSql.setOnAction(event -> {
            String sql = genViewSql(tableList.getSelectionModel().getSelectedItem());
            viewSql.setText(sql);
        });
    }

    public List<String> getTables() {
        List<String> tables = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/syyundong", "root", "root");
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet rs = metaData.getTables("syyundong", null, null, null);
            while (rs.next()) {
                tables.add(rs.getString("TABLE_NAME"));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return tables;
    }

    public List<Column> getColumns(String tableName) {
        List<Column> columns = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/syyundong", "root", "root");
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet rs = metaData.getColumns("syyundong", null, tableName, null);
            while (rs.next()) {
                String colName = rs.getString("COLUMN_NAME");
                Column column = new Column(true, colName, UString.columnNameToFieldName(colName));
                columns.add(column);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return columns;
    }

    public class Column {
        private final BooleanProperty checked;
        private final StringProperty name;
        private final StringProperty alias;

        public Column(boolean checked, String name, String alias) {
            this.checked = new SimpleBooleanProperty(checked);
            this.name = new SimpleStringProperty(name);
            this.alias = new SimpleStringProperty(alias);
        }

        public boolean getChecked() {
            return checked.get();
        }

        public BooleanProperty checkedProperty() {
            return checked;
        }

        public String getName() {
            return name.get();
        }

        public StringProperty nameProperty() {
            return name;
        }

        public String getAlias() {
            return alias.get();
        }

        public StringProperty aliasProperty() {
            return alias;
        }
    }

    public String genViewSql(String tableName) {
        StringBuilder sb = new StringBuilder();
        List<Column> columns = viewTable.getItems();
        sb.append("SELECT ");
        for (Column column : columns) {
            if (column.getChecked()) {
                sb.append(column.getName()).append(" ").append(column.getAlias()).append(",");
            }
        }
        UString.deleteEndStr(sb, ",");
        sb.append(" FROM ").append(tableName);

        return sb.toString();
    }

    public static void main(String[] args) {
        ViewDesignCtl ctl = new ViewDesignCtl();
        ctl.getTables().forEach(System.out::println);
    }
}
