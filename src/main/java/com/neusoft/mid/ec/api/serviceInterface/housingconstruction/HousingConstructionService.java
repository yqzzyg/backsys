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
    /**
               *  化妆品许可证信息查询
     * @param SHXYDM   社会统一信用代码
     * @return
     * @throws Exception
     */
    JSONArray getC006(String SHXYDM) throws Exception;
    /**
               *   食品经营许可证信息查询
     * @param xzqh  行政区划
     * @param xkzbh 许可证编号
     * @param comMc 企业名称
     * @return
     * @throws Exception
     */
    JSONArray getC007(String xzqh, String xkzbh, String comMc) throws Exception;
    /**
                *   药品经营零售GSP许可证信息查询
     * @param xzqh  行政区划
     * @param xkzbh 许可证编号
     * @param comMc 企业名称
     * @return
     * @throws Exception
     */
    JSONArray getC008(String xzqh, String xkzbh, String comMc) throws Exception;
    /**
               * 药品经营零售许可证信息查询
     * @param xzqh
     * @param xkzbh
     * @param comMc
     * @return
     * @throws Exception
     */
    JSONArray getC009(String xzqh, String xkzbh, String comMc) throws Exception;
    /**
               * 药品生产许可证信息查询
     * @param SHXYDM
     * @return
     * @throws Exception
     */
    JSONArray getC010(String SHXYDM) throws Exception;
}
