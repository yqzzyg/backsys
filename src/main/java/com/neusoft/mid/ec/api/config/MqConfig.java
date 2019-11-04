/*
package com.neusoft.mid.ec.api.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class MqConfig {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Value("${rabbitmq.host}")
    private String host;
    @Value("${rabbitmq.port}")
    private String port;
    @Value("${rabbitmq.username}")
    private String username;
    @Value("${rabbitmq.password}")
    private String password;
    @Value("${rabbitmq.virtual-host}")
    private String virtualhost;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(Integer.valueOf(port));
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualhost);
        connectionFactory.setPublisherConfirms(true);//消息确认
        connectionFactory.setPublisherReturns(true);
        return connectionFactory;
    }


    */
/**
     * 配置交换机实例
     *
     * @return
     *//*

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("dc_hnduj_media_exchange");
    }

    */
/**
     * 配置队列实例，并且设置持久化队列
     *
     * @return
     *//*

    @Bean
    public Queue queue() {
        return new Queue("dc_hnduj_media_queue",true);
    }

    */
/**
     * 将队列绑定到交换机上，并设置消息分发的路由键
     *
     * @return
     *//*

    @Bean
    public Binding binding() {
        //链式写法: 用指定的路由键将队列绑定到交换机
        return BindingBuilder.bind(queue()).to(directExchange()).with("dc_hnduj_media_routingkey");
    }
}
*/
