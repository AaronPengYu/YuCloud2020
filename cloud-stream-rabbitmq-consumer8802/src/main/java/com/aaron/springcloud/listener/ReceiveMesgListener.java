package com.aaron.springcloud.listener;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(Sink.class)
public class ReceiveMesgListener {

    @Value(value = "${server.port}")
    private String serverPort;

    @StreamListener(Sink.INPUT)
    public void receiveMesg(Message<String> message){
        String mesgStr = message.getPayload();
        System.out.println("****消费者8802接收消息****："+mesgStr+"\t 端口号："+serverPort);
    }
}
