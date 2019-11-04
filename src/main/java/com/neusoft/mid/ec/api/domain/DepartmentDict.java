package com.neusoft.mid.ec.api.domain;

import java.util.Date;

public class DepartmentDict {
    private Long id;

    private String dictCode;

    private String dictName;

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
    public String getDictCode() {
        return dictCode;
    }
    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }
    public String getDictName() {
        return dictName;
    }
    public void setDictName(String dictName) {
        this.dictName = dictName;
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
