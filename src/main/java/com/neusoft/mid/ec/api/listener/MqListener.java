package com.neusoft.mid.ec.api.listener;


import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.neusoft.mid.ec.api.domain.RequestInfo;
import com.neusoft.mid.ec.api.domain.UserLog;
import com.neusoft.mid.ec.api.serviceInterface.userlog.UserLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;


/** 
* @Description: RabbitMQ监听器
*/
@Component
public class MqListener {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private Environment environment;
	@Resource
	private RabbitTemplate rabbitTemplate;
	@Autowired
	private UserLogService userLogService;
	//@RabbitListener(queues = "dc_hnduj_media_queue")
	public void onMessage(Message message) {
		try {
			String body = new String(message.getBody());
			logger.info("MQ消息解密, body=" + body);
			JSONObject jo = JSON.parseObject(body);
			logger.info("入参："+ jo);
			UserLog userLog = JSONObject.parseObject(JSON.toJSONString(jo.get("userLog")),UserLog.class);
			userLog.setUserIp(jo.get("ip").toString());
			RequestInfo header = JSONObject.parseObject(JSON.toJSONString(jo.get("header")),RequestInfo.class);
			userLog.setUserName(header.getName());
			userLog.setSysid(header.getSysid());
			userLog.setFuncid(header.getFuncid());
			userLog.setToserverid(header.getToserverid());
			userLog.setToken(header.getToken());
			userLog.setIdtype(header.getIdtype());
			userLog.setIdno(header.getIdno());
			userLog.setFromserverid(header.getFromserverid());
			userLog.setVersion(header.getVersion());
			userLogService.insertLog(userLog);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			try {
				logger.error("MQ消息处理失败，返回MQ消息：");
				if (message.getBody()!=null) {
					Message msg = MessageBuilder.withBody(message.getBody())
							.setContentType(MessageProperties.CONTENT_TYPE_BYTES).setContentEncoding("utf-8").build();
					rabbitTemplate.convertAndSend(environment.getProperty("mq.media.exchange"),environment.getProperty("mq.media.routingKey"), msg);
				}
			}catch(Exception ex) {
				logger.error(ex.getMessage(), ex);
			}
		}
	}
}
