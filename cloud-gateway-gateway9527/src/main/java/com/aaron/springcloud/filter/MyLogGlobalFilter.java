package com.aaron.springcloud.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;

/**
 * @Author: Yu
 * @Date: 2020/11/30 13:37
 */
@Component
@Slf4j
public class MyLogGlobalFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("**********进入自定义全局过滤器**********\t" + new Date());
        //通过key获取用户名
        String uname = exchange.getRequest().getQueryParams().getFirst("uname");
        if (uname == null) {
            log.info("********用户名uname为空，非法用户!!!********");
            //不接受非法用户
            exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);
            return exchange.getResponse().setComplete();
        }
        //用户名不为空就放行，去过滤链下一个过滤器进行验证和过滤
        return chain.filter(exchange);
    }

    /**
     * 加载过滤器的顺序
     *
     * @return 返回数字越小，优先级越高
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
