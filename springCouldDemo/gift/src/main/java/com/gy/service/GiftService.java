package com.gy.service;

import com.gy.entity.Gift;

import java.util.List;
import java.util.Map;

public interface GiftService {

    Gift getGiftById(Map<String, Object> params);

    List<Gift> getGiftListByMap(Map<String, Object> params);

    Integer getGiftCountByMap(Map<String, Object> params);

    Integer insertGift(Map<String, Object> params);

    Integer updateGift(Map<String, Object> params);

    /**
     * 购买商品
     */
    void buySomeGift(Map<String, Object> params);
}
