package com.neusoft.mid.ec.api.controller.reservedFund;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.neusoft.mid.ec.api.controller.BaseController;
import com.neusoft.mid.ec.api.domain.RequestInfo;
import com.neusoft.mid.ec.api.serviceInterface.reservedfound.ProvinceReservedFoundService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import me.puras.common.json.Response;
import me.puras.common.json.ResponseHelper;

/**
 * 省直公积金
 */
@Api(value = "省直公积金查询", description = "省直公积金查询")
@RestController
@RequestMapping("/province/reserved/fund/")
public class ProvinceReservedFundController  extends BaseController{
	@Autowired
	public ProvinceReservedFoundService proresFoundService; 
    /**
             * 获取省直公积金信息
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping("/provinceInfo")
    @ApiOperation(value = "获取省直公积金信息")
    @ApiImplicitParam(paramType = "query")
    public Response getProvinceInfo(@RequestBody Map<String, String> params,HttpServletRequest request){
    	logger.info("获取省直公积金信息" + params);
    	RequestInfo reqInfo = getRequestInfo(request);
        if (StringUtils.isEmpty(reqInfo.getIdno())) {
            return ResponseHelper.createResponse(-9999, "身份证号不能为空");
        }
        if (StringUtils.isEmpty(reqInfo.getName())) {
            return ResponseHelper.createResponse(-9999, "姓名不能为空");
        }
        String biz_type=String.valueOf(params.get("bizType"));
        if (StringUtils.isEmpty(biz_type)) {
            return ResponseHelper.createResponse(-9999, "查询接口类型不能为空");
        }
        try {
            String result = proresFoundService.getBasicInfo(reqInfo.getIdno(), reqInfo.getName(),biz_type);

            if (result == null) {
                return ResponseHelper.createResponse(-9999, "查询失败，请稍候重试！");
            }
            JSONObject json = JSONObject.parseObject(result);
            Map map = new HashMap(10);
            map.put("result", json);
            map.put("idno", reqInfo.getIdno());
            map.put("name", reqInfo.getName());
            return ResponseHelper.createSuccessResponse(map);
        } catch (Exception e) {
            logger.error("getCardBasicQuery error", e);
        }
        return ResponseHelper.createResponse(-9999, "查询失败，请稍候重试！");
    }


}
