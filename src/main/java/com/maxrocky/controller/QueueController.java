package com.maxrocky.controller;

import com.maxrocky.service.QueueSender;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Description: 基础演示
 * @ClassName: CornerstoneController
 * @Author: beyondLi
 * @Date: 2021/1/14 14:03
 */
@RestController
@RequestMapping(value = "/queue")
public class QueueController {

    @Resource
    QueueSender queueSender;

    @RequestMapping(value = "/sender/{info}", method = RequestMethod.GET)
    public String sender (@PathVariable("info") String info ) {
        String send = queueSender.Send(info);
        return send;
    }

    @RequestMapping(value = "/sender/delay/{info}", method = RequestMethod.GET)
    public String senderDelay (@PathVariable("info") String info ) {
        String send = queueSender.SendDelay(info);
        return send;
    }

}
