package com.metaui.core.datasource;

import com.metaui.core.datasource.db.QueryResult;
import com.metaui.core.datasource.eventdata.LoaderEventData;
import com.metaui.core.datasource.persist.IPDB;
import com.metaui.core.datasource.request.IRequest;
import com.metaui.core.datasource.request.IResponse;
import com.metaui.core.meta.model.Meta;
import com.metaui.core.model.ITreeNode;
import com.metaui.core.observer.BaseSubject;
import com.metaui.core.observer.Subject;
import com.metaui.core.rest.RestHandler;
import com.metaui.core.ui.ICanQuery;
import com.metaui.core.ui.IValue;

import javax.xml.bind.annotation.XmlAttribute;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据源接口
 *
 * @author wei_jc
 * @since 1.0.0
 */
public abstract class DataSource implements RestHandler {
    private String id;
    private String name;
    private String displayName;
    private String description;
    private DataSourceType type;
    private String url;
    private String host;
    private int port;
    private String userName;
    private String pwd;
    private boolean isValid;
    private int sortNum;
    private Date inputDate;
    private Map<String, String> properties;
    // 资源缓存
    private Map<String, VirtualResource> resourceCache = new HashMap<String, VirtualResource>();
    //
    private Subject<LoaderEventData> loaderSubject = new BaseSubject<LoaderEventData>();

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setType(DataSourceType type) {
        this.type = type;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public void setValid(boolean isValid) {
        this.isValid = isValid;
    }

    public void setSortNum(int sortNum) {
        this.sortNum = sortNum;
    }

    public void setInputDate(Date inputDate) {
        this.inputDate = inputDate;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    /**
     * 获得数据源唯一ID
     *
     * @return 返回数据源唯一ID
     */
    @XmlAttribute
    public String getId() {
        return id;
    }

    /**
     * 获得数据源名称
     *
     * @return 返回数据源名称
     */
    @XmlAttribute
    public String getName() {
        return name;
    }

    /**
     * 获得数据源显示名
     *
     * @return 返回数据源显示名
     */
    @XmlAttribute
    public String getDisplayName() {
        return displayName;
    }

    /**
     * 获得数据源类型
     *
     * @return 返回数据源类型
     */
    @XmlAttribute
    public DataSourceType getType() {
        return type;
    }

    /**
     * 获得数据源描述信息
     *
     * @return 返回数据源描述信息
     */
    @XmlAttribute
    public String getDescription() {
        return description;
    }

    /**
     * 获得数据源URL
     *
     * @return 返回数据源URL
     */
    @XmlAttribute
    public String getUrl() {
        return url;
    }

    /**
     * 获得数据源主机信息
     *
     * @return 返回主机信息
     */
    @XmlAttribute
    public String getHost() {
        return host;
    }

    /**
     * 获得数据源端口
     *
     * @return 返回数据源端口
     */
    @XmlAttribute
    public int getPort() {
        return port;
    }

    /**
     * 获得数据源用户名
     *
     * @return 返回数据源用户名
     */
    @XmlAttribute
    public String getUserName() {
        return userName;
    }

    /**
     * 获得数据源密码
     *
     * @return 返回数据源密码
     */
    @XmlAttribute
    public String getPwd() {
        return pwd;
    }

    /**
     * 获得数据源是否有效
     *
     * @return 如果有效返回true，否则返回false
     */
    @XmlAttribute
    public boolean isValid() {
        return isValid;
    }

    /**
     * 获得排序号
     *
     * @return 返回排序号
     */
    @XmlAttribute
    public int getSortNum() {
        return sortNum;
    }

    /**
     * 获得录入时间
     *
     * @return 返回录入时间
     */
    @XmlAttribute
    public Date getInputDate() {
        return inputDate;
    }

    /**
     * 获得数据源连接属性
     *
     * @return 返回数据源连接属性
     */
    public Map<String, String> getProperties() {
        return properties;
    }

    /**
     * 获取根资源
     *
     * @return 返回根资源
     */
    public abstract VirtualResource getRootResource() throws Exception;

    /**
     * 检索数据
     *
     * @param meta 元数据
     * @param queryList 查询条件列表
     * @return 返回查询结果
     */
    public abstract QueryResult<DataMap> retrieve(Meta meta, List<ICanQuery> queryList, int page, int rows) throws Exception;

    /**
     * 删除数据
     *
     * @param meta 元数据
     * @param keys 主键值
     * @throws Exception
     */
    public abstract void delete(Meta meta, String... keys) throws Exception;

    /**
     * 删除数据
     *
     * @param id 资源ID
     * @throws Exception
     */
    public abstract void delete(String id) throws Exception;

    /**
     * 获得导航树
     *
     * @return 返回导航树
     */
    public abstract ITreeNode getNavTree() throws Exception;

    /**
     * 获得某个节点下的导航树
     *
     * @param parent 父节点
     * @return 返回导航树
     * @throws Exception
     */
    public abstract ITreeNode getNavTree(String parent) throws Exception;

    /**
     * 获得某个节点下的孩子节点
     *
     * @param parent 父节点
     * @return 返回孩子节点
     * @throws Exception
     */
    public abstract List<ITreeNode> getChildren(String parent) throws Exception;

    /**
     * 加载数据源
     *
     * @throws Exception
     */
    public abstract void load() throws Exception;

    public abstract QueryResult<DataMap> retrieve(QueryBuilder queryBuilder, int page, int rows) throws Exception;

    public abstract ResourceItem getResource(String path);

    public abstract void save(Map<String, IValue> valueMap) throws Exception;

    public abstract void save(IPDB pdb) throws Exception;

    /**
     * 是否可用
     *
     * @return 如果可以获得数据源连接，返回true，否则返回false
     */
    public abstract boolean isAvailable();

    /**
     * 将资源内容写入到输出流中
     *
     * @param id 资源id
     * @param os 输出流
     * @since 1.0.0
     */
    public abstract void write(String id, OutputStream os) throws Exception;

    public abstract VirtualResource findResourceByPath(String path);

    public abstract List<VirtualResource> findResourcesByPath(String path);

    @Override
    public IResponse exp(IRequest request) throws Exception {
        return null;
    }

    @Override
    public void imp(IRequest request) throws Exception {

    }

    public Subject<LoaderEventData> getLoaderSubject() {
        return loaderSubject;
    }
}
