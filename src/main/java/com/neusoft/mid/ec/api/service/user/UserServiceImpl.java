package com.neusoft.mid.ec.api.service.user;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neusoft.mid.ec.api.domain.User;
import com.neusoft.mid.ec.api.serviceInterface.user.UserService;
import com.neusoft.mid.ec.api.util.JedisClusterUtil;

@Service
public class UserServiceImpl implements UserService {
	
	private static Logger LOGGEER = Logger.getLogger(UserServiceImpl.class);
	
	@Autowired
	public JedisClusterUtil jedisClusterUtil;

	@Autowired
	private UserRepository userRepository;


	@Override
	public User getUserInfo(User user) {

		return userRepository.getUserInfo(user);
	}


}
