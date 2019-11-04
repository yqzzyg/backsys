package com.neusoft.mid.ec.api.controller.country.socialsecurity;

import com.alibaba.fastjson.JSONArray;
import com.neusoft.mid.ec.api.controller.BaseController;
import com.neusoft.mid.ec.api.serviceInterface.socialsecurity.SocialSecurityCardQueryService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import me.puras.common.json.Response;
import me.puras.common.json.ResponseHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 社保查询
 */
@RestController
@RequestMapping("/country/rs/query")
public class CountrySocialSecurityQueryController extends BaseController {
	@Autowired
	SocialSecurityCardQueryService socialSecurityCardQueryService;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ApiOperation(value = "人员基本信息查询")
	@RequestMapping(value = "/personInfo", method = RequestMethod.POST)
	@ApiImplicitParam(paramType = "query")
	public Response personInfo(@RequestBody Map params, HttpServletRequest request) {
		Response<Object> object = new Response<>();
		try {
			Map<String, Object> mapHeaders = getMapHeaders(request);
			String idno = request.getParameter("idno");
			String name = request.getParameter("name");
			if (StringUtils.isEmpty(idno) || StringUtils.isEmpty(name)) {
				object.setCode(1);
				object.setDescription("姓名和证件号码不能为空");
				return object;
			}
			params.put("account", idno);
			params.put("name", name);
			params.put("aac147", idno);
			String result = socialSecurityCardQueryService.getPeron(params, mapHeaders);
			if (StringUtils.isEmpty(result)) {
				return ResponseHelper.createResponse(-9999, "查询失败");
			}
			JSONArray jsonArray = JSONArray.parseArray(result);

			return ResponseHelper.createSuccessResponse(jsonArray);
		} catch (Exception e) {
			this.logger.error("getCard error", e);
			logger.info("调用人员基本信息查询为空,入参为：[{}]", params);
			object.setCode(500);
			object.setDescription("内部服务错误");
		}
		return object;
	}
}
