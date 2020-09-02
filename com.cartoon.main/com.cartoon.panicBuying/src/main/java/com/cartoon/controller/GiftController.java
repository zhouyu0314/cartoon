package com.cartoon.controller;

import com.cartoon.dto.Dto;
import com.cartoon.dto.DtoUtil;
import com.cartoon.service.GiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/panicbuying")
public class GiftController extends BaseController {
    @Autowired
    private GiftService giftService;

    /**
     * 手动添加红包信息到数据库，应该是自动添加
     * @return
     */
    @GetMapping("/addDB")
    public Dto addDB(){
        giftService.addDB();
        return DtoUtil.returnSuccess();
    }

    /**
     * 查询用户抢红包的状态 0正在抢 1已抢到 2失效
     * @return
     */
    @GetMapping("/showGiftStatus")
    public Dto showGiftStatus(String extime){
        return DtoUtil.returnSuccess("用户状态",giftService.showGiftStatus(extime));
    }

    /**
     * 抢红包,返回排队中。前端异步查看状态。
     */
    @GetMapping("/RushRedPacket")
    public Dto RushRedPacket(String extime){
        giftService.RushRedPacket(extime);
        return DtoUtil.returnSuccess("排队中");
    }
}
