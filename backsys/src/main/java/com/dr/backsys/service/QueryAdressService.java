package com.dr.backsys.service;


import com.dr.backsys.entity.QueryNewUser;
import com.dr.backsys.utils.JsonResult;

import java.util.List;

/**
 * @ Description   :
 * @ Author        :  yqz
 * @ CreateDate    :  2019/12/12 20:02
 */
public interface QueryAdressService {
    List<QueryNewUser> queryNewUsers(String beginTime, String endTime);

    JsonResult queryNewUser(String beginTime, String endTime);


}
