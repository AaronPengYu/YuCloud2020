package com.aaron.springcloud.service;

import com.aaron.springcloud.entities.CommonResult;
import com.aaron.springcloud.entities.Payment;
import org.springframework.stereotype.Component;

/**
 * @Author: Yu
 * @Date: 2021/1/19 23:17
 */
@Component
public class PaymentFallbackService implements PaymentService {
    @Override
    public CommonResult<Payment> paymentSQL(Long id) {
        return new CommonResult<>(44444,
                "服务降级返回，---PaymentFallbackService",
                new Payment(id, "error serial"));
    }
}
