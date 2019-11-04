package com.neusoft.mid.ec.api.domain.country;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @ClassName: CountryOragnization
 * @Description: 机构
 * @author 蔡旭新
 * @date 2019年10月5日
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CountryOrganization {
	private String tableName;
	private String id;
	//机构编号
	private String AJG001;
	//机构名称
	private String AJG004;
	//机构地址
	private String AJG008;
	//机构电子信箱
	private String AJG002;
	//机构传真
	private String AJG003;
	//机构联系电话
	private String AJG005;
	//机构联系手机
	private String AJG006;
	//机构联系人
	private String AJG007;
	//机构网站
	private String AJG009;
	//机构简介
	private String AJG010;
	//机构统一社会信用代码
	private String AJG011;
	//机构注册时间（成立时间）
	private String AJG012;
	//数据来源
	private String DSOURCE;
	//备注
	private String AJG013;
	//邮政编码
	private String AJG014;
	//所属厅局
	private String AJG015;
	//机构状态
	private String AJG016;
	//经办人代码
	private String AAE011;
	//经办机构代码
	private String AAE017;
	//经办日期
	private String AAE036;
	//经办人
	private String AAE019;
	//经办机构
	private String AAE020;
	//经办机构区划代码
	private String AAE022;
	//经办机构区划名称
	private String AAE024;
	//经办记录序号
	private String AAE040;
	//最后一次修改经办人代码
	private String AAF011;
	//最后一次修改经办机构代码
	private String AAF017;
	//最后一次修改经办日期
	private String AAF036;
	//最后一次修改经办人
	private String AAF019;
	//最后一次修改经办机构
	private String AAF020;
	//最后一次修改经办机构区划代码
	private String AAF022;
	//最后一次修改经办机构区划名称
	private String AAF024;
	//地图坐标x
	private String GPSX;
	//地图坐标y
	private String GPSY;
	//预留字段
	private String AJG100;
	private String AJG101;
	private String AJG102;
	private String AJG103;
	private String AJG104;
	private String AJG105;
	private String AJG106;
	private String AJG107;
	private String AJG108;
	private String AJG109;
	private String AJG110;
	private String AJG111;
	private String AJG112;
	private String AJG113;
	private String AJG114;
	private String AJG115;
	private String AJG116;
	private String AJG117;
	private String AJG118;
	private String AJG119;
	private String AJG120;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAJG001() {
		return AJG001;
	}
	public void setAJG001(String aJG001) {
		AJG001 = aJG001;
	}
	public String getAJG004() {
		return AJG004;
	}
	public void setAJG004(String aJG004) {
		AJG004 = aJG004;
	}
	public String getAJG008() {
		return AJG008;
	}
	public void setAJG008(String aJG008) {
		AJG008 = aJG008;
	}
	public String getAJG002() {
		return AJG002;
	}
	public void setAJG002(String aJG002) {
		AJG002 = aJG002;
	}
	public String getAJG003() {
		return AJG003;
	}
	public void setAJG003(String aJG003) {
		AJG003 = aJG003;
	}
	public String getAJG005() {
		return AJG005;
	}
	public void setAJG005(String aJG005) {
		AJG005 = aJG005;
	}
	public String getAJG006() {
		return AJG006;
	}
	public void setAJG006(String aJG006) {
		AJG006 = aJG006;
	}
	public String getAJG007() {
		return AJG007;
	}
	public void setAJG007(String aJG007) {
		AJG007 = aJG007;
	}
	public String getAJG009() {
		return AJG009;
	}
	public void setAJG009(String aJG009) {
		AJG009 = aJG009;
	}
	public String getAJG010() {
		return AJG010;
	}
	public void setAJG010(String aJG010) {
		AJG010 = aJG010;
	}
	public String getAJG011() {
		return AJG011;
	}
	public void setAJG011(String aJG011) {
		AJG011 = aJG011;
	}
	public String getAJG012() {
		return AJG012;
	}
	public void setAJG012(String aJG012) {
		AJG012 = aJG012;
	}
	public String getDSOURCE() {
		return DSOURCE;
	}
	public void setDSOURCE(String dSOURCE) {
		DSOURCE = dSOURCE;
	}
	public String getAJG013() {
		return AJG013;
	}
	public void setAJG013(String aJG013) {
		AJG013 = aJG013;
	}
	public String getAJG014() {
		return AJG014;
	}
	public void setAJG014(String aJG014) {
		AJG014 = aJG014;
	}
	public String getAJG015() {
		return AJG015;
	}
	public void setAJG015(String aJG015) {
		AJG015 = aJG015;
	}
	public String getAJG016() {
		return AJG016;
	}
	public void setAJG016(String aJG016) {
		AJG016 = aJG016;
	}
	public String getAAE011() {
		return AAE011;
	}
	public void setAAE011(String aAE011) {
		AAE011 = aAE011;
	}
	public String getAAE017() {
		return AAE017;
	}
	public void setAAE017(String aAE017) {
		AAE017 = aAE017;
	}
	public String getAAE036() {
		return AAE036;
	}
	public void setAAE036(String aAE036) {
		AAE036 = aAE036;
	}
	public String getAAE019() {
		return AAE019;
	}
	public void setAAE019(String aAE019) {
		AAE019 = aAE019;
	}
	public String getAAE020() {
		return AAE020;
	}
	public void setAAE020(String aAE020) {
		AAE020 = aAE020;
	}
	public String getAAE022() {
		return AAE022;
	}
	public void setAAE022(String aAE022) {
		AAE022 = aAE022;
	}
	public String getAAE024() {
		return AAE024;
	}
	public void setAAE024(String aAE024) {
		AAE024 = aAE024;
	}
	public String getAAE040() {
		return AAE040;
	}
	public void setAAE040(String aAE040) {
		AAE040 = aAE040;
	}
	public String getAAF011() {
		return AAF011;
	}
	public void setAAF011(String aAF011) {
		AAF011 = aAF011;
	}
	public String getAAF017() {
		return AAF017;
	}
	public void setAAF017(String aAF017) {
		AAF017 = aAF017;
	}
	public String getAAF036() {
		return AAF036;
	}
	public void setAAF036(String aAF036) {
		AAF036 = aAF036;
	}
	public String getAAF019() {
		return AAF019;
	}
	public void setAAF019(String aAF019) {
		AAF019 = aAF019;
	}
	public String getAAF020() {
		return AAF020;
	}
	public void setAAF020(String aAF020) {
		AAF020 = aAF020;
	}
	public String getAAF022() {
		return AAF022;
	}
	public void setAAF022(String aAF022) {
		AAF022 = aAF022;
	}
	public String getAAF024() {
		return AAF024;
	}
	public void setAAF024(String aAF024) {
		AAF024 = aAF024;
	}
	public String getGPSX() {
		return GPSX;
	}
	public void setGPSX(String gPSX) {
		GPSX = gPSX;
	}
	public String getGPSY() {
		return GPSY;
	}
	public void setGPSY(String gPSY) {
		GPSY = gPSY;
	}
	public String getAJG100() {
		return AJG100;
	}
	public void setAJG100(String aJG100) {
		AJG100 = aJG100;
	}
	public String getAJG101() {
		return AJG101;
	}
	public void setAJG101(String aJG101) {
		AJG101 = aJG101;
	}
	public String getAJG102() {
		return AJG102;
	}
	public void setAJG102(String aJG102) {
		AJG102 = aJG102;
	}
	public String getAJG103() {
		return AJG103;
	}
	public void setAJG103(String aJG103) {
		AJG103 = aJG103;
	}
	public String getAJG104() {
		return AJG104;
	}
	public void setAJG104(String aJG104) {
		AJG104 = aJG104;
	}
	public String getAJG105() {
		return AJG105;
	}
	public void setAJG105(String aJG105) {
		AJG105 = aJG105;
	}
	public String getAJG106() {
		return AJG106;
	}
	public void setAJG106(String aJG106) {
		AJG106 = aJG106;
	}
	public String getAJG107() {
		return AJG107;
	}
	public void setAJG107(String aJG107) {
		AJG107 = aJG107;
	}
	public String getAJG108() {
		return AJG108;
	}
	public void setAJG108(String aJG108) {
		AJG108 = aJG108;
	}
	public String getAJG109() {
		return AJG109;
	}
	public void setAJG109(String aJG109) {
		AJG109 = aJG109;
	}
	public String getAJG110() {
		return AJG110;
	}
	public void setAJG110(String aJG110) {
		AJG110 = aJG110;
	}
	public String getAJG111() {
		return AJG111;
	}
	public void setAJG111(String aJG111) {
		AJG111 = aJG111;
	}
	public String getAJG112() {
		return AJG112;
	}
	public void setAJG112(String aJG112) {
		AJG112 = aJG112;
	}
	public String getAJG113() {
		return AJG113;
	}
	public void setAJG113(String aJG113) {
		AJG113 = aJG113;
	}
	public String getAJG114() {
		return AJG114;
	}
	public void setAJG114(String aJG114) {
		AJG114 = aJG114;
	}
	public String getAJG115() {
		return AJG115;
	}
	public void setAJG115(String aJG115) {
		AJG115 = aJG115;
	}
	public String getAJG116() {
		return AJG116;
	}
	public void setAJG116(String aJG116) {
		AJG116 = aJG116;
	}
	public String getAJG117() {
		return AJG117;
	}
	public void setAJG117(String aJG117) {
		AJG117 = aJG117;
	}
	public String getAJG118() {
		return AJG118;
	}
	public void setAJG118(String aJG118) {
		AJG118 = aJG118;
	}
	public String getAJG119() {
		return AJG119;
	}
	public void setAJG119(String aJG119) {
		AJG119 = aJG119;
	}
	public String getAJG120() {
		return AJG120;
	}
	public void setAJG120(String aJG120) {
		AJG120 = aJG120;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
}
