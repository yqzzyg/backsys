package com.neusoft.mid.ec.api.controller.country.organization;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.neusoft.mid.ec.api.controller.BaseController;
import com.neusoft.mid.ec.api.domain.country.CountryOrganization;
import com.neusoft.mid.ec.api.serviceInterface.country.organization.CountryOrganizationService;

import io.swagger.annotations.ApiOperation;
import me.puras.common.json.Response;
import me.puras.common.json.ResponseHelper;
import me.puras.common.util.ClientListSlice;
import me.puras.common.util.ListBounds;
import me.puras.common.util.ListSlice;
import me.puras.common.util.Pagination;

/**
 * @ClassName: CountryOrganizationController
 * @Description: 机构查询
 * @author 蔡旭新
 * @date 2019年10月5日
 */
@RestController
@RequestMapping("/country/organization")
public class CountryOrganizationController extends BaseController {
    @Autowired
    private CountryOrganizationService service;
    @ApiOperation("社会福利院查询接口")
    @RequestMapping(value = "/getShflyInfo", method = RequestMethod.POST)
    public Response getShflyInfo(CountryOrganization organization, Pagination pagination) {
        Response<ListSlice<CountryOrganization>> object = new Response<>();
        try {
            if (StringUtils.isBlank(organization.getAJG004())) {
                logger.error("机构名称不能为空");
                object.setCode(500);
                object.setDescription("请输入机构名称");
                object.setLastUpdateTime(System.currentTimeMillis());
                return object;
            }
            if (0 >= pagination.getCurrentPage()) {
                pagination.setCurrentPage(1);
            }
            ListBounds bounds = new ListBounds(pagination.getCurrentPage(), pagination.getPageSize());
            organization.setTableName("jg01");
            ListSlice<CountryOrganization> ls = service.getShflyInfoList(organization, bounds);
            logger.info("社会福利院查询出参："+ JSON.toJSONString(ls));
            return ResponseHelper.createSuccessResponse(new ClientListSlice(ls, bounds.getOffset(), bounds.getLimit()));
        } catch (Exception e) {
            logger.error("社会福利院查询接口异常" + e.getMessage(), e);
            object.setCode(500);
            object.setDescription("内部服务错误");
            object.setLastUpdateTime(System.currentTimeMillis());
        }
        return object;
    }
    
    @ApiOperation("慈善机构查询接口")
    @RequestMapping(value = "/getCsjgInfo", method = RequestMethod.POST)
    public Response getCsjgInfo(CountryOrganization organization, Pagination pagination) {
        Response<ListSlice<CountryOrganization>> object = new Response<>();
        try {
            if (StringUtils.isBlank(organization.getAJG004())) {
                logger.error("机构名称不能为空");
                object.setCode(500);
                object.setDescription("请输入机构名称");
                object.setLastUpdateTime(System.currentTimeMillis());
                return object;
            }
            if (0 >= pagination.getCurrentPage()) {
                pagination.setCurrentPage(1);
            }
            ListBounds bounds = new ListBounds(pagination.getCurrentPage(), pagination.getPageSize());
            organization.setTableName("jg02");
            ListSlice<CountryOrganization> ls = service.getCsjgInfo(organization, bounds);
            logger.info("慈善机构查询出参："+ JSON.toJSONString(ls));
            return ResponseHelper.createSuccessResponse(new ClientListSlice(ls, bounds.getOffset(), bounds.getLimit()));
        } catch (Exception e) {
            logger.error("慈善机构查询接口异常" + e.getMessage(), e);
            object.setCode(500);
            object.setDescription("内部服务错误");
            object.setLastUpdateTime(System.currentTimeMillis());
        }
        return object;
    }
    
    @ApiOperation("艺术馆查询接口")
    @RequestMapping(value = "/getYsgInfo", method = RequestMethod.POST)
    public Response getYsgInfo(CountryOrganization organization, Pagination pagination) {
        Response<ListSlice<CountryOrganization>> object = new Response<>();
        try {
            if (StringUtils.isBlank(organization.getAJG004())) {
                logger.error("机构名称不能为空");
                object.setCode(500);
                object.setDescription("请输入机构名称");
                object.setLastUpdateTime(System.currentTimeMillis());
                return object;
            }
            if (0 >= pagination.getCurrentPage()) {
                pagination.setCurrentPage(1);
            }
            ListBounds bounds = new ListBounds(pagination.getCurrentPage(), pagination.getPageSize());
            organization.setTableName("jg03");
            ListSlice<CountryOrganization> ls = service.getYsgInfo(organization, bounds);
            logger.info("艺术馆查询出参："+ JSON.toJSONString(ls));
            return ResponseHelper.createSuccessResponse(new ClientListSlice(ls, bounds.getOffset(), bounds.getLimit()));
        } catch (Exception e) {
            logger.error("艺术馆查询接口异常" + e.getMessage(), e);
            object.setCode(500);
            object.setDescription("内部服务错误");
            object.setLastUpdateTime(System.currentTimeMillis());
        }
        return object;
    }
    
    @ApiOperation("体育馆查询接口")
    @RequestMapping(value = "/getTygInfo", method = RequestMethod.POST)
    public Response getTygInfo(CountryOrganization organization, Pagination pagination) {
        Response<ListSlice<CountryOrganization>> object = new Response<>();
        try {
            if (StringUtils.isBlank(organization.getAJG004())) {
                logger.error("机构名称不能为空");
                object.setCode(500);
                object.setDescription("请输入机构名称");
                object.setLastUpdateTime(System.currentTimeMillis());
                return object;
            }
            if (0 >= pagination.getCurrentPage()) {
                pagination.setCurrentPage(1);
            }
            ListBounds bounds = new ListBounds(pagination.getCurrentPage(), pagination.getPageSize());
            organization.setTableName("jg04");
            ListSlice<CountryOrganization> ls = service.getTygInfo(organization, bounds);
            logger.info("体育馆查询出参："+ JSON.toJSONString(ls));
            return ResponseHelper.createSuccessResponse(new ClientListSlice(ls, bounds.getOffset(), bounds.getLimit()));
        } catch (Exception e) {
            logger.error("体育馆查询接口异常" + e.getMessage(), e);
            object.setCode(500);
            object.setDescription("内部服务错误");
            object.setLastUpdateTime(System.currentTimeMillis());
        }
        return object;
    }
    
    @ApiOperation("档案馆查询接口")
    @RequestMapping(value = "/getDagInfo", method = RequestMethod.POST)
    public Response getDagInfo(CountryOrganization organization, Pagination pagination) {
        Response<ListSlice<CountryOrganization>> object = new Response<>();
        try {
            if (StringUtils.isBlank(organization.getAJG004())) {
                logger.error("机构名称不能为空");
                object.setCode(500);
                object.setDescription("请输入机构名称");
                object.setLastUpdateTime(System.currentTimeMillis());
                return object;
            }
            if (0 >= pagination.getCurrentPage()) {
                pagination.setCurrentPage(1);
            }
            ListBounds bounds = new ListBounds(pagination.getCurrentPage(), pagination.getPageSize());
            organization.setTableName("jg05");
            ListSlice<CountryOrganization> ls = service.getDagInfo(organization, bounds);
            logger.info("档案馆查询出参："+ JSON.toJSONString(ls));
            return ResponseHelper.createSuccessResponse(new ClientListSlice(ls, bounds.getOffset(), bounds.getLimit()));
        } catch (Exception e) {
            logger.error("档案馆查询接口异常" + e.getMessage(), e);
            object.setCode(500);
            object.setDescription("内部服务错误");
            object.setLastUpdateTime(System.currentTimeMillis());
        }
        return object;
    }
    
    @ApiOperation("博物馆查询接口")
    @RequestMapping(value = "/getBwgInfo", method = RequestMethod.POST)
    public Response getBwgInfo(CountryOrganization organization, Pagination pagination) {
        Response<ListSlice<CountryOrganization>> object = new Response<>();
        try {
            if (StringUtils.isBlank(organization.getAJG004())) {
                logger.error("机构名称不能为空");
                object.setCode(500);
                object.setDescription("请输入机构名称");
                object.setLastUpdateTime(System.currentTimeMillis());
                return object;
            }
            if (0 >= pagination.getCurrentPage()) {
                pagination.setCurrentPage(1);
            }
            ListBounds bounds = new ListBounds(pagination.getCurrentPage(), pagination.getPageSize());
            organization.setTableName("jg06");
            ListSlice<CountryOrganization> ls = service.getBwgInfo(organization, bounds);
            logger.info("博物馆查询出参："+ JSON.toJSONString(ls));
            return ResponseHelper.createSuccessResponse(new ClientListSlice(ls, bounds.getOffset(), bounds.getLimit()));
        } catch (Exception e) {
            logger.error("博物馆查询接口异常" + e.getMessage(), e);
            object.setCode(500);
            object.setDescription("内部服务错误");
            object.setLastUpdateTime(System.currentTimeMillis());
        }
        return object;
    }
    
    @ApiOperation("纪念馆查询接口")
    @RequestMapping(value = "/getJngInfo", method = RequestMethod.POST)
    public Response getJngInfo(CountryOrganization organization, Pagination pagination) {
        Response<ListSlice<CountryOrganization>> object = new Response<>();
        try {
            if (StringUtils.isBlank(organization.getAJG004())) {
                logger.error("机构名称不能为空");
                object.setCode(500);
                object.setDescription("请输入机构名称");
                object.setLastUpdateTime(System.currentTimeMillis());
                return object;
            }
            if (0 >= pagination.getCurrentPage()) {
                pagination.setCurrentPage(1);
            }
            ListBounds bounds = new ListBounds(pagination.getCurrentPage(), pagination.getPageSize());
            organization.setTableName("jg07");
            ListSlice<CountryOrganization> ls = service.getJngInfo(organization, bounds);
            logger.info("纪念馆查询出参："+ JSON.toJSONString(ls));
            return ResponseHelper.createSuccessResponse(new ClientListSlice(ls, bounds.getOffset(), bounds.getLimit()));
        } catch (Exception e) {
            logger.error("纪念馆查询接口异常" + e.getMessage(), e);
            object.setCode(500);
            object.setDescription("内部服务错误");
            object.setLastUpdateTime(System.currentTimeMillis());
        }
        return object;
    }
    
    @ApiOperation("体检机构查询接口")
    @RequestMapping(value = "/getTjjgInfo", method = RequestMethod.POST)
    public Response getTjjgInfo(CountryOrganization organization, Pagination pagination) {
        Response<ListSlice<CountryOrganization>> object = new Response<>();
        try {
            if (StringUtils.isBlank(organization.getAJG004())) {
                logger.error("机构名称不能为空");
                object.setCode(500);
                object.setDescription("请输入机构名称");
                object.setLastUpdateTime(System.currentTimeMillis());
                return object;
            }
            if (0 >= pagination.getCurrentPage()) {
                pagination.setCurrentPage(1);
            }
            ListBounds bounds = new ListBounds(pagination.getCurrentPage(), pagination.getPageSize());
            organization.setTableName("jg08");
            ListSlice<CountryOrganization> ls = service.getTjjgInfo(organization, bounds);
            logger.info("体检机构查询出参："+ JSON.toJSONString(ls));
            return ResponseHelper.createSuccessResponse(new ClientListSlice(ls, bounds.getOffset(), bounds.getLimit()));
        } catch (Exception e) {
            logger.error("体检机构查询接口异常" + e.getMessage(), e);
            object.setCode(500);
            object.setDescription("内部服务错误");
            object.setLastUpdateTime(System.currentTimeMillis());
        }
        return object;
    }
    
    @ApiOperation("文物保护单位查询接口")
    @RequestMapping(value = "/getWwbhdwInfo", method = RequestMethod.POST)
    public Response getWwbhdwInfo(CountryOrganization organization, Pagination pagination) {
        Response<ListSlice<CountryOrganization>> object = new Response<>();
        try {
            if (StringUtils.isBlank(organization.getAJG004())) {
                logger.error("机构名称不能为空");
                object.setCode(500);
                object.setDescription("请输入机构名称");
                object.setLastUpdateTime(System.currentTimeMillis());
                return object;
            }
            if (0 >= pagination.getCurrentPage()) {
                pagination.setCurrentPage(1);
            }
            ListBounds bounds = new ListBounds(pagination.getCurrentPage(), pagination.getPageSize());
            organization.setTableName("jg09");
            ListSlice<CountryOrganization> ls = service.getWwbhdwInfo(organization, bounds);
            logger.info("文物保护单位查询出参："+ JSON.toJSONString(ls));
            return ResponseHelper.createSuccessResponse(new ClientListSlice(ls, bounds.getOffset(), bounds.getLimit()));
        } catch (Exception e) {
            logger.error("文物保护单位查询接口异常" + e.getMessage(), e);
            object.setCode(500);
            object.setDescription("内部服务错误");
            object.setLastUpdateTime(System.currentTimeMillis());
        }
        return object;
    }
    
    @ApiOperation("公证机构查询接口")
    @RequestMapping(value = "/getGzjgInfo", method = RequestMethod.POST)
    public Response getGzjgInfo(CountryOrganization organization, Pagination pagination) {
        Response<ListSlice<CountryOrganization>> object = new Response<>();
        try {
            if (StringUtils.isBlank(organization.getAJG004())) {
                logger.error("机构名称不能为空");
                object.setCode(500);
                object.setDescription("请输入机构名称");
                object.setLastUpdateTime(System.currentTimeMillis());
                return object;
            }
            if (0 >= pagination.getCurrentPage()) {
                pagination.setCurrentPage(1);
            }
            ListBounds bounds = new ListBounds(pagination.getCurrentPage(), pagination.getPageSize());
            organization.setTableName("jg10");
            ListSlice<CountryOrganization> ls = service.getGzjgInfo(organization, bounds);
            logger.info("公证机构查询出参："+ JSON.toJSONString(ls));
            return ResponseHelper.createSuccessResponse(new ClientListSlice(ls, bounds.getOffset(), bounds.getLimit()));
        } catch (Exception e) {
            logger.error("公证机构查询接口异常" + e.getMessage(), e);
            object.setCode(500);
            object.setDescription("内部服务错误");
            object.setLastUpdateTime(System.currentTimeMillis());
        }
        return object;
    }
    
    @ApiOperation("图书馆查询接口")
    @RequestMapping(value = "/getTsgInfo", method = RequestMethod.POST)
    public Response getTsgInfo(CountryOrganization organization, Pagination pagination) {
        Response<ListSlice<CountryOrganization>> object = new Response<>();
        try {
            if (StringUtils.isBlank(organization.getAJG004())) {
                logger.error("机构名称不能为空");
                object.setCode(500);
                object.setDescription("请输入机构名称");
                object.setLastUpdateTime(System.currentTimeMillis());
                return object;
            }
            if (0 >= pagination.getCurrentPage()) {
                pagination.setCurrentPage(1);
            }
            ListBounds bounds = new ListBounds(pagination.getCurrentPage(), pagination.getPageSize());
            organization.setTableName("jg11");
            ListSlice<CountryOrganization> ls = service.getTsgInfo(organization, bounds);
            logger.info("图书馆查询出参："+ JSON.toJSONString(ls));
            return ResponseHelper.createSuccessResponse(new ClientListSlice(ls, bounds.getOffset(), bounds.getLimit()));
        } catch (Exception e) {
            logger.error("图书馆查询接口异常" + e.getMessage(), e);
            object.setCode(500);
            object.setDescription("内部服务错误");
            object.setLastUpdateTime(System.currentTimeMillis());
        }
        return object;
    }
    
    @ApiOperation("预防接种服务机构查询接口")
    @RequestMapping(value = "/getYfjzfwjgInfo", method = RequestMethod.POST)
    public Response getYfjzfwjgInfo(CountryOrganization organization, Pagination pagination) {
        Response<ListSlice<CountryOrganization>> object = new Response<>();
        try {
            if (StringUtils.isBlank(organization.getAJG004())) {
                logger.error("机构名称不能为空");
                object.setCode(500);
                object.setDescription("请输入机构名称");
                object.setLastUpdateTime(System.currentTimeMillis());
                return object;
            }
            if (0 >= pagination.getCurrentPage()) {
                pagination.setCurrentPage(1);
            }
            ListBounds bounds = new ListBounds(pagination.getCurrentPage(), pagination.getPageSize());
            organization.setTableName("jg12");
            ListSlice<CountryOrganization> ls=service.getYfjzfwjgInfo(organization, bounds);
            logger.info("预防接种服务机构查询出参："+ JSON.toJSONString(ls));
            return ResponseHelper.createSuccessResponse(new ClientListSlice(ls, bounds.getOffset(), bounds.getLimit()));
        } catch (Exception e) {
            logger.error("预防接种服务机构查询接口异常" + e.getMessage(), e);
            object.setCode(500);
            object.setDescription("内部服务错误");
            object.setLastUpdateTime(System.currentTimeMillis());
        }
        return object;
    }
    
    @ApiOperation("医疗机构查询接口")
    @RequestMapping(value = "/getYljgInfo", method = RequestMethod.POST)
    public Response getYljgInfo(CountryOrganization organization, Pagination pagination) {
        Response<ListSlice<CountryOrganization>> object = new Response<>();
        try {
            if (StringUtils.isBlank(organization.getAJG004())) {
                logger.error("机构名称不能为空");
                object.setCode(500);
                object.setDescription("请输入机构名称");
                object.setLastUpdateTime(System.currentTimeMillis());
                return object;
            }
            if (0 >= pagination.getCurrentPage()) {
                pagination.setCurrentPage(1);
            }
            ListBounds bounds = new ListBounds(pagination.getCurrentPage(), pagination.getPageSize());
            organization.setTableName("jg13");
            ListSlice<CountryOrganization> ls=service.getYljgInfo(organization, bounds);
            logger.info("医疗机构查询出参："+ JSON.toJSONString(ls));
            return ResponseHelper.createSuccessResponse(new ClientListSlice(ls, bounds.getOffset(), bounds.getLimit()));
        } catch (Exception e) {
            logger.error("医疗机构查询接口异常" + e.getMessage(), e);
            object.setCode(500);
            object.setDescription("内部服务错误");
            object.setLastUpdateTime(System.currentTimeMillis());
        }
        return object;
    }
    
    @ApiOperation("殡仪馆查询接口")
    @RequestMapping(value = "/getBygcxInfo", method = RequestMethod.POST)
    public Response getBygcxInfo(CountryOrganization organization, Pagination pagination) {
        Response<ListSlice<CountryOrganization>> object = new Response<>();
        try {
            if (StringUtils.isBlank(organization.getAJG004())) {
                logger.error("机构名称不能为空");
                object.setCode(500);
                object.setDescription("请输入机构名称");
                object.setLastUpdateTime(System.currentTimeMillis());
                return object;
            }
            if (0 >= pagination.getCurrentPage()) {
                pagination.setCurrentPage(1);
            }
            ListBounds bounds = new ListBounds(pagination.getCurrentPage(), pagination.getPageSize());
            organization.setTableName("jg14");
            ListSlice<CountryOrganization> ls=service.getBygcxInfo(organization, bounds);
            logger.info("殡仪馆查询出参："+ JSON.toJSONString(ls));
            return ResponseHelper.createSuccessResponse(new ClientListSlice(ls, bounds.getOffset(), bounds.getLimit()));
        } catch (Exception e) {
            logger.error("殡仪馆查询接口异常" + e.getMessage(), e);
            object.setCode(500);
            object.setDescription("内部服务错误");
            object.setLastUpdateTime(System.currentTimeMillis());
        }
        return object;
    }
    
    @ApiOperation("经营性公墓查询接口")
    @RequestMapping(value = "/getJyxgmcxInfo", method = RequestMethod.POST)
    public Response getJyxgmcxInfo(CountryOrganization organization, Pagination pagination) {
        Response<ListSlice<CountryOrganization>> object = new Response<>();
        try {
            if (StringUtils.isBlank(organization.getAJG004())) {
                logger.error("机构名称不能为空");
                object.setCode(500);
                object.setDescription("请输入机构名称");
                object.setLastUpdateTime(System.currentTimeMillis());
                return object;
            }
            if (0 >= pagination.getCurrentPage()) {
                pagination.setCurrentPage(1);
            }
            ListBounds bounds = new ListBounds(pagination.getCurrentPage(), pagination.getPageSize());
            organization.setTableName("jg15");
            ListSlice<CountryOrganization> ls=service.getJyxgmcxInfo(organization, bounds);
            logger.info("经营性公墓查询出参："+ JSON.toJSONString(ls));
            return ResponseHelper.createSuccessResponse(new ClientListSlice(ls, bounds.getOffset(), bounds.getLimit()));
        } catch (Exception e) {
            logger.error("经营性公墓查询接口异常" + e.getMessage(), e);
            object.setCode(500);
            object.setDescription("内部服务错误");
            object.setLastUpdateTime(System.currentTimeMillis());
        }
        return object;
    }
    
    @ApiOperation("办税服务大厅查询接口")
    @RequestMapping(value = "/getBsfwdtcxInfo", method = RequestMethod.POST)
    public Response getBsfwdtcxInfo(CountryOrganization organization, Pagination pagination) {
        Response<ListSlice<CountryOrganization>> object = new Response<>();
        try {
            if (StringUtils.isBlank(organization.getAJG004())) {
                logger.error("机构名称不能为空");
                object.setCode(500);
                object.setDescription("请输入机构名称");
                object.setLastUpdateTime(System.currentTimeMillis());
                return object;
            }
            if (0 >= pagination.getCurrentPage()) {
                pagination.setCurrentPage(1);
            }
            ListBounds bounds = new ListBounds(pagination.getCurrentPage(), pagination.getPageSize());
            organization.setTableName("jg16");
            ListSlice<CountryOrganization> ls=service.getBsfwdtcxInfo(organization, bounds);
            logger.info("办税服务大厅查询出参："+ JSON.toJSONString(ls));
            return ResponseHelper.createSuccessResponse(new ClientListSlice(ls, bounds.getOffset(), bounds.getLimit()));
        } catch (Exception e) {
            logger.error("办税服务大厅查询接口异常" + e.getMessage(), e);
            object.setCode(500);
            object.setDescription("内部服务错误");
            object.setLastUpdateTime(System.currentTimeMillis());
        }
        return object;
    }
    @SuppressWarnings("rawtypes")
	@ApiOperation("公交卡充值网点查询接口")
    @RequestMapping(value = "/getGjkczwdInfo", method = RequestMethod.POST)
    public Response getGjkczwdInfo(CountryOrganization organization, Pagination pagination) {
        Response<ListSlice<CountryOrganization>> object = new Response<>();
        try {
            if (StringUtils.isBlank(organization.getAJG004())) {
                logger.error("网点名称不能为空");
                object.setCode(500);
                object.setDescription("请输入网点名称");
                object.setLastUpdateTime(System.currentTimeMillis());
                return object;
            }
            if (0 >= pagination.getCurrentPage()) {
                pagination.setCurrentPage(1);
            }
            ListBounds bounds = new ListBounds(pagination.getCurrentPage(), pagination.getPageSize());
            organization.setTableName("jg17");
            ListSlice<CountryOrganization> ls=service.getGjkczwdInfo(organization, bounds);
            logger.info("公交卡充值网点查询出参："+ JSON.toJSONString(ls));
            return ResponseHelper.createSuccessResponse(new ClientListSlice(ls, bounds.getOffset(), bounds.getLimit()));
        } catch (Exception e) {
            logger.error("公交卡充值网点查询接口异常" + e.getMessage(), e);
            object.setCode(500);
            object.setDescription("内部服务错误");
            object.setLastUpdateTime(System.currentTimeMillis());
        }
        return object;
    }
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@ApiOperation("河南省公园信息查询接口")
    @RequestMapping(value = "/getHnsgyxxInfo", method = RequestMethod.POST)
    public Response getHnsgyxxInfo(CountryOrganization organization, Pagination pagination) {
        Response<ListSlice<CountryOrganization>> object = new Response<>();
        try {
            if (StringUtils.isBlank(organization.getAJG004())) {
                logger.error("公园名称不能为空");
                object.setCode(500);
                object.setDescription("请输入公园名称");
                object.setLastUpdateTime(System.currentTimeMillis());
                return object;
            }
            if (0 >= pagination.getCurrentPage()) {
                pagination.setCurrentPage(1);
            }
            ListBounds bounds = new ListBounds(pagination.getCurrentPage(), pagination.getPageSize());
            organization.setTableName("jg18");
            ListSlice<CountryOrganization> ls=service.getHnsgyxxInfo(organization, bounds);
            logger.info("河南省公园信息查询出参："+ JSON.toJSONString(ls));
            return ResponseHelper.createSuccessResponse(new ClientListSlice(ls, bounds.getOffset(), bounds.getLimit()));
        } catch (Exception e) {
            logger.error("河南省公园信息查询接口异常" + e.getMessage(), e);
            object.setCode(500);
            object.setDescription("内部服务错误");
            object.setLastUpdateTime(System.currentTimeMillis());
        }
        return object;
    }
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation("就业服务机构查询接口")
    @RequestMapping(value = "/getJyffjgInfo", method = RequestMethod.POST)
    public Response getJyffjgInfo(CountryOrganization organization, Pagination pagination) {
        Response<ListSlice<CountryOrganization>> object = new Response<>();
        try {
            if (StringUtils.isBlank(organization.getAJG004())) {
                logger.error("机构名称不能为空");
                object.setCode(500);
                object.setDescription("请输入机构名称");
                object.setLastUpdateTime(System.currentTimeMillis());
                return object;
            }
            if (0 >= pagination.getCurrentPage()) {
                pagination.setCurrentPage(1);
            }
            ListBounds bounds = new ListBounds(pagination.getCurrentPage(), pagination.getPageSize());
            organization.setTableName("jg19");
            ListSlice<CountryOrganization> ls=service.getJyffjgInfo(organization, bounds);
            logger.info("就业服务机构查询出参："+ JSON.toJSONString(ls));
            return ResponseHelper.createSuccessResponse(new ClientListSlice(ls, bounds.getOffset(), bounds.getLimit()));
        } catch (Exception e) {
            logger.error("就业服务机构查询接口异常" + e.getMessage(), e);
            object.setCode(500);
            object.setDescription("内部服务错误");
            object.setLastUpdateTime(System.currentTimeMillis());
        }
        return object;
    }
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@ApiOperation("社会保险基金管理中心服务大厅查询接口")
    @RequestMapping(value = "/getShbxjjglzxffdtInfo", method = RequestMethod.POST)
    public Response getShbxjjglzxffdtInfo(CountryOrganization organization, Pagination pagination) {
        Response<ListSlice<CountryOrganization>> object = new Response<>();
        try {
            if (StringUtils.isBlank(organization.getAJG004())) {
                logger.error("机构名称不能为空");
                object.setCode(500);
                object.setDescription("请输入机构名称");
                object.setLastUpdateTime(System.currentTimeMillis());
                return object;
            }
            if (0 >= pagination.getCurrentPage()) {
                pagination.setCurrentPage(1);
            }
            ListBounds bounds = new ListBounds(pagination.getCurrentPage(), pagination.getPageSize());
            organization.setTableName("jg20");
            ListSlice<CountryOrganization> ls=service.getShbxjjglzxffdtInfo(organization, bounds);
            logger.info("社会保险基金管理中心服务大厅查询出参："+ JSON.toJSONString(ls));
            return ResponseHelper.createSuccessResponse(new ClientListSlice(ls, bounds.getOffset(), bounds.getLimit()));
        } catch (Exception e) {
            logger.error("社会保险基金管理中心服务大厅查询接口异常" + e.getMessage(), e);
            object.setCode(500);
            object.setDescription("内部服务错误");
            object.setLastUpdateTime(System.currentTimeMillis());
        }
        return object;
    }
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation("省直公积金业务办理网点查询接口")
    @RequestMapping(value = "/getSzgjjywblwdInfo", method = RequestMethod.POST)
    public Response getSzgjjywblwdInfo(CountryOrganization organization, Pagination pagination) {
        Response<ListSlice<CountryOrganization>> object = new Response<>();
        try {
            if (StringUtils.isBlank(organization.getAJG004())) {
                logger.error("网点名称不能为空");
                object.setCode(500);
                object.setDescription("请输入网点名称");
                object.setLastUpdateTime(System.currentTimeMillis());
                return object;
            }
            if (0 >= pagination.getCurrentPage()) {
                pagination.setCurrentPage(1);
            }
            ListBounds bounds = new ListBounds(pagination.getCurrentPage(), pagination.getPageSize());
            organization.setTableName("jg21");
            ListSlice<CountryOrganization> ls=service.getSzgjjywblwdInfo(organization, bounds);
            logger.info("省直公积金业务办理网点查询出参："+ JSON.toJSONString(ls));
            return ResponseHelper.createSuccessResponse(new ClientListSlice(ls, bounds.getOffset(), bounds.getLimit()));
        } catch (Exception e) {
            logger.error("省直公积金业务办理网点查询接口异常" + e.getMessage(), e);
            object.setCode(500);
            object.setDescription("内部服务错误");
            object.setLastUpdateTime(System.currentTimeMillis());
        }
        return object;
    }
    @ApiOperation("中石化加油站查询接口")
    @RequestMapping(value = "/getZshjyzInfo", method = RequestMethod.POST)
    public Response getZshjyzInfo(CountryOrganization organization, Pagination pagination) {
        Response<ListSlice<CountryOrganization>> object = new Response<>();
        try {
            if (StringUtils.isBlank(organization.getAJG004())) {
                logger.error("加油站名称不能为空");
                object.setCode(500);
                object.setDescription("请输入加油站名称");
                object.setLastUpdateTime(System.currentTimeMillis());
                return object;
            }
            if (0 >= pagination.getCurrentPage()) {
                pagination.setCurrentPage(1);
            }
            ListBounds bounds = new ListBounds(pagination.getCurrentPage(), pagination.getPageSize());
            organization.setTableName("jg22");
            ListSlice<CountryOrganization> ls=service.getZshjyzInfo(organization, bounds);
            logger.info("中石化加油站查询出参："+ JSON.toJSONString(ls));
            return ResponseHelper.createSuccessResponse(new ClientListSlice(ls, bounds.getOffset(), bounds.getLimit()));
        } catch (Exception e) {
            logger.error("中石化加油站查询接口异常" + e.getMessage(), e);
            object.setCode(500);
            object.setDescription("内部服务错误");
            object.setLastUpdateTime(System.currentTimeMillis());
        }
        return object;
    }
    @ApiOperation("医保定点药店查询接口")
    @RequestMapping(value = "/getYbddydInfo", method = RequestMethod.POST)
    public Response getYbddydInfo(CountryOrganization organization, Pagination pagination) {
        Response<ListSlice<CountryOrganization>> object = new Response<>();
        try {
            if (StringUtils.isBlank(organization.getAJG004())) {
                logger.error("药店名称不能为空");
                object.setCode(500);
                object.setDescription("请输入药店名称");
                object.setLastUpdateTime(System.currentTimeMillis());
                return object;
            }
            if (0 >= pagination.getCurrentPage()) {
                pagination.setCurrentPage(1);
            }
            ListBounds bounds = new ListBounds(pagination.getCurrentPage(), pagination.getPageSize());
            organization.setTableName("jg23");
            ListSlice<CountryOrganization> ls=service.getYbddydInfo(organization, bounds);
            logger.info("医保定点药店查询出参："+ JSON.toJSONString(ls));
            return ResponseHelper.createSuccessResponse(new ClientListSlice(ls, bounds.getOffset(), bounds.getLimit()));
        } catch (Exception e) {
            logger.error("医保定点药店查询接口异常" + e.getMessage(), e);
            object.setCode(500);
            object.setDescription("内部服务错误");
            object.setLastUpdateTime(System.currentTimeMillis());
        }
        return object;
    }
    @ApiOperation("医保定点医院查询接口")
    @RequestMapping(value = "/getYbddyyInfo", method = RequestMethod.POST)
    public Response getYbddyyInfo(CountryOrganization organization, Pagination pagination) {
        Response<ListSlice<CountryOrganization>> object = new Response<>();
        try {
            if (StringUtils.isBlank(organization.getAJG004())) {
                logger.error("医院名称不能为空");
                object.setCode(500);
                object.setDescription("请输入医院名称");
                object.setLastUpdateTime(System.currentTimeMillis());
                return object;
            }
            if (0 >= pagination.getCurrentPage()) {
                pagination.setCurrentPage(1);
            }
            ListBounds bounds = new ListBounds(pagination.getCurrentPage(), pagination.getPageSize());
            organization.setTableName("jg24");
            ListSlice<CountryOrganization> ls=service.getYbddyyInfo(organization, bounds);
            logger.info("医保定点医院查询出参："+ JSON.toJSONString(ls));
            return ResponseHelper.createSuccessResponse(new ClientListSlice(ls, bounds.getOffset(), bounds.getLimit()));
        } catch (Exception e) {
            logger.error("医保定点医院查询接口异常" + e.getMessage(), e);
            object.setCode(500);
            object.setDescription("内部服务错误");
            object.setLastUpdateTime(System.currentTimeMillis());
        }
        return object;
    }
    @ApiOperation("医保定点乡镇卫生院查询接口")
    @RequestMapping(value = "/getXzwsyInfo", method = RequestMethod.POST)
    public Response getXzwsyInfo(CountryOrganization organization, Pagination pagination) {
        Response<ListSlice<CountryOrganization>> object = new Response<>();
        try {
            if (StringUtils.isBlank(organization.getAJG004())) {
                logger.error("卫生院名称不能为空");
                object.setCode(500);
                object.setDescription("请输入卫生院名称");
                object.setLastUpdateTime(System.currentTimeMillis());
                return object;
            }
            if (0 >= pagination.getCurrentPage()) {
                pagination.setCurrentPage(1);
            }
            ListBounds bounds = new ListBounds(pagination.getCurrentPage(), pagination.getPageSize());
            organization.setTableName("jg25");
            ListSlice<CountryOrganization> ls=service.getXzwsyInfo(organization, bounds);
            logger.info("医保定点乡镇卫生院查询出参："+ JSON.toJSONString(ls));
            return ResponseHelper.createSuccessResponse(new ClientListSlice(ls, bounds.getOffset(), bounds.getLimit()));
        } catch (Exception e) {
            logger.error("医保定点乡镇卫生院查询接口异常" + e.getMessage(), e);
            object.setCode(500);
            object.setDescription("内部服务错误");
            object.setLastUpdateTime(System.currentTimeMillis());
        }
        return object;
    }
    @ApiOperation("医保定点社区门诊查询接口")
    @RequestMapping(value = "/getSqmzInfo", method = RequestMethod.POST)
    public Response getSqmzInfo(CountryOrganization organization, Pagination pagination) {
        Response<ListSlice<CountryOrganization>> object = new Response<>();
        try {
            if (StringUtils.isBlank(organization.getAJG004())) {
                logger.error("门诊名称不能为空");
                object.setCode(500);
                object.setDescription("请输入门诊名称");
                object.setLastUpdateTime(System.currentTimeMillis());
                return object;
            }
            if (0 >= pagination.getCurrentPage()) {
                pagination.setCurrentPage(1);
            }
            ListBounds bounds = new ListBounds(pagination.getCurrentPage(), pagination.getPageSize());
            organization.setTableName("jg26");
            ListSlice<CountryOrganization> ls=service.getSqmzInfo(organization, bounds);
            logger.info("医保定点社区门诊查询出参："+ JSON.toJSONString(ls));
            return ResponseHelper.createSuccessResponse(new ClientListSlice(ls, bounds.getOffset(), bounds.getLimit()));
        } catch (Exception e) {
            logger.error("医保定点社区门诊查询接口异常" + e.getMessage(), e);
            object.setCode(500);
            object.setDescription("内部服务错误");
            object.setLastUpdateTime(System.currentTimeMillis());
        }
        return object;
    }
    @ApiOperation("医保定点村卫生院查询接口")
    @RequestMapping(value = "/getCwsyInfo", method = RequestMethod.POST)
    public Response getCwsyInfo(CountryOrganization organization, Pagination pagination) {
        Response<ListSlice<CountryOrganization>> object = new Response<>();
        try {
            if (StringUtils.isBlank(organization.getAJG004())) {
                logger.error("卫生院名称不能为空");
                object.setCode(500);
                object.setDescription("请输入卫生院名称");
                object.setLastUpdateTime(System.currentTimeMillis());
                return object;
            }
            if (0 >= pagination.getCurrentPage()) {
                pagination.setCurrentPage(1);
            }
            ListBounds bounds = new ListBounds(pagination.getCurrentPage(), pagination.getPageSize());
            organization.setTableName("jg27");
            ListSlice<CountryOrganization> ls=service.getCwsyInfo(organization, bounds);
            logger.info("医保定点村卫生院查询出参："+ JSON.toJSONString(ls));
            return ResponseHelper.createSuccessResponse(new ClientListSlice(ls, bounds.getOffset(), bounds.getLimit()));
        } catch (Exception e) {
            logger.error("医保定点村卫生院查询接口异常" + e.getMessage(), e);
            object.setCode(500);
            object.setDescription("内部服务错误");
            object.setLastUpdateTime(System.currentTimeMillis());
        }
        return object;
    }
    
}
