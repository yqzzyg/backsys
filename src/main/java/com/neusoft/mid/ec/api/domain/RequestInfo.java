package com.neusoft.mid.ec.api.domain;

/**
 *请求参数信息
 */
public class RequestInfo {
    /**
     * 接口版本号
     */
    private String version;

    /**
     * 标注消息发起地服务id，由交易平台下发
     */
    private String fromserverid;

    /**
     * 标注消息接受地服务id，由交易平台下发
     */
    private String toserverid;

    /**
     * 交易令牌，由交易平台下发
     */
    private String token;

    /**
     * 交易平台接口编码，由交易平台下发
     */
    private String sysid;

    /**
     * 功能编号，由交易平台下发
     */
    private String funcid;

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 用户证件号码
     */
    private String idno;


    /**
     * 用户证件类型
     */
    private String idtype;
    /**
     * 云政平台userid
     */
    private String yunzheng;
    
    public String getYunzheng() {
		return yunzheng;
	}

	public void setYunzheng(String yunzheng) {
		this.yunzheng = yunzheng;
	}

	public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getFromserverid() {
        return fromserverid;
    }

    public void setFromserverid(String fromserverid) {
        this.fromserverid = fromserverid;
    }

    public String getToserverid() {
        return toserverid;
    }

    public void setToserverid(String toserverid) {
        this.toserverid = toserverid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSysid() {
        return sysid;
    }

    public void setSysid(String sysid) {
        this.sysid = sysid;
    }

    public String getFuncid() {
        return funcid;
    }

    public void setFuncid(String funcid) {
        this.funcid = funcid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdno() {
        return idno;
    }

    public void setIdno(String idno) {
        this.idno = idno;
    }

    public String getIdtype() {
        return idtype;
    }

    public void setIdtype(String idtype) {
        this.idtype = idtype;
    }


    @Override
    public String toString() {
        return "RequestInfo{" +
                "version='" + version + '\'' +
                ", fromserverid='" + fromserverid + '\'' +
                ", toserverid='" + toserverid + '\'' +
                ", token='" + token + '\'' +
                ", sysid='" + sysid + '\'' +
                ", funcid='" + funcid + '\'' +
                ", name='" + name + '\'' +
                ", idno='" + idno + '\'' +
                ", idtype='" + idtype + '\'' +
                ", yunzheng='" + yunzheng + '\'' +
                '}';
    }
}
