package com.neusoft.mid.ec.api.service.user;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.neusoft.mid.ec.api.domain.User;

@Mapper
public interface UserRepository {

    User getUserInfo(@Param(value = "user")User user);
    
}
