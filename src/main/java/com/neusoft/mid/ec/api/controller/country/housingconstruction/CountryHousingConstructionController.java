package com.neusoft.mid.ec.api.controller.country.housingconstruction;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.neusoft.mid.ec.api.controller.BaseController;
import com.neusoft.mid.ec.api.serviceInterface.housingconstruction.HousingConstructionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.puras.common.json.Response;
import me.puras.common.json.ResponseHelper;

/***
 * 住建
 */
@RestController
@RequestMapping("/country/zj/query")
@Api(value = "住建", description = "住建")
public class CountryHousingConstructionController extends BaseController {

	@Autowired
	HousingConstructionService housingConstructionService;

	/**
	 * 二级建造师
	 * 
	 * @return
	 */
	@RequestMapping(value = "/architectInfo", method = RequestMethod.POST)
	@ApiOperation("二级建造师查询")
	public Response getArchitect(@RequestParam Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			if (null == params.get("registrationNO") || null == params.get("enterpriseName")) {
				return ResponseHelper.createResponse(-9999, "必传参数注册号或企业名称为空！");
			}
			if (null == params.get("idno")) {
				return ResponseHelper.createResponse(-9999, "必传参数身份证号为空！");
			}
			String registrationNO = (String) params.get("registrationNO");
			String enterpriseName = (String) params.get("enterpriseName");
			String idno = (String) params.get("idno");
			JSONObject userInfo = housingConstructionService.getC003(registrationNO, idno, enterpriseName);
			JSONArray zczy = JSON.parseArray(userInfo.getString("zczy"));
			userInfo.put("zczy", zczy);
			return ResponseHelper.createSuccessResponse(userInfo);
		} catch (Exception e) {
			this.logger.error("getArchitect error", e);
			return ResponseHelper.createResponse(-9999, e.getMessage() == null ? "查询失败" : e.getMessage());
		}
	}

	/**
	 * 特种作业人员证书查询
	 * 
	 * @return
	 */
	@RequestMapping(value = "/architectWork", method = RequestMethod.POST)
	@ApiOperation("建筑施工特种作业")
	public Response architectWork(@RequestParam Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			if (null == params.get("certificate")) {
				return ResponseHelper.createResponse(-9999, "必传参数证书编号为空！");
			}
			if(null==params.get("idno")) {
				return ResponseHelper.createResponse(-9999, "必传参数身份证号为空！");
			}
			String certificate = (String) params.get("certificate");
			String idno = (String) params.get("idno");
			JSONArray userInfo = housingConstructionService.getC002(idno, certificate);
			return ResponseHelper.createSuccessResponse(userInfo);
		} catch (Exception e) {
			this.logger.error("getArchitect error", e);
			return ResponseHelper.createResponse(-9999, e.getMessage() == null ? "查询失败" : e.getMessage());
		}
	}

	/**
	 * 安全生产管理人员证书查询
	 */
	@RequestMapping(value = "/architectCard", method = RequestMethod.POST)
	@ApiOperation("安全生产考核合格证书 ")
	public Response architectCard(@RequestParam Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			if (null == params.get("certificate") || null == params.get("enterpriseName")) {
				return ResponseHelper.createResponse(-9999, "必传参数证书编号或企业名称为空！");
			}
			if(null==params.get("idno")) {
				return ResponseHelper.createResponse(-9999, "必传参数身份证号为空！");
			}
			String certificate = (String) params.get("certificate");
			String enterpriseName = (String) params.get("enterpriseName");
			String idno = (String) params.get("idno");
			JSONArray userInfo = housingConstructionService.getC001(certificate, idno, enterpriseName);
			return ResponseHelper.createSuccessResponse(userInfo);
		} catch (Exception e) {
			this.logger.error("getArchitect error", e);
			return ResponseHelper.createResponse(-9999, e.getMessage() == null ? "查询失败" : e.getMessage());
		}
	}
}
