package com.cartoon.controller;

import com.cartoon.dto.Dto;
import com.cartoon.serivce.AccountRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accountRecord")
public class AccountRecordController {
    @Autowired
    private AccountRecordService accountRecordService;

    /**
     * 查询账户记录
     * @param id
     * @param type 记录分类type（元宝（gold）1、阅读券(coupon)2、积分(score)3、月票(ticket)4、vip5）
     * @return
     */
    @GetMapping("/findAccountRecord")
    public Dto findAccountRecord(String id,Integer type){

        return null;
    }


}
