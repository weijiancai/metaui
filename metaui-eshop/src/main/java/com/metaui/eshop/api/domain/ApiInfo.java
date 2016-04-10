package com.metaui.eshop.api.domain;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * 接口信息：
 *
 * @author wei_jc
 * @since 1.0.0
 * 2016/4/9.
 */
@XmlRootElement
@XmlType(propOrder = {"id", "name", "desc", "explain", "authorize", "scene", "url", "sysParams", "appParams", "returnParams", "reqCodes", "resCodes", "exceptionCodes", "errorInfos"})
public class ApiInfo {
    /**
     * 接口唯一标识
     */
    private String id;
    /**
     * 接口名称
     */
    private String name;
    /**
     * 接口描述
     */
    private String desc;
    /**
     * 解释说明
     */
    private String explain;
    /**
     * 授权
     */
    private String authorize;
    /**
     * 应用场景
     */
    private String scene;
    /**
     * api url
     */
    private String url;
    /**
     * 系统参数列表
     */
    private List<ParamInfo> sysParams;
    /**
     * 应用参数列表
     */
    private List<ParamInfo> appParams;
    /**
     * 返回参数列表
     */
    private List<ParamInfo> returnParams;
    /**
     * 请求代码列表
     */
    private List<CodeExample> reqCodes;
    /**
     * 响应代码列表
     */
    private List<CodeExample> resCodes;
    /**
     * 异常代码列表
     */
    private List<CodeExample> exceptionCodes;
    /**
     * 错误码列表
     */
    private List<ErrorInfo> errorInfos;

    @XmlAttribute
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getAuthorize() {
        return authorize;
    }

    public void setAuthorize(String authorize) {
        this.authorize = authorize;
    }

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @XmlElementWrapper(name = "systemParams")
    @XmlElement(name = "param")
    public List<ParamInfo> getSysParams() {
        return sysParams;
    }

    public void setSysParams(List<ParamInfo> sysParams) {
        this.sysParams = sysParams;
    }

    @XmlElementWrapper(name = "appParams")
    @XmlElement(name = "param")
    public List<ParamInfo> getAppParams() {
        return appParams;
    }

    public void setAppParams(List<ParamInfo> appParams) {
        this.appParams = appParams;
    }

    @XmlElementWrapper(name = "returnParams")
    @XmlElement(name = "param")
    public List<ParamInfo> getReturnParams() {
        return returnParams;
    }

    public void setReturnParams(List<ParamInfo> returnParams) {
        this.returnParams = returnParams;
    }

    @XmlElementWrapper(name = "requestCodes")
    @XmlElement(name = "code")
    public List<CodeExample> getReqCodes() {
        return reqCodes;
    }

    public void setReqCodes(List<CodeExample> reqCodes) {
        this.reqCodes = reqCodes;
    }

    @XmlElementWrapper(name = "responseCodes")
    @XmlElement(name = "code")
    public List<CodeExample> getResCodes() {
        return resCodes;
    }

    public void setResCodes(List<CodeExample> resCodes) {
        this.resCodes = resCodes;
    }

    @XmlElementWrapper(name = "exceptionCodes")
    @XmlElement(name = "code")
    public List<CodeExample> getExceptionCodes() {
        return exceptionCodes;
    }

    public void setExceptionCodes(List<CodeExample> exceptionCodes) {
        this.exceptionCodes = exceptionCodes;
    }

    @XmlElementWrapper(name = "errorCodes")
    @XmlElement(name = "error")
    public List<ErrorInfo> getErrorInfos() {
        return errorInfos;
    }

    public void setErrorInfos(List<ErrorInfo> errorInfos) {
        this.errorInfos = errorInfos;
    }

    @Override
    public String toString() {
        return String.format("%s\n%s", id, name);
    }
}
