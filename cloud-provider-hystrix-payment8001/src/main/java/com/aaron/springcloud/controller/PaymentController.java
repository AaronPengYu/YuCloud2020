package com.aaron.springcloud.controller;

import com.aaron.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: Yu
 * @Date: 2020/11/16 10:55
 */
@Slf4j
@RestController
public class PaymentController {

    @Resource
    PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/payment/hystrix/ok/{id}")
    public String getPaymentInfo_OK(@PathVariable("id") int id) {
        String result = paymentService.paymentInfo_OK(id);
        log.info("PaymentController的getPaymentInfo_OK方法调用结果是："+result);
        return result;
    }

    @GetMapping("/payment/hystrix/timeout/{id}")
    public String getPaymentInfo_TimeOut(@PathVariable("id") int id) {
        String result = paymentService.paymentInfo_TimeOut(id);
        log.info("PaymentController的getPaymentInfo_Timeout方法调用结果是："+result);
        return result;
    }

    /****************Controller层服务熔断******************/
    @GetMapping("/payment/circuitbreaker/{id}")
    public String paymentCircuitBreaker(@PathVariable("id") Integer id){
        String result = paymentService.paymentCircuitBreaker(id);
        log.info("controller层调用结果："+result);
        return result;
    }

}
