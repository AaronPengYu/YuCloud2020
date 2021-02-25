package com.aaron.springcloudAlibaba.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * @Author: Yu
 * @Date: 2021/2/4 21:36
 */
@Mapper
public interface AccountDao {
    void decrease(@Param("userId") Long userId,
                  @Param("money") BigDecimal money);
}
