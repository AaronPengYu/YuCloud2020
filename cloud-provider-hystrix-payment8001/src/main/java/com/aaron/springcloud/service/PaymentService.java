package com.aaron.springcloud.service;

import org.springframework.web.bind.annotation.PathVariable;

public interface PaymentService {
    //正常返回的方法
    public String paymentInfo_OK(int id);

    //异常返回，服务降级的方法
    public String paymentInfo_TimeOut(int id);

    //兜底方法
    //public String paymentInfo_TimeoutHandler(int id);

    /****************Service层服务熔断******************/
    public String paymentCircuitBreaker(@PathVariable("id") Integer id);

    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id);

}
