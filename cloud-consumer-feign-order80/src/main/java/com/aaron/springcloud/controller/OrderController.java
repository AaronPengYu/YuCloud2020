package com.aaron.springcloud.controller;

import com.aaron.springcloud.entities.CommonResult;
import com.aaron.springcloud.entities.Payment;
import com.aaron.springcloud.service.PaymentFeignService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class OrderController {

    @Resource
    private PaymentFeignService paymentFeignService;

    @GetMapping(value = "/orderfeign/payment/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id){
        return paymentFeignService.getPaymentById(id);
    }

    @GetMapping(value = "/orderfeign/payment/feign/timeout")
    public String paymentFeignTimeout(){
        // openfeign-ribbon 客户端默认等待1s
        return paymentFeignService.paymentFeignTimeout();
    }
}
