package com.aaron.springcloudAlibaba.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Yu
 * @Date: 2021/2/4 0:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Storage {
    private Long id;
    private Long productId; //产品id
    private Integer total; //总库存
    private Integer used; //已用库存
    private Integer residue; //剩余库存
}
