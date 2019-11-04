package com.neusoft.mid.ec.api.controller.householdadministration;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.neusoft.mid.ec.api.controller.BaseController;
import com.neusoft.mid.ec.api.domain.RequestInfo;
import com.neusoft.mid.ec.api.serviceInterface.householdadministration.HouseholdAdministrationApplyService;
import com.neusoft.mid.ec.api.util.JedisClusterUtil;
import com.neusoft.mid.ec.api.util.http.HttpRequestUtil;
import me.puras.common.json.Response;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 户政服务办理、申报
@RestController
@RequestMapping("/hz/apply")
public class HouseholdAdministrationApplyController extends BaseController {

    @Autowired
    private Environment environment;
    @Autowired
    private HouseholdAdministrationApplyService service;
    @Autowired
    private JedisClusterUtil util;

    /**
     * 户政申报_变更户主或与户主关系
     *
     * @param params
     * @param request
     * @return
     */
    @RequestMapping(value = "/changeHouseHolder", method = RequestMethod.POST)
    public Response changeHouseHolder(@RequestBody Map params, HttpServletRequest request) {
        logger.info("户政申报_变更户主或与户主关系[changeHouseHolder]入参：" + params);
        Response<Object> object = new Response<>();
        try {
            initParams(params, "HZ_CZRK_HJGL_HLWYY_JTGXBG", request, object);
            logger.info("户政申报_变更户主或与户主关系[changeHouseHolder]出参：" + JSONObject.toJSONString(object));
        } catch (Exception e) {
            logger.error("[changeHouseHolder]错误日志" + e.getMessage(), e);
            object.setCode(500);
            object.setDescription("内部服务错误");
            object.setLastUpdateTime(System.currentTimeMillis());
        }
        return object;
    }

    //封装参数，调用接口
    private void initParams(Map params, String tableName, HttpServletRequest request, Response object) {
        RequestInfo requestInfo = getRequestInfo(request);
        //redis缓存获取
        params.put("userguid", util.get("gonganUserId", requestInfo.getIdno()));
        //test user
        //params.put("userGuid", "2684112d-e0d1-4a6e-a6a0-3c466c887ba0");
        params.put("mainTableName", tableName);
        params.put("applyType", "1");
        params.put("casePoliceCategory", "6");
        params.put("formType", "HZ");
        params.put("source", "30");
        params.put("sourceDetailed", "zfb");
        JSONObject maintable = JSONObject.parseObject(JSON.toJSONString(params.get("maintable")));
        maintable.put("BS", "D");
        //实名认证身份证，姓名
        maintable.put("SQR_GMSFHM", requestInfo.getIdno());
        maintable.put("SQR_XM", requestInfo.getName());
        params.put("maintable", maintable);
        httpHzResopnse(JSON.toJSONString(params), request, object);
    }

    /**
     * 户政申报_变更民族成分
     */
    @RequestMapping(value = "/changeNation", method = RequestMethod.POST)
    public Response changeNation(@RequestBody Map params, HttpServletRequest request) {
        logger.info("户政申报_变更民族成分[changeNation]入参：" + params);
        Response<Object> object = new Response<>();
        try {
            initParams(params, "HZ_CZRK_HJGL_HLWYY_ZYXBG", request, object);
            logger.info("户政申报_变更民族成分[changeNation]出参：" + JSONObject.toJSONString(object));
        } catch (Exception e) {
            logger.error("[changeNation]错误日志" + e.getMessage(), e);
            object.setCode(500);
            object.setDescription("内部服务错误");
            object.setLastUpdateTime(System.currentTimeMillis());
        }
        return object;
    }

    /**
     * 户政申报_变更文化程度、婚姻状况
     */

    @RequestMapping(value = "/changeCulture", method = RequestMethod.POST)
    public Response changeCulture(@RequestBody Map params, HttpServletRequest request) {
        logger.info("户政申报_变更文化程度、婚姻状况[changeCulture]入参：" + params);
        Response<Object> object = new Response<>();
        try {
            JSONObject maintable = JSONObject.parseObject(JSON.toJSONString(params.get("maintable")));
            //户主身份证，姓名，与申请人关系
            //testuser 测试用户正式环境从handler中获取
        /*    maintable.put("GMSFHM",  request.getHeader("idno"));
            maintable.put("XM", request.getHeader("name"));*/
            maintable.put("GMSFHM", "412727198901170879");
            maintable.put("XM", "陈玉龙");
            maintable.put("YSQRGX_DM", "01");
            params.put("maintable", maintable);
            initParams(params, "HZ_CZRK_HJGL_HLWYY_FXBG", request, object);
            logger.info("户政申报_变更文化程度、婚姻状况[changeCulture]出参：" + JSONObject.toJSONString(object));
        } catch (Exception e) {
            logger.error("[changeCulture]错误日志" + e.getMessage(), e);
            object.setCode(500);
            object.setDescription("内部服务错误");
            object.setLastUpdateTime(System.currentTimeMillis());
        }
        return object;
    }

    /**
     * 户政申报_变更性别
     */
    @RequestMapping(value = "/changeSex", method = RequestMethod.POST)
    public Response changeSex(@RequestBody Map params, HttpServletRequest request) {
        logger.info("户政申报_变更性别[changeSex]入参：" + params);
        Response<Object> object = new Response<>();
        try {
            initParams(params, "HZ_CZRK_HJGL_HLWYY_ZYXBG", request, object);
            logger.info("户政申报_变更性别[changeSex]出参：" + JSONObject.toJSONString(object));
        } catch (Exception e) {
            logger.error("[changeSex]错误日志" + e.getMessage(), e);
            object.setCode(500);
            object.setDescription("内部服务错误");
            object.setLastUpdateTime(System.currentTimeMillis());
        }
        return object;
    }

    /**
     * 户政申报_变更姓名
     */
    @RequestMapping(value = "/changeName", method = RequestMethod.POST)
    public Response changeName(@RequestBody Map params, HttpServletRequest request) {
        logger.info("户政申报_变更姓名[changeName]入参：" + params);
        Response<Object> object = new Response<>();
        try {
            initParams(params, "HZ_CZRK_HJGL_HLWYY_ZYXBG", request, object);
            logger.info("户政申报_变更姓名[changeName]出参：" + JSONObject.toJSONString(object));
        } catch (Exception e) {
            logger.error("[changeName]错误日志" + e.getMessage(), e);
            object.setCode(500);
            object.setDescription("内部服务错误");
            object.setLastUpdateTime(System.currentTimeMillis());
        }
        return object;
    }

    /**
     * 户政申报_出国（境）定居注销户口
     */
    @RequestMapping(value = "/logoutHouseholdRegister", method = RequestMethod.POST)
    public Response logoutHouseholdRegister(@RequestBody Map params, HttpServletRequest request) {
        logger.info("户政申报_出国（境）定居注销户口[logoutHouseholdRegister]入参：" + params);
        Response<Object> object = new Response<>();
        try {
            //
            params.put("subTableName", "HZ_CZRK_HJGL_HLWYY_QR_SQRY");
            initParams(params, "HZ_CZRK_HJGL_HLWYY_ZX", request, object);
            logger.info("户政申报_出国（境）定居注销户口[logoutHouseholdRegister]出参：" + JSONObject.toJSONString(object));
        } catch (Exception e) {
            logger.error("[logoutHouseholdRegister]错误日志" + e.getMessage(), e);
            object.setCode(500);
            object.setDescription("内部服务错误");
            object.setLastUpdateTime(System.currentTimeMillis());
        }
        return object;
    }

    /**
     * 户政申报_出生登记（持省内新版出生证）
     */
    @RequestMapping(value = "/birthRregistration", method = RequestMethod.POST)
    public Response birthRregistration(@RequestBody Map params, HttpServletRequest request) {
        logger.info("户政申报_出生登记（持省内新版出生证）[birthRregistration]入参：" + params);
        Response<Object> object = new Response<>();
        try {
            initParams(params, "HZ_CZRK_HJGL_HLWYY_CSDJ", request, object);
            logger.info("户政申报_出生登记（持省内新版出生证）[birthRregistration]出参：" + JSONObject.toJSONString(object));
        } catch (Exception e) {
            logger.error("[birthRregistration]错误日志" + e.getMessage(), e);
            object.setCode(500);
            object.setDescription("内部服务错误");
            object.setLastUpdateTime(System.currentTimeMillis());
        }
        return object;
    }

    /**
     * 户政申报_大中专院校毕业学生迁入
     */
    @RequestMapping(value = "/graduatesMoveIn", method = RequestMethod.POST)
    public Response graduatesMoveIn(@RequestBody Map params, HttpServletRequest request) {
        logger.info("户政申报_大中专院校毕业学生迁入[graduatesMoveIn]入参：" + params);
        Response<Object> object = new Response<>();
        try {
            initParams(params, "HZ_CZRK_HJGL_HLWYY_QR", request, object);
            logger.info("户政申报_大中专院校毕业学生迁入[graduatesMoveIn]出参：" + JSONObject.toJSONString(object));
        } catch (Exception e) {
            logger.error("[graduatesMoveIn]错误日志" + e.getMessage(), e);
            object.setCode(500);
            object.setDescription("内部服务错误");
            object.setLastUpdateTime(System.currentTimeMillis());
        }
        return object;
    }


    /**
     * 户政申报_大中专院校录取学生迁出
     */
    @RequestMapping(value = "/admissionStudentsMoveOut", method = RequestMethod.POST)
    public Response admissionStudentsMoveOut(@RequestBody Map params, HttpServletRequest request) {
        logger.info("户政申报_大中专院校录取学生迁出[admissionStudentsMoveOut]入参：" + params);
        Response<Object> object = new Response<>();
        try {
            //获取headers请求头数据
            initParams(params, "HZ_CZRK_HJGL_HLWYY_QC", request, object);
            logger.info("户政申报_大中专院校录取学生迁出[admissionStudentsMoveOut]出参：" + JSONObject.toJSONString(object));
        } catch (Exception e) {
            logger.error("[admissionStudentsMoveOut]错误日志" + e.getMessage(), e);
            object.setCode(500);
            object.setDescription("内部服务错误");
            object.setLastUpdateTime(System.currentTimeMillis());
        }
        return object;
    }

    /**
     * 户政申报_大中专院校录取学生迁入
     */
    @RequestMapping(value = "/admissionStudentsMoveIn", method = RequestMethod.POST)
    public Response admissionStudentsMoveIn(@RequestBody Map params, HttpServletRequest request) {
        logger.info("户政申报_大中专院校录取学生迁入[admissionStudentsMoveIn]入参：" + params);
        Response<Object> object = new Response<>();
        try {
            initParams(params, "HZ_CZRK_HJGL_HLWYY_QR", request, object);
            logger.info("户政申报_大中专院校录取学生迁入[admissionStudentsMoveIn]出参：" + JSONObject.toJSONString(object));
        } catch (Exception e) {
            logger.error("[admissionStudentsMoveIn]错误日志" + e.getMessage(), e);
            object.setCode(500);
            object.setDescription("内部服务错误");
            object.setLastUpdateTime(System.currentTimeMillis());
        }
        return object;
    }

    /**
     * 子女投靠父母
     */
    @RequestMapping(value = "/children", method = RequestMethod.POST)
    public Response children(@RequestBody Map params, HttpServletRequest request) {
        Response<Object> object = new Response<>();
        try {
            logger.info("户政申报_子女投靠父母[children]入参：" + params);
            params.put("subTableName", "HZ_CZRK_HJGL_HLWYY_QR_SQRY");
            initParams(params, "HZ_CZRK_HJGL_HLWYY_QR", request, object);
            logger.info("户政申报_子女投靠父母[children]出参：" + JSONObject.toJSONString(object));
        } catch (Exception e) {
            logger.error("[children]错误日志" + e.getMessage(), e);
            object.setCode(500);
            object.setDescription("内部服务错误");
            object.setLastUpdateTime(System.currentTimeMillis());
        }
        return object;
    }

    /**
     * 准迁证补发
     */
    @RequestMapping(value = "/replacement", method = RequestMethod.POST)
    public Response replacement(@RequestBody Map params, HttpServletRequest request) {
        Response<Object> object = new Response<>();
        try {
            logger.info("户政申报_准迁证补发[replacement]入参：" + params);
            initParams(params, "HZ_CZRK_H_H_ZQZ_Q_YSBF", request, object);
            logger.info("户政申报_准迁证补发[replacement]出参：" + JSONObject.toJSONString(object));
        } catch (Exception e) {
            logger.error("[replacement]错误日志" + e.getMessage(), e);
            object.setCode(500);
            object.setDescription("内部服务错误");
            object.setLastUpdateTime(System.currentTimeMillis());
        }
        return object;
    }

    /**
     * 户政申报_转业、复员、退伍军人入户
     */
    @RequestMapping(value = "/households", method = RequestMethod.POST)
    public Response households(@RequestBody Map params, HttpServletRequest request) {
        Response<Object> object = new Response<>();
        try {
            logger.info("户政申报_转业、复员、退伍军人入户[households]入参：" + params);
            initParams(params, "HZ_CZRK_HJGL_HLWYY_HFHK", request, object);
            logger.info("户政申报_转业、复员、退伍军人入户[households]出参：" + JSONObject.toJSONString(object));
        } catch (Exception e) {
            logger.error("[households]错误日志" + e.getMessage(), e);
            object.setCode(500);
            object.setDescription("内部服务错误");
            object.setLastUpdateTime(System.currentTimeMillis());
        }
        return object;
    }

    /**
     * 户政申报_刑满释放人员恢复户口
     */
    @RequestMapping(value = "/emancipist", method = RequestMethod.POST)
    public Response emancipist(@RequestBody Map params, HttpServletRequest request) {
        Response<Object> object = new Response<>();
        try {
            logger.info("户政申报_刑满释放人员恢复户口[emancipist]入参：" + params);
            initParams(params, "HZ_CZRK_HJGL_HLWYY_HFHK", request, object);
            logger.info("户政申报_刑满释放人员恢复户口[emancipist]出参：" + JSONObject.toJSONString(object));
        } catch (Exception e) {
            logger.error("[emancipist]错误日志" + e.getMessage(), e);
            object.setCode(500);
            object.setDescription("内部服务错误");
            object.setLastUpdateTime(System.currentTimeMillis());
        }
        return object;
    }

    /**
     * 户口本遗失补办.
     */
    @RequestMapping(value = "/lostReplenishment", method = RequestMethod.POST)
    public Response lostReplenishment(@RequestBody Map params, HttpServletRequest request) {
        Response<Object> response = new Response<>();
        try {
            logger.info("调用用户本遗失补办[lostReplenishment]入参" + params);
            JSONObject maintable = JSONObject.parseObject(JSON.toJSONString(params.get("maintable")));
            //户主身份证，姓名，与申请人关系
            //testuser 测试用户正式环境从handler中获取
        /*    maintable.put("GMSFHM",  request.getHeader("idno"));
            maintable.put("XM", request.getHeader("name"));*/
            maintable.put("HZ_GMSFHM", "412727198901170879");
            maintable.put("HZ_XM", "陈玉龙");
            maintable.put("HZ_YSQRGX_DM", "01");
            params.put("maintable", maintable);
            initParams(params, "HZ_CZRK_HJGL_HLWYY_HKBBF", request, response);
            logger.info("调用用户本遗失补办[lostReplenishment]出参" + response);
        } catch (Exception e) {
            logger.error("[lostReplenishment]用户遗失补办接口调用异常" + e.getMessage(), e);
            response.setCode(500);
            response.setDescription("内部服务错误");
            response.setLastUpdateTime(System.currentTimeMillis());
        }
        return response;
    }

    /**
     * 回国（入境）
     */
    @RequestMapping(value = "/returnHome", method = RequestMethod.POST)
    public Response returnHome(@RequestBody Map params, HttpServletRequest request) {
        Response<Object> response = new Response<>();
        try {
            logger.info("调用回国入境接口[returnHome]入参" + params);
            initParams(params, "HZ_CZRK_HJGL_HLWYY_HFHK", request, response);
            logger.info("调用回国入境接口[returnHome]出参" + response);
        } catch (Exception e) {
            logger.error("[returnHome]调用回国入境接口调用异常" + e.getMessage(), e);
            response.setCode(500);
            response.setDescription("内部服务错误");
            response.setLastUpdateTime(System.currentTimeMillis());
        }
        return response;
    }

    /**
     * 弃婴入户
     */
    @RequestMapping(value = "/abandonBabyHome", method = RequestMethod.POST)
    public Response abandonBabyHome(@RequestBody Map params, HttpServletRequest request) {
        Response<Object> response = new Response<>();
        try {
            logger.info("调用弃婴入户[abandonBabyHome]入参" + params);
            initParams(params, "HZ_CZRK_HJGL_HLWYY_SYRH", request, response);
            logger.info("调用弃婴入户[abandonBabyHome]出参" + response);
        } catch (Exception e) {
            logger.error("[abandonBabyHome]调用弃婴入户接口调用异常" + e.getMessage(), e);
            response.setCode(500);
            response.setDescription("内部服务错误");
            response.setLastUpdateTime(System.currentTimeMillis());
        }
        return response;
    }

    /**
     * 迁往市（县）外有准迁证
     */
    @RequestMapping(value = "/move", method = RequestMethod.POST)
    public Response move(@RequestBody Map params, HttpServletRequest request) {
        Response<Object> response = new Response<>();
        try {
            logger.info("调用有准迁证[move]入参" + params);
            params.put("subTableName", "HZ_CZRK_HJGL_HLWYY_QR_SQRY");
            initParams(params, "HZ_CZRK_HJGL_HLWYY_QC", request, response);
            logger.info("调用有准迁证[move]出参" + response);
        } catch (Exception e) {
            logger.error("[move]调用有准迁证接口调用异常" + e.getMessage(), e);
            response.setCode(500);
            response.setDescription("内部服务错误");
            response.setLastUpdateTime(System.currentTimeMillis());
        }
        return response;
    }

    /**
     * 迁移证补发
     */
    @RequestMapping(value = "/relocationCertificate", method = RequestMethod.POST)
    public Response relocationCertificate(@RequestBody Map params, HttpServletRequest request) {
        Response<Object> response = new Response<>();
        try {
            logger.info("调用迁移证补发[relocationCertificate]入参" + params);
            JSONObject maintable = JSONObject.parseObject(JSON.toJSONString(params.get("maintable")));
            //户主身份证，姓名，与申请人关系
            //testuser 测试用户正式环境从handler中获取
        /*    maintable.put("GMSFHM",  request.getHeader("idno"));
            maintable.put("XM", request.getHeader("name"));*/
            maintable.put("GMSFHM", "412727198901170879");
            maintable.put("XM", "陈玉龙");
            maintable.put("YSQRGX_DM", "01");
            params.put("maintable", maintable);


            initParams(params, "HZ_CZRK_H_H_ZQZ_Q_YSBF", request, response);
            logger.info("调用迁移证补发[relocationCertificate]出参" + response);
        } catch (Exception e) {
            logger.error("[relocationCertificate]调用迁移证补发接口调用异常" + e.getMessage(), e);
            response.setCode(500);
            response.setDescription("内部服务错误");
            response.setLastUpdateTime(System.currentTimeMillis());
        }
        return response;
    }

    /**
     * 入伍注销户口
     */
    @RequestMapping(value = "/enlisted", method = RequestMethod.POST)
    public Response enlisted(@RequestBody Map params, HttpServletRequest request) {
        Response<Object> response = new Response<>();
        try {
            logger.info("调用入伍注销户口[enlisted]入参" + params);
            initParams(params, "HZ_CZRK_HJGL_HLWYY_ZX", request, response);
            logger.info("调用入伍注销户口[enlisted]出参" + response);
        } catch (Exception e) {
            logger.error("[enlisted]调用入伍注销户口接口调用异常" + e.getMessage(), e);
            response.setCode(500);
            response.setDescription("内部服务错误");
            response.setLastUpdateTime(System.currentTimeMillis());
        }
        return response;
    }

    /**
     * 设立单位集体户口
     */
    @RequestMapping(value = "/collective", method = RequestMethod.POST)
    public Response collective(@RequestBody Map params, HttpServletRequest request) {
        Response<Object> response = new Response<>();
        try {
            logger.info("调用设立单位集体户口[collective]入参" + params);
            initParams(params, "HZ_CZRK_HJGL_HLWYY_QR", request, response);
            logger.info("调用设立单位集体户口[collective]出参" + response);
        } catch (Exception e) {
            logger.error("[collective]调用设立单位集体户口接口调用异常" + e.getMessage(), e);
            response.setCode(500);
            response.setDescription("内部服务错误");
            response.setLastUpdateTime(System.currentTimeMillis());
        }
        return response;
    }

    /**
     * 收养入户
     */
    @RequestMapping(value = "/adoption", method = RequestMethod.POST)
    public Response adoption(@RequestBody Map params, HttpServletRequest request) {
        Response<Object> response = new Response<>();
        try {
            logger.info("调用收养入户[adoption]入参" + params);
            initParams(params, "HZ_CZRK_HJGL_HLWYY_SYRH", request, response);
            logger.info("调用收养入户[adoption]出参" + response);
        } catch (Exception e) {
            logger.error("[adoption]调用收养入户接口调用异常" + e.getMessage(), e);
            response.setCode(500);
            response.setDescription("内部服务错误");
            response.setLastUpdateTime(System.currentTimeMillis());
        }
        return response;
    }

    /**
     * 死亡登记
     */
    @RequestMapping("/deathRegistration")
    public Response deathRegistration(@RequestBody Map params, HttpServletRequest request) {
        Response<Object> response = new Response<>();
        try {
            logger.info("调用死亡登记[deathRegistration]入参" + params);
            initParams(params, "HZ_CZRK_HJGL_HLWYY_ZX", request, response);
            logger.info("调用死亡登记[deathRegistration]出参" + response);
        } catch (Exception e) {
            logger.error("[deathRegistration]调用死亡登记接口调用异常" + e.getMessage(), e);
            response.setCode(500);
            response.setDescription("内部服务错误");
            response.setLastUpdateTime(System.currentTimeMillis());
        }
        return response;
    }

    /**
     * 务工人员入户
     */
    @RequestMapping(value = "/workers", method = RequestMethod.POST)
    public Response workers(@RequestBody Map params, HttpServletRequest request) {
        Response<Object> response = new Response<>();
        try {
            logger.info("调用务工人员入户[workers]入参" + params);
            params.put("subTableName", "HZ_CZRK_HJGL_HLWYY_QR_SQRY");
            initParams(params, "HZ_CZRK_HJGL_HLWYY_QR", request, response);
            logger.info("调用务工人员入户[workers]出参" + response);
        } catch (Exception e) {
            logger.error("[workers]调用务工人员接口调用异常" + e.getMessage(), e);
            response.setCode(500);
            response.setDescription("内部服务错误");
            response.setLastUpdateTime(System.currentTimeMillis());
        }
        return response;
    }

    /**
     * 户政申报_分户、立户
     */
    @RequestMapping(value = "/ledgerAccount", method = RequestMethod.POST)
    public Response ledgerAccount(@RequestBody Map params, HttpServletRequest request) {
        Response<Object> response = new Response<>();
        try {
            logger.info("[ledgerAccount]调用户政申报_分户、立户入参" + params);
            initParams(params, "HZ_CZRK_HJGL_HLWYY_QR", request, response);
            logger.info("[ledgerAccount]调用户政申报_分户、立户出参" + response);
        } catch (Exception e) {
            logger.error("[ledgerAccount]户政申报_分户、立户接口调用异常" + e.getMessage(), e);
            response.setCode(500);
            response.setDescription("内部服务错误");
            response.setLastUpdateTime(System.currentTimeMillis());
        }
        return response;
    }

    /**
     * 户政申报_夫妻投靠
     */
    @RequestMapping(value = "/couple", method = RequestMethod.POST)
    public Response couple(@RequestBody Map params, HttpServletRequest request) {
        Response<Object> response = new Response<>();
        try {
            logger.info("[couple]调用户政申报_夫妻投靠[workers]入参" + params);
            params.put("subTableName", "HZ_CZRK_HJGL_HLWYY_QR_SQRY");
            initParams(params, "HZ_CZRK_HJGL_HLWYY_QR", request, response);
            logger.info("[couple]调用户政申报_夫妻投靠[workers]出参" + response);
        } catch (Exception e) {
            logger.error("[couple]调用户政申报_夫妻投靠接口调用异常" + e.getMessage(), e);
            response.setCode(500);
            response.setDescription("内部服务错误");
            response.setLastUpdateTime(System.currentTimeMillis());
        }
        return response;
    }

    /**
     * 户政申报_父母投靠子女
     */
    @RequestMapping(value = "/parents", method = RequestMethod.POST)
    public Response parents(@RequestBody Map params, HttpServletRequest request) {
        Response<Object> response = new Response<>();
        try {
            logger.info("[parents]调用户政申报_父母投靠子女入参" + params);
            params.put("subTableName", "HZ_CZRK_HJGL_HLWYY_QR_SQRY");
            initParams(params, "HZ_CZRK_HJGL_HLWYY_QR", request, response);
            logger.info("[parents]调用户政申报_父母投靠子女出参" + response);
        } catch (Exception e) {
            logger.error("[parents]调用户政申报_父母投靠子女接口调用异常" + e.getMessage(), e);
            response.setCode(500);
            response.setDescription("内部服务错误");
            response.setLastUpdateTime(System.currentTimeMillis());
        }
        return response;
    }

    /**
     * 工作调动入户
     */
    @RequestMapping(value = "/transfer", method = RequestMethod.POST)
    public Response transfer(@RequestBody Map params, HttpServletRequest request) {
        Response<Object> response = new Response<>();
        try {
            logger.info("[transfer]调用工作调动入户[workers]入参" + params);
            params.put("subTableName", "HZ_CZRK_HJGL_HLWYY_QR_SQRY");
            initParams(params, "HZ_CZRK_HJGL_HLWYY_QR", request, response);
            logger.info("[transfer]调用工作调动入户[workers]出参" + response);
        } catch (Exception e) {
            logger.error("[transfer]调用工作调动入户接口调用异常" + e.getMessage(), e);
            response.setCode(500);
            response.setDescription("内部服务错误");
            response.setLastUpdateTime(System.currentTimeMillis());
        }
        return response;
    }

    /**
     * 户政申报_购房入户
     */
    @RequestMapping(value = "/housePurchase", method = RequestMethod.POST)
    public Response housePurchase(@RequestBody Map params, HttpServletRequest request) {
        Response<Object> response = new Response<>();
        try {
            logger.info("[housePurchase]调用购房入户入参" + params);
            params.put("subTableName", "HZ_CZRK_HJGL_HLWYY_QR_SQRY");
            JSONObject maintable = JSONObject.parseObject(JSON.toJSONString(params.get("maintable")));
            //户主身份证，姓名，与申请人关系
            //testuser 测试用户正式环境从handler中获取
        /*    maintable.put("GMSFHM",  request.getHeader("idno"));
            maintable.put("XM", request.getHeader("name"));*/
            maintable.put("GMSFHM", "412727198901170879");
            maintable.put("XM", "陈玉龙");
            maintable.put("YSQRGX_DM", "01");
            params.put("maintable", maintable);
            initParams(params, "HZ_CZRK_HJGL_HLWYY_QR", request, response);
            logger.info("[housePurchase]调用购房入户出参" + response);
        } catch (Exception e) {
            logger.error("[housePurchase]调用购房入户接口调用异常" + e.getMessage(), e);
            response.setCode(500);
            response.setDescription("内部服务错误");
            response.setLastUpdateTime(System.currentTimeMillis());
        }
        return response;
    }

    /**
     * 上传附件
     */
    @RequestMapping(value = "/uploadEnclosure", method = RequestMethod.POST)
    public Response uploadEnclosure(MultipartFile file, HttpServletRequest request) {
        Response<Object> response = new Response<>();
        try {
            //获取配置文件中的 url地址
            String urlPath = environment.getProperty("hz.query.url.selectEnclosure");
            //获取headers请求头数据
            Map<String, Object> mapHeaders = getMapHeaders(request);
            //初始化token值
            mapHeaders.put("token", service.initToken(request));

            logger.info("[uploadEnclosure]上传附件入参" + file.getSize() / 1024 + " KB");
            File toFile = HttpRequestUtil.setFile(file, "materialFile");
            List<File> listFile = new ArrayList<>();
            listFile.add(toFile);
            FilePart filePart = new FilePart("materialFile", toFile);
            Part[] part = {filePart};
            String result = HttpRequestUtil.URLPostFile(urlPath, mapHeaders, part, listFile);
            logger.info("上传后返回结果：" + result);
            if (StringUtils.isNotBlank(result)) {
                JSONObject json = JSONObject.parseObject(result);
                JSONObject Object = new JSONObject();
                //如果回传数据成功取出data
                if ("true".equals(json.getString("succ"))) {
                    Object.put("attGuid", json.get("data"));
                    Object.put("contentSize", file.getSize());
                    Object.put("friendlyFileName", file.getOriginalFilename());
                    String getimage = getimage(String.valueOf(json.get("data")), request, mapHeaders);
                    if (StringUtils.isBlank(getimage)) {
                        response.setCode(250710);
                        response.setDescription("必传材料附件未上传！");
                        response.setLastUpdateTime(System.currentTimeMillis());
                        return response;
                    }
                    Object.put("fileContent", getimage);
                    response.setPayload(Object);
                } else {
                    response.setCode(Integer.valueOf(json.get("code").toString()));
                }
                String s = json.getString("msg");
                if ("".equals(s) || null == s) {
                    response.setDescription(codeMap().get(json.getInteger("code")));
                } else {
                    response.setDescription(s);
                }
            } else {
                response.setCode(500);
                response.setDescription("内部服务错误");
                response.setLastUpdateTime(System.currentTimeMillis());
            }
        } catch (Exception e) {
            logger.error("[uploadEnclosure]上传附件异常" + e.getMessage(), e);
            response.setCode(500);
            response.setDescription("内部服务错误");
            response.setLastUpdateTime(System.currentTimeMillis());
        }
        return response;
    }

    /**
     * code值对应的msg值
     */
    public static Map<Integer, String> codeMap() {
        HashMap<Integer, String> map = new HashMap<>();
        //事项申报
        map.put(250701, "事项申报成功！");
        map.put(250702, "事项申报失败！");
        map.put(250703, "事项警种不能为空！");
        map.put(250704, "事项编号不能为空！");
        map.put(250705, "事项代码不能为空！");
        map.put(250706, "部门编号不能为空！");
        map.put(250707, "部门名称不能为空！");
        map.put(250708, "用户GUID不能为空！");
        map.put(250709, "业务类型不能为空！");
        map.put(250710, "必传材料附件未上传！");
        map.put(250711, "无该警种的事项申报！");
        map.put(250712, "市局代码不能为空！");
        map.put(250713, "办件提交类型不能为空");
        map.put(250714, "办件草稿保存成功");
        map.put(250715, "数据来源不能为空！");
        map.put(250716, "应用类型不能为空！");
        map.put(250717, "数据来源传值非法！");
        map.put(250718, "应用类型传值非法！");
        map.put(250719, "该出生证已登记！");
        map.put(250720, "父亲或母亲信息不符合，如果出生证父母亲信息有误请到窗口办理！");
        //材料查看
        map.put(251301, "附件获取成功！");
        map.put(251302, "附件获取失败！");
        //材料上传
        map.put(251401, "附件上传成功！");
        map.put(251402, "附件上传失败！");
        map.put(251403, "附件不能为空！");
        map.put(250202, "办事指南不能为空！");
        //材料删除
        map.put(251501, "材料附件删除成功！");
        map.put(251502, "材料附件删除失败！");
        //查询接口
        map.put(500700, "请求成功！");
        map.put(500701, "请求参数不完整！");
        map.put(500702, "核对校验不通过！");
        map.put(500703, "新生儿信息为空");
        map.put(500704, "接口服务器异常！");
        map.put(500705, "请求数据不存在！");
        //户政列表
        map.put(250301, "获取户政事项列表成功！");
        map.put(250302, "获取户政事项列表失败！");
        map.put(250303, "事项类型不能为空！");
        map.put(250304, "警种不能为空！");
        //办事指南
        map.put(250401, "获取户政办事指南成功！");
        map.put(250402, "获取户政办事指南失败！");
        //类别字典
        map.put(251601, "获取字典数据成功！");
        map.put(251602, "获取字典数据失败！");
        //字典值
        map.put(251201, "获取字典值成功！");
        map.put(251202, "获取字典值失败！");
        //用户值
        map.put(210301, "获取用户成功！");
        map.put(210302, "用户不存在！");
        //市局列表
        map.put(240101, "获取户政市局列表成功！");
        map.put(240102, "获取户政市局列表失败");
        //分局列表
        map.put(240201, "获取户政县市（区分）局列表成功！");
        map.put(240202, "获取户政县市（区分）局列表失败！");
        //警察局列表
        map.put(240301, "获取户政派出所列表成功");
        map.put(240302, "获取户政派出所列表失败！");


        return map;
    }

    /**
     * 申报反参统一封装
     *
     * @param params
     * @param request
     * @param object
     */
    public void httpHzResopnse(@RequestBody String params, HttpServletRequest request, Response<Object> object) {
        //获取headers请求头数据
        Map<String, Object> mapHeaders = getMapHeaders(request);
        //初始化token
        mapHeaders.put("token", service.initToken(request));
        //获取配置文件中的 url地址
        String urlPath = environment.getProperty("hz.apply.url");
        //调用公安厅接口
        String response = HttpRequestUtil.URLPostJSONParams(urlPath, params, mapHeaders);
        logger.info("调用户政接口返回的数据" + response);
        if (StringUtils.isNotBlank(response)) {
            JSONObject json = JSONObject.parseObject(response);
            //如果回传数据成功取出data
            if ("true".equals(json.getString("succ"))) {
                object.setPayload(json.get("data"));
            } else {
                //获取code值，如果msg为空，根据code值匹配对应的msg值
                object.setCode(json.getInteger("code"));
            }

            object.setDescription(codeMap().get(json.getInteger("code")));
        } else {
            logger.info("调用户政接口异常返回值为空,入参为：[{}]", params);
            object.setCode(500);
            object.setDescription("内部服务错误");
        }
        object.setLastUpdateTime(System.currentTimeMillis());
    }

    private String getimage(String att_guid, HttpServletRequest request, Map<String, Object> mapHeaders) {
        //获取配置文件中的 url地址
        String urlPath = environment.getProperty("hz.query.url.selectEnclosure");
        //url地址请求拼接
        StringBuffer stb = new StringBuffer(urlPath);
        stb.append("/");
        stb.append(att_guid);
        urlPath = stb.toString();
        logger.info("调用公安接口地址：" + urlPath);
        String result = HttpRequestUtil.URLGet(urlPath, new HashMap<>(), "utf-8", mapHeaders);
        logger.info("调用公安厅接口返回" + result);
        if (StringUtils.isNotBlank(result)) {
            JSONObject json = JSONObject.parseObject(result);
            //如果回传数据成功取出data
            if ("true".equals(json.getString("succ"))) {
                try {
                    String data = json.getString("data");
                    JSONObject jsonObject = JSONObject.parseObject(data);
                    return jsonObject.getString("fileContent");
                } catch (Exception e) {
                    logger.error("解密失败", e);
                }
            }
        }
        return null;
    }

}
