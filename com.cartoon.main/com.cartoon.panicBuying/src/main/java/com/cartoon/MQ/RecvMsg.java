package com.cartoon.MQ;

import com.cartoon.config.GiftMQ;
import com.cartoon.entity.AccountRecord;
import com.cartoon.entity.Gift;
import com.cartoon.feign.AccountRecordFeignClient;
import com.cartoon.service.GiftService;
import com.cartoon.util.IdWorker;
import com.cartoon.util.SimpleDate;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class RecvMsg {
    @Autowired
    private GiftService giftService;
    @Autowired(required = false)
    private AccountRecordFeignClient accountRecordFeignClient;

    @RabbitListener(queues = GiftMQ.RUSHREDPACKET_QUEUE)
    public void recv(Gift gift, Message message, Channel channel){
        System.out.println("接收到队列消息");
        //修改红包db，添加用户信息和修改状态
        giftService.updateGift(gift);
        //修改用户流水记录
        AccountRecord accountRecord = new AccountRecord();
        accountRecord.setId(IdWorker.getId());
        accountRecord.setCreateTime(SimpleDate.getDate(new Date()));
        accountRecord.setCount(gift.getCount());
        accountRecord.setPhone(gift.getPhone());
        accountRecord.setRecordReason("系统红包");
        accountRecord.setType(gift.getType());
        accountRecordFeignClient.addAccountRecords(accountRecord);
    }
}
