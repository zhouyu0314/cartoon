package com.cartoon.serivce;

import com.cartoon.entity.AccountRecord;

import java.util.List;

public interface AccountRecordService {

    /**
     * 查询账户记录
     * @param id
     * @param type 记录分类type（元宝（gold）1、阅读券(coupon)2、积分(score)3、月票(ticket)4、vip5）
     * @return
     */
    List<AccountRecord> findAccountRecord(String id,Integer type);

}
