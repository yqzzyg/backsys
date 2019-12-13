package com.dr.backsys.controller;

import com.dr.backsys.service.QueryAdressService;
import com.dr.backsys.utils.JsonResult;
import com.mysql.cj.jdbc.exceptions.MySQLTimeoutException;
import org.apache.ibatis.exceptions.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



/**
 * @ Description   :  统计用户注册情况
 * @ Author        :  yqz
 * @ CreateDate    :  2019/12/13 9:21
 */
@RestController
@RequestMapping("api/newuser")
public class QueryNewUsersController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private QueryAdressService queryAdressService;


    @GetMapping("/queryNewUsers")
    public JsonResult queryNewUsers(String beginTime,String endTime){
        JsonResult jsonResult = new JsonResult();
        try {
             jsonResult=queryAdressService.queryNewUser(beginTime, endTime);

        }catch (PersistenceException e){
            /*jsonResult.setCode(1);
            jsonResult.setMsg("数据库异常");
            jsonResult.setData(e.getMessage());*/
            logger.error("错误日志"+e.getMessage());
            logger.info("数据库连接超时");
        }
        return jsonResult;
    }
}
