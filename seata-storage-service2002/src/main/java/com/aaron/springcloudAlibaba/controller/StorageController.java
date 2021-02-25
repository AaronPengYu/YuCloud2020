package com.aaron.springcloudAlibaba.controller;

import com.aaron.springcloudAlibaba.domain.CommonResult;
import com.aaron.springcloudAlibaba.service.StorageService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: Yu
 * @Date: 2021/2/4 17:57
 */
@RestController
public class StorageController {
    @Resource
    private StorageService storageService;

    @RequestMapping("/storage/decrease")
    public CommonResult decreaseStorage(@RequestParam("productId") Long productId,
                                        @RequestParam("count") Integer count){

        storageService.decreaseStorage(productId, count);
        return new CommonResult(200, "扣减库存成功");
    }
}
