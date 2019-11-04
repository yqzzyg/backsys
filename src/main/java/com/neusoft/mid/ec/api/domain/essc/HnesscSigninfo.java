/**   
* @Title: HnesscSigninfo.java
* @Package com.neusoft.mid.ec.api.domain.essc
* @Description: TODO
* @author zhaohk   
* @date 2019年10月16日 下午3:07:45
* @version V1.0   
*/
package com.neusoft.mid.ec.api.domain.essc;

import java.util.Date;

/**
* @ClassName: HnesscSigninfo
* @Description: TODO(这里用一句话描述这个类的作用)
* @author zhaohk
* @date 2019年10月16日 下午3:07:45
* 
*/
public class HnesscSigninfo {
	private String id;
	private String bizid;
	private String reqTime;
	private String reqMsgId;
	private String cmdNo;
	private String userid;
	private String signStatus;
	private String medicalCardId;
	private String medicalCardNo;
	private String realName;
	private String userCardNo;
	private String areaCode;
	private String mobile;
	private String type;
	private String sign;
	private Date createTime;
	private Date modifyTime;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public HnesscSigninfo() {
	}
	public String getBizid() {
		return bizid;
	}
	public void setBizid(String bizid) {
		this.bizid = bizid;
	}
	public String getReqTime() {
		return reqTime;
	}
	public void setReqTime(String reqTime) {
		this.reqTime = reqTime;
	}
	public String getReqMsgId() {
		return reqMsgId;
	}
	public void setReqMsgId(String reqMsgId) {
		this.reqMsgId = reqMsgId;
	}
	public String getCmdNo() {
		return cmdNo;
	}
	public void setCmdNo(String cmdNo) {
		this.cmdNo = cmdNo;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getSignStatus() {
		return signStatus;
	}
	public void setSignStatus(String signStatus) {
		this.signStatus = signStatus;
	}
	public String getMedicalCardId() {
		return medicalCardId;
	}
	public void setMedicalCardId(String medicalCardId) {
		this.medicalCardId = medicalCardId;
	}
	public String getMedicalCardNo() {
		return medicalCardNo;
	}
	public void setMedicalCardNo(String medicalCardNo) {
		this.medicalCardNo = medicalCardNo;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getUserCardNo() {
		return userCardNo;
	}
	public void setUserCardNo(String userCardNo) {
		this.userCardNo = userCardNo;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

}
