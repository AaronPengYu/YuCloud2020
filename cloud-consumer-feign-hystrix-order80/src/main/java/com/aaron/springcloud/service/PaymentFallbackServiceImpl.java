package com.aaron.springcloud.service;
import org.springframework.stereotype.Component;
@Component
public class PaymentFallbackServiceImpl implements PaymentHystrixService {
    @Override
    public String getPaymentInfo_OK(int id) {
        return "消费者80的getPaymentInfo_OK方法的解耦合兜底方法";
    }

    @Override
    public String getPaymentInfo_TimeOut(int id) {
        return "消费者80的getPaymentInfo_TimeOut方法的解耦合兜底方法";
    }
}
