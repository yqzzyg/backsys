package com.neusoft.mid.ec.api.controller.country.socialsecurity;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.neusoft.mid.ec.api.controller.BaseController;
import com.neusoft.mid.ec.api.serviceInterface.country.socialsecurity.CountryEmploymentQueryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.puras.common.json.Response;
import me.puras.common.json.ResponseHelper;
import net.sf.json.JSONObject;

/**
 * @author Administrator
 * 就业查询业务
 */
@RestController
@RequestMapping("/country/rs/jy/query")
@Api(value = "人社",description = "就业")
public class CountryEmploymentQueryController  extends BaseController  {

    @Autowired
    CountryEmploymentQueryService employmentQueryService;

	
	private String nameErrorMsg = "姓名不能为空";
	private String idnoErrorMsg = "身份证号不能为空";
	private String subsidyBidCategoryErrorMsg = "补贴大类不能为空";
	private String subsidySmallCategoryErrorMsg = "补贴小类不能为空";
	private String areaCodeErrorMsg = "行政区划编码不能为空";
	private String serviceBigCategoryErrorMsg = "业务类型大类不能为空";
	private String serviceSmallCategoryErrorMsg = "业务类型小类不能为空";
	
	 /**
     * 就业技能培训补贴标准查询
     * @return
     */
    @ApiOperation("就业技能培训补贴标准查询")
    @RequestMapping("/queryJyjnpxstandard")
    public Response queryJyjnpxstandard(@RequestParam Map<String, Object> params, HttpServletRequest request){
    	Response<Object> response = new Response<Object>();
    	if (StringUtils.isBlank(params.get("ADA101")==null?null:params.get("ADA101").toString())) {
    		response.setCode(1);
    		response.setDescription(subsidyBidCategoryErrorMsg);
            return response;
        }
        if (StringUtils.isBlank(params.get("ADA102")==null?null:params.get("ADA102").toString())) {
        	response.setCode(1);
        	response.setDescription(subsidySmallCategoryErrorMsg);
            return response;
        }
        if (StringUtils.isBlank(params.get("AAA020")==null?null:params.get("AAA020").toString())) {
        	response.setCode(1);
        	response.setDescription(areaCodeErrorMsg);
            return response;
        }
    	return employmentQueryService.queryInfo("就业技能培训补贴标准查询", params, "QUERY_JYJNPXSTANDARD");
    }

    /**
     * 创业培训补贴标准查询
     * @return
     */
    @ApiOperation("创业培训补贴标准查询")
    @RequestMapping("/queryCypxstandard")
    public Response queryCypxstandard(@RequestParam Map<String, Object> params, HttpServletRequest request){
    	Response<Object> response = new Response<Object>();
    	if (StringUtils.isBlank(params.get("ADA101")==null?null:params.get("ADA101").toString())) {
    		response.setCode(1);
    		response.setDescription(subsidyBidCategoryErrorMsg);
            return response;
        }
        if (StringUtils.isBlank(params.get("ADA102")==null?null:params.get("ADA102").toString())) {
        	response.setCode(1);
        	response.setDescription(subsidySmallCategoryErrorMsg);
            return response;
        }
        if (StringUtils.isBlank(params.get("AAA020")==null?null:params.get("AAA020").toString())) {
        	response.setCode(1);
        	response.setDescription(areaCodeErrorMsg);
            return response;
        }
    	return employmentQueryService.queryInfo("创业培训补贴标准查询", params, "QUERY_CYPXSTANDARD");
    }
    /**
     * 灵活就业社保补贴标准查询
     * @return
     */
    @ApiOperation("灵活就业社保补贴标准查询")
    @RequestMapping("/queryLhjysbbtstandard")
    public Response queryLhjysbbtstandard(@RequestParam Map<String, Object> params, HttpServletRequest request){
    	Response<Object> response = new Response<Object>();
    	if (StringUtils.isBlank(params.get("ADA101")==null?null:params.get("ADA101").toString())) {
    		response.setCode(1);
    		response.setDescription(subsidyBidCategoryErrorMsg);
            return response;
        }
        if (StringUtils.isBlank(params.get("ADA102")==null?null:params.get("ADA102").toString())) {
        	response.setCode(1);
        	response.setDescription(subsidySmallCategoryErrorMsg);
            return response;
        }
        if (StringUtils.isBlank(params.get("AAA020")==null?null:params.get("AAA020").toString())) {
        	response.setCode(1);
        	response.setDescription(areaCodeErrorMsg);
            return response;
        }
        return employmentQueryService.queryInfo("灵活就业社保补贴标准查询", params, "QUERY_LHJYSBBTSTANDARD");
    }
    
	 /**
     * 灵活就业社保补贴申请相关政策查询
     * @return
     */
    @ApiOperation("灵活就业社保补贴申请相关政策查询")
    @RequestMapping("/querySbbtpolicy")
    public Response querySbbtpolicy(@RequestParam Map<String, Object> params, HttpServletRequest request){
    	Response<Object> response = new Response<Object>();
    	if (StringUtils.isBlank(params.get("ACC0P6")==null?null:params.get("ACC0P6").toString())) {
    		response.setCode(1);
    		response.setDescription(serviceBigCategoryErrorMsg);
            return response;
        }
        if (StringUtils.isBlank(params.get("ACC0P2")==null?null:params.get("ACC0P2").toString())) {
        	response.setCode(1);
        	response.setDescription(serviceSmallCategoryErrorMsg);
            return response;
        }
    	
    	return employmentQueryService.queryInfo("灵活就业社保补贴申请相关政策查询", params, "QUERY_SBBTPOLICY");
    }
    
    /**
     * 单位社保补贴标准查询
     * @return
     */
    @ApiOperation("单位社保补贴标准查询")
    @RequestMapping("/queryDwsbbtstandard")
    public Response queryDwsbbtstandard(@RequestParam Map<String, Object> params, HttpServletRequest request){
    	Response<Object> response = new Response<Object>();
    	if (StringUtils.isBlank(params.get("ADA101")==null?null:params.get("ADA101").toString())) {
    		response.setCode(1);
    		response.setDescription(subsidyBidCategoryErrorMsg);
            return response;
        }
        if (StringUtils.isBlank(params.get("ADA102")==null?null:params.get("ADA102").toString())) {
        	response.setCode(1);
        	response.setDescription(subsidySmallCategoryErrorMsg);
            return response;
        }
        if (StringUtils.isBlank(params.get("AAA020")==null?null:params.get("AAA020").toString())) {
        	response.setCode(1);
        	response.setDescription(areaCodeErrorMsg);
            return response;
        }
        return employmentQueryService.queryInfo("单位社保补贴标准查询", params, "QUERY_DWSBBTSTANDARD");
    }
    
    /**
     * 就业见习补贴标准查询
     * @return
     */
    @ApiOperation("就业见习补贴标准查询")
    @RequestMapping("/queryJyjxstandard")
    public Response queryJyjxstandard(@RequestParam Map<String, Object> params, HttpServletRequest request){
    	Response<Object> response = new Response<Object>();
    	if (StringUtils.isBlank(params.get("ADA101")==null?null:params.get("ADA101").toString())) {
    		response.setCode(1);
    		response.setDescription(subsidyBidCategoryErrorMsg);
            return response;
        }
        if (StringUtils.isBlank(params.get("ADA102")==null?null:params.get("ADA102").toString())) {
        	response.setCode(1);
        	response.setDescription(subsidySmallCategoryErrorMsg);
            return response;
        }
        if (StringUtils.isBlank(params.get("AAA020")==null?null:params.get("AAA020").toString())) {
        	response.setCode(1);
        	response.setDescription(areaCodeErrorMsg);
            return response;
        }
        return employmentQueryService.queryInfo("就业见习补贴标准查询", params, "QUERY_JYJXSTANDARD");
    }
    

    /**
     * 求职创业补贴标准查询
     * @return
     */
    @ApiOperation("求职创业补贴标准查询")
    @RequestMapping("/queryQzcystandard")
    public Response queryQzcystandard(@RequestParam Map<String, Object> params, HttpServletRequest request){
    	Response<Object> response = new Response<Object>();
    	if (StringUtils.isBlank(params.get("ADA101")==null?null:params.get("ADA101").toString())) {
    		response.setCode(1);
    		response.setDescription(subsidyBidCategoryErrorMsg);
            return response;
        }
        if (StringUtils.isBlank(params.get("AAA020")==null?null:params.get("AAA020").toString())) {
        	response.setCode(1);
        	response.setDescription(areaCodeErrorMsg);
            return response;
        }
    	return employmentQueryService.queryInfo("求职创业补贴标准查询", params, "QUERY_QZCYSTANDARD");
    }
    
    /**
     * 就业困难人员申请相关政策查询
     * @return
     */
    @ApiOperation("就业困难人员申请相关政策查询")
    @RequestMapping("/queryKnrypolicy")
    public Response queryKnrypolicy(@RequestParam Map<String, Object> params, HttpServletRequest request){
    	Response<Object> response = new Response<Object>();
    	if (StringUtils.isBlank(params.get("ACC0P6")==null?null:params.get("ACC0P6").toString())) {
    		response.setCode(1);
    		response.setDescription(serviceBigCategoryErrorMsg);
            return response;
        }
        if (StringUtils.isBlank(params.get("ACC0P2")==null?null:params.get("ACC0P2").toString())) {
        	response.setCode(1);
        	response.setDescription(serviceSmallCategoryErrorMsg);
            return response;
        }
    	return employmentQueryService.queryInfo("就业困难人员申请相关政策查询", params, "QUERY_KNRYPOLICY");
    }
    /**
     * 就业创业证查询
     * @return
     */
    @ApiOperation("就业创业证查询")
    @RequestMapping("/serchJycyz")
    public Response serchJycyz(@RequestParam Map<String, Object> params,HttpServletRequest request){
    	return employmentQueryService.queryInfo("就业创业证查询", params, "SERCH_JYCYZ");
    }
    
    /**
     * 就业登记查询
     * @return
     */
    @ApiOperation("就业登记查询")
    @RequestMapping("/serchJydj")
    public Response serchJydj(@RequestParam Map<String, Object> params,HttpServletRequest request){
        if(StringUtils.isBlank(params.get("AAC002")==null?null:params.get("AAC002").toString())){
            return ResponseHelper.createResponse(-9999,"身份证号不能为空");
        }
        if(StringUtils.isBlank(params.get("AAC003")==null?null:params.get("AAC003").toString())){
            return ResponseHelper.createResponse(-9999,"姓名不能为空");
        }
    	return employmentQueryService.queryInfo("就业登记查询", params, "SERCH_JYDJ");
    }
    /**
     * 失业登记查询
     * @return
     */
    @ApiOperation("失业登记查询")
    @PostMapping("/serchSydj")
    public Response getUnemploymentRegistration(@RequestParam Map<String, Object> params,HttpServletRequest request){
    	 if(StringUtils.isBlank(params.get("AAC002")==null?null:params.get("AAC002").toString())){
             return ResponseHelper.createResponse(-9999,"身份证号不能为空");
         }
         if(StringUtils.isBlank(params.get("AAC003")==null?null:params.get("AAC003").toString())){
             return ResponseHelper.createResponse(-9999,"姓名不能为空");
         }
       return employmentQueryService.queryInfo("失业登记查询", params, "SERCH_SYDJ");
    }
    /**
     * 就业技能培训人员报名相关政策查询
     * @return
     */
    @ApiOperation("就业技能培训人员报名相关政策查询")
    @RequestMapping("/queryJnpxpolicy")
    public Response queryJnpxpolicy(@RequestParam Map<String, Object> params, HttpServletRequest request){
    	Response<Object> response = new Response<Object>();
    	if (StringUtils.isBlank(params.get("ACC0P6")==null?null:params.get("ACC0P6").toString())) {
    		response.setCode(1);
    		response.setDescription(serviceBigCategoryErrorMsg);
            return response;
        }
        if (StringUtils.isBlank(params.get("ACC0P2")==null?null:params.get("ACC0P2").toString())) {
        	response.setCode(1);
        	response.setDescription(serviceSmallCategoryErrorMsg);
            return response;
        }
    	return employmentQueryService.queryInfo("就业技能培训人员报名相关政策查询", params, "QUERY_JNPXPOLICY");
    }
    /**
     * 创业担保贷款申请政策查询
     * @return
     */
    @ApiOperation("创业担保贷款申请政策查询")
    @RequestMapping("/queryDbdkpolicy")
    public Response queryDbdkpolicy(@RequestParam Map<String, Object> params, HttpServletRequest request){
    	Response<Object> response = new Response<Object>();
    	if (StringUtils.isBlank(params.get("ACC0P6")==null?null:params.get("ACC0P6").toString())) {
    		response.setCode(1);
    		response.setDescription(serviceBigCategoryErrorMsg);
            return response;
        }
        if (StringUtils.isBlank(params.get("ACC0P2")==null?null:params.get("ACC0P2").toString())) {
        	response.setCode(1);
        	response.setDescription(serviceSmallCategoryErrorMsg);
            return response;
        }
    	return employmentQueryService.queryInfo("创业担保贷款申请政策查询", params, "QUERY_DBDKPOLICY");
    }
}
