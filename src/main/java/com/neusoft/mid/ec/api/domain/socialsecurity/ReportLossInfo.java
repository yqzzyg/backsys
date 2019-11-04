package com.neusoft.mid.ec.api.domain.socialsecurity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;

/**
 * 卡挂失
 * <![CDATA[
 <操作*>临时挂失</操作*>
 <用户名*>$username</用户名*>
 <密码*>$password</密码*>
 <城市代码*></城市代码*>
 <社会保障卡卡号*>$cardNo</社会保障卡卡号*>
 <社会保障号码*>$IDNo</社会保障号码*>
 <姓名*>name</姓名*>
 <开户银行></开户银行>
 <银行卡号></银行卡号>
 ]]>
 */

public class ReportLossInfo {


    private String IDNo;

    private String cityCode;

    private String cardNo;

    private String name;


    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getIDNo() {
        return IDNo;
    }

    public void setIDNo(String IDNo) {
        this.IDNo = IDNo;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "ReportLossInfo{" +
                "IDNo='" + IDNo + '\'' +
                ", cityCode='" + cityCode + '\'' +
                ", cardNo='" + cardNo + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
