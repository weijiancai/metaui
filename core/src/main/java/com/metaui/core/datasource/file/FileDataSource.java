package com.metaui.core.datasource.file;

import com.metaui.core.datasource.*;
import com.metaui.core.datasource.db.QueryResult;
import com.metaui.core.datasource.persist.IPDB;
import com.metaui.core.datasource.request.IRequest;
import com.metaui.core.meta.model.Meta;
import com.metaui.core.model.ITreeNode;
import com.metaui.core.ui.ICanQuery;
import com.metaui.core.ui.IValue;

import java.io.OutputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 文件系统数据源
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class FileDataSource extends DataSource {
    @Override
    public VirtualResource getRootResource() {
        return null;
    }

    @Override
    public QueryResult<DataMap> retrieve(Meta meta, List<ICanQuery> queryList, int page, int rows) throws SQLException {
        return null;
    }

    @Override
    public void delete(Meta meta, String... keys) throws Exception {

    }

    @Override
    public void delete(String id) throws Exception {

    }

    @Override
    public ITreeNode getNavTree() {
        return null;
    }

    @Override
    public ITreeNode getNavTree(String parent) throws Exception {
        return null;
    }

    @Override
    public List<ITreeNode> getChildren(String parent) throws Exception {
        return null;
    }

    @Override
    public void load() throws Exception {

    }

    @Override
    public QueryResult<DataMap> retrieve(QueryBuilder queryBuilder, int page, int rows) throws SQLException {
        return null;
    }

    @Override
    public ResourceItem getResource(String path) {
        return null;
    }

    @Override
    public void save(Map<String, IValue> valueMap) throws Exception {

    }

    @Override
    public void save(IPDB pdb) throws Exception {

    }

    @Override
    public boolean isAvailable() {
        return false;
    }

    @Override
    public void write(String id, OutputStream os) throws Exception {

    }

    @Override
    public VirtualResource findResourceByPath(String path) {
        return null;
    }

    @Override
    public List<VirtualResource> findResourcesByPath(String path) {
        return null;
    }

    @Override
    public VirtualResource get(IRequest request) {
        return null;
    }

    @Override
    public void post(IRequest request) {

    }

    @Override
    public void put(IRequest request) {

    }

    @Override
    public void delete(IRequest request) {

    }
}
