package com.aaron.springcloudAlibaba.service;

import com.aaron.springcloudAlibaba.domain.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.math.BigDecimal;

@FeignClient(value = "seata-account-service")
public interface AccountService {

    @PostMapping(value = "/account/decrease")
    CommonResult decreaseAccount(@RequestParam("userId") Long userId,
                                 @RequestParam("money")BigDecimal money);
}
