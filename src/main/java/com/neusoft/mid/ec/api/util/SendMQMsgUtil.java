package com.neusoft.mid.ec.api.util;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class SendMQMsgUtil {

    @Autowired
    private Environment environment;
    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送MQ消息
     *
     * @param jsonStr
     * @throws Exception
     */
    public void sendMQMsg(String jsonStr) throws Exception {
        Message message = MessageBuilder.withBody(jsonStr.getBytes())
                .setContentType(MessageProperties.CONTENT_TYPE_BYTES).setContentEncoding("utf-8").build();
        rabbitTemplate.convertAndSend(environment.getProperty("mq.media.exchange"),environment.getProperty("mq.media.routingKey"),message);
    }
}
