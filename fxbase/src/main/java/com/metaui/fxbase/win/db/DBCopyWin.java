package com.metaui.fxbase.win.db;

import com.metaui.core.datasource.db.DBCopy;
import com.metaui.core.datasource.db.DBDataSource;
import com.metaui.core.datasource.db.JdbcDrivers;
import com.metaui.core.dict.EnumAlign;
import com.metaui.core.meta.MetaDataType;
import com.metaui.core.ui.IView;
import com.metaui.fxbase.model.ModelFactory;
import com.metaui.fxbase.view.table.MUTable;
import com.metaui.fxbase.view.table.model.TableFieldModel;
import com.metaui.fxbase.view.table.model.TableModel;
import javafx.scene.layout.BorderPane;

/**
 * @author wei_jc
 * @since 1.0
 */
public class DBCopyWin extends BorderPane implements IView {
    private MUTable table;
    private DBCopy db;

    public DBCopyWin() {
        initUI();
    }

    @Override
    public void initUI() {
        DBDataSource source = new DBDataSource("ecargo", JdbcDrivers.ORACLE, "jdbc:oracle:thin:@192.168.19.128:1521:orcl", "ecargo", "ecargo", null);
        DBDataSource target = new DBDataSource("test", JdbcDrivers.ORACLE, "jdbc:oracle:thin:@192.168.19.128:1521:orcl", "ecargo", "ecargo", null);

        try {
            db = new DBCopy(source, target);

            TableModel model = new TableModel();
            ModelFactory.convert(db.getSourceTables(), model,
                    new TableFieldModel("name", "表名", 200, 10),
                    new TableFieldModel("comment", "显示名", 200, 20),
                    new TableFieldModel("numRows", "行数", 80, 30, MetaDataType.INTEGER, EnumAlign.RIGHT)
            );
            table = new MUTable(model);
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.setCenter(table);
    }
}
