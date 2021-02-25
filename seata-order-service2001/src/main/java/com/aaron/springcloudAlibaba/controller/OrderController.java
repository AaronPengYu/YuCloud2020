package com.aaron.springcloudAlibaba.controller;

import com.aaron.springcloudAlibaba.domain.CommonResult;
import com.aaron.springcloudAlibaba.domain.Order;
import com.aaron.springcloudAlibaba.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: Yu
 * @Date: 2021/2/2 21:54
 */
@RestController
public class OrderController {
    @Resource
    private OrderService orderService;

    @GetMapping(value = "/order/createOrder")
    public CommonResult createOrder(Order order){
        orderService.createOrder(order);
        return new CommonResult(200, "创建订单成功");
    }
}
