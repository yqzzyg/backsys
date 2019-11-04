package com.neusoft.mid.ec.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

	// redis地址
	@Value("${jedisPool.redis_url}")
	private String redis_url;

	// redis端口
	@Value("${jedisPool.redis_port}")
	private int redis_port;

	@Value("${AES-KEY.key1}")
	private String key1;

	@Value("${AES-KEY.key2}")
	private String key2;

	@Value("${AES-KEY.key3}")
	private String key3;

	@Value("${filePath.whiteIPFile}")
	private String whiteIPFile;
	
	@Value("${filePath.sensitiveFile}")
	private String sensitiveFile;

	@Value("${expired-time.days}")
	private String days;
	

	public String getRedis_url() {
		return redis_url;
	}

	public void setRedis_url(String redis_url) {
		this.redis_url = redis_url;
	}

	public int getRedis_port() {
		return redis_port;
	}

	public void setRedis_port(int redis_port) {
		this.redis_port = redis_port;
	}

	public String getKey1() {
		return key1;
	}

	public void setKey1(String key1) {
		this.key1 = key1;
	}

	public String getKey2() {
		return key2;
	}

	public void setKey2(String key2) {
		this.key2 = key2;
	}

	public String getKey3() {
		return key3;
	}

	public void setKey3(String key3) {
		this.key3 = key3;
	}

	public String getWhiteIPFile() {
		return whiteIPFile;
	}

	public void setWhiteIPFile(String whiteIPFile) {
		this.whiteIPFile = whiteIPFile;
	}

	public String getSensitiveFile() {
		return sensitiveFile;
	}

	public void setSensitiveFile(String sensitiveFile) {
		this.sensitiveFile = sensitiveFile;
	}

	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}
	
	
	
	
	
	
	

}
