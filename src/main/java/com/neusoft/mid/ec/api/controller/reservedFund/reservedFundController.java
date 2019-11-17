package com.neusoft.mid.ec.api.controller.reservedFund;

import java.util.*;

import javax.servlet.http.HttpServletRequest;


import com.alibaba.fastjson.JSONArray;
import com.neusoft.mid.ec.api.domain.ReservedFundDict;
import com.neusoft.mid.ec.api.serviceInterface.reservedfunddict.ReservedFundDictService;
import com.neusoft.mid.ec.api.util.JedisClusterUtil;


import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.neusoft.mid.ec.api.controller.BaseController;
import com.neusoft.mid.ec.api.controller.householdadministration.HouseholdAdministrationQueryController;
import com.neusoft.mid.ec.api.domain.RequestInfo;
import com.neusoft.mid.ec.api.service.reservedfound.ReservedFoundServiceImpl;
import com.neusoft.mid.ec.api.serviceInterface.reservedFund.TraderService;
import com.neusoft.mid.ec.api.serviceInterface.reservedfound.ReservedFoundService;
import com.neusoft.mid.ec.api.util.RopUtil;

import me.puras.common.json.Response;
import me.puras.common.json.ResponseHelper;

@RestController
@RequestMapping("/reserved/fund")
public class reservedFundController extends BaseController {

    @Autowired
    ReservedFoundService reservedFoundService;
    
    
    @Autowired
    private JedisClusterUtil jedisClusterUtil;
    @Autowired
    private ReservedFundDictService reservedFundDictService;
    static TraderService trader;
    // trader地址

    public static TraderService getTrader() {
        return ReservedFoundServiceImpl.getTrader();
    }

    /**
     * 个人公积金数据查询授权
     */
    @RequestMapping("/authorization")
    public Response authorization(@RequestBody Map<String, String> map) throws Exception {
        Response<Object> response = new Response<>();
        map.put("txcode", "1PBL010");
        try {
            logger.info("个人公积金数据查询授权 入参：" + map);
            response = checkparams(response, map);
            if (0 != response.getCode()) {
                return response;
            }
            if (null == map.get("cardcode") || StringUtils.isBlank(map.get("cardcode"))) {
                logger.info("身份证号入参为空");
                return new HouseholdAdministrationQueryController().errorRsponse(response, 1, "身份证号入参为空");
            }
            if (null == map.get("authcode") || StringUtils.isBlank(map.get("authcode"))) {
                logger.info("授权证明入参为空");
                return new HouseholdAdministrationQueryController().errorRsponse(response, 1, "授权证明入参为空");
            }
            getResponse(map, response);
        } catch (Exception e) {
            logger.error("调用公积金接口异常" + e.getMessage(), e);
            response.setCode(500);
            response.setDescription("内部服务错误");
        }
        response.setLastUpdateTime(System.currentTimeMillis());
        logger.info("个人公积金数据查询授权 出参：" + response);
        return response;
    }

    /**
     * 获取个人基本信息
     */
    @RequestMapping("/userMsg")
    public Response userMsg(HttpServletRequest request) throws Exception {

        Map params = new HashMap(10);
        params.put("txcode", "1PBL004");

        Response<Object> response = new Response<>();
        try {
            RequestInfo requestInfo = getRequestInfo(request);
            // test
            //requestInfo.setIdno("411281198801134029");
            if (StringUtils.isEmpty(requestInfo.getIdno())) {
                return ResponseHelper.createResponse(500, "身份证号码不能为空");
            }
            params.put("idcard", requestInfo.getIdno());
            Map<String, String> authInfo = reservedFoundService.getAuthInfo(requestInfo.getIdno());

            if (authInfo == null || authInfo.isEmpty()) {
                logger.info("无法获取perCode,授权失败");
                return ResponseHelper.createResponse(500, "授权失败");
            }
            params.putAll(authInfo);

            logger.info("获取个人基本信息 入参：" + params);
            /* response = checkparams(response, params);
             * if (0 != response.getCode()) {
             * return response;
             * }
             * // 校验数据是否至少2个不为为空
             * if (checkMap(params, response)) {
             * return new HouseholdAdministrationQueryController().errorRsponse(response, 1, "请求参数最少两个不为空");
             * } */
            getResponse(params, response);
        } catch (Exception e) {
            logger.error("调用公积金接口异常" + e.getMessage(), e);
            response.setCode(500);
            response.setDescription("内部服务错误");
        }
        response.setLastUpdateTime(System.currentTimeMillis());
        logger.info("获取个人基本信息 出参：" + response);
        return response;
    }

    private boolean checkMap(@RequestBody Map<String, String> map, Response<Object> response) {
        List<Boolean> list = new ArrayList<>();
        if (null == map.get("percode") || StringUtils.isBlank(map.get("percode"))) {
            logger.info("个人编号为空");
            list.add(false);
        }
        if (null == map.get("idcard") || StringUtils.isBlank(map.get("idcard"))) {
            logger.info("证件号码为空");
            list.add(false);
        }
        if (null == map.get("bkcard") || StringUtils.isBlank(map.get("bkcard"))) {
            logger.info("银行卡号为空");
            list.add(false);
        }
        int check = 0;
        for (int i = 0; i < list.size(); i++) {
            if (false == list.get(i)) {
                check++;
            }
        }
        if (check >= 2) {
            return true;
        }
        return false;
    }

    /**
     * 查询个人公积金缴存账户
     */
    @RequestMapping(value = "/userAccount", method = RequestMethod.POST)
    public Response userAccount(HttpServletRequest request) throws Exception {
        Map<String, String> params = new HashMap<>(5);
        Response<Object> returnobj = new Response<>();
        try {
            Response<Object> response = new Response<>();
            RequestInfo requestInfo = getRequestInfo(request);
            // test
            if (StringUtils.isEmpty(requestInfo.getIdno())) {
                requestInfo.setIdno("41030319830103051X");
            }
            if (StringUtils.isEmpty(requestInfo.getIdno())) {
                return ResponseHelper.createResponse(500, "身份证号码不能为空");
            }
            params.put("idcard", requestInfo.getIdno());
            Map<String, String> authInfo = reservedFoundService.getAuthInfo(requestInfo.getIdno());

            if (authInfo == null || authInfo.isEmpty()) {
                logger.info("无法获取perCode,授权失败");
                return ResponseHelper.createResponse(500, "授权失败");
            }
            params.putAll(authInfo);
            params.put("txcode", "1PMS001");
            logger.info("查询个人公积金缴存账户 入参：" + params);
            getResponse(params, response);
            Response<Object> object = new Response<>();
            params.put("txcode", "1PBL004");
            getResponse(params, object);
            Map userAccount = (HashMap) response.getPayload();
            Map list = (HashMap) userAccount.get("list");
            Object user = object.getPayload();

            if(list != null && list.containsKey("accstate")){
                list.put("accstate",ReservedFoundServiceImpl.getAccountStateText((String) list.get("accstate")));
            }

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("account", userAccount);
            jsonObject.put("user", user);
            returnobj.setPayload(jsonObject);
            returnobj.setDescription("获取数据成功");
        } catch (Exception e) {
            logger.error("调用公积金接口异常" + e.getMessage(), e);
            returnobj.setCode(1);
            returnobj.setDescription("获取个人帐户基本信息失败，原因是信息异常，请咨询公积金服务大厅");
        }
        returnobj.setLastUpdateTime(System.currentTimeMillis());
        logger.info("查询个人公积金缴存账户 出参：" + returnobj);
        return returnobj;
    }

    /**
     * 查询个人缴存账户变动明细
     */
    @RequestMapping("/userDetail")
    public Response userDetail(HttpServletRequest request, @RequestBody Map<String, String> map) throws Exception {
        map.put("txcode", "1PMS002");
        Response<Object> response = new Response<>();
        try {
            RequestInfo requestInfo = getRequestInfo(request);
            // test
            if (StringUtils.isEmpty(requestInfo.getIdno())) {
                requestInfo.setIdno("41030319830103051X");
            }
            if (StringUtils.isEmpty(requestInfo.getIdno())) {
                return ResponseHelper.createResponse(500, "身份证号码不能为空");
            }
            map.put("idcard", requestInfo.getIdno());
            Map<String, String> authInfo = reservedFoundService.getAuthInfo(requestInfo.getIdno());

            if (authInfo == null || authInfo.isEmpty()) {
                logger.info("无法获取perCode,授权失败");
                return ResponseHelper.createResponse(500, "授权失败");
            }
            map.putAll(authInfo);
            logger.info("查询个人缴存账户变动明细 入参：" + map);
            response = checkparams(response, map);
            if (0 != response.getCode()) {
                return response;
            }
            getResponse(map, response);
        } catch (Exception e) {
            logger.error("调用公积金接口异常" + e.getMessage(), e);
            response.setCode(500);
            response.setDescription("内部服务错误");
        }
        response.setLastUpdateTime(System.currentTimeMillis());
        logger.info("查询个人缴存账户变动明细 出参：" + response);
        return response;
    }

    /**
     * 获取个人公积金提取业务记录
     */
    @RequestMapping("/extractAccount")
    public Response extractAccount(HttpServletRequest request, @RequestBody Map<String, String> map) throws Exception {
        map.put("txcode", "1PMS003");
        Response<Object> response = new Response<>();
        try {
            logger.info("获取个人公积金提取业务记录 入参：" + map);
            RequestInfo requestInfo = getRequestInfo(request);
            // test
            if (StringUtils.isBlank(requestInfo.getIdno())) {
                requestInfo.setIdno("41030319830103051X");
            }
            map.put("idcard", requestInfo.getIdno());
            Map<String, String> authInfo = reservedFoundService.getAuthInfo(requestInfo.getIdno());
            if (authInfo == null || authInfo.isEmpty()) {
                logger.info("无法获取perCode,授权失败");
                return ResponseHelper.createResponse(500, "授权失败");
            }
            map.putAll(authInfo);
            getResponse(map, response);
            if (1 == response.getCode()) {
                return response;
            }
            JSONObject jsonObj = JSONObject.parseObject(JSON.toJSONString(response.getPayload()));
            JSONArray list = JSONArray.parseArray(JSON.toJSONString(jsonObj.get("list")));
            List<JSONObject> resultList = new ArrayList<>();
            for (Object obj : list) {
                JSONObject jsonO = JSONObject.parseObject(obj.toString());
                jsonO.put("reason", getParamName("TQYY", jsonO.get("reason") == null ? null : jsonO.get("reason").toString()));
                jsonO.put("paytype", getParamName("TQFS", jsonO.get("paytype") == null ? null : jsonO.get("paytype").toString()));
                jsonO.put("source", getParamName("XXLY", jsonO.get("source") == null ? null : jsonO.get("source").toString()));
                resultList.add(jsonO);
            }
            jsonObj.put("list", resultList);
            response.setPayload(null);
            response.setPayload(jsonObj);
        } catch (Exception e) {
            logger.error("调用公积金接口异常" + e.getMessage(), e);
            response.setCode(500);
            response.setDescription("内部服务错误");
        }
        response.setLastUpdateTime(System.currentTimeMillis());
        logger.info("获取个人公积金提取业务记录 出参：" + response);
        return response;
    }

    /**
     * 获取个人公积金缴存记录
     */
    @RequestMapping("/deposite")
    public Response deposite(HttpServletRequest request, @RequestBody Map<String, String> map) throws Exception {
        map.put("txcode", "1PMS005");
        Response<Object> response = new Response<>();
        try {
            logger.info("获取个人公积金缴存记录 入参：" + map);
            RequestInfo requestInfo = getRequestInfo(request);
            // test
            if (StringUtils.isEmpty(requestInfo.getIdno())) {
                requestInfo.setIdno("41030319830103051X");
            }
            if (StringUtils.isEmpty(requestInfo.getIdno())) {
                return ResponseHelper.createResponse(500, "身份证号码不能为空");
            }
            map.put("idcard", requestInfo.getIdno());
            Map<String, String> authInfo = reservedFoundService.getAuthInfo(requestInfo.getIdno());
            if (authInfo == null || authInfo.isEmpty()) {
                logger.info("无法获取perCode,授权失败");
                return ResponseHelper.createResponse(500, "授权失败");
            }
            map.putAll(authInfo);
            getResponse(map, response);
        } catch (Exception e) {
            logger.error("调用公积金接口异常" + e.getMessage(), e);
            response.setCode(500);
            response.setDescription("内部服务错误");
        }
        response.setLastUpdateTime(System.currentTimeMillis());
        logger.info("获取个人公积金缴存记录 出参：" + response);
        return response;
    }

    /**
     * 获取个人贷款申请记录
     */
    @RequestMapping("/loanApplication")
    public Response loanApplication(HttpServletRequest request) throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("txcode", "1PLS002");
        Response<Object> response = new Response<>();
        try {
            logger.info("获取个人贷款申请记录 入参：" + map);
            RequestInfo requestInfo = getRequestInfo(request);
            // test
            if (StringUtils.isEmpty(requestInfo.getIdno())) {
                requestInfo.setIdno("41030319830103051X");
            }
            if (StringUtils.isEmpty(requestInfo.getIdno())) {
                return ResponseHelper.createResponse(500, "身份证号码不能为空");
            }
            map.put("cardcode", requestInfo.getIdno());
            Map<String, String> authInfo = reservedFoundService.getAuthInfo(requestInfo.getIdno());
            if (authInfo == null || authInfo.isEmpty()) {
                logger.info("无法获取perCode,授权失败");
                return ResponseHelper.createResponse(500, "授权失败");
            }
            map.putAll(authInfo);
            getResponse(map, response);
        } catch (Exception e) {
            logger.error("调用公积金接口异常" + e.getMessage(), e);
            response.setCode(500);
            response.setDescription("内部服务错误");
        }
        response.setLastUpdateTime(System.currentTimeMillis());
        logger.info("获取个人贷款申请记录 出参：" + response);
        return response;
    }

    /**
     * 获取公积金贷款合同详情        ------ 接口出现sql异常---
     */
    @RequestMapping("/loanDetails")
    public Response loanDetails(HttpServletRequest request, @RequestBody Map<String, String> map) throws Exception {
        map.put("txcode", "1PLS003");
        Response<Object> response = new Response<>();
        try {
            logger.info("获取公积金贷款合同详情 入参：" + map);
            if (null == map.get("agrcode") || StringUtils.isBlank(map.get("agrcode"))) {
                logger.info("借款合同号为空");
                return new HouseholdAdministrationQueryController().errorRsponse(response, 1, "借款合同号为空");
            }
            if (null == map.get("cardcode") || StringUtils.isBlank(map.get("cardcode"))) {
                logger.info("证件号码为空");
                return new HouseholdAdministrationQueryController().errorRsponse(response, 1, "证件号码为空");
            }
            RequestInfo requestInfo = getRequestInfo(request);
            requestInfo.setIdno(map.get("cardcode"));
            Map<String, String> authInfo = reservedFoundService.getAuthInfo(requestInfo.getIdno());
            if (authInfo == null || authInfo.isEmpty()) {
                logger.info("无法获取perCode,授权失败");
                return ResponseHelper.createResponse(500, "授权失败");
            }
            map.putAll(authInfo);
            getResponse(map, response);
        } catch (Exception e) {
            logger.error("调用公积金接口异常" + e.getMessage(), e);
            response.setCode(500);
            response.setDescription("内部服务错误");
        }
        response.setLastUpdateTime(System.currentTimeMillis());
        logger.info("获取公积金贷款合同详情 出参：" + response);
        return response;
    }

    /**
     * 获取公积金贷款账户详情
     */
    @RequestMapping("/accountDetails")
    public JSONObject accountDetails(HttpServletRequest request, @RequestBody Map<String, String> map)
            throws Exception {
        map.put("txcode", "1PLS004");
        Response<Object> response = new Response<>();
        JSONObject returnJson = new JSONObject();
        try {
            logger.info("获取公积金贷款账户详情 入参：" + map);
            if (null == map.get("agrcode") || StringUtils.isBlank(map.get("agrcode"))) {
                logger.info("借款合同号为空");
//                return new HouseholdAdministrationQueryController().errorRsponse(response, 1, "借款合同号为空");
                returnJson.put("code", "500");
                returnJson.put("description", "借款合同号为空");
                returnJson.put("lastUpdateTime", System.currentTimeMillis());
                return returnJson;
            }

            RequestInfo requestInfo = getRequestInfo(request);
            if (StringUtils.isBlank(requestInfo.getIdno())) {
                requestInfo.setIdno("41030319830103051X");
            }

            Map<String, String> authInfo = reservedFoundService.getAuthInfo(requestInfo.getIdno());
            if (authInfo == null || authInfo.isEmpty()) {
                logger.info("无法获取perCode,授权失败");
//                return ResponseHelper.createResponse(500, "授权失败");
                returnJson.put("code", "500");
                returnJson.put("description", "授权失败");
                returnJson.put("lastUpdateTime", System.currentTimeMillis());
                return returnJson;
            }
            map.putAll(authInfo);
            map.put("cardcode",requestInfo.getIdno());
            Response<Object> accountDetails = getResponseResult(map, response);
            returnJson.put("code", accountDetails.getCode());
            returnJson.put("description", accountDetails.getDescription());
            if (0 == accountDetails.getCode()) {
                JSONObject accountObj = JSONObject.parseObject(JSON.toJSONString(accountDetails.getPayload()));
                accountObj.put("loanstate", getStepcodeName(accountObj.get("loanstate") == null ? null : accountObj.get("loanstate").toString()));
                returnJson.put("accountDetails", accountObj);// 账户信息
                // 3.查询 获取公积金贷款合同详情
                Response<Object> loanDetails = loanDetails(request, map);
                if (0 == loanDetails.getCode()) {
                    JSONObject loanDetailsObj = JSONObject.parseObject(JSON.toJSONString(loanDetails.getPayload()));
                    loanDetailsObj.put("repayway", getParamName("DKHKFS", loanDetailsObj.get("repayway") == null ? null : loanDetailsObj.get("repayway").toString()));
                    loanDetailsObj.put("paynature", getParamName("HKLX", loanDetailsObj.get("paynature") == null ? null : loanDetailsObj.get("paynature").toString()));
                    loanDetailsObj.put("loantype", getParamName("DKJYLX", loanDetailsObj.get("loantype") == null ? null : loanDetailsObj.get("loantype").toString()));
                    loanDetailsObj.put("loanbank", getParamName("YHHB", loanDetailsObj.get("loanbank") == null ? null : loanDetailsObj.get("loanbank").toString()));
                    loanDetailsObj.put("cardbank", getParamName("YHHB", loanDetailsObj.get("cardbank") == null ? null : loanDetailsObj.get("cardbank").toString()));
                    loanDetailsObj.put("stepcode", getStepcodeName(loanDetailsObj.get("stepcode") == null ? null : loanDetailsObj.get("stepcode").toString()));
                    returnJson.put("loanDetails", loanDetailsObj);// 合同详情
                } else {
                    returnJson.put("loanDetails", new JSONObject());// 合同详情
                }
            } else {
                returnJson.put("accountDetails", new JSONObject());// 账户信息
                returnJson.put("loanDetails", new JSONObject());// 合同详情
            }
        } catch (Exception e) {
            logger.error("调用公积金接口异常" + e.getMessage(), e);
            response.setCode(500);
            response.setDescription("内部服务错误");
        }
        response.setLastUpdateTime(System.currentTimeMillis());
        logger.info("获取公积金贷款账户详情 出参：" + response);
        return returnJson;
    }

    /**
     * 获取个人公积金还款记录
     */
    @RequestMapping("/repaymentRecords")
    public Response repaymentRecords(@RequestBody Map<String, String> map, HttpServletRequest request)
            throws Exception {
        Response<Object> response = new Response<>();
        try {
            logger.info("获取个人公积金还款记录 入参：" + map);
            if (null == map.get("year") || StringUtils.isBlank(map.get("year"))) {
                logger.info("计划年度为空");
                return new HouseholdAdministrationQueryController().errorRsponse(response, 1, "计划年度为空");
            }
            RequestInfo requestInfo = getRequestInfo(request);
            // test
            if (StringUtils.isEmpty(requestInfo.getIdno())) {
                requestInfo.setIdno("41030319830103051X");
            }
            if (StringUtils.isEmpty(requestInfo.getIdno())) {
                return ResponseHelper.createResponse(500, "身份证号码不能为空");
            }
            Map<String, String> authInfo = reservedFoundService.getAuthInfo(requestInfo.getIdno());
            if (null == authInfo || authInfo.isEmpty()) {
                return new HouseholdAdministrationQueryController().errorRsponse(response, 1, "未获取到个人编号");
            }
            /* map.put("cardcode",requestInfo.getIdno());
             * map.put("percode",authInfo.get("percode")); */
            authInfo.put("txcode", "1PLS002");
            getResponse(authInfo, response);
            JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(response.getPayload()));
            if (null == jsonObject || jsonObject.isEmpty()) {
                logger.info("无法获取agrcode借款合同号,授权失败");
                return ResponseHelper.createResponse(1, "未获取到借款合同号");
            }
            JSONObject jsonObj = JSON.parseObject(JSON.toJSONString(jsonObject.get("list")));
            if (null == jsonObj || jsonObj.isEmpty() || StringUtils.isBlank(jsonObj.get("agrcode").toString())) {
                logger.info("无法获取agrcode借款合同号,授权失败");
                return ResponseHelper.createResponse(1, "未获取到借款合同号");
            }
            response.setPayload(null);
            authInfo.put("txcode", "1PLS006");
            authInfo.put("agrcode", jsonObj.get("agrcode").toString());
            authInfo.put("year", map.get("year"));
            getResponse(authInfo, response);
            response.setLastUpdateTime(System.currentTimeMillis());
            //厅局调整倒叙，则注释以下转换代码
            if (StringUtils.isBlank(response.getPayload()==null?null:response.getPayload().toString())) {
                logger.info("获取个人公积金还款记录 出参：" + response);
                return response;
            }
            JSONObject jsonObject1 = JSONObject.parseObject(JSON.toJSONString(response.getPayload()));
            String str = jsonObject1.get("list")==null?null:JSON.toJSONString(jsonObject1.get("list"));
            if (StringUtils.isNotBlank(str)) {
                JSONArray objects = JSONObject.parseArray(str);
                if (CollectionUtils.isNotEmpty(objects)) {
                    Collections.reverse(objects);
                }
                jsonObject1.put("list",objects);
                response.setPayload(null);
                response.setPayload(jsonObject1);
            }
        } catch (Exception e) {
            logger.error("调用公积金接口异常" + e.getMessage(), e);
            response.setCode(500);
            response.setDescription("内部服务错误");
        }
        logger.info("获取个人公积金还款记录 出参：" + response);
        return response;
    }

    /**
     * 个人公积金审批进度跟踪
     */
    @RequestMapping("/approvals")
    public Response approvals(@RequestBody Map<String, String> map) throws Exception {
        map.put("txcode", "1PLS007");
        Response<Object> response = new Response<>();
        try {
            logger.info("个人公积金审批进度跟踪 入参：" + map);
            response = checkparams(response, map);
            if (0 != response.getCode()) {
                return response;
            }
            if (null == map.get("cardcode") || StringUtils.isBlank(map.get("cardcode"))) {
                logger.info("证件号码为空");
                return new HouseholdAdministrationQueryController().errorRsponse(response, 1, "证件号码为空");
            }
            if (null == map.get("loancode") || StringUtils.isBlank(map.get("loancode"))) {
                logger.info("个贷申请号为空");
                return new HouseholdAdministrationQueryController().errorRsponse(response, 1, "个贷申请号为空");
            }
            getResponse(map, response);
        } catch (Exception e) {
            logger.error("调用公积金接口异常" + e.getMessage(), e);
            response.setCode(500);
            response.setDescription("内部服务错误");
        }
        response.setLastUpdateTime(System.currentTimeMillis());
        logger.info("个人公积金审批进度跟踪 出参：" + response);
        return response;
    }

    private Response checkparams(Response response, Map<String, String> map) {
        if (null == map || map.size() == 0) {
            logger.info("请求参数为空");
            return new HouseholdAdministrationQueryController().errorRsponse(response, 1, "请求参数为空");
        }
        if (null == map.get("certcode") || StringUtils.isBlank(map.get("certcode"))) {
            logger.info("服务安全验证码为空");
            return new HouseholdAdministrationQueryController().errorRsponse(response, 1, "服务安全验证码为空");
        }
        if (null == map.get("forgcode") || StringUtils.isBlank(map.get("forgcode"))) {
            logger.info("交易发起方代码入参为空");
            return new HouseholdAdministrationQueryController().errorRsponse(response, 1, "交易发起方代码入参为空");
        }
        if (null == map.get("txchannel") || StringUtils.isBlank(map.get("txchannel"))) {
            logger.info("渠道号为空");
            return new HouseholdAdministrationQueryController().errorRsponse(response, 1, "渠道号为空");
        }
        return response;
    }

    private void getResponse(@RequestBody Map<String, String> map, Response<Object> response) throws Exception {
        String reqData = RopUtil.getRequestData(map);
        String xml = getTrader().doTrader(reqData);
        Map result = RopUtil.xmlTOMap(xml);
        logger.info("调用接口返回数据" + result);
        response.setDescription(String.valueOf(result.get("resmsg")));
//        if (!"1".equals(result.get("rescode"))) {
//            logger.info("调用公积金接口失败：" + response.getDescription());
//            response.setCode(1);
//            /*if (!"个人账户信息不存在!！".equals(String.valueOf(result.get("resmsg")))
//                || !"非封存状态不可提取！".equals(String.valueOf(result.get("resmsg")))
//                || !"未传入交易编号".equals(String.valueOf(result.get("resmsg")))) {
//                response.setDescription("参数格式不正确");
//            }*/
//            if (15 <= String.valueOf(result.get("resmsg")).length()) {
//                response.setDescription("参数格式不正确");
//            }
//        } else {
            response.setCode(0);
//            result.remove("rescode");
//            result.remove("resmsg");
            response.setPayload(result);
//        }
    }

    private Response<Object> getResponseResult(@RequestBody Map<String, String> map, Response<Object> response)
            throws Exception {
        String reqData = RopUtil.getRequestData(map);
        String xml = getTrader().doTrader(reqData);
        Map result = RopUtil.xmlTOMap(xml);
        logger.info("调用接口返回数据" + result);
        response.setDescription(String.valueOf(result.get("resmsg")));
        if (!"1".equals(result.get("rescode"))) {
            logger.info("调用公积金接口失败：" + response.getDescription());
            response.setCode(1);
            if (!"个人不存在贷款账户信息!".equals(response.getDescription())) {
                response.setDescription("参数格式不正确");
            }
        } else {
            response.setCode(0);
            result.remove("rescode");
            result.remove("resmsg");
            response.setPayload(result);
        }
        return response;
    }

    // (一)个人基础信息修改

    /**
     * 个人基本信息获取
     */
    @RequestMapping("/userMessage")
    public Response userMessage( HttpServletRequest request) throws Exception {
    	Map<String, String> map=new HashMap<>();
        map.put("txcode", "PMC001");
        Response<Object> response = new Response<>();
        try {
            logger.info("个人基本信息获取 入参：" + map);
            //response = checkparams(response, map);
            
            map=reservedFoundService.getCommonInfo(map);
            
            if (0 != response.getCode()) {
                return response;
            }
//            if (null == map.get("percode") || StringUtils.isBlank(map.get("percode"))) {
//                logger.info("个人编号/证件号码/个人存款账户号码为空");
//                return new HouseholdAdministrationQueryController().errorRsponse(response, 1, "个人编号/证件号码/个人存款账户号码为空");
//            }
            
          RequestInfo requestInfo = getRequestInfo(request);
          if (StringUtils.isEmpty(requestInfo.getIdno())) {
              return ResponseHelper.createResponse(500, "身份证号码不能为空");
          }
          Map<String, String> authInfo = reservedFoundService.getAuthInfo(requestInfo.getIdno());
          if (null == authInfo && authInfo.isEmpty()) {
              return new HouseholdAdministrationQueryController().errorRsponse(response, 1, "未获取到个人编号");
          }
          
          map.put("percode", authInfo.get("percode"));
          
            getResponse(map, response);
        } catch (Exception e) {
            logger.error("调用公积金接口异常" + e.getMessage(), e);
            response.setCode(500);
            response.setDescription("内部服务错误");
        }
        response.setLastUpdateTime(System.currentTimeMillis());
        logger.info("个人基本信息获取 出参：" + response);
        return response;
    }

    /**
     * 2.个人基本信息变更
     */
    @RequestMapping("/changeUser")
    public Response changeUser(@RequestBody Map<String, String> map,HttpServletRequest request) throws Exception {
        map.put("txcode", "PMB0001");
        Response<Object> response = new Response<>();
        try {
            logger.info("个人基本信息变更 入参：" + map);
           // response = checkparams(response, map);
            
            map=reservedFoundService.getCommonInfo(map);
            
            if (0 != response.getCode()) {
                return response;
            }
            
            RequestInfo requestInfo = getRequestInfo(request);
            if (StringUtils.isEmpty(requestInfo.getIdno())) {
                return ResponseHelper.createResponse(500, "身份证号码不能为空");
            }
            Map<String, String> authInfo = reservedFoundService.getAuthInfo(requestInfo.getIdno());
            if (null == authInfo && authInfo.isEmpty()) {
                return new HouseholdAdministrationQueryController().errorRsponse(response, 1, "未获取到个人编号");
            }
            
            map.put("percode", authInfo.get("percode"));
            
            getResponse(map, response);
        } catch (Exception e) {
            logger.error("调用公积金接口异常" + e.getMessage(), e);
            response.setCode(500);
            response.setDescription("内部服务错误");
        }
        response.setLastUpdateTime(System.currentTimeMillis());
        logger.info("个人基本信息变更 出参：" + response);
        return response;
    }
//(二)手机号码变更

    /**
     * 1．获取动态验证码
     */
    @RequestMapping("/authCode")
    public Response authCode(@RequestBody Map<String, String> map) throws Exception {
        map.put("txcode", "NLC004");
        Response<Object> response = new Response<>();
        try {
            logger.info("获取动态验证码 入参：" + map);
           // response = checkparams(response, map);
            
            map=reservedFoundService.getCommonInfo(map);
            
            if (0 != response.getCode()) {
                return response;
            }
            getResponse(map, response);
        } catch (Exception e) {
            logger.error("调用公积金接口异常" + e.getMessage(), e);
            response.setCode(500);
            response.setDescription("内部服务错误");
        }
        response.setLastUpdateTime(System.currentTimeMillis());
        logger.info("获取动态验证码 出参：" + response);
        return response;
    }

    /**
     * 校验动态验证码
     */
    @RequestMapping("/checkAuth")
    public Response checkAuth(@RequestBody Map<String, String> map) throws Exception {
        map.put("txcode", "NLC005");
        Response<Object> response = new Response<>();
        try {
            logger.info("校验动态验证码 入参：" + map);
           // response = checkparams(response, map);
            
            map=reservedFoundService.getCommonInfo(map);
            
            if (0 != response.getCode()) {
                return response;
            }
            getResponse(map, response);
        } catch (Exception e) {
            logger.error("调用公积金接口异常" + e.getMessage(), e);
            response.setCode(500);
            response.setDescription("内部服务错误");
        }
        response.setLastUpdateTime(System.currentTimeMillis());
        logger.info("校验动态验证码 出参：" + response);
        return response;
    }

    // (三)公积金提取

    /**
     * 1.公积金在线提取验证
     */
    @RequestMapping("/extractFund")
    public Response extractFund(@RequestBody Map<String, String> map, HttpServletRequest request) throws Exception {
        Response<Object> response = new Response<>();
        try {
            logger.info("公积金在线提取验证 入参：" + map);
           
            map.put("txcode", "ZPC001");
            map=reservedFoundService.getCommonInfo(map);
            
            if (0 != response.getCode()) {
                return response;
            }
            
//            RequestInfo requestInfo = getRequestInfo(request);
//            if (StringUtils.isEmpty(requestInfo.getIdno())) {
//                return ResponseHelper.createResponse(500, "身份证号码不能为空");
//            }
//            Map<String, String> authInfo = reservedFoundService.getAuthInfo(requestInfo.getIdno());
//            if (null == authInfo && authInfo.isEmpty()) {
//                return new HouseholdAdministrationQueryController().errorRsponse(response, 1, "未获取到个人编号");
//            }
//            authInfo.put("fetchtype", map.get("fetchtype"));
//            authInfo.put("txcode", "ZPC001");
//            authInfo.put("pertype", map.get("pertype"));
                    
            RequestInfo requestInfo = getRequestInfo(request);
            if (StringUtils.isEmpty(requestInfo.getIdno())) {
                return ResponseHelper.createResponse(500, "身份证号码不能为空");
            }
            Map<String, String> authInfo = reservedFoundService.getAuthInfo(requestInfo.getIdno());
            if (null == authInfo && authInfo.isEmpty()) {
                return new HouseholdAdministrationQueryController().errorRsponse(response, 1, "未获取到个人编号");
            }
            
            map.put("percode", authInfo.get("percode"));
            
            logger.info("调用公积金在线提取验证接口入参：" + JSON.toJSONString(map));
            getResponse(map, response);
        } catch (Exception e) {
            logger.error("调用公积金接口异常" + e.getMessage(), e);
            response.setCode(500);
            response.setDescription("内部服务错误");
        }
        response.setLastUpdateTime(System.currentTimeMillis());
        logger.info("公积金在线提取验证 出参：" + response);
        return response;
    }

    /**
     * 2.办理提取
     */
    @RequestMapping("/transactionExtract")
    public Response transactionExtract(@RequestBody Map<String, String> map, HttpServletRequest request) throws Exception {
        map.put("txcode", "EMB0017");
        Response<Object> response = new Response<>();
        try {
            logger.info("办理提取入参：" + map);
            //response = checkparams(response, map);
            
          RequestInfo requestInfo = getRequestInfo(request);
          if (StringUtils.isEmpty(requestInfo.getIdno())) {
              return ResponseHelper.createResponse(500, "身份证号码不能为空");
          }
          
          map.put("idno", requestInfo.getIdno());
            
            map=reservedFoundService.getCommonInfo(map);
            
            if (0 != response.getCode()) {
                return response;
            }
           
            
            Map<String, String> authInfo = reservedFoundService.getAuthInfo(requestInfo.getIdno());
            if (null == authInfo && authInfo.isEmpty()) {
                return new HouseholdAdministrationQueryController().errorRsponse(response, 1, "未获取到个人编号");
            }
            
            map.put("percode", authInfo.get("percode"));
            
            reservedFoundService.insertReservedFundContent(map);
            
            getResponse(map, response);
        } catch (Exception e) {
            logger.error("调用公积金接口异常" + e.getMessage(), e);
            response.setCode(500);
            response.setDescription("内部服务错误");
        }
        response.setLastUpdateTime(System.currentTimeMillis());
        logger.info("办理提取 出参：" + response);
        return response;
    }

    // (四)提前还款

    /**
     * 1.贷款基本信息查询
     */
    @RequestMapping("/loanMsg")
    public Response loanMsg(@RequestBody Map<String, String> map) throws Exception {
        map.put("txcode", "PLB0009");
        Response<Object> response = new Response<>();
        try {
            logger.info("贷款基本信息查询 入参：" + map);
            response = checkparams(response, map);
            if (0 != response.getCode()) {
                return response;
            }
            if (null == map.get("percode") || StringUtils.isBlank(map.get("percode"))) {
                logger.info("个人编号为空");
                return new HouseholdAdministrationQueryController().errorRsponse(response, 1, "个人编号为空");
            }
            getResponse(map, response);
        } catch (Exception e) {
            logger.error("调用公积金接口异常" + e.getMessage(), e);
            response.setCode(500);
            response.setDescription("内部服务错误");
        }
        response.setLastUpdateTime(System.currentTimeMillis());
        logger.info("贷款基本信息查询 出参：" + response);
        return response;
    }

    /**
     * 2.借款人公积金账户信息查询
     */
    @RequestMapping("/provideFundMsg")
    public Response provideFundMsg(@RequestBody Map<String, String> map) throws Exception {
        map.put("txcode", "PLB0022");
        Response<Object> response = new Response<>();
        try {
            logger.info("借款人公积金账户信息查询 入参：" + map);
            response = checkparams(response, map);
            if (0 != response.getCode()) {
                return response;
            }
            if (null == map.get("loancontractno") || StringUtils.isBlank(map.get("loancontractno"))) {
                logger.info("借款合同号为空");
                return new HouseholdAdministrationQueryController().errorRsponse(response, 1, "借款合同号为空");
            }
            getResponse(map, response);
        } catch (Exception e) {
            logger.error("调用公积金接口异常" + e.getMessage(), e);
            response.setCode(500);
            response.setDescription("内部服务错误");
        }
        response.setLastUpdateTime(System.currentTimeMillis());
        logger.info("借款人公积金账户信息查询 出参：" + response);
        return response;
    }

    /**
     * 3.提前还款利息计算
     */
    @RequestMapping("/RepaymentInterest")
    public Response RepaymentInterest(@RequestBody Map<String, String> map) throws Exception {
        map.put("txcode", "PLB0017");
        Response<Object> response = new Response<>();
        try {
            logger.info("提前还款利息计算 入参：" + map);
            response = checkparams(response, map);
            if (0 != response.getCode()) {
                return response;
            }
            if (null == map.get("loancardcode") || StringUtils.isBlank(map.get("loancardcode"))) {
                logger.info("贷款账号为空");
                return new HouseholdAdministrationQueryController().errorRsponse(response, 1, "贷款账号为空");
            }
            if (null == map.get("expdate") || StringUtils.isBlank(map.get("expdate"))) {
                logger.info("预计还款时间为空");
                return new HouseholdAdministrationQueryController().errorRsponse(response, 1, "预计还款时间为空");
            }
            if (null == map.get("paymnh") || StringUtils.isBlank(map.get("paymnh"))) {
                logger.info("还款金额为空");
                return new HouseholdAdministrationQueryController().errorRsponse(response, 1, "还款金额为空");
            }
            getResponse(map, response);
        } catch (Exception e) {
            logger.error("调用公积金接口异常" + e.getMessage(), e);
            response.setCode(500);
            response.setDescription("内部服务错误");
        }
        response.setLastUpdateTime(System.currentTimeMillis());
        logger.info("提前还款利息计算 出参：" + response);
        return response;
    }

    /**
     * 4.个人公积金提前还款，变更方式
     */
    @RequestMapping("/fundprepayment")
    public Response fundprepayment(@RequestBody Map<String, String> map) throws Exception {
        map.put("txcode", "PLB0018");
        Response<Object> response = new Response<>();
        try {
            logger.info("个人公积金提前还款，变更方式 入参：" + map);
            response = checkparams(response, map);
            if (0 != response.getCode()) {
                return response;
            }
            if (null == map.get("loancardcode") || StringUtils.isBlank(map.get("loancardcode"))) {
                logger.info("贷款账号为空");
                return new HouseholdAdministrationQueryController().errorRsponse(response, 1, "贷款账号为空");
            }
            if (null == map.get("payway") || StringUtils.isBlank(map.get("payway"))) {
                logger.info("变更方式为空");
                return new HouseholdAdministrationQueryController().errorRsponse(response, 1, "变更方式为空");
            }
            if (null == map.get("paymnh") || StringUtils.isBlank(map.get("paymnh"))) {
                logger.info("还款金额为空");
                return new HouseholdAdministrationQueryController().errorRsponse(response, 1, "还款金额为空");
            }
            getResponse(map, response);
        } catch (Exception e) {
            logger.error("调用公积金接口异常" + e.getMessage(), e);
            response.setCode(500);
            response.setDescription("内部服务错误");
        }
        response.setLastUpdateTime(System.currentTimeMillis());
        logger.info("个人公积金提前还款，变更方式 出参：" + response);
        return response;
    }

    /**
     * 4.个人公积金提前还款
     */
    @RequestMapping("/prepayment")
    public Response prepayment(@RequestBody Map<String, String> map) throws Exception {
        map.put("txcode", "PLB0018");
        Response<Object> response = new Response<>();
        try {
            logger.info("个人公积金提前还款入参：" + map);
            response = checkparams(response, map);
            if (0 != response.getCode()) {
                return response;
            }
            if (null == map.get("loancardcode") || null == map.get("bustype") || null == map.get("paymnh")
                    || null == map.get("payway") || null == map.get("paymentyway") || null == map.get("expdate")
                    || null == map.get("owemny") || null == map.get("oweratemny") || null == map.get("owepunishmny")
                    || null == map.get("whethermanual")) {
                return new HouseholdAdministrationQueryController().errorRsponse(response, 1, "请求数据为空");
            }
            getResponse(map, response);
        } catch (Exception e) {
            logger.error("调用公积金接口异常" + e.getMessage(), e);
            response.setCode(500);
            response.setDescription("内部服务错误");
        }
        response.setLastUpdateTime(System.currentTimeMillis());
        logger.info("个人公积金提前还款 出参：" + response);
        return response;
    }

    // （五）公积金公共接口

    /**
     * 查询受理机构等相关信息
     */
    @RequestMapping("/acceptingInstitution")
    public Response acceptingInstitution(@RequestBody Map<String, String> map, HttpServletRequest request) throws Exception {
        map.put("txcode", "ZPC002");
        Response<Object> response = new Response<>();
        try {
            logger.info("查询受理机构等相关信息 入参：" + map);
           // response = checkparams(response, map);
            
            map=reservedFoundService.getCommonInfo(map);
            
            if (0 != response.getCode()) {
                return response;
            }
//            if (null == map.get("percode") || StringUtils.isBlank(map.get("percode"))) {
//                logger.info("个人编号/证件号码/个人存款账户号码为空");
//                return new HouseholdAdministrationQueryController().errorRsponse(response, 1, "个人编号/证件号码/个人存款账户号码为空");
//            }
            
            RequestInfo requestInfo = getRequestInfo(request);
            if (StringUtils.isEmpty(requestInfo.getIdno())) {
                return ResponseHelper.createResponse(500, "身份证号码不能为空");
            }
            Map<String, String> authInfo = reservedFoundService.getAuthInfo(requestInfo.getIdno());
            if (null == authInfo && authInfo.isEmpty()) {
                return new HouseholdAdministrationQueryController().errorRsponse(response, 1, "未获取到个人编号");
            }
            
            map.put("percode", authInfo.get("percode"));
            
            getResponse(map, response);
        } catch (Exception e) {
            logger.error("调用公积金接口异常" + e.getMessage(), e);
            response.setCode(500);
            response.setDescription("内部服务错误");
        }
        response.setLastUpdateTime(System.currentTimeMillis());
        logger.info("查询受理机构等相关信息 出参：" + response);
        return response;
    }

    // 查询编码名称和编码值
    @RequestMapping("/coding")
    public Response coding(@RequestBody Map<String, String> map) throws Exception {
        map.put("txcode", "ZPC003");
        Response<Object> response = new Response<>();
        try {
            logger.info("查询编码名称和编码值 入参：" + map);
           // response = checkparams(response, map);
            
            map=reservedFoundService.getCommonInfo(map);
            
            if (0 != response.getCode()) {
                return response;
            }
            if (null == map.get("codetype") || StringUtils.isBlank(map.get("codetype"))) {
                logger.info("编码类型为空");
                return new HouseholdAdministrationQueryController().errorRsponse(response, 1, "编码类型为空");
            }
            getResponse(map, response);
        } catch (Exception e) {
            logger.error("调用公积金接口异常" + e.getMessage(), e);
            response.setCode(500);
            response.setDescription("内部服务错误");
        }
        response.setLastUpdateTime(System.currentTimeMillis());
        logger.info("查询编码名称和编码值 出参：" + response);
        return response;
    }

    // 查询个人银行账户信息
    @RequestMapping("/bankMsg")
    public Response bankMsg(@RequestBody Map<String, String> map, HttpServletRequest request) throws Exception {
        map.put("txcode", "1PBL005");
        Response<Object> response = new Response<>();
        try {
            logger.info("查询个人银行账户信息入参：" + map);
            //response = checkparams(response, map);
            
            map=reservedFoundService.getCommonInfo(map);
            
            if (0 != response.getCode()) {
                return response;
            }
            
            if (null == map.get("idcard") || StringUtils.isBlank(map.get("idcard"))) {
                logger.info("证件号码为空");
                return new HouseholdAdministrationQueryController().errorRsponse(response, 1, "证件号码为空");
            }
            
            RequestInfo requestInfo = getRequestInfo(request);
            if (StringUtils.isEmpty(requestInfo.getIdno())) {
                return ResponseHelper.createResponse(500, "身份证号码不能为空");
            }
            Map<String, String> authInfo = reservedFoundService.getAuthInfo(requestInfo.getIdno());
            if (null == authInfo && authInfo.isEmpty()) {
                return new HouseholdAdministrationQueryController().errorRsponse(response, 1, "未获取到个人编号");
            }
            
            map.put("percode", authInfo.get("percode"));
            getResponse(map, response);
        } catch (Exception e) {
            logger.error("调用公积金接口异常" + e.getMessage(), e);
            response.setCode(500);
            response.setDescription("内部服务错误");
        }
        response.setLastUpdateTime(System.currentTimeMillis());
        logger.info("查询个人银行账户信息 出参：" + response);
        return response;
    }

    // 查询个人公积金缴存账户信息
    @RequestMapping("/depositeMsg")
    public Response depositeMsg(@RequestBody Map<String, String> map, HttpServletRequest request) throws Exception {
        map.put("txcode", "PMC002");
        Response<Object> response = new Response<>();
        try {
            logger.info("查询个人公积金缴存账户信息 入参：" + map);
           // response = checkparams(response, map);
            
            map=reservedFoundService.getCommonInfo(map);
            
            if (0 != response.getCode()) {
                return response;
            }
            
            if (null == map.get("idcard") || StringUtils.isBlank(map.get("idcard"))) {
                logger.info("证件号码为空");
                return new HouseholdAdministrationQueryController().errorRsponse(response, 1, "证件号码为空");
            }
            
            RequestInfo requestInfo = getRequestInfo(request);
            if (StringUtils.isEmpty(requestInfo.getIdno())) {
                return ResponseHelper.createResponse(500, "身份证号码不能为空");
            }
            Map<String, String> authInfo = reservedFoundService.getAuthInfo(requestInfo.getIdno());
            if (null == authInfo && authInfo.isEmpty()) {
                return new HouseholdAdministrationQueryController().errorRsponse(response, 1, "未获取到个人编号");
            }
            
            map.put("percode", authInfo.get("percode"));
            
            getResponse(map, response);
        } catch (Exception e) {
            logger.error("调用公积金接口异常" + e.getMessage(), e);
            response.setCode(500);
            response.setDescription("内部服务错误");
        }
        response.setLastUpdateTime(System.currentTimeMillis());
        logger.info("查询个人公积金缴存账户信息 出参：" + response);
        return response;
    }

    public String getParamName(String dictCode, String dictValue) {
        if (StringUtils.isBlank(dictValue)) {
            return null;
        }
        String code = jedisClusterUtil.get(dictCode, "dictValue" + dictValue);
        if (StringUtils.isBlank(code)) {
            ReservedFundDict resultObj = reservedFundDictService.getDictDesc(dictCode, dictValue);
            if (resultObj != null) {
                code = resultObj.getDictdesc();
                jedisClusterUtil.setWithExpireTime(dictCode, "dictValue" + dictValue, resultObj.getDictdesc(), 60 * 60 * 12);
            }
        }
        return StringUtils.isBlank(code) ? dictValue : code;
    }

    public String getStepcodeName(String code) {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("01", "已登记待受理");
        jsonObj.put("02", "审批中");
        jsonObj.put("03", "待合同签订");
        jsonObj.put("04", "已签订合同");
        jsonObj.put("05", "个贷已发放");
        jsonObj.put("06", "个贷回收中");
        jsonObj.put("09", "已结清");
        jsonObj.put("1", "正常");
        jsonObj.put("2", "逾期");
        jsonObj.put("4", "结清");
        jsonObj.put("0", "注销");
        return jsonObj.get(code) == null ? code : jsonObj.get(code).toString();
    }
}
