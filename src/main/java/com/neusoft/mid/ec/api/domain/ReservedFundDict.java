package com.neusoft.mid.ec.api.domain;

import java.util.Date;

public class ReservedFundDict {
    private Long id;

    private String dictcode; //代码

    private String dictname; //名称

    private String dictvalue; // 码值

    private String dictdesc; // 描述

    private Date crtTime;//创建日期

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDictcode() {
        return dictcode;
    }

    public void setDictcode(String dictcode) {
        this.dictcode = dictcode;
    }

    public String getDictname() {
        return dictname;
    }

    public void setDictname(String dictname) {
        this.dictname = dictname;
    }

    public String getDictvalue() {
        return dictvalue;
    }

    public void setDictvalue(String dictvalue) {
        this.dictvalue = dictvalue;
    }

    public String getDictdesc() {
        return dictdesc;
    }

    public void setDictdesc(String dictdesc) {
        this.dictdesc = dictdesc;
    }

    public Date getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }
}
