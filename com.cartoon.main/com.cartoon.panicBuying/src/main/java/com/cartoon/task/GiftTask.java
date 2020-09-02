package com.cartoon.task;

import com.cartoon.config.DateUtil;
import com.cartoon.entity.Gift;
import com.cartoon.enums.GiftEmun;
import com.cartoon.service.GiftService;
import com.cartoon.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.*;

@Component
public class GiftTask {
    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private GiftService giftService;

    @Value("${giftCount.redPacketCount}")
    private Integer redPacketCount;

    /**
     * 每天11-21每隔5秒自动扫描db添加redis
     */
    //@Scheduled(cron = "0/5 * 11-21 * * ?")
    @Scheduled(cron = "0/5 * * * * ?")
    public void addRedis() {
        Map<String,Object> params = new HashMap<>();
        Map<String ,Object> giftMap = new HashMap<>();
        List<Date> dates = DateUtil.getDates(5);
        String startTime = null;
        String endTime = null;
        //遍历每个12-20时间段
        for (Date start : dates) {
            giftMap.clear();
            Date end = DateUtil.addDateHour(start, 2);
            String extime = DateUtil.data2str(start,DateUtil.PATTERN_YYYYMMDDHH);
            try {
                startTime = DateUtil.format(start, "yyyy-MM-dd HH:mm:ss");
                endTime = DateUtil.format(end, "yyyy-MM-dd HH:mm:ss");
                params.put("startTime",startTime);
                params.put("endTime",endTime);
                params.put("status",0);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            //查询redis本次时段都有哪些商品，防止商品重复添加
            Map<Object, Object> hmget = redisUtil.hmget(GiftEmun.rushPurchaseTime.getName() + extime);
            Set<Object> objects = hmget.keySet();
            if (objects !=null && objects.size()!=0) {
                Object[] ids = objects.toArray();
                params.put("ids" ,ids);
            }

            //找到符合每个时间段的数据，加入redis
            List<Gift> giftList = giftService.getGiftListByMap(params);
            //找到数据就加redis
            if (giftList !=null && giftList.size()!=0) {
                for (Gift gift : giftList) {
                    giftMap.put(gift.getId(),gift);
                    //创建gift令牌，拿到令牌的用户才能抢红包
                    redisUtil.lSet(GiftEmun.rushPurchaseToken.getName()+extime,gift);
                }
                //存入每个时间段的数据
                redisUtil.hmset(GiftEmun.rushPurchaseTime.getName() +extime,giftMap);
            }
        }
    }

    /**
     * 当过了场次之后，要查看还有多少上一个上次没抢完的红包，并删除redis表，没抢完的需要修改红包状态为2
     **/
    //14点到23点，每小时的第一分钟执行一次
    @Scheduled(cron = "* 1 14-23 * * ?")
    //@Scheduled(cron = "0/5 * * * * ?")
    public void del(){
        //获取当前时间段的开始时间
        Date currentStartTime = DateUtil.getDateMenus().get(0);
        //获取上一场次
        Date prevStartTime = DateUtil.addDateHour(currentStartTime, -2);

        /*
        需要删除
        "rushPurchaseTime:" 秒杀时段
        "rushPurchaseToken:" 秒杀商品令牌队列
        "commitCount-" 提交次数
         */
        //删除秒杀场次
        redisUtil.hdel(GiftEmun.rushPurchaseTime.getName()+prevStartTime);
        //删除剩余没抢完的商品令牌
        if (redisUtil.hasKey(GiftEmun.rushPurchaseToken.getName()+prevStartTime)) {
            //如果有,取出场次里的全部,修改数据库状态2失效
            List<Object> objects = redisUtil.lGet(GiftEmun.rushPurchaseToken.getName() + prevStartTime, 0, -1);
            for (Object object : objects) {
                Gift gift = (Gift)object;
                gift.setStatus(2);
                giftService.updateGift(gift);
            }
        }
        //删除用户上一个场次的提交次数
        redisUtil.del(GiftEmun.commitCount.getName()+prevStartTime);

    }







    //每日2点将明早8点准备加入redis的数据加入数据库
//    @Scheduled(cron = "0 0 2 * * ?")
//    public void addDB() {
//           /*
//        时间段
//        12：00-14：00
//        14：00-16：00
//        16：00-18：00
//        18：00-20：00
//        20：00-22：00
//        每个时间段的红包数量使用配置文件的数量，此处redPacketCount 为3
//        每天早8点自动加入一天活动时间的红包
//         */
//        List<Date> dates = DateUtil.getDates(5);
//        String startTime = null;
//        String endTime = null;
//        //每个时段限制定量空包加入redis
//        for (Date start : dates) {
//            Date end = DateUtil.addDateHour(start, 2);
//            try {
//                startTime = DateUtil.format(start, "yyyy-MM-dd HH:mm:ss");
//                endTime = DateUtil.format(end, "yyyy-MM-dd HH:mm:ss");
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//
//            for (Integer i = 0; i < redPacketCount; i++) {
//                Gift gift = giftService.autoGenerate();
//                gift.setStartTime(startTime);
//                gift.setEndTime(endTime);
//                giftService.addGift(gift);
//            }
//        }
//    }

    //---------------------private----------------------------------
}
