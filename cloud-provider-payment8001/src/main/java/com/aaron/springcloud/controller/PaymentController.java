package com.aaron.springcloud.controller;

import com.aaron.springcloud.entities.CommonResult;
import com.aaron.springcloud.entities.Payment;
import com.aaron.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Yu
 * @Date: 2020/10/8 21:29
 */
@RestController
@Slf4j
public class PaymentController {

    @Resource
    PaymentService paymentService;

    @Value("${server.port}")  //读取yaml文件里配置的端口号
    private String serverPort;

    @Resource
    DiscoveryClient discoveryClient;

    @PostMapping("/payment")
    public CommonResult createPayment(@RequestBody Payment payment) {
        int result = paymentService.create(payment);
        log.info("********插入结果："+result+" **********");

        if (result > 0) {  //插入成功
            return new CommonResult(200, "插入支付数据成功："+serverPort, result);
        }

        return new CommonResult(444, "插入支付数据失败", result);
    }

    @GetMapping("/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id) {
        Payment payment = paymentService.getPaymentById(id);
        log.info("********插入结果："+payment+" **********");

        if (payment != null) {
            return new CommonResult(200, "查询成功："+serverPort, payment);
        }

        return new CommonResult(444, "查询失败："+id, payment);
    }

    /**
     * 配置服务发现功能，在前端页面后后台日志打印信息
     * @return
     */
    @GetMapping(value = "/payment/discovery")
    public Object getServicesInfos(){
        // 得到所有服务的列表
        List<String> services = discoveryClient.getServices();
        for (String service: services){  //遍历和打印所有服务名称
            log.info("服务名称为："+service+"\n");

            List<ServiceInstance> instances = discoveryClient.getInstances(service);
            for (ServiceInstance instance: instances){
                log.info("服务下的微服务实例："+instance.getServiceId()+"\t"
                                            +instance.getInstanceId()+"\t"
                                            +instance.getUri()+"\t"
                                            +instance.getPort()+"\n");
            }
        }
        return discoveryClient;
    }

    /**
     * 模拟长流程调用，使消费者调用超时
     * @return
     */
    @GetMapping(value = "/payment/feign/timeout")
    public String paymentFeignTimeout(){
        // 暂停3s相当于长流程调用
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) { e.printStackTrace(); }

        return serverPort;
    }

    /**
     * 得到当前访问的端口号
     * @return
     */
    @GetMapping(value = "/payment/lb")
    public String getPaymentLB(){
        return serverPort;
    }

    /**
     * sleuth + zipkin 整合的业务类
     * @return
     */
    @GetMapping("/payment/zipkin")
    public String paymentZipkin(){
        return "hi, I am paymentZipkin server fall back...O(∩_∩)O~";
    }
}
