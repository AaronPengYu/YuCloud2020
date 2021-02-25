package com.aaron.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;

/**
 * @Author: Yu
 * @Date: 2020/10/19 20:52
 */
@RestController
@Slf4j
public class PaymentController {
    @Value("${server.port}")
    private String serverPort;

    @RequestMapping("/payment/consul")
    public String getPaymentInfo(){
        return "springcloud with Consul: "+serverPort+"\t"+ UUID.randomUUID().toString();
    }
}
