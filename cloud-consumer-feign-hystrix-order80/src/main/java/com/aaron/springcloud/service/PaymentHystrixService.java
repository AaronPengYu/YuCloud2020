package com.aaron.springcloud.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author: Yu
 * @Date: 2020/11/17 0:59
 */
@Component
@FeignClient(value = "cloud-provider-hystrix-payment", fallback = PaymentFallbackServiceImpl.class)
public interface PaymentHystrixService {
    @GetMapping("/payment/hystrix/ok/{id}")
    public String getPaymentInfo_OK(@PathVariable("id") int id);

    @GetMapping("/payment/hystrix/timeout/{id}")
    public String getPaymentInfo_TimeOut(@PathVariable("id") int id);
}
