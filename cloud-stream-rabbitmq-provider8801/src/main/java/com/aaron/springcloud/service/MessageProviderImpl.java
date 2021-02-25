package com.aaron.springcloud.service;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import javax.annotation.Resource;
import java.util.UUID;

/**
 * 该实现类操作的不是Dao和数据库，而是操作的RabbitMQ消息中间件
 * 所以使用 @EnableBinding 注解标签和 MessageChannel信道
 * 信道搭建好了之后要在上面绑定要发送的消息
 */
@EnableBinding(Source.class)  //定义一个消息生产者的发送管道，这是源，是output
public class MessageProviderImpl implements IMessageProvider {

    @Resource
    private MessageChannel output; //消息发送信道

    @Override
    public Message<String> sendMesg() {
        String serial = UUID.randomUUID().toString();
        System.out.println("*********serial: "+serial);
        // 构建一个消息
        Message<String> buildSerial = MessageBuilder.withPayload(serial)
                .setHeader("PengHeader", serial)
                .build();
        // 把消息用绑定器绑定起来，通过信道向消息中间件发送一个流水号
        output.send(buildSerial);
        return buildSerial;
    }
}