package com.aaron.springcloud.controller;

import com.aaron.springcloud.entities.CommonResult;
import com.aaron.springcloud.entities.Payment;
import com.aaron.springcloud.service.PaymentService;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * 微服务84去调用 nacos-payment-provider，即9003微服务提供者
 */
@RestController
@Slf4j
public class CircuitBreakerController {
    public static final String SERVICE_URL = "http://nacos-payment-provider";

    @Resource
    private RestTemplate restTemplate;

    @RequestMapping("/consumer/fallback/{id}")
    //@SentinelResource(value = "testFallback") //没有配置
    //@SentinelResource(value = "testFallback", fallback = "handleFallback") //fallback只负责业务异常
    //@SentinelResource(value = "testFallback", blockHandler = "blockHandler") //blockHandler只负责sentinel控制台配置违规
    @SentinelResource(value = "testFallback", fallback = "handleFallback", blockHandler = "blockHandler",
            exceptionsToIgnore = IllegalArgumentException.class)
    public CommonResult testFallback(@PathVariable("id") Long id){
        CommonResult result =
                restTemplate.getForObject(SERVICE_URL+"/paymentSQL/"+id, CommonResult.class, id);

        if (id == 4) {
            throw new IllegalArgumentException("IllegalArgumentException, 非法参数异常...");
        } else if (result.getData() == null) {
            throw new NullPointerException("NullPointerException, 该ID没有对应记录，空指针异常");
        }
        return result;
    }

    // 本例是fallback的兜底方法
    public CommonResult<Payment> handleFallback(@PathVariable Long id, Throwable throwable){
        Payment payment = new Payment(id, null);
        return new CommonResult<>(444,
                "handleFallback兜底方法异常处理，异常内容："+throwable.getMessage(),
                payment);
    }

    // 本例是blockHandler的兜底方法
    public CommonResult<Payment> blockHandler(@PathVariable Long id, BlockException exception){
        Payment payment = new Payment(id, "null");
        return new CommonResult<>(445,
                "blockHandler兜底的方法-sentinel限流，无此流水：blockException "
                        +exception.getMessage(), payment);
    }


    /********************************************************
     * 使用OpenFeign进行调用
     */
    @Resource
    private PaymentService paymentService;

    @GetMapping(value = "/consumer/paymentSQL/{id}")
    public CommonResult<Payment> paymentSQL(@PathVariable("id") Long id){
        return paymentService.paymentSQL(id);
    }
}
