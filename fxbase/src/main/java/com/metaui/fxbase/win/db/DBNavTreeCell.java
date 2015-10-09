package com.metaui.fxbase.win.db;

import com.metaui.core.datasource.db.object.DBObject;
import com.metaui.core.datasource.db.object.enums.DBObjectType;
import com.metaui.fxbase.view.tree.TextTreeCell;
import com.metaui.fxbase.view.tree.TreeNodeModel;
import com.metaui.fxbase.win.db.model.DBTreeNodeModel;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.paint.Color;

/**
 * 数据库导航 TreeCell
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class DBNavTreeCell extends TextTreeCell {
    private ContextMenu contextMenu = new ContextMenu();

    public DBNavTreeCell() {

    }

    @Override
    protected void updateItem(TreeNodeModel item, boolean empty) {
        super.updateItem(item, empty);

        if (presentableLabel != null && item != null) {
            DBTreeNodeModel model = (DBTreeNodeModel) item;
            DBObject dbObject = model.getDbObject();
            if (dbObject != null) {
                DBObjectType dbObjectType = dbObject.getObjectType();
                if (DBObjectType.TABLE == dbObjectType || DBObjectType.COLUMN == dbObjectType) {
                    presentableLabel.setTextFill(Color.color(0.5, 0.5, 0.5));
                }
                // 设置弹出菜单
                initContextMenu(dbObject);
            }
        }
    }

    public void initContextMenu(DBObject dbObject) {
        DBObjectType dbObjectType = dbObject.getObjectType();
        if (DBObjectType.TABLES == dbObjectType) {
            MenuItem addTableItem = new MenuItem("新增表");
            contextMenu.getItems().add(addTableItem);
            addTableItem.setOnAction(t -> AddTableDialog.show());
        }
        setContextMenu(contextMenu);
    }
}
