package com.aaron.springcloudAlibaba.service.Impl;

import com.aaron.springcloudAlibaba.dao.StorageDao;
import com.aaron.springcloudAlibaba.service.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: Yu
 * @Date: 2021/2/4 10:48
 */
@Service
public class StorageServiceImpl implements StorageService {
    private static final Logger LOGGER = LoggerFactory.getLogger(StorageServiceImpl.class);

    @Resource
    private StorageDao storageDao;

    /**
     * 扣减库存
     * @param productId
     * @param count
     */
    @Override
    public void decreaseStorage(Long productId, Integer count) {
        LOGGER.info("------------>2002库存服务开始扣减库存");
        storageDao.decrease(productId, count);
        LOGGER.info("------------>2002库存服务扣减库存结束");
    }
}
