package com.neusoft.mid.ec.api.util;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class RocketMQSendUitl {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Environment environment;
    @Resource
    private RocketMQTemplate rocketMQTemplate;

    /**
     * 发送MQ消息 byte
     * @param jsonStr
     * @throws Exception
     */
    public void sendMQMsg(String jsonStr){
        try {
            //发送
            rocketMQTemplate.send(environment.getProperty("rocketmq.producer.group"), MessageBuilder.withPayload(jsonStr).build());
            logger.info("输出生产者信息={}",jsonStr);
        } catch (Exception e) {
            logger.info("发送MQ消息异常",jsonStr);
        }
    }

    /**
     * 发送MQ消息String
     * @param jsonStr
     * @throws Exception
     */
    public void convertAndSendMQMsg(String jsonStr)  {
        try {
            //发送
            rocketMQTemplate.convertAndSend(environment.getProperty("rocketmq.producer.group"), jsonStr);
            logger.info("输出生产者信息={}",jsonStr);
        } catch (MessagingException e) {
            logger.info("发送MQ消息异常",jsonStr);
        }
    }
}
