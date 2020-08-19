package com.cartoon.controller;

import com.cartoon.dto.Dto;
import com.cartoon.dto.DtoUtil;
import com.cartoon.serivce.AccountRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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


}
