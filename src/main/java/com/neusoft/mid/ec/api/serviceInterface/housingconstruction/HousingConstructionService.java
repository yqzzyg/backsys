package com.neusoft.mid.ec.api.serviceInterface.housingconstruction;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/***
 *住建
 */
public interface HousingConstructionService {

    /**
     * 安管人员证书信息查询
     * @param zsbh 证书编号
     * @param sfzh  身份证号
     * @param qymc 企业名称
     * @return
     */
    JSONArray getC001(String zsbh,String sfzh,String qymc) throws Exception;
    /**
     * 特种作业 人员证书信息查询
     *@param zsbh 证书编号
     *@param sfzh  身份证号
     * @return
     */
    JSONArray getC002(String sfzh, String zsbh) throws Exception;

    /**
     *@param  zcbh 注册编号
     *@param  zjhm 证件号码
     *@param  qymc 企业名称
     * 二级建造师证书信息查询
     * @return
     */
    JSONObject getC003(String zcbh,String zjhm,String qymc) throws Exception;
}
