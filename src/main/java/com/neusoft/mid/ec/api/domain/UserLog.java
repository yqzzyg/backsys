package com.neusoft.mid.ec.api.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class UserLog {
    private Long id;

    private String tableName; //表名分页获取

    private String userIp; //用户ip

    private String userName; // 用户姓名

    private String idtype; // 证件类型

    private String idno; // 证件号

    private String funcid; // 功能编码

    private String sysid; // 发起地接口编码

    private String token; // token令牌

    private String toserverid; // 接收地服务ID

    private String fromserverid; // 发起地服务ID

    private String version; // 版本号

    private String description; // 行为描述

    private String deptCode; // 厅局编码

    private String deptName; // 厅局名称

    private String interfaceCode; // 接口编码

    private String interfaceName; // 接口名称

    private String interfaceUrl; // 接口路径

    private String type; // 操作类型 query/查询，apply/经办

    private Date crtTime;

    private  Integer completionTime;

    @DateTimeFormat(pattern="yyyy-MM-dd 00:00:00")
    private Date startTime;
    @DateTimeFormat(pattern="yyyy-MM-dd 23:59:59")
    private Date endTime;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUserIp() {
        return userIp;
    }
    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getInterfaceCode() {
        return interfaceCode;
    }
    public void setInterfaceCode(String interfaceCode) {
        this.interfaceCode = interfaceCode;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public Date getCrtTime() {
        return crtTime;
    }
    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }
    public String getTableName() {
        return tableName;
    }
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    public String getInterfaceName() {
        return interfaceName;
    }
    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }
    public Date getStartTime() {
        return startTime;
    }
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
    public Date getEndTime() {
        return endTime;
    }
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
    public String getIdtype() {
        return idtype;
    }
    public void setIdtype(String idtype) {
        this.idtype = idtype;
    }
    public String getIdno() {
        return idno;
    }
    public void setIdno(String idno) {
        this.idno = idno;
    }
    public String getFuncid() {
        return funcid;
    }
    public void setFuncid(String funcid) {
        this.funcid = funcid;
    }
    public String getSysid() {
        return sysid;
    }
    public void setSysid(String sysid) {
        this.sysid = sysid;
    }
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    public String getToserverid() {
        return toserverid;
    }
    public void setToserverid(String toserverid) {
        this.toserverid = toserverid;
    }
    public String getFromserverid() {
        return fromserverid;
    }
    public void setFromserverid(String fromserverid) {
        this.fromserverid = fromserverid;
    }
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
    public String getDeptCode() {
        return deptCode;
    }
    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }
    public String getDeptName() {
        return deptName;
    }
    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
    public String getInterfaceUrl() {
        return interfaceUrl;
    }
    public void setInterfaceUrl(String interfaceUrl) {
        this.interfaceUrl = interfaceUrl;
    }

    public Integer getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(Integer completionTime) {
        this.completionTime = completionTime;
    }
}
