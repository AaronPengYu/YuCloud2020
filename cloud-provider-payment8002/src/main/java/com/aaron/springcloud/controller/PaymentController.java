package com.aaron.springcloud.controller;

import com.aaron.springcloud.entities.CommonResult;
import com.aaron.springcloud.entities.Payment;
import com.aaron.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: Yu
 * @Date: 2020/10/8 21:29
 */
@RestController
@Slf4j
public class PaymentController {

    @Resource
    PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @PostMapping("/payment")
    public CommonResult createPayment(@RequestBody Payment payment) {
        int result = paymentService.create(payment);
        log.info("********插入结果："+result+" **********");

        if (result > 0) {  //插入成功
            return new CommonResult(200, "插入支付数据成功："+serverPort, result);
        }

        return new CommonResult(444, "插入支付数据失败", result);
    }

    @GetMapping("/payment/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id) {
        Payment payment = paymentService.getPaymentById(id);
        log.info("********插入结果："+payment+" **********");

        if (payment != null) {
            return new CommonResult(200, "查询成功："+serverPort, payment);
        }

        return new CommonResult(444, "查询失败："+id, payment);
    }

    /**
     * 得到当前访问的端口号
     * @return
     */
    @GetMapping(value = "/payment/lb")
    public String getPaymentLB(){
        return serverPort;
    }
}
