package com.aaron.springcloudAlibaba.controller;

import com.aaron.springcloudAlibaba.domain.CommonResult;
import com.aaron.springcloudAlibaba.service.AccountService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @Author: Yu
 * @Date: 2021/2/4 23:14
 */
@RestController
public class AccountController {
    @Resource
    private AccountService accountService;

    @RequestMapping("/account/decrease")
    public CommonResult decreaseAccount(@RequestParam("userId") Long userId,
                                        @RequestParam("money") BigDecimal money){

        accountService.decreaseAccount(userId, money);
        return new CommonResult(200, "扣减钱款成功");
    }
}
