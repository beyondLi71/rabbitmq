package com.maxrocky.listener;/**
 * Created by beyondLi on 2021/9/15 11:51
 */

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @Description: 延迟队列监听
 * @ClassName: DeadQueueListener
 * @Author: beyondLi
 * @Date: 2021/9/15 11:51   
 */
@Component
public class DelayQueueListener {

    @RabbitListener(queues = "delayQueue")
    public void deadQueueInfo(Message message, Channel channel,@Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        System.out.println(LocalDateTime.now());
        System.out.println(new String("接收到的消息为：" + new String(message.getBody())));
        //channel.basicNack(tag,false,true);
    }
}
