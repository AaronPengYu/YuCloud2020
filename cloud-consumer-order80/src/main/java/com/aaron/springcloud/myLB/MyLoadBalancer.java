package com.aaron.springcloud.myLB;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

/**
 * @Author: Yu
 * @Date: 2020/11/11 0:23
 */
public interface MyLoadBalancer {
    ServiceInstance getServiceInstance(List<ServiceInstance> serviceInstances);
}