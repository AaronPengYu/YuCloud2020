package com.aaron.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Yu
 * @Date: 2020/12/18 13:11
 */
@RestController
@RefreshScope  // 支持nacos的动态刷新功能
public class NacosConfigClientController {
    @Value("${config.info}")
    private String configInfo;

    @GetMapping("/nacos/configInfo")
    public String getConfigInfo(){
        return configInfo;
    }
}
