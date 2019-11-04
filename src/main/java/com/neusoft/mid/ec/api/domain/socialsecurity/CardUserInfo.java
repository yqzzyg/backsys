package com.neusoft.mid.ec.api.domain.socialsecurity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

/**
 * 社保卡用户信息
 * <操作*>人员信息修改</操作*>
 <用户名*>$username</用户名*>
 <密码*>$password</密码*>
 <证件号码*>$IDNo</证件号码*>
 <姓名*>$name</姓名*>
 <性别>$sex</性别>
 <移动电话>$phone</移动电话>
 <通讯地址>$address</通讯地址>
 <邮政编码>$postalCode</邮政编码
 <电子邮箱>$email</电子邮箱>
 <单位编号>$companyCode</单位编号>
 <单位名称>$company</单位名称>
 <所在社区(村)>$community</所在社区(村)>
 */

public class CardUserInfo {

    private String IDNo;

    private String name;

    private String sex;

    private String phone;

    private String address;

    private String postalCode;

    private String email;

    private String company;

    private String companyCode;

    private String community;




    public String getIDNo() {
        return IDNo;
    }

    public void setIDNo(String IDNo) {
        this.IDNo = IDNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }


    @Override
    public String toString() {
        return "SBKUserInfo{" +
                "IDNo='" + IDNo + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", email='" + email + '\'' +
                ", company='" + company + '\'' +
                ", companyCode='" + companyCode + '\'' +
                ", community='" + community + '\'' +
                '}';
    }
}
