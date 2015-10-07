package com.metaui.fxbase.win.db.model;

import com.metaui.core.datasource.DataSourceManager;
import com.metaui.core.datasource.db.DBDataSource;
import com.metaui.core.datasource.db.DBIcons;
import com.metaui.core.datasource.db.object.DBSchema;
import com.metaui.core.datasource.db.object.enums.DBObjectType;
import com.metaui.core.datasource.db.object.impl.DBObjectImpl;
import com.metaui.fxbase.view.dialog.MUDialog;

/**
 * @author wei_jc
 * @since 1.0
 */
public class DBNavTreeModel {
    private DBTreeNodeModel root;

    public DBNavTreeModel() {
        init();
    }

    private void init() {
        root = new DBTreeNodeModel("数据源");
        root.setLeaf(false);
        for (DBDataSource ds : DataSourceManager.getAvailableDbDataSource()) {
            DBObjectImpl navTree = new DBObjectImpl();
            navTree.setId(ds.getId());
            navTree.setObjectType(DBObjectType.DATABASE);
            navTree.setName(ds.getDisplayName());
            navTree.setComment(ds.getDisplayName());
            try {
                navTree.setDataSource(ds);
            } catch (Exception e) {
                MUDialog.showException(e);
            }

            DBTreeNodeModel dsModel = new DBTreeNodeModel(navTree);
            /*dsModel.setDisplayName(ds.getDisplayName());
            dsModel.setIcon(DBIcons.DBO_DATABASE);
            dsModel.setLeaf(false);*/

            root.getChildren().add(dsModel);

            /*try {
                for (DBSchema schema : ds.getSchemas()) {
                    DBTreeNodeModel model = new DBTreeNodeModel(schema);
                    model.setDisplayName(schema.getDisplayName());
                    model.setIcon(DBIcons.DBO_SCHEMA);
                    model.setLeaf(false);

                    dsModel.getChildren().add(model);
                }
            } catch (Exception e) {
                MUDialog.showException(e);
            }*/
        }
    }

    public DBTreeNodeModel getRoot() {
        return root;
    }
}
