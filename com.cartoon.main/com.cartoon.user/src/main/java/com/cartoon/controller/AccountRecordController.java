package com.cartoon.controller;

import com.cartoon.dto.Dto;
import com.cartoon.dto.DtoUtil;
import com.cartoon.entity.AccountRecord;
import com.cartoon.serivce.AccountRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/accountRecord")
public class AccountRecordController {
    @Autowired
    private AccountRecordService accountRecordService;

    @Value("${page.size}")
    private Integer pageSize;

    /**
     * 查询账户记录
     * 记录分类type（元宝（gold）1、阅读券(coupon)2、积分(score)3、月票(ticket)4、vip5）
     * @return
     */
    @GetMapping("/findAccountRecord")
    public Dto findAccountRecord(@RequestParam(defaultValue = "1")Integer currentPage,@RequestParam(defaultValue = "1")Integer type){
        List<String> recordList = new ArrayList<>();
        recordList.add("元宝");
        recordList.add("阅读券");
        recordList.add("积分");
        recordList.add("月票");
        recordList.add("vip");
        Map<String,Object> params = new HashMap<>();
        params.put("pageSize",pageSize);
        params.put("type",type);
        params.put("currentPage",currentPage);


        return DtoUtil.returnSuccess("消费记录"+recordList.get(type-1),accountRecordService.findAccountRecord(params));
    }


//------------------------feign--------------------------------
    /**
     * 创建消费记录，前端需提供：
     * 2.记录分类（阅读券1、积分2、月票3、优惠券4、限免券5、净化卡6）
     * 3.记录描述
     * 4.数量
     * 5.消费目标/获得来源
     * @return
     */
    @PostMapping("/addAccountRecords")
    public void addAccountRecords(@RequestBody AccountRecord accountRecord){
        accountRecordService.addAccountRecord(accountRecord);
    }

}
