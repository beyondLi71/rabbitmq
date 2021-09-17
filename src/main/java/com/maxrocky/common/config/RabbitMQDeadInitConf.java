package com.maxrocky.common.config;/**
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
 * @ClassName: RabbitMQ死信队列初始化配置
 * @Author: beyondLi
 * @Date: 2021/9/6 18:26   
 */
@Component
public class RabbitMQDeadInitConf {

    //交换机、路由及队列名称
    //普通交换机
    private static final String COMMON_EXCHANGE = "commonExchange";
    //普通route
    private static final String COMMON_ROUTE = "commonRoute";
    //普通队列
    private static final String COMMON_QUEUE = "commonQueue";

    //死信交换机
    private static final String DEAD_EXCHANGE = "deadExchange";
    //死信route
    private static final String DEAD_ROUTE = "deadRoute";
    //死信队列
    private static final String DEAD_QUEUE = "deadQueue";

    //初始化交换机
    @Bean("commonExchange")
    public DirectExchange commonExchange() {
        //初始化普通交换机
        return new DirectExchange(COMMON_EXCHANGE,true, false);
    }

    @Bean("deadExchange")
    public DirectExchange deadExchange() {
        //初始化死信交换机
        return new DirectExchange(DEAD_EXCHANGE,true, false);
    }

    //初始化队列
    @Bean("commonQueue")
    public Queue commonQueue() {
        //初始化普通队列
        Map<String, Object> argument = new HashMap<>();
        //设置死信交换机
        argument.put("x-dead-letter-exchange", DEAD_EXCHANGE);
        //设置死信路由
        argument.put("x-dead-letter-routing-key", DEAD_ROUTE);
        return QueueBuilder.durable(COMMON_QUEUE).withArguments(argument).build();
    }

    @Bean("deadQueue")
    public Queue deadQueue() {
        //初始化死信队列
        return QueueBuilder.durable(DEAD_QUEUE).build();
    }

    //绑定交换机与队列关系
    //普通绑定
    @Bean("deadBinding")
    public Binding commonQueueBindingCommonExchange(@Qualifier("commonQueue") Queue commonQueue, @Qualifier("commonExchange") DirectExchange commonExchange){
        return BindingBuilder.bind(commonQueue).to(commonExchange).with(COMMON_ROUTE);
    };

    //死信绑定
    @Bean
    public Binding deadQueueBindingDeadExchange(@Qualifier("deadQueue") Queue deadQueue, @Qualifier("deadExchange") DirectExchange deadExchange){
        return BindingBuilder.bind(deadQueue).to(deadExchange).with(DEAD_ROUTE);
    };
}
