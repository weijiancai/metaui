package com.metaui.fxbase.win.db.model;

import com.metaui.core.datasource.db.object.DBObject;
import com.metaui.core.datasource.db.object.enums.DBObjectType;
import com.metaui.core.model.ITreeNode;
import com.metaui.fxbase.view.tree.TreeNodeModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wei_jc
 * @since 1.0
 */
public class DBTreeNodeModel extends TreeNodeModel {
    private DBObject dbObject;
    private boolean isInit;
    private DBObjectType dbObjectType;

    public DBTreeNodeModel() {
    }

    public DBTreeNodeModel(DBObject dbObject) {
        this.dbObject = dbObject;
        this.dbObjectType = dbObject.getObjectType();
        this.setId(dbObject.getId());
        this.setDisplayName(dbObject.getDisplayName());
        this.setIcon(dbObject.getIcon());
        this.setLeaf(isLeaf());

        if (DBObjectType.TABLE == dbObjectType || DBObjectType.COLUMN == dbObjectType) {
            setPresentableText(dbObject.getPresentableText());
        }
    }

    public boolean isLeaf() {
        if (dbObject != null) {
            return DBObjectType.COLUMN == dbObjectType;
        }

        return super.isLeaf();
    }

    public DBTreeNodeModel(String displayName) {
        super(displayName);
    }

    public DBTreeNodeModel(String displayName, String presentableText) {
        super(displayName, presentableText);
    }

    @Override
    public void onExpanded(TreeNodeModel parent, Boolean newValue) {
        if (dbObject != null && !isInit && newValue) {
            Service<List<DBTreeNodeModel>> service = new Service<List<DBTreeNodeModel>>() {
                @Override
                protected Task<List<DBTreeNodeModel>> createTask() {
                    return new Task<List<DBTreeNodeModel>>() {
                        @Override
                        protected List<DBTreeNodeModel> call() throws Exception {
                            List<ITreeNode> list = dbObject.getChildren();
                            List<DBTreeNodeModel> modelList = new ArrayList<>();
                            for (ITreeNode node : list) {
                                modelList.add(new DBTreeNodeModel((DBObject) node));
                            }
                            return modelList;
                        }
                    };
                }
            };

            service.valueProperty().addListener(new ChangeListener<List<DBTreeNodeModel>>() {
                @Override
                public void changed(ObservableValue<? extends List<DBTreeNodeModel>> observable, List<DBTreeNodeModel> oldValue, List<DBTreeNodeModel> newValue) {
                    List<DBTreeNodeModel> modelList = newValue;
                    getChildren().setAll(modelList);

                    if (DBObjectType.SCHEMAS == dbObjectType || DBObjectType.TABLES == dbObjectType || DBObjectType.COLUMNS == dbObjectType || DBObjectType.CONSTRAINTS == dbObjectType
                            || DBObjectType.INDEXES == dbObjectType || DBObjectType.TRIGGERS == dbObjectType || DBObjectType.USERS == dbObjectType || DBObjectType.PRIVILEGES == dbObjectType
                            || DBObjectType.CHARSETS == dbObjectType || DBObjectType.VIEWS == dbObjectType || DBObjectType.FUNCTIONS == dbObjectType || DBObjectType.PROCEDURES == dbObjectType) {
                        setPresentableText("(" + modelList.size() + ")");
                    }
                }
            });

            // 启动服务
            service.start();

            isInit = true;
        }
    }

    public DBObject getDbObject() {
        return dbObject;
    }
}
