package com.cartoon.listener;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
//还有一个监听类，是配合上面的配置文件注释的部分的，可以灵活配置，配置监听不同的库，redis有16个库。
public class RedisExpiredListener implements MessageListener {
    @Override
    public void onMessage(Message message, byte[] bytes) {
        byte[] body = message.getBody();// 建议使用: valueSerializer
        byte[] channel = message.getChannel();
        System.out.print("onMessage >> " );
        System.out.println(String.format("channel: %s, body: %s, bytes: %s"
                ,new String(channel), new String(body), new String(bytes)));
    }

}