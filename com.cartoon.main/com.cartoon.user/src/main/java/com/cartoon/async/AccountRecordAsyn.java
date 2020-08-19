package com.cartoon.async;

import com.cartoon.entity.AccountRecord;
import com.cartoon.util.IdWorker;
import com.cartoon.util.SimpleDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
public class AccountRecordAsyn {
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 创建消费记录，前端需提供：
     * 1.用户phone
     * 2.记录分类type（元宝（gold）1、vip2、阅读券(coupon)3、积分(score)4、月票(ticket)5）
     * 3.记录描述RecordReason
     * 4.数量count
     * 5.消费目标/获得来源target
     */
    @Async
    public void add(Map<String, Object> params) {

        AccountRecord accountRecord = new AccountRecord();
        accountRecord.setId(IdWorker.getId());
        accountRecord.setCreateTime(SimpleDate.getDate(new Date()));
        accountRecord.setPhone(params.get("phone").toString());
        accountRecord.setRecordReason(params.get("recordReason").toString());
        accountRecord.setType(Integer.valueOf(params.get("type").toString()));
        accountRecord.setCount(Integer.valueOf(params.get("count").toString()));
        Object target = params.get("target");
        if (target==null) {
            accountRecord.setTarget("");
        }else{
            accountRecord.setTarget(target.toString());
        }
        mongoTemplate.save(accountRecord, "accountRecords");
    }
}
