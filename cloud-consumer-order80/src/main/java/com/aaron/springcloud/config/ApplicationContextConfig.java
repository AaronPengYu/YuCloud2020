package com.aaron.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationContextConfig {

    @Bean  //使用注解的方式将方法返回值注入到SpringBoot容器，容器里就有了RestTemplate对象
    //@LoadBalanced  //赋予了RestTemplate负载均衡的能力
    //注销Ribbon自带的负载均衡，使用自定义的负载均衡算法
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
