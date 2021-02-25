package com.aaron.springcloud.controller;

import com.aaron.springcloud.service.PaymentHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: Yu
 * @Date: 2020/11/17 1:07
 */
@RestController
@Slf4j
public class OrderHystrixController {
    @Resource
    PaymentHystrixService paymentHystrixService;

    @GetMapping("/consumer/payment/hystrix/ok/{id}")
    public String getPaymentInfo_OK(@PathVariable("id") int id){
        String result = paymentHystrixService.getPaymentInfo_OK(id);
        System.out.println("OrderHystrixController调用结果为："+result);
        return result;
    }
    
    @GetMapping("/consumer/payment/hystrix/timeout/{id}")
    @HystrixCommand(fallbackMethod = "getPaymentInfo_TimeOutFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1500")
    })
    public String getPaymentInfo_TimeOut(@PathVariable("id") int id){
        String result = paymentHystrixService.getPaymentInfo_TimeOut(id);
        System.out.println("OrderHystrixController的getPaymentInfo_TimeOut方法调用结果为："+result);
        return result;
    }

    public String getPaymentInfo_TimeOutFallback(int id){
        return "id = "+id+"\t我是消费者80的兜底方法，对方支付系统繁忙请30秒再试，若自己运行出错请自检......";
    }


}
