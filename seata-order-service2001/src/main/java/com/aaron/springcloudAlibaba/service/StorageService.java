package com.aaron.springcloudAlibaba.service;

import com.aaron.springcloudAlibaba.domain.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 订单会找"seata-storage-service"微服务下的"/storage/decrease"地址对应的方法，
 * 完成对某种商品的数量上的扣减
 */
@FeignClient(value = "seata-storage-service")  //将要调用的库存微服务名称
public interface StorageService {

    //用PostMapping对数据库操作，减库存后库存数量发生变更
    @PostMapping(value = "/storage/decrease")
    CommonResult decreaseStorage(@RequestParam("productId") Long productId,
                                 @RequestParam("count") Integer count);
}
