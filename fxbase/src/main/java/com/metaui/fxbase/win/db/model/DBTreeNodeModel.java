package com.metaui.fxbase.win.db.model;

import com.metaui.core.datasource.db.object.DBObject;
import com.metaui.fxbase.view.tree.TreeNodeModel;

/**
 * @author wei_jc
 * @since 1.0
 */
public class DBTreeNodeModel extends TreeNodeModel {
    private DBObject dbObject;

    public DBTreeNodeModel() {
    }

    public DBTreeNodeModel(DBObject dbObject) {
        this.dbObject = dbObject;
    }

    public DBTreeNodeModel(String displayName) {
        super(displayName);
    }

    public DBTreeNodeModel(String displayName, String presentableText) {
        super(displayName, presentableText);
    }

    @Override
    public void onExpanded(TreeNodeModel parent, Boolean newValue) {

    }
}
