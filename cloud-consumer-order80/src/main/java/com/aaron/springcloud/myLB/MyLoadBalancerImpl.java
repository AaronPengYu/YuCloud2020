package com.aaron.springcloud.myLB;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: Yu
 * @Date: 2020/11/11 0:32
 */
@Component
public class MyLoadBalancerImpl implements MyLoadBalancer {
    private AtomicInteger atomicInteger = new AtomicInteger(0);

    /**
     * 与RoundRobinRule类的incrementAndGetModulo方法类似,用自旋锁取余数
     * 本方法为了获得访问的次数（第几次访问）
     *
     * @return
     */
    public final int incrementAndGetNext() {
        int current;
        int next;
        do {
            current = this.atomicInteger.get();
            next = (current >= Integer.MAX_VALUE) ? 0 : (current + 1);
        } while (!this.atomicInteger.compareAndSet(current, next));

        System.out.println("第next次访问，next = "+next);
        return next;
    }

    /**
     * 用 incrementAndGetNext 方法取得的访问次数取余，
     * 得到下个需要访问的机器的index，再取出该机器的地址
     * @param serviceInstances
     * @return
     */
    @Override
    public ServiceInstance getServiceInstance(List<ServiceInstance> serviceInstances) {
        int index = incrementAndGetNext() % serviceInstances.size();
        return serviceInstances.get(index);
    }
}
