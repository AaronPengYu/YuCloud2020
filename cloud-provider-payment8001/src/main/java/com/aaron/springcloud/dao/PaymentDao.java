package com.aaron.springcloud.dao;

import com.aaron.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: Yu
 * @Date: 2020/10/7 23:53
 */
@Mapper
public interface PaymentDao {
    public int create(Payment payment);

    public Payment getPaymentById(@Param("pid") Long id);
}
