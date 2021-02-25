package com.aaron.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @Author: Yu
 * @Date: 2021/1/7 22:39
 */
@RestController
@Slf4j
public class FlowLimitController {
    @GetMapping("/sentinelTestA")
    public String testA() {
        return "Sentinel--------------testA.";
    }

    @GetMapping("/sentinelTestB")
    public String testB() {
        /*try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        log.info(Thread.currentThread().getName() + "\t ...sentinelTestB");
        return "Sentinel--------------testB.";
    }

    @GetMapping("/sentinelTestD")
    public String testD() {
        // 暂停1秒线程
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("...sentinelTestD 测试 RT");
        return "Sentinel--------------testD.";
    }

    @GetMapping("/sentinelTestE")
    public String testE() {
        log.info("...sentinelTestE 测试 异常比例");
        int a = 10 / 0;
        return "Sentinel--------------testE.";
    }

    @GetMapping("/sentinelTestF")
    public String testF() {
        log.info("...sentinelTestE 测试 异常数");
        int a = 10 / 0;
        return "Sentinel--------------testF.";
    }


    /*********************************************************************
     * 热点key限流的方法
     * @SentinelResource 中的value就是个标识，只要不重复即可，最后要作为资源名称
     */
    @GetMapping("/sentinelTestHotKey")
    @SentinelResource(value = "sentinelTestHotKey", blockHandler = "deal_sentinelTestHotKey")
    public String testHotKey(@RequestParam(value = "p1", required = false) String p1,
                             @RequestParam(value = "p2", required = false) String p2){
        return "成功了......sentinelTestHotKey";
    }
    public String deal_sentinelTestHotKey(String p1, String  p2, BlockException exception){
        // sentinel系统默认提示：blocked by sentinel（flow limiting）
        return "......deal_sentinelTestHotKey, o(╥﹏╥)o";
    }


}