package com.cartoon.controller;

import com.cartoon.entity.AccountRecord;
import com.cartoon.serivce.AccountRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accountRecord")
public class AccountRecordController {
    @Autowired
    private AccountRecordService accountRecordService;


    //----------------------feign----------------------------

    /**
     * 创建消费记录，前端需提供：
     * 1.用户phone
     * 2.记录分类（阅读券1、积分2、月票3、优惠券4、限免券5、净化卡6）
     * 3.记录描述
     * 4.数量
     * 5.消费目标/获得来源
     * @param accountRecord
     * @return
     */
    @PostMapping("/addAccountRecords")
    public AccountRecord addAccountRecords(@RequestBody AccountRecord accountRecord){
        return accountRecordService.addAccountRecords(accountRecord);
    }
}
