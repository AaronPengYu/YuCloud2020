package com.aaron.springcloud.service;

import org.springframework.messaging.Message;

public interface IMessageProvider {
    public Message<String> sendMesg();
}
