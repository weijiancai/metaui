package com.metaui.core.model;

import com.metaui.core.meta.annotation.MetaTreeElement;
import com.metaui.core.model.impl.BaseTreeModel;
import com.metaui.core.model.impl.BaseTreeNode;

import java.util.HashMap;
import java.util.Map;

/**
 * 模型管理器
 *
 * @author wei_jc
 * @version 1.0.0
 */
public class ModelManager {
    private static ModelManager instance;
    private Map<String, ITreeModel> treeModelMap = new HashMap<String, ITreeModel>();

    private ModelManager() {
        loadTreeModel();
    }

    /**
     * 加载树形模型数据
     */
    private void loadTreeModel() {

    }

    public static ModelManager getInstance() {
        if (instance == null) {
            instance = new ModelManager();
        }
        return instance;
    }

    public ITreeModel getTreeModel(String modelName) {
        return treeModelMap.get(modelName);
    }

    public void addTreeModel(Object obj) throws Exception {
        Class<?> clazz = obj.getClass();
        if (!clazz.isAnnotationPresent(MetaTreeElement.class)) {
            throw new Exception(String.format("不能将非MetaTreeElement的对象【%s】转化为Meta对象！", obj.getClass().getName()));
        }
        MetaTreeElement metaTree = clazz.getAnnotation(MetaTreeElement.class);
        BaseTreeModel treeModel = new BaseTreeModel();
//        treeModel.setName(meta.getName() + "Tree");

        BaseTreeNode root = new BaseTreeNode();
        root.setId(clazz.getMethod(metaTree.id()).invoke(obj).toString());
        root.setPid(clazz.getMethod(metaTree.pid()).invoke(obj).toString());
        root.setName(clazz.getMethod(metaTree.name()).invoke(obj).toString());
        root.setDisplayName(root.getName());

        treeModel.setRoot(root);
    }
}
