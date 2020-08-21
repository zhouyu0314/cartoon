package com.cartoon.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class GiftMQ {
    //声明 队列 交换机 routingkey
    public static final String RUSHREDPACKET_DELAY_QUEUE = "user.rushRedPacket.delay.queue";

    public static final String RUSHREDPACKET_DELAY_EXCHANGE = "user.rushRedPacket.delay.exchange";

    public static final String RUSHREDPACKET_DELAY_ROUTING_KEY = "user.rushRedPacket.delay.routing";

    //第二队列
    public static final String RUSHREDPACKET_QUEUE = "user.rushRedPacket.queue";

    public static final String RUSHREDPACKET_EXCHANGE = "user.rushRedPacket.exchange";

    public static final String RUSHREDPACKET_ROUTING_KEY = "user.rushRedPacket.routing";


    //声明死信队列
    @Bean(RUSHREDPACKET_DELAY_QUEUE)
    public Queue delayRUSHREDPACKETQueue() {
        Map<String, Object> params = new HashMap<>();
        params.put("x-dead-letter-exchange", RUSHREDPACKET_EXCHANGE);
        params.put("x-dead-letter-routing-key", RUSHREDPACKET_ROUTING_KEY);
        return new Queue(RUSHREDPACKET_DELAY_QUEUE, true, false, false, params);
    }

    //声明死信交换机
    @Bean(RUSHREDPACKET_DELAY_EXCHANGE)
    public Exchange delayRUSHREDPACKETExchange() {
        return ExchangeBuilder.topicExchange(RUSHREDPACKET_DELAY_EXCHANGE).build();
    }

    //绑定
    @Bean
    public Binding delayRUSHREDPACKETBinding(@Qualifier(RUSHREDPACKET_DELAY_QUEUE) Queue queue,
                                             @Qualifier(RUSHREDPACKET_DELAY_EXCHANGE) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(RUSHREDPACKET_DELAY_ROUTING_KEY).noargs();

    }

    //第二队列的声明及绑定
    @Bean(RUSHREDPACKET_QUEUE)
    public Queue RUSHREDPACKETQueue() {
        return new Queue(RUSHREDPACKET_QUEUE, true, false, false, null);
    }

    @Bean(RUSHREDPACKET_EXCHANGE)
    public Exchange RUSHREDPACKETExchange() {
        return ExchangeBuilder.topicExchange(RUSHREDPACKET_EXCHANGE).build();
    }

    @Bean
    public Binding RUSHREDPACKETBinding(@Qualifier(RUSHREDPACKET_QUEUE) Queue queue,
                                        @Qualifier(RUSHREDPACKET_EXCHANGE) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(RUSHREDPACKET_ROUTING_KEY).noargs();
    }
}


