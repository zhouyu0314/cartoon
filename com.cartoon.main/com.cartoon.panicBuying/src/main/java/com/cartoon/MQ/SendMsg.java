package com.cartoon.MQ;

import com.cartoon.config.GiftMQ;
import com.cartoon.entity.Gift;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SendMsg {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(Gift gift){
        System.out.println("发送延迟队列成功！");

        rabbitTemplate.convertAndSend(GiftMQ.RUSHREDPACKET_DELAY_EXCHANGE,GiftMQ.RUSHREDPACKET_DELAY_ROUTING_KEY, gift, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setExpiration(((int)(Math.random()*90000))+"");
                return message;
            }
        });
    }

}
