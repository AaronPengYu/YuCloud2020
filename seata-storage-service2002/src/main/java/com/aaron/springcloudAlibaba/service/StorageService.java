package com.aaron.springcloudAlibaba.service;

import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: Yu
 * @Date: 2021/2/4 0:36
 */
public interface StorageService {
    // 扣减库存
    void decreaseStorage(@RequestParam("productId") Long productId,
                         @RequestParam("count") Integer count);
}
