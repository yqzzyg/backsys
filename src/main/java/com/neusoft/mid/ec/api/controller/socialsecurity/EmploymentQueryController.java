package com.neusoft.mid.ec.api.controller.socialsecurity;

import com.neusoft.mid.ec.api.controller.BaseController;
import com.neusoft.mid.ec.api.domain.RequestInfo;
import com.neusoft.mid.ec.api.serviceInterface.socialsecurity.EmploymentQueryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.puras.common.json.Response;
import me.puras.common.json.ResponseHelper;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 * 就业查询业务
 */
@RestController
@RequestMapping("/rs/jy/query")
@Api(value = "人社",description = "就业")
public class EmploymentQueryController  extends BaseController  {

    @Autowired
    EmploymentQueryService employmentQueryService;

	
	private String nameErrorMsg = "姓名不能为空";
	private String idnoErrorMsg = "身份证号不能为空";
	private String subsidyBidCategoryErrorMsg = "补贴大类不能为空";
	private String subsidySmallCategoryErrorMsg = "补贴小类不能为空";
	private String areaCodeErrorMsg = "行政区划编码不能为空";
	private String serviceBigCategoryErrorMsg = "业务类型大类不能为空";
	private String serviceSmallCategoryErrorMsg = "业务类型小类不能为空";

    /**
     * 就业创业证查询
     * @return
     */
    @ApiOperation("就业创业证查询")
    @RequestMapping("/serchJycyz")
    public Response serchJycyz(HttpServletRequest request){

        RequestInfo requestInfo = getRequestInfo(request);
        Map params = new HashMap(2);
        params.put("AAC002",requestInfo.getIdno());
        params.put("AAC003",requestInfo.getName());
//    	if (StringUtils.isBlank(params.get("AAC002")==null?null:params.get("AAC002").toString())) {
//    		response.setCode(1);
//    		response.setDescription(idnoErrorMsg);
//            return response;
//        }
//        if (StringUtils.isBlank(params.get("AAC003")==null?null:params.get("AAC003").toString())) {
//        	response.setCode(1);
//        	response.setDescription(nameErrorMsg);
//            return response;
//        }
    	return employmentQueryService.queryInfo("就业创业证查询", params, "SERCH_JYCYZ", this);
    }
    
    /**
     * 就业登记查询
     * @return
     */
    @ApiOperation("就业登记查询")
    @RequestMapping("/serchJydj")
    public Response serchJydj(HttpServletRequest request){


        RequestInfo requestInfo = getRequestInfo(request);
        Map params = new HashMap(2);
        params.put("AAC002",requestInfo.getIdno());
        params.put("AAC003",requestInfo.getName());

        if(StringUtils.isEmpty(requestInfo.getIdno())){
            return ResponseHelper.createResponse(-9999,"身份证号不能为空");
        }
        if(StringUtils.isEmpty(requestInfo.getName())){
            return ResponseHelper.createResponse(-9999,"姓名不能为空");
        }
    /*	Response<Object> response = new Response<Object>();
    	if (StringUtils.isBlank(params.get("AAC002")==null?null:params.get("AAC002").toString())) {
    		response.setCode(1);
    		response.setDescription(idnoErrorMsg);
            return response;
        }
        if (StringUtils.isBlank(params.get("AAC003")==null?null:params.get("AAC003").toString())) {
        	response.setCode(1);
        	response.setDescription(nameErrorMsg);
            return response;
        }*/
    	return employmentQueryService.queryInfo("就业登记查询", params, "SERCH_JYDJ", this);
    }
    
    /**
     * 失业登记查询
     * @return
     */
    @ApiOperation("失业登记查询")
    @RequestMapping("/serchSydj")
    public Response serchSydj(HttpServletRequest request){

        RequestInfo requestInfo = getRequestInfo(request);
        Map params = new HashMap(2);
        params.put("AAC002",requestInfo.getIdno());
        params.put("AAC003",requestInfo.getName());

        if(StringUtils.isEmpty(requestInfo.getIdno())){
            return ResponseHelper.createResponse(-9999,"身份证号不能为空");
        }
        if(StringUtils.isEmpty(requestInfo.getName())){
            return ResponseHelper.createResponse(-9999,"姓名不能为空");
        }
        /*
    	Response<Object> response = new Response<Object>();
    	if (StringUtils.isBlank(params.get("AAC002")==null?null:params.get("AAC002").toString())) {
    		response.setCode(1);
    		response.setDescription(idnoErrorMsg);
            return response;
        }
        if (StringUtils.isBlank(params.get("AAC003")==null?null:params.get("AAC003").toString())) {
        	response.setCode(1);
        	response.setDescription(nameErrorMsg);
            return response;
        }    */
    	return employmentQueryService.queryInfo("失业登记查询", params, "SERCH_SYDJ", this);
    }
    
    /**
     * 就业技能培训补贴标准查询
     * @return
     */
    @ApiOperation("就业技能培训补贴标准查询")
    @RequestMapping("/queryJyjnpxstandard")
    public Response queryJyjnpxstandard(@RequestBody Map params, HttpServletRequest request){
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
    	return employmentQueryService.queryInfo("就业技能培训补贴标准查询", params, "QUERY_JYJNPXSTANDARD", this);
    }
 
    /**
     * 创业培训补贴标准查询
     * @return
     */
    @ApiOperation("创业培训补贴标准查询")
    @RequestMapping("/queryCypxstandard")
    public Response queryCypxstandard(@RequestBody Map params, HttpServletRequest request){
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
    	return employmentQueryService.queryInfo("创业培训补贴标准查询", params, "QUERY_CYPXSTANDARD", this);
    }
    
    /**
     * 灵活就业社保补贴标准查询
     * @return
     */
    @ApiOperation("灵活就业社保补贴标准查询")
    @RequestMapping("/queryLhjysbbtstandard")
    public Response queryLhjysbbtstandard(@RequestBody Map params, HttpServletRequest request){
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
        return employmentQueryService.queryInfo("灵活就业社保补贴标准查询", params, "QUERY_LHJYSBBTSTANDARD", this);
    }
    
    /**
     * 单位社保补贴标准查询
     * @return
     */
    @ApiOperation("单位社保补贴标准查询")
    @RequestMapping("/queryDwsbbtstandard")
    public Response queryDwsbbtstandard(@RequestBody Map params, HttpServletRequest request){
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
        return employmentQueryService.queryInfo("单位社保补贴标准查询", params, "QUERY_DWSBBTSTANDARD", this);
    }
    
    /**
     * 就业见习补贴标准查询
     * @return
     */
    @ApiOperation("就业见习补贴标准查询")
    @RequestMapping("/queryJyjxstandard")
    public Response queryJyjxstandard(@RequestBody Map params, HttpServletRequest request){
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
        return employmentQueryService.queryInfo("就业见习补贴标准查询", params, "QUERY_JYJXSTANDARD", this);
    }
    
    /**
     * 求职创业补贴标准查询
     * @return
     */
    @ApiOperation("求职创业补贴标准查询")
    @RequestMapping("/queryQzcystandard")
    public Response queryQzcystandard(@RequestBody Map params, HttpServletRequest request){
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
    	return employmentQueryService.queryInfo("求职创业补贴标准查询", params, "QUERY_QZCYSTANDARD", this);
    }
    
    /**
     * 灵活就业社保补贴申请相关政策查询
     * @return
     */
    @ApiOperation("灵活就业社保补贴申请相关政策查询")
    @RequestMapping("/querySbbtpolicy")
    public Response querySbbtpolicy(@RequestBody Map params, HttpServletRequest request){
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
    	return employmentQueryService.queryInfo("灵活就业社保补贴申请相关政策查询", params, "QUERY_SBBTPOLICY", this);
    }
    
    /**
     * 就业困难人员申请相关政策查询
     * @return
     */
    @ApiOperation("就业困难人员申请相关政策查询")
    @RequestMapping("/queryKnrypolicy")
    public Response queryKnrypolicy(@RequestBody Map params, HttpServletRequest request){
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
    	return employmentQueryService.queryInfo("就业困难人员申请相关政策查询", params, "QUERY_KNRYPOLICY", this);
    }
    
    /**
     * 就业技能培训人员报名相关政策查询
     * @return
     */
    @ApiOperation("就业技能培训人员报名相关政策查询")
    @RequestMapping("/queryJnpxpolicy")
    public Response queryJnpxpolicy(@RequestBody Map params, HttpServletRequest request){
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
    	return employmentQueryService.queryInfo("就业技能培训人员报名相关政策查询", params, "QUERY_JNPXPOLICY", this);
    }
    
    /**
     * 个人创业担保贷款申请相关政策查询
     * @return
     */
    @ApiOperation("个人创业担保贷款申请相关政策查询")
    @RequestMapping("/queryDbdkpolicy")
    public Response queryDbdkpolicy(@RequestBody Map params, HttpServletRequest request){
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
    	return employmentQueryService.queryInfo("个人创业担保贷款申请相关政策查询", params, "QUERY_DBDKPOLICY", this);
    }
    
    public Response queryInfo(HttpServletRequest request,String serviceCode){
        RequestInfo requestInfo = getRequestInfo(request);

        Map params = new HashMap(2);
        params.put("AAC002",requestInfo.getIdno());
        params.put("AAC003",requestInfo.getName());

        Response result = employmentQueryService.callEmploymentService(serviceCode, JSONObject.fromObject(params).toString());
        return result;
    }

    /**
     *查询创业证
     * @return
     */
    @ApiOperation("创业证查询")
    @GetMapping("/businessCredentials")
    public Response getBusinessCredentials(HttpServletRequest request){
       return queryInfo(request,"SERCH_JYCYZ");
    }

    /**
     * 就业登陆查询
     * @return
     */
    @ApiOperation("就业登陆查询")
    @GetMapping("/employmentRegistration")
    public Response getEmploymentRegistration(HttpServletRequest request){
        return queryInfo(request,"SERCH_JYDJ");
    }

    /**
     * 失业登记查询
     * @return
     */
    @ApiOperation("失业登记查询")
    @GetMapping("/unemploymentRegistration")
    public Response getUnemploymentRegistration(HttpServletRequest request){
        return queryInfo(request,"SERCH_SYDJ");
    }

    /**
     * 就业困难查询
     * @return
     */
    @ApiOperation("就业困难查询")
    @GetMapping("/employmentHard")
    public Response getEmploymentHard(HttpServletRequest request){
        return queryInfo(request,"SERCH_JYKNRY");
    }


    /**
     * 公益性岗位
     * @return
     */
    @ApiOperation("公益性岗位查询")
    @GetMapping("/publicWelfareJob")
    public Response getPublicWelfareJob(HttpServletRequest request){
        return queryInfo(request,"SERCH_GYXGW");
    }

    /**
     * 享受就业补贴
     * @return
     */
    @ApiOperation("享受就业补贴查询")
    @GetMapping("/employmentSubsidy")
    public Response getEmploymentSubsidy(HttpServletRequest request){
        return queryInfo(request,"SERCH_JYBZZJ");
    }
    /**
     * 2.2.7 创业担保贷款接口
     * @return
     */
    @ApiOperation("创业担保货款查询")
    @GetMapping("/guaranteePayment")
    public Response getGuaranteePayment(HttpServletRequest request){
        return queryInfo(request,"SERCH_CYDBDK");
    }


    /**
     * 高校毕业生接口
     * @return
     */
    @ApiOperation("高校毕业生查询")
    @GetMapping("/graduate")
    public Response getGraduate(HttpServletRequest request){
        return queryInfo(request,"SERCH_JYCYZ");
    }

    /**
     * 2.2.9 就业见习接口
     * @return
     */
    @ApiOperation("就业见习查询")
    @GetMapping("/noviciate")
    public Response getNoviciate(HttpServletRequest request){
        return queryInfo(request,"SERCH_JYJC");
    }

    /**
     *  创业培训接口
     * @return
     */
    @ApiOperation("创业培训查询")
    @GetMapping("/training")
    public Response getTraining(HttpServletRequest request){
        return queryInfo(request,"SERCH_CYPX");
    }

    /**
     *  就业培训接口
     * @return
     */
    @ApiOperation("就业培训查询")
    @GetMapping("/employmentTraining")
    public Response getEmploymentTraining(HttpServletRequest request){
        return queryInfo(request,"SERCH_JYPX");
    }


    /**
     *  2.2.12 劳务派遣接口
     * @return
     */
    @ApiOperation("劳务派遣查询")
    @GetMapping("/laborDispatch")
    public Response getLaborDispatch(HttpServletRequest request){
        return queryInfo(request,"SERCH_LWPQ");
    }
}
