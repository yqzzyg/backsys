package com.neusoft.mid.ec.api.service.country.socialsecurity;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.neusoft.mid.ec.api.controller.socialsecurity.EmploymentQueryController;
import com.neusoft.mid.ec.api.serviceInterface.country.socialsecurity.CountryEmploymentQueryService;
import com.neusoft.mid.ec.api.util.http.WebServiceClientUtil;

import me.puras.common.json.Response;
import me.puras.common.json.ResponseHelper;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 就业查询业务
 */
@Service
public class CountryEmploymentQueryServiceImpl implements CountryEmploymentQueryService {

    private Logger LOG = LoggerFactory.getLogger(CountryEmploymentQueryServiceImpl.class);

    /**
     * 就业类型
     */
    private  static final  Map<String,String> emp_types = new HashMap<>();
    /**
     * 发放类别
     * 01	新增
     02	补发
     03	换证
     */
    private  static final  Map<String,String> given_types = new HashMap<>();
    /**
     * 失业原因
     * 10	年满16周岁，从各类学校毕业、结业、肄业的
     20	从企业、机关、事业单位等各类用人单位失业的
     32	个体工商户（含认定的网络创业）业主、私营企业和民办非企业业主停产、破产停止经营的
     50	承包土地被征用的农村劳动力
     60	军人退出现役且未纳入国家统一安置的，以及随军家属未安置就业的
     70	城镇刑满释放、假释、监外执行的
     90	其他符合失业登记的

     */
    private  static final  Map<String,String> unemp_reason = new HashMap<>();

    /**
     * 11	被用人单位录用
     12	从事个体经营或创办企业，并领取工商营业执照
     13	已从事有稳定收入的劳动，并且月收入不低于当地最低工资标准
     14	公益性岗位安置
     21	已享受基本养老保险待遇
     22	达到退休年龄
     23	完全丧失劳动能力
     24	入学、服兵役、移居境外
     25	被判刑收监执行
     26	死亡
     31	终止就业要求或拒绝接受公共就业服务
     32	在规定时间内未与公共就业服务机构联系
     90	其他

     */
    private  static final  Map<String,String> destroy_unemp_reason = new HashMap<>();




    static {

        destroy_unemp_reason.put("11","被用人单位录用");
        destroy_unemp_reason.put("12","从事个体经营或创办企业，并领取工商营业执照");
        destroy_unemp_reason.put("13","已从事有稳定收入的劳动，并且月收入不低于当地最低工资标准");
        destroy_unemp_reason.put("14","公益性岗位安置");
        destroy_unemp_reason.put("21","已享受基本养老保险待遇");
        destroy_unemp_reason.put("22","达到退休年龄");
        destroy_unemp_reason.put("23","完全丧失劳动能力");
        destroy_unemp_reason.put("24","入学、服兵役、移居境外");
        destroy_unemp_reason.put("25","被判刑收监执行");
        destroy_unemp_reason.put("26","死亡");
        destroy_unemp_reason.put("31","终止就业要求或拒绝接受公共就业服务");
        destroy_unemp_reason.put("32","在规定时间内未与公共就业服务机构联系");
        destroy_unemp_reason.put("90","其他");
        emp_types.put("010","单位就业");
        emp_types.put("021","个体经营");
        emp_types.put("022","灵活就业");
        emp_types.put("029","其他自主就业");
        emp_types.put("030","公益性岗位安置");
        emp_types.put("040","特殊事项就业");
        emp_types.put("090","其它就业形式");

        given_types.put("01","新增");
        given_types.put("02","补发");
        given_types.put("03","换证");

        unemp_reason.put("10","年满16周岁，从各类学校毕业、结业、肄业的");
        unemp_reason.put("20","从企业、机关、事业单位等各类用人单位失业的");
        unemp_reason.put("32","个体工商户（含认定的网络创业）业主、私营企业和民办非企业业主停产、破产停止经营的");
        unemp_reason.put("50","承包土地被征用的农村劳动力");
        unemp_reason.put("60","军人退出现役且未纳入国家统一安置的，以及随军家属未安置就业的");
        unemp_reason.put("70","城镇刑满释放、假释、监外执行的");
        unemp_reason.put("90","其他符合失业登记的");
    }


    @Value("${employment.ws.username}")
    private String username = "username";
    @Value("${employment.ws.password}")
    private String password = "password";

    @Override
    public Response callEmploymentService(String employmentServiceCode, String jsonStr) {
        JSON json = WebServiceClientUtil.callEmploymentService(username, password, employmentServiceCode, jsonStr);
        String errorMsg = getErrorMsg(json);
        if (StringUtils.isNotBlank(errorMsg)) {
            return ResponseHelper.createResponse(-9999, errorMsg);
        }
        return ResponseHelper.createSuccessResponse(json);
    }


    private String getErrorMsg(JSON result) {
        if (result == null) {
            return "";
        }
        String errorKey = "err";
        if (result.isArray()) {
            JSONArray array = (JSONArray) result;
            if (!array.isEmpty() && array.getJSONObject(0).has(errorKey)) {
                return array.getJSONObject(0).getString(errorKey);
            }
        }
        return "";
    }
    @Override
    public   Response queryInfo(String functionName, Map params, String serviceCode) {
        Response<Object> response = new Response<Object>();
        LOG.info(functionName + "入参：" + params);
        try {
            JSON json = WebServiceClientUtil.callEmploymentService(username, password, serviceCode, JSONObject.fromObject(params).toString());
            if (json == null || json.isEmpty()) {
                LOG.info(functionName + "调用第三方异常出参：" + json);
                response.setCode(500);
                response.setDescription("内部服务错误");
            } else {
                LOG.info(functionName + "调用第三方出参：" + json);
                String code;
                String errorMsg;
                Object data = null;
                if (json.isArray()) {
                    JSONArray jsonArr = (JSONArray) json;
                    code = jsonArr.getJSONObject(0).get("code").toString();
                    errorMsg = jsonArr.getJSONObject(0).get("err").toString();
                } else {
                    JSONObject jsonObj = (JSONObject) json;
                    code = jsonObj.getJSONObject("head").get("code").toString();
                    errorMsg = jsonObj.getJSONObject("head").get("err").toString();
                    if (!"SERCH_JYCYZ".equals(serviceCode) && !"SERCH_JYDJ".equals(serviceCode) && !"SERCH_SYDJ".equals(serviceCode)) {
                        String str = StringEscapeUtils.unescapeXml(jsonObj.get("body").toString()).replaceAll(Matcher.quoteReplacement("&rt;"), ">");
                        LOG.info("去除html标签后的数据={}", com.neusoft.mid.ec.api.util.StringUtils.removeHtmlTag(str));
                        data = com.alibaba.fastjson.JSON.parse(com.neusoft.mid.ec.api.util.StringUtils.removeHtmlTag(str));
                    } else {
                        data = replaceCode(com.alibaba.fastjson.JSON.parse(jsonObj.get("body").toString()));
                    }
                }
                // 第三方接口code=1为成功，替换成本系统code=0为成功
                String tempCode = code;
                if (StringUtils.equals(tempCode, "1")) {
                    code = "0";
                } else if (StringUtils.equals(tempCode, "0")) {
                    code = "1";
                }
                response.setCode(Integer.valueOf(code));
                response.setDescription(errorMsg);
                response.setPayload(data);
            }
        } catch (Exception e) {
            LOG.info(functionName + "异常：" + params, e);
            response.setCode(500);
            response.setDescription("内部服务错误");
        }
        response.setLastUpdateTime(System.currentTimeMillis());
        LOG.info(functionName + "出参：" + response);
        return response;
    }


    /**
     * 010	单位就业
     * 021	个体经营
     * 022	灵活就业
     * 029	其他自主就业
     * 030	公益性岗位安置
     * 040	特殊事项就业
     * 090	其它就业形式
     *
     * @param json
     * @return
     */
    private Object replaceCode(Object json) {
        com.alibaba.fastjson.JSONArray jsonArray = new com.alibaba.fastjson.JSONArray();
        if(json instanceof com.alibaba.fastjson.JSONArray){
                if(!((com.alibaba.fastjson.JSONArray) json).isEmpty()){
                    jsonArray =  ((com.alibaba.fastjson.JSONArray) json).getJSONArray(0);
                }
        }
        for(int i = 0 ;i<jsonArray.size();i++){

            com.alibaba.fastjson.JSONObject jsonObject = jsonArray.getJSONObject(i);

            if(!jsonObject.isEmpty() && jsonObject.containsKey("就业类型")){
                String code =  jsonObject.getString("就业类型");
                jsonObject.put("就业类型",emp_types.get(code));
            }

            if(!jsonObject.isEmpty() && jsonObject.containsKey("发放类别")){
                String code =  jsonObject.getString("发放类别");
                jsonObject.put("发放类别",given_types.get(code));
            }

            if(!jsonObject.isEmpty() && jsonObject.containsKey("失业原因")){
                String code =  jsonObject.getString("失业原因");
                jsonObject.put("失业原因",unemp_reason.get(code));
            }
            if(!jsonObject.isEmpty() && jsonObject.containsKey("失业注销原因")){
                String code =  jsonObject.getString("失业注销原因");
                jsonObject.put("失业注销原因",destroy_unemp_reason.get(code));
            }
            if(!jsonObject.isEmpty() && jsonObject.containsKey("有效标志")){
                String code =  jsonObject.getString("有效标志");
                if("1".equals(code)){
                    jsonObject.put("有效标志","有效");
                }else{
                    jsonObject.put("有效标志","无效");
                }
            }
        }
        return json;
    }
}
