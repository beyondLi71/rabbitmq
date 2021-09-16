package com.maxrocky.service;/**
 * Created by beyondLi on 2021/9/6 18:28
 */

import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @Description:
 * @ClassName: QueueSender
 * @Author: beyondLi
 * @Date: 2021/9/6 18:28   
 */
@Service
public class QueueSender {

    @Autowired
    private RabbitTemplate template;

    public String SendDelay(String info) {
        String msg = info;
        System.out.println(LocalDateTime.now());
        //设置延迟时间
        template.convertAndSend("delayExchange","delayRoute", "30秒" + msg, message -> {
            MessageProperties messageProperties = message.getMessageProperties();
            // 设置这条消息的过期时间
            messageProperties.setDelay(30000);
            return message;
        });
        //设置延迟时间
        template.convertAndSend("delayExchange","delayRoute", "10秒" + msg, message -> {
            MessageProperties messageProperties = message.getMessageProperties();
            // 设置这条消息的过期时间
            messageProperties.setDelay(10000);
            return message;
        });
        System.out.println("maxrocky_one_test队列发送消息：" + msg);
        return "maxrocky_one_test队列发送消息：" + msg;
    }

    public String Send(String info) {
        String msg = info;
        System.out.println(LocalDateTime.now());
        //设置ttl时间
        template.convertAndSend("commonExchange","commonRoute", "10秒" + msg, message -> {
            MessageProperties messageProperties = message.getMessageProperties();
            // 设置这条消息的过期时间
            messageProperties.setExpiration("10000");
            return message;
        });
        //设置ttl时间
        template.convertAndSend("commonExchange","commonRoute", "30秒" + msg, message -> {
            MessageProperties messageProperties = message.getMessageProperties();
            // 设置这条消息的过期时间
            messageProperties.setExpiration("30000");
            return message;
        });
        System.out.println("maxrocky_one_test队列发送消息：" + msg);
        return "maxrocky_one_test队列发送消息：" + msg;
    }


}
