package com.maxrocky.config;/**
 * Created by beyondLi on 2021/9/6 18:26
 */

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @ClassName: RabbitMQ延迟队列初始化配置
 * @Author: beyondLi
 * @Date: 2021/9/6 18:26   
 */
@Component
public class RabbitMQDelayInitConf {

    //交换机、路由及队列名称
    //延迟交换机
    private static final String DELAY_EXCHANGE = "delayExchange";
    //延迟route
    private static final String DELAY_ROUTE = "delayRoute";
    //延迟队列
    private static final String DELAY_QUEUE = "delayQueue";


    //初始化交换机
    @Bean("delayExchange")
    public CustomExchange delayExchange() {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-delayed-type", "direct");
        //初始化延迟交换机
        /**
         * 1.交换机名称
         * 2.交换机类型
         * 3.是否持久化
         * 4.是否自动删除
         * 5.其他参数
         */
        return new CustomExchange(DELAY_EXCHANGE, "x-delayed-message", true, false, arguments);
    }

    //初始化队列
    @Bean("delayQueue")
    public Queue delayQueue() {
        return QueueBuilder.durable(DELAY_QUEUE).build();
    }

    //绑定交换机与队列关系
    @Bean
    public Binding commonQueueBindingCommonExchange(@Qualifier("delayQueue") Queue delayQueue, @Qualifier("delayExchange") CustomExchange delayExchange){
        return BindingBuilder.bind(delayQueue).to(delayExchange).with(DELAY_ROUTE).noargs();
    };

}
