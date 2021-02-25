package com.aaron.springcloud.service;

import com.aaron.springcloud.entities.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 作为Feign功能的接口
 */
@FeignClient(value = "cloud-payment-service")
public interface PaymentFeignService {

    @GetMapping("/payment/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id);

    /**
     * 长流程调用，模拟调用超时的情景
     * @return
     */
    @GetMapping(value = "/payment/feign/timeout")
    public String paymentFeignTimeout();
}
