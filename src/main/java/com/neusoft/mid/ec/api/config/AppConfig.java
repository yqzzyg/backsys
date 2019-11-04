package com.neusoft.mid.ec.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.neusoft.mid.ec.api.Constants;
import com.neusoft.mid.ec.api.util.JedisClusterUtil;
import com.neusoft.mid.ec.api.util.TokenHelper;

import me.puras.common.holder.JsonConverterHolder;
import me.puras.common.json.Converter;

/**
 * Created by puras on 10/11/16.
 */
@Configuration
public class AppConfig {

	@Bean
	public Converter jsonConverter() {
		return JsonConverterHolder.getInstance().getConverter();
	}

	@Bean
	public TokenHelper tokenHelper() {
		return new TokenHelper() {
			@Override
			protected String getDigestKey(Long userId) {
				return Constants.ACCESS_TOKEN_KEY;
			}
		};
	}

	@Autowired
	private RedisProperties redisProperties;

	@Value("${redis.cache.clusterNodes}")
	private String redis_url;

	@Value("${redis.cache.commandTimeout}")
	private int timeout;

	/**
	 * 注意： 这里返回的JedisCluster是单例的，并且可以直接注入到其他类中去使用
	 * 
	 * @return
	 */
	@Bean
	public JedisClusterUtil getJedisCluster() {
//		JedisClusterUtil jedisClusterUtil = new JedisClusterUtil(redisProperties.getClusterNodes(), redisProperties.getCommandTimeout(), redisProperties.getExpireSeconds());
		JedisClusterUtil jedisClusterUtil = new JedisClusterUtil(redis_url,timeout,0);
		return jedisClusterUtil;
	}
	
	
}
