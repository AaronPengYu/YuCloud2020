package com.aaron.springcloudAlibaba.service.Impl;

import com.aaron.springcloudAlibaba.dao.AccountDao;
import com.aaron.springcloudAlibaba.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Yu
 * @Date: 2021/2/4 23:09
 */
@Service
public class AccountServiceImpl implements AccountService {
    public static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Resource
    private AccountDao accountDao;

    @Override
    public void decreaseAccount(Long userId, BigDecimal money) {
        LOGGER.info("-------> 2003账户服务开始扣减账户余额");
        // 模拟超时异常，全局事务回滚
        // 暂停20秒线程
//        try {
//            TimeUnit.SECONDS.sleep(20);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        accountDao.decrease(userId, money);
        LOGGER.info("-------> 2003账户服务扣减账户余额结束");
    }
}
