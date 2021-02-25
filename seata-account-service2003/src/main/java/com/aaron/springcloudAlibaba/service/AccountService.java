package com.aaron.springcloudAlibaba.service;

import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

/**
 * @Author: Yu
 * @Date: 2021/2/4 23:08
 */
public interface AccountService {
    void decreaseAccount(@RequestParam("userId") Long userId,
                         @RequestParam("money") BigDecimal money);
}
