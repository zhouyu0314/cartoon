package com.gy.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.gy.entity.Gift;
import com.gy.exceptions.DataNotFoundException;
import com.gy.exceptions.NotEnoughStockException;
import com.gy.exceptions.UpdateDataException;
import com.gy.feign.OrderFeignClient;
import com.gy.mapper.GiftMapper;
import com.gy.service.GiftService;
import com.gy.utils.IdWorker;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class GiftServiceImpl implements GiftService {
    @Autowired(required = false)
    private GiftMapper giftMapper;

    @Autowired
    private OrderFeignClient orderFeignClient;

    @Override
    public Gift getGiftById(Map<String, Object> params) {
        return giftMapper.getGiftById(Long.valueOf(params.get("id").toString()));
    }

    @Override
    public List<Gift> getGiftListByMap(Map<String, Object> params) {

        return giftMapper.getGiftListByMap(params);
    }

    @Override
    public Integer getGiftCountByMap(Map<String, Object> params) {
        return giftMapper.getGiftCountByMap(params);
    }

    @Override
    public Integer insertGift(Map<String, Object> params) {
        Gift gift = JSONObject.parseObject(JSONObject.toJSONString(params), Gift.class);
        gift.setId(IdWorker.getId());
        return giftMapper.insertGift(gift);
    }

    @Override
    public Integer updateGift(Map<String, Object> params) {
        Gift gift = JSONObject.parseObject(JSONObject.toJSONString(params), Gift.class);
        return giftMapper.updateGift(gift);
    }

    //买东西
    @GlobalTransactional
    @Override
    public void buySomeGift(Map<String, Object> params) {
        String[] ids = params.get("ids").toString().split(",");
        String[] counts = params.get("counts").toString().split(",");
        Integer totalCount = 0;
        Double totalPrice = 0.0;
        List<Gift> gifts = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            Gift gift = this.findById(ids[i]);
            //查询有没有
            if (gift == null) {
                throw new DataNotFoundException("未查询到商品！");
            }
            //判断数量
            if (gift.getCount() < Integer.valueOf(counts[i])) {
                throw new NotEnoughStockException("库存不足，仅剩" + gift.getCount() + "件");
            }
            //修改数量
            gift.setCount(gift.getCount() - Integer.valueOf(counts[i]));
            Integer rows = this.changeGift(gift);
            if (rows != 1) {
                throw new UpdateDataException("修改数据失败");
            }
            gift.setCount(Integer.valueOf(counts[i]));
            gifts.add(gift);
            totalCount += Integer.valueOf(counts[i]);
            totalPrice += Integer.valueOf(counts[i]) * gift.getPrice();

        }
        params.put("gifts", gifts);
        params.put("totalCount", totalCount);
        params.put("totalPrice", totalPrice);
        //调用订单模块
        orderFeignClient.genOrders(params);

    }

    //*****************************************************************
    private Gift findById(String id) {
        return giftMapper.getGiftById(Long.valueOf(id));
    }

    private Integer changeGift(Gift gift) {
        return giftMapper.updateGift(gift);
    }

}
