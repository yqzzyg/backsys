package com.neusoft.mid.ec.api.controller.userlog;

import com.alibaba.fastjson.JSON;
import com.neusoft.mid.ec.api.domain.DepartmentDict;
import com.neusoft.mid.ec.api.domain.InterfaceDict;
import com.neusoft.mid.ec.api.domain.UserLog;
import com.neusoft.mid.ec.api.serviceInterface.userlog.UserLogService;
import com.neusoft.mid.ec.api.util.TableNameUtil;
import me.puras.common.json.Response;
import me.puras.common.json.ResponseHelper;
import me.puras.common.util.ClientListSlice;
import me.puras.common.util.ListBounds;
import me.puras.common.util.ListSlice;
import me.puras.common.util.Pagination;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 *  用户操作日志记录
 */
@RestController
@RequestMapping("/userLog")
public class UserLogController {

    private static Logger LOGGEER = Logger.getLogger(UserLogController.class);

    @Autowired
    private UserLogService userLogService;

    /**
     *  查询用户操作日志
     */
    @RequestMapping(value = "/selectUserLog",method = RequestMethod.POST)
    public Response selectUserLog(@RequestBody UserLog params, Pagination pagination) {
        Response<Object> object = new Response<>();
        try {
            LOGGEER.info("查询用户操作日志[selectUserLog]入参：" + params);
            if (0>=pagination.getCurrentPage()) {
                pagination.setCurrentPage(1);
            }
            ListBounds bounds = new ListBounds(pagination.getCurrentPage(), pagination.getPageSize());
            if (null!=params.getStartTime()) {
                params.setTableName(TableNameUtil.getTableName("user_action_log",params.getStartTime()));
            }else if(null!=params.getEndTime()){
                params.setTableName(TableNameUtil.getTableName("user_action_log",params.getEndTime()));
            }else{
                params.setTableName(TableNameUtil.getTableName());
            }
            ListSlice<UserLog> userInfo = userLogService.selectUserLogList(params,bounds);
            LOGGEER.info("[selectUserLog]用户操作日志查询出参："+ JSON.toJSONString(userInfo));
            return ResponseHelper.createSuccessResponse(new ClientListSlice(userInfo, bounds.getOffset(), bounds.getLimit()));
        } catch (Exception e) {
            LOGGEER.error("[selectUserLog]错误日志" + e.getMessage(), e);
            object.setCode(500);
            object.setDescription("内部服务错误");
            object.setLastUpdateTime(System.currentTimeMillis());
        }
        return object;
    }

    //厅局对应字典表
    @RequestMapping(value="/selectDepartmentDict",method = RequestMethod.POST)
    public Response selectDepartmentDict(@RequestBody DepartmentDict params, Pagination pagination) {
        Response<Object> object = new Response<>();
        try {
            LOGGEER.info("查询厅局对应字典表日志[selectDepartmentDict]入参：" + params);
            if (0>=pagination.getCurrentPage()) {
                pagination.setCurrentPage(1);
            }
            ListBounds bounds = new ListBounds(pagination.getCurrentPage(), pagination.getPageSize());
           // params.setUserName(TableNameUtil.getTableName());
            ListSlice<DepartmentDict> userInfo = userLogService.selectDepartmentDict(params,bounds);
            LOGGEER.info("[selectDepartmentDict]查询厅局对应字典表日志查询出参："+ JSON.toJSONString(userInfo));
            return ResponseHelper.createSuccessResponse(new ClientListSlice(userInfo, bounds.getOffset(), bounds.getLimit()));
        } catch (Exception e) {
            LOGGEER.error("[selectDepartmentDict]错误日志" + e.getMessage(), e);
            object.setCode(500);
            object.setDescription("内部服务错误");
            object.setLastUpdateTime(System.currentTimeMillis());
        }
        return object;
    }

    //接口对应字典表
    @RequestMapping(value="/selectInterfaceDict",method = RequestMethod.POST)
    public Response selectInterfaceDict(@RequestBody InterfaceDict params, Pagination pagination) {
        Response<Object> object = new Response<>();
        try {
            LOGGEER.info("查询厅局对应字典表日志[selectDepartmentDict]入参：" + params);
            if (0>=pagination.getCurrentPage()) {
                pagination.setCurrentPage(1);
            }
            ListBounds bounds = new ListBounds(pagination.getCurrentPage(), pagination.getPageSize());
            // params.setUserName(TableNameUtil.getTableName());
          ListSlice<InterfaceDict> userInfo = userLogService.selectInterfaceDict(params,bounds);
            LOGGEER.info("[selectDepartmentDict]查询厅局对应字典表日志查询出参："+ JSON.toJSONString(userInfo));
            return ResponseHelper.createSuccessResponse(new ClientListSlice(userInfo, bounds.getOffset(), bounds.getLimit()));
        } catch (Exception e) {
            LOGGEER.error("[selectDepartmentDict]错误日志" + e.getMessage(), e);
            object.setCode(500);
            object.setDescription("内部服务错误");
            object.setLastUpdateTime(System.currentTimeMillis());
        }
        return object;
    }



    /**
     *  记录日志
     */
    @RequestMapping(value = "/insertLog",method = RequestMethod.POST)
    public Response insertLog(@RequestBody UserLog params) {
        LOGGEER.info("记录用户操作日志[insertLog]入参：" + params);
        Response<Object> object = new Response<>();
        try {
            if (null!=params) {
                userLogService.insertLog(params);
            }
        } catch (Exception e) {
            LOGGEER.error("[insertLog]错误日志" + e.getMessage(), e);
            object.setCode(500);
            object.setDescription("内部服务错误");
            object.setLastUpdateTime(System.currentTimeMillis());
        }
        return object;
    }

    /**
     *  新增厅局字典
     */
    @RequestMapping(value = "/insertDepartmentDict",method = RequestMethod.POST)
    public Response insertDepartmentDict(@RequestBody DepartmentDict params) {
        LOGGEER.info("新增厅局字典[insertDepartmentDict]入参：" + params);
        Response<Object> object = new Response<>();
        try {
            if (null!=params) {
                userLogService.insertDeparment(params);
            }
        } catch (Exception e) {
            LOGGEER.error("[insertDepartmentDict]错误日志" + e.getMessage(), e);
            object.setCode(500);
            object.setDescription("内部服务错误");
            object.setLastUpdateTime(System.currentTimeMillis());
        }
        return object;
    }

    /**
     *  修改厅局字典
     */
    @RequestMapping(value = "/updateDepartmentDict",method = RequestMethod.POST)
    public Response updateDepartmentDict(@RequestBody DepartmentDict params) {
        LOGGEER.info("修改[updateDepartmentDict]入参：" + params);
        Response<Object> object = new Response<>();
        try {
            if (null!=params) {
                userLogService.updateDepartment(params);
            }
        } catch (Exception e) {
            LOGGEER.error("[updateDepartmentDict]错误日志" + e.getMessage(), e);
            object.setCode(500);
            object.setDescription("内部服务错误");
            object.setLastUpdateTime(System.currentTimeMillis());
        }
        return object;
    }

    /**
     *  新增接口字典
     */
    @RequestMapping(value = "/insertInterfaceDict",method = RequestMethod.POST)
    public Response insertInterfaceDict(@RequestBody InterfaceDict params) {
        LOGGEER.info("新增接口[insertInterfaceDict]入参：" + params);
        Response<Object> object = new Response<>();
        try {
            if (null!=params) {
                userLogService.insertInterface(params);
            }
        } catch (Exception e) {
            LOGGEER.error("[insertInterfaceDict]错误日志" + e.getMessage(), e);
            object.setCode(500);
            object.setDescription("内部服务错误");
            object.setLastUpdateTime(System.currentTimeMillis());
        }
        return object;
    }

    /**
     *  修改接口字典
     */
    @RequestMapping(value = "/updateInterfaceDict",method = RequestMethod.POST)
    public Response updateInterfaceDict(@RequestBody InterfaceDict params) {
        LOGGEER.info("修改接口[updateInterfaceDict]入参：" + params);
        Response<Object> object = new Response<>();
        try {
            if (null!=params) {
                userLogService.updateInterface(params);
            }
        } catch (Exception e) {
            LOGGEER.error("[updateInterfaceDict]错误日志" + e.getMessage(), e);
            object.setCode(500);
            object.setDescription("内部服务错误");
            object.setLastUpdateTime(System.currentTimeMillis());
        }
        return object;
    }

    /**
     *  删除接口字典
     */
    @RequestMapping(value = "/deleteInterfaceDict",method = RequestMethod.POST)
    public Response deleteInterfaceDict(@RequestBody Map params) {
        LOGGEER.info("删除接口字典[deleteInterfaceDict]入参：" + params);
        Response<Object> object = new Response<>();
        try {
            userLogService.deleteInterface(params.get("ids").toString());
        } catch (Exception e) {
            LOGGEER.error("[deleteInterfaceDict]错误日志" + e.getMessage(), e);
            object.setCode(500);
            object.setDescription("内部服务错误");
            object.setLastUpdateTime(System.currentTimeMillis());
        }
        return object;
    }

    /**
     *  删除厅局字典
     */
    @RequestMapping(value = "/deleteDepartment",method = RequestMethod.POST)
    public Response deleteDepartment(@RequestBody Map params) {
        LOGGEER.info("删除厅局字典[deleteDepartment]入参：" + params);
        Response<Object> object = new Response<>();
        try {
            userLogService.deleteDepartment(params.get("ids").toString());
        } catch (Exception e) {
            LOGGEER.error("[deleteDepartment]错误日志" + e.getMessage(), e);
            object.setCode(500);
            object.setDescription("内部服务错误");
            object.setLastUpdateTime(System.currentTimeMillis());
        }
        return object;
    }
}
