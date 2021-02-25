package com.aaron.springcloud.controller;

import com.aaron.springcloud.service.IMessageProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class SendMesgController {

    @Resource
    private IMessageProvider iMessageProvider;

    @GetMapping(value = "/sendMesg")
    public String sendMesg(){
        return iMessageProvider.sendMesg().toString();
    }
}
