package com.maxrocky.service;/**
 * Created by beyondLi on 2021/9/6 18:28
 */

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @Description:
 * @ClassName: QueueSender
 * @Author: beyondLi
 * @Date: 2021/9/6 18:28   
 */
@Service
public class QueueSender {

    @Resource
    private RabbitTemplate template;

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
        System.out.println("死信队列发送消息：" + msg);
        return "死信队列发送消息：" + msg;
    }

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
        System.out.println("延迟队列发送消息：" + msg);
        return "延迟队列发送消息：" + msg;
    }



    public String SendCallBack(String info) {
        String msg = info;
        System.out.println(LocalDateTime.now());
        //设置发布确认回调消息
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(info);
        //消息发送
        template.convertAndSend("callBackExchange","callBackRoute1", "发布确认:" + msg, correlationData);
        //消息发送
        //template.convertAndSend("callBackExchange","callBackRoute", "发布确认:" + msg);
        System.out.println("发布确认队列发送消息：" + msg);
        return "发布确认队列发送消息：" + msg;
    }
}
