package com.neusoft.mid.ec.api.domain;

import java.util.Date;

public class InterfaceDict {
    private Long id;

    private String interfaceCode;

    private String interfaceName;

    private String interfaceUrl;

    private String crtUser;

    private Date crtTime;

    private String modfUser;

    private Date modfTime;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getInterfaceCode() {
        return interfaceCode;
    }
    public void setInterfaceCode(String interfaceCode) {
        this.interfaceCode = interfaceCode;
    }
    public String getInterfaceName() {
        return interfaceName;
    }
    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }
    public String getInterfaceUrl() {
        return interfaceUrl;
    }
    public void setInterfaceUrl(String interfaceUrl) {
        this.interfaceUrl = interfaceUrl;
    }
    public String getCrtUser() {
        return crtUser;
    }
    public void setCrtUser(String crtUser) {
        this.crtUser = crtUser;
    }
    public Date getCrtTime() {
        return crtTime;
    }
    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }
    public String getModfUser() {
        return modfUser;
    }
    public void setModfUser(String modfUser) {
        this.modfUser = modfUser;
    }
    public Date getModfTime() {
        return modfTime;
    }
    public void setModfTime(Date modfTime) {
        this.modfTime = modfTime;
    }
}
