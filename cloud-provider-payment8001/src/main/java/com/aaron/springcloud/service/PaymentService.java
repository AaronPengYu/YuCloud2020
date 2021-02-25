package com.aaron.springcloud.service;

import com.aaron.springcloud.entities.Payment;

/**
 * @Author: Yu
 * @Date: 2020/10/8 20:55
 */
public interface PaymentService {
    public int create(Payment payment);

    public Payment getPaymentById(Long id);
}
