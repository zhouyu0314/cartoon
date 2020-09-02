package com.cartoon.service.impl;

import com.cartoon.async.GiftAsync;
import com.cartoon.config.DateUtil;
import com.cartoon.entity.Gift;
import com.cartoon.enums.GiftEmun;
import com.cartoon.exceptions.*;
import com.cartoon.mapper.GiftMapper;
import com.cartoon.service.GiftService;
import com.cartoon.util.IdWorker;
import com.cartoon.util.RedisUtil;
import com.cartoon.util.TokenDecode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GiftServiceImpl implements GiftService {


    @Value("${probability.goldProbability}")
    private Integer goldProbability;
    @Value("${probability.couponProbability}")
    private Integer couponProbability;
    @Value("${probability.scoreProbability}")
    private Integer scoreProbability;
    @Value("${probability.ticketProbability}")
    private Integer ticketProbability;
    @Value("${probability.vipProbability}")
    private Integer vipProbability;


    @Value("${giftCount.goldCount}")
    private Integer goldCount;
    @Value("${giftCount.couponCount}")
    private Integer couponCount;
    @Value("${giftCount.scoreCount}")
    private Integer scoreCount;
    @Value("${giftCount.ticketCount}")
    private Integer ticketCount;
    @Value("${giftCount.vipCount}")
    private Integer vipCount;

    @Autowired(required = false)
    private GiftMapper giftMapper;
    @Autowired
    private RedisUtil redisUtil;
    @Value("${giftCount.redPacketCount}")
    private Integer redPacketCount;

    @Autowired
    private GiftAsync giftAsync;

    /**
     * 添加数据库
     * @param gift
     * @return
     */
    @Override
    public Integer addGift(Gift gift) {
        return insert(gift);
    }


    /**
     * 随机生成Gift对象
     * （元宝（gold）1、阅读券(coupon)2、积分(score)3、月票(ticket)4、vip5）
     * 在配置文件中可以调节爆装备概率，总概率为0-99 100个值
     * 按顺序排列，0-19，20-39，40-59，60-79，80-99 目前为平均
     *
     * @return
     */
    @Override
    public Gift autoGenerate() {
        Gift gift = new Gift();
        gift.setId(IdWorker.getId());
        int probability = (int) (Math.random() * 100 + 1);
        int type;
        int count;
        if (probability <= goldProbability) {
            //元宝（gold）
            type = 1;
            count = (int) (Math.random() * goldCount + 1);
        } else if (probability <= couponProbability) {
            //阅读券(coupon)
            type = 2;
            count = (int) (Math.random() * couponCount + 1);
        } else if (probability <= scoreProbability) {
            //积分(score)
            type = 3;
            count = (int) (Math.random() * scoreCount + 1);
        } else if (probability <= ticketProbability) {
            //月票(ticket)
            type = 4;
            count = (int) (Math.random() * ticketCount + 1);
        } else {
            //vip
            type = 5;
            count = (int) (Math.random() * vipCount + 1);
        }
        gift.setType(type);
        gift.setCount(count);
        gift.setStatus(0);
        return gift;
    }

    //获取对象
    @Override
    public List<Gift> getGiftListByMap(Map<String, Object> param) {

        return giftMapper.getGiftListByMap(param);
    }


    //此方法为测试方法，最终需要放到定时任务执行
    @Override
        public void addDB() {
           /*
        时间段
        12：00-14：00
        14：00-16：00
        16：00-18：00
        18：00-20：00
        20：00-22：00
        每个时间段的红包数量使用配置文件的数量，此处redPacketCount 为3
        每天早8点自动加入一天活动时间的红包
         */
        List<Date> dates = DateUtil.getDates(5);
        String startTime = null;
        String endTime = null;
        //每个时段限制定量空包加入redis
        for (Date start : dates) {
            Date end = DateUtil.addDateHour(start, 2);
            try {
                startTime = DateUtil.format(start, "yyyy-MM-dd HH:mm:ss");
                endTime = DateUtil.format(end, "yyyy-MM-dd HH:mm:ss");
            } catch (ParseException e) {
                e.printStackTrace();
            }

            for (Integer i = 0; i < redPacketCount; i++) {
                Gift gift = this.autoGenerate();
                gift.setStartTime(startTime);
                gift.setEndTime(endTime);
                this.addGift(gift);
            }
        }
    }

    //抢红包
    @Override
    public void RushRedPacket(String extime) throws SellOutException,RecommitException {
        //获取用户phone
        String phone = TokenDecode.getUserInfo().get("username");

        //判断是否重复提交
        if (redisUtil.hincr(GiftEmun.commitCount.getName()+extime/*+":" + phone*/, phone, 1) > 1) {
            throw new RecommitException("请勿重复提交！");
        }

        //用户加入redis 的list队列,
        redisUtil.lSet(GiftEmun.userQueue.getName()+extime,phone);
        //将用户加入购买状态redis hash
        Gift gift = new Gift();
        gift.setStatus(0);
        //状态 0未抢到 1已抢到 2失效
        Map<String,Object> map = new HashMap<>();
        map.put(phone,gift);
        redisUtil.hmset(GiftEmun.userRushStatus.getName()+extime,map);
        giftAsync.rushAsync(extime);
    }

    @Override
    public Integer updateGift(Gift gift)throws UpdateDataException{
        Integer rows = giftMapper.updateGift(gift);
        if (rows != 1) {
            throw new UpdateDataException("修改数据失败！");
        }
        return rows;
    }

    //查询redis中红包的状态，查询完之后只要不是派对中（status 1）就可以删除了
    @Override
    public Gift showGiftStatus(String extime)throws  DataNotFoundException {
        //获取用户phone
        String phone = TokenDecode.getUserInfo().get("username");
        Gift gift = (Gift) redisUtil.hmget(GiftEmun.userRushStatus.getName()+extime).get(phone);
        if (gift == null) {
            throw new DataNotFoundException("未查询到数据！");
        }
        //查询完之后只要不是派对中（status 1）就可以删除了
        if (gift.getStatus()!=1) {
            redisUtil.hdel(GiftEmun.userRushStatus.getName()+extime,phone);
        }
        return gift;
    }


    //-----------------private----------------------




    private Integer insert(Gift gift) {
        Integer rows = giftMapper.insertGift(gift);
        if (rows != 1) {
            throw new InsertDataException("插入数据失败！");
        }
        return rows;
    }
}
