package com.aaron.springcloud.controller;

import com.aaron.springcloud.entities.CommonResult;
import com.aaron.springcloud.entities.Payment;
import com.aaron.springcloud.myHandler.CustomerBlockHandler;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RateLimitController {
    /**
     * 根据资源名限流
     */
    @GetMapping("/byResource")
    @SentinelResource(value = "byResource", blockHandler = "handleException")
    public CommonResult<Payment> byResource(){
        return new CommonResult<>(200,
                "按资源名称限流测试OK", new Payment(2021L, "serial001"));
    }
    public CommonResult handleException(BlockException exception){
        return new CommonResult<>(444,
                exception.getClass().getCanonicalName()+"\t 服务不可用");
    }

    /**
     * 根据URL地址限流
     */
    @GetMapping("/byUrl")
    @SentinelResource(value = "byUrl")
    public CommonResult<Payment> byUrl(){
        return new CommonResult<>(200,
                "按URL限流测试OK", new Payment(2021L, "serial002"));
    }

    /**
     * 自定义异常响应的限流
     */
    @GetMapping("/customerBlockHandler")
    @SentinelResource(value = "customerBlockHandler",
            blockHandlerClass = CustomerBlockHandler.class,
            blockHandler = "handleException2")
    public CommonResult<Payment> customerBlockHandler(){
        return new CommonResult<>(200,
                "自定义的限流处理逻辑", new Payment(2022L, "serial003"));
    }
}
