package com.neusoft.mid.ec.api.controller.housingconstruction;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.neusoft.mid.ec.api.controller.BaseController;
import com.neusoft.mid.ec.api.domain.RequestInfo;
import com.neusoft.mid.ec.api.serviceInterface.housingconstruction.HousingConstructionService;
import com.neusoft.mid.ec.api.util.ValidateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import me.puras.common.json.Response;
import me.puras.common.json.ResponseHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/***
 *住建
 */
@RestController
@RequestMapping("/zj/query")
@Api(value = "住建",description = "住建")
public class HousingConstructionController extends BaseController {

    @Autowired
    HousingConstructionService housingConstructionService;

    /**
     * 二级建造师
     * @return
     */
    @RequestMapping(value="/getUserInfo")
    @ApiOperation("获取用户信息")
    public Response getUserInfo(HttpServletRequest request){
        try {
            RequestInfo reqInfo = getRequestInfo(request);
            JSONObject jsonObj = new JSONObject();
            if (StringUtils.isNotBlank(reqInfo.getIdno())) {
                jsonObj.put("idno", ValidateUtils.idMask(reqInfo.getIdno(),1,1));
                jsonObj.put("name",reqInfo.getName());
            }else{
                jsonObj.put("idno", ValidateUtils.idMask("410923197909145423",1,1));
                jsonObj.put("name","张三");
            }
            return ResponseHelper.createSuccessResponse(jsonObj);
        } catch (Exception e) {
            logger.error("获取用户信息异常"+e);
            return ResponseHelper.createResponse(500,"内部服务器错误");
        }
    }


    /**
     * 二级建造师
     * @return
     */
    @RequestMapping(value="/architectInfo", method = RequestMethod.POST)
    @ApiOperation("二级建造师查询")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "registrationNO", value = "注册号", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "enterpriseName", value = "企业名称", required = true, dataType = "String")
    })
    public Response getArchitect(HttpServletRequest request,@RequestBody Map params,HttpServletResponse response){
        RequestInfo reqInfo = getRequestInfo(request);
        try {
            //  verifyCode(request,response,String.valueOf(params.get("validateCode")),null);

            if(null==params.get("registrationNO")||null==params.get("enterpriseName")){
                return ResponseHelper.createResponse(-9999,"必传参数注册号或企业名称为空！");
            }
            if (StringUtils.isBlank(reqInfo.getIdno())) {
                reqInfo.setIdno("410923197909145423");
            }
            String registrationNO=(String) params.get("registrationNO");
            String enterpriseName=(String) params.get("enterpriseName");
            JSONObject userInfo = housingConstructionService.getC003(registrationNO, reqInfo.getIdno(), enterpriseName);
            JSONArray zczy = JSON.parseArray(userInfo.getString("zczy"));
            userInfo.put("zczy",zczy);
            return ResponseHelper.createSuccessResponse(userInfo);
        } catch (Exception e) {
            this.logger.error("getArchitect error",e);
            return ResponseHelper.createResponse(-9999,e.getMessage() == null?"查询失败":e.getMessage());
        }
    }


    /**
     * 建筑施工特种作业
     * @return
     */
    @RequestMapping(value="/architectWork",method = RequestMethod.POST)
    @ApiOperation("建筑施工特种作业")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "certificate", value = "证书编号", required = true, dataType = "String")
    })
    public Response architectWork(@RequestBody Map params,HttpServletRequest request,HttpServletResponse response){
        RequestInfo reqInfo = getRequestInfo(request);
        try {
            //  verifyCode(request,response,String.valueOf(params.get("validateCode")),"page");

            if(null==params.get("certificate")){
                return ResponseHelper.createResponse(-9999,"必传参数证书编号为空！");
            }
            if (StringUtils.isBlank(reqInfo.getIdno())) {
                reqInfo.setIdno("41088219780813552X");
            }
            String certificate=(String) params.get("certificate");
            System.out.println(reqInfo);
            JSONArray userInfo = housingConstructionService.getC002(reqInfo.getIdno(), certificate);
            return ResponseHelper.createSuccessResponse(userInfo);
        } catch (Exception e) {
            this.logger.error("getArchitect error",e);
            return ResponseHelper.createResponse(-9999,e.getMessage() == null?"查询失败":e.getMessage());
        }
    }


    /**
     * 安全生产考核合格证书
     * @return·
     */
    @RequestMapping(value = "/architectCard",method = RequestMethod.POST)
    @ApiOperation("安全生产考核合格证书 ")
    public Response architectCard(@RequestBody Map params,HttpServletRequest request,HttpServletResponse response){
        RequestInfo reqInfo = getRequestInfo(request);
        try {

            // verifyCode(request,response,String.valueOf(params.get("validateCode")),"page");

            if(null==params.get("certificate")||null==params.get("enterpriseName")){
                return ResponseHelper.createResponse(-9999,"必传参数证书编号或企业名称为空！");
            }
            if (StringUtils.isBlank(reqInfo.getIdno())) {
                reqInfo.setIdno("412727198108117714");
            }
            String certificate=(String) params.get("certificate");
            String enterpriseName=(String) params.get("enterpriseName");
            JSONArray userInfo = housingConstructionService.getC001(certificate, reqInfo.getIdno(), enterpriseName);
            return ResponseHelper.createSuccessResponse(userInfo);
        } catch (Exception e) {
            this.logger.error("getArchitect error",e);
            return ResponseHelper.createResponse(-9999,e.getMessage() == null?"查询失败":e.getMessage());
        }
    }
}
