package com.gy.controller;

import com.gy.dto.Dto;
import com.gy.dto.DtoUtil;
import com.gy.entity.Gift;
import com.gy.service.GiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/gift")
public class GiftController {

    @Autowired
    private GiftService giftService;


    @PostMapping("/getGiftById")
    public Dto getGiftById(@RequestBody Map<String, Object> params) {
        Gift gift = giftService.getGiftById(params);
        return DtoUtil.returnSuccess("根据id查询商品",gift);
    }

    @PostMapping("/getGiftList")
    public Dto getGiftListByMap(@RequestBody Map<String, Object> params) {
        List<Gift> giftList = giftService.getGiftListByMap(params);
        return DtoUtil.returnSuccess("查询商品集合",giftList);
    }

    @PostMapping("/getGiftCount")
    public Dto getGiftCountByMap(@RequestBody Map<String, Object> params) {
        Integer giftCount = giftService.getGiftCountByMap(params);
        return DtoUtil.returnSuccess("查询商品集合数量",giftCount);
    }

    @PostMapping("/insertGift")
    public Dto insertGift(@RequestBody Map<String, Object> params) {
        giftService.insertGift(params);
        return DtoUtil.returnSuccess("插入数据");
    }

    @PostMapping("/updateGift")
    public Dto updateGift(@RequestBody Map<String, Object> params) {
        giftService.updateGift(params);
        return DtoUtil.returnSuccess("修改数据数据");
    }

    @PostMapping("/buySomeGift")
    public Dto buySomeGift(@RequestBody Map<String, Object> params){
        giftService.buySomeGift(params);
        return DtoUtil.returnSuccess("购买成功");
    }

}
