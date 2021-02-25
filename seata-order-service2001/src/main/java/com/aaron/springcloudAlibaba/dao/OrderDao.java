package com.aaron.springcloudAlibaba.dao;

import com.aaron.springcloudAlibaba.domain.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: Yu
 * @Date: 2021/2/1 18:16
 */
@Mapper
public interface OrderDao {
    // 1.创建订单
    void createOrder(Order order);

    // 2.更新订单，把状态从0改为1
    void updateOrder(@Param("userId") Long userId, @Param("status") Integer status);
}