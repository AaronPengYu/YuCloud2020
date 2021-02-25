package com.aaron.springcloud.controller;

import com.aaron.springcloud.entities.CommonResult;
import com.aaron.springcloud.entities.Payment;
import com.aaron.springcloud.myLB.MyLoadBalancer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

@RestController
@Slf4j
public class OrderController {
    //public static final String PAYMENT_URL = "http://localhost:8001/payment";
    public static final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE/payment";

    @Resource
    RestTemplate restTemplate;

    @Resource
    DiscoveryClient discoveryClient;

    @Resource
    MyLoadBalancer myLoadBalancer;

    @GetMapping("/consumer/payment")
    public CommonResult<Payment> create(Payment payment){
        return restTemplate.postForObject(PAYMENT_URL, payment, CommonResult.class);
    }

    @GetMapping("/consumer/payment/{id}")
    public CommonResult<Payment> getOrderById(@PathVariable("id") Long id){
        return restTemplate.getForObject(PAYMENT_URL+"/"+id, CommonResult.class);
    }

    /**
     * 使用postForEntity方法来提交修改
     * @param payment
     * @return
     */
    @GetMapping("consumer/paymentUseEntity")
    public CommonResult<Payment> createUseEntity(Payment payment){
        ResponseEntity<CommonResult> entity =
                restTemplate.postForEntity(PAYMENT_URL, payment, CommonResult.class);
        if (entity.getStatusCode().is2xxSuccessful()) {
            return entity.getBody();
        } else {
            return new CommonResult<>(444, "使用postForEntity插入数据失败");
        }
    }

    /**
     * 使用getForEntity方法来返回响应体
     * @param id
     * @return
     */
    @GetMapping("consumer/paymentUseEntity/{id}")
    public CommonResult<Payment> getOrderByIdUseEntity(@PathVariable("id") Long id){
        ResponseEntity<CommonResult> entity =
                restTemplate.getForEntity(PAYMENT_URL+"/"+id, CommonResult.class);
        if (entity.getStatusCode().is2xxSuccessful()) {
            return entity.getBody();
        } else {
            return new CommonResult<>(444, "使用getForEntity操作失败");
        }
    }

    /**
     * 使用手写的轮询算法，消费者远程调用服务
     * @return
     */
    @GetMapping("/order/payment/lb")
    public String getPaymentLB(){
        List<ServiceInstance> instances =
                discoveryClient.getInstances("cloud-payment-service");

        // 如果服务列表存在且不为空 就从中用手写轮询算法取一个服务
        if (instances != null && instances.size() != 0) {
            ServiceInstance serviceInstance =
                    myLoadBalancer.getServiceInstance(instances);

            // 取出服务主机地址
            URI serviceInstanceUri = serviceInstance.getUri();

            // 远程调用
            return restTemplate.getForObject(serviceInstanceUri+"/payment/lb", String.class);
        }

        return "服务为空";
    }

    /**
     * sleuth + zipkin 整合的业务类
     * @return
     */
    @GetMapping("/consumer/payment/zipkin")
    public String paymentZipkin(){
        String result = restTemplate
                .getForObject("http://localhost:8001"+"/payment/zipkin/", String.class);
        return result;
    }
}
