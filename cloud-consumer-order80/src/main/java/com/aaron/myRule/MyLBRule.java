package com.aaron.myRule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyLBRule {
    @Bean
    public IRule myLBRule(){
        //定义为随机访问规则
        return new RandomRule();
    }
}
