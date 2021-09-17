package com.maxrocky.common.config;/**
 * Created by beyondLi on 2021/9/6 18:26
 */

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @ClassName: RabbitMQ发布确认(高级)初始化配置
 * @Author: beyondLi
 * @Date: 2021/9/6 18:26   
 */
@Component
public class RabbitMQCallBackInitConf {

    //交换机、路由及队列名称
    //发布确认交换机
    private static final String CALLBACK_EXCHANGE = "callBackExchange";
    //发布确认route
    private static final String CALLBACK_ROUTE = "callBackRoute";
    //发布确认队列
    private static final String CALLBACK_QUEUE = "callBackQueue";


    //初始化交换机
    @Bean("callBackExchange")
    public DirectExchange delayExchange() {
        //初始化交换机
        return new DirectExchange(CALLBACK_EXCHANGE,true, false);
    }

    //初始化队列
    @Bean("callBackQueue")
    public Queue delayQueue() {
        return QueueBuilder.durable(CALLBACK_QUEUE).build();
    }

    //绑定交换机与队列关系
    @Bean("callBackBinding")
    public Binding callBackQueueBindingCallBackExchange(@Qualifier("callBackQueue") Queue callBackQueue, @Qualifier("callBackExchange") DirectExchange callBackExchange){
        return BindingBuilder.bind(callBackQueue).to(callBackExchange).with(CALLBACK_ROUTE);
    };

}
