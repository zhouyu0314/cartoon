package com.cartoon.async;

import com.cartoon.MQ.SendMsg;
import com.cartoon.config.DateUtil;
import com.cartoon.entity.Gift;
import com.cartoon.enums.GiftEmun;
import com.cartoon.exceptions.SellOutException;
import com.cartoon.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class GiftAsync {
    @Autowired
    private RedisUtil redisUtil;


    @Autowired
    private SendMsg sendMsg;


    /**
     * 创建消费记录，前端需提供：
     * 1.用户phone
     * 2.记录分类type（元宝（gold）1、vip2、阅读券(coupon)3、积分(score)4、月票(ticket)5）
     * 3.记录描述RecordReason
     * 4.数量count
     * 5.消费目标/获得来源target
     */
    @Async
    public void rushAsync() {
        Date date = DateUtil.getDateMenus().get(0);
        Map<String,Object> map = new HashMap<>();
        Gift gift = new Gift();
        String phone = null;
        //获取当前场次
        String extime = DateUtil.data2str(date, DateUtil.PATTERN_YYYYMMDDHH);
        //取出令牌
        Gift token  = (Gift)redisUtil.rifhtPop(GiftEmun.rushPurchaseToken.getName() + extime);
        if (token!=null) {
            //如果还有令牌
            //取出用户
            phone = (String)redisUtil.rifhtPop(GiftEmun.userQueue.getName());
            //去redis拿红包
            Gift giftData = (Gift) redisUtil.hmget(GiftEmun.rushPurchaseTime + extime).get(token.getId());
            //并删除此红包
            redisUtil.hdel(GiftEmun.rushPurchaseTime + extime,token.getId());

            //将用户的状态改成2已抢到
            //将用户加入购买状态redis list
            //状态 0未抢到 1已抢到 2失效
            gift.setStatus(1);
            map.put(phone,gift);
            redisUtil.hmset(GiftEmun.userRushStatus.getName(),map);
            //拿到红包之后，修改红包记录信息，存用户记录信息
            /*
            修改gift表和用户记录表可以通过mq 死信队列设置 1分钟内的随机时间进行 ，防止同一时间都操作数据库。
             */
            //修改状态为1，已抢到
            giftData.setStatus(1);
            sendMsg.send(giftData);

        }else{
            phone = (String)redisUtil.rifhtPop(GiftEmun.userQueue.getName());
            gift.setStatus(2);
            map.put(phone,gift);
            redisUtil.hmset(GiftEmun.userRushStatus.getName(),map);
            throw new SellOutException("抢光了！");
        }

    }
}
