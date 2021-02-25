package com.aaron.springcloud.myHandler;

import com.aaron.springcloud.entities.CommonResult;
import com.alibaba.csp.sentinel.slots.block.BlockException;

public class CustomerBlockHandler {
    public static CommonResult handleException1(BlockException exception){
        return new CommonResult(4441, "自定义全局异常1：用户名密码错误！");
    }
    public static CommonResult handleException2(BlockException exception){
        return new CommonResult(4442, "自定义全局异常2：程序内运行异常！");
    }
}
