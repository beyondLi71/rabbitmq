package com.maxrocky.common.callback;/**
 * Created by beyondLi on 2021/9/17 10:31
 */

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @Description: 消息队列发布确认回调
 * @ClassName: RabbitMQCallBack
 * @Author: beyondLi
 * @Date: 2021/9/17 10:31   
 */
@Component
public class RabbitMQCallBack implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback{

    @Resource
    RabbitTemplate rabbitTemplate;

    //将重写的方法注入至rabbitTemplate中
    @PostConstruct
    public void myinit() {
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnCallback(this);
    }

    //监听交换机是否成功
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            //消息发送成功
            System.out.println("消息发送成功");
            if (correlationData != null) {
                System.out.println("内容为" + correlationData.getId());
            }
        }else {
            //消息发送失败
            System.out.println("消息发送失败");
            if (correlationData != null) {
                System.out.println("原因为" + cause);
                System.out.println("内容为" + correlationData.getId());
            }
        }
    }

    //监听失败路由
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
            System.out.println("消息"+new String(message.getBody())+"发送失败，失败原因为" + replyText);
    }
}
