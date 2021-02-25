package com.aaron.springcloudAlibaba.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
/**
 * @Author: Yu
 * @Date: 2021/2/2 22:08
 */

/**
 * 作用和功能是MybatisConfig.xml配置文件
 * 本配置类优点多余，其实@MapperScan写在主启动类上也可以扫描包
 */
@Configuration
@MapperScan(value = "com.aaron.springcloudAlibaba.dao")
public class MybatisConfig {
}
