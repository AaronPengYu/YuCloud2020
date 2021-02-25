package com.aaron.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class PaymentServiceImpl implements PaymentService {
    // 服务降级
    @Override
    public String paymentInfo_OK(int id) {
        return "线程池：" + Thread.currentThread().getName() +
                "    paymentInfo_OK, id=" + id + "   O(∩_∩)O哈哈~";
    }

    @Override
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeoutHandler", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")
    })
    public String paymentInfo_TimeOut(int id) {
        int timeout = 3;
        try {
            Thread.sleep(timeout * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        int num = 10/0;
        return "线程池：" + Thread.currentThread().getName() +
                "    paymentInfo_TimeOut, id=" + id + "  ┭┮﹏┭┮，耗时（秒）：";
    }

    public String paymentInfo_TimeoutHandler(int id) {

        return "线程池：" + Thread.currentThread().getName() +
                "    paymentInfo_TimeOutHandler兜底方法, 系统繁忙或运行报错，请稍后再试 id=" + id + "  ~~~~(>_<)~~~~";
    }

    /****************Service层服务熔断******************/
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"), //是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"), //请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"), //时间窗口期
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60") //失败率达到多少后跳闸

    })
    public String paymentCircuitBreaker(Integer id) {
        if (id < 0) {
            throw new RuntimeException("===============id不能是负数");
        }

        String serialNum = IdUtil.simpleUUID();
        return Thread.currentThread().getName()+"\t调用成功，流水号是："+serialNum;
    }
    public String paymentCircuitBreaker_fallback(Integer id){
        return "id 不能为负，请稍后再试  o(╥﹏╥)o，id = "+id;
    }
}
