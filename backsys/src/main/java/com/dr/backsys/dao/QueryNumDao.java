package com.dr.backsys.dao;


import com.dr.backsys.entity.QueryNewUser;

import java.util.List;

/**
 * @ Description   :
 * @ Author        :  yqz
 * @ CreateDate    :  2019/12/12 17:23
 */
public interface QueryNumDao {

    List<QueryNewUser> queryNewUsers(String beginTime, String endTime);
}
