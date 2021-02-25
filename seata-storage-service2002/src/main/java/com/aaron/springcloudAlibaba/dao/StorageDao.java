package com.aaron.springcloudAlibaba.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: Yu
 * @Date: 2021/2/4 0:11
 */
@Mapper
public interface StorageDao {
    // 扣减库存
    void decrease(@Param("productId") Long productId, @Param("count") Integer count);
}
