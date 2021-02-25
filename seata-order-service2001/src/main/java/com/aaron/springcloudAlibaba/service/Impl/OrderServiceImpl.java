package com.aaron.springcloudAlibaba.service.Impl;

import com.aaron.springcloudAlibaba.dao.OrderDao;
import com.aaron.springcloudAlibaba.domain.Order;
import com.aaron.springcloudAlibaba.service.AccountService;
import com.aaron.springcloudAlibaba.service.OrderService;
import com.aaron.springcloudAlibaba.service.StorageService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 * 订单微服务调用库存微服务2002、调用账户微服务2003，
 * 是通过Feign接口调用的（用@FeignClient注解）
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderDao orderDao;
    @Resource
    private StorageService storageService;
    @Resource
    private AccountService accountService;

    /**
     * 创建订单-->调用库存服务扣减库存-->调用账户服务扣减账户金额-->修改订单状态
     * 简单说：下订单--减库存--减金额--改状态
     */
    @Override
    @GlobalTransactional(name = "yupengGT", rollbackFor = Exception.class)
    public void createOrder(Order order) {
        // 1.开始创建订单
        log.info("----->开始创建订单");
        orderDao.createOrder(order);

        // 2.开始扣减库存
        log.info("----->订单微服务调用库存微服务，扣减库存");
        storageService.decreaseStorage(order.getProductId(), order.getCount());

        // 3.开始扣减账户金额
        log.info("----->订单微服务调用账户微服务，扣减账户金额");
        accountService.decreaseAccount(order.getUserId(), order.getMoney());

        // 4.最后调用updateOrder修改订单状态，由0改为1，表示已完成
        log.info("----->修改订单状态");
        orderDao.updateOrder(order.getUserId(), 0);

        log.info("-----------下订单结束了，成功，O(∩_∩)O哈哈~----------");
    }
}
