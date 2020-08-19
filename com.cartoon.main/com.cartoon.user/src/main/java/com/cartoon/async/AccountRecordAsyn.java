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
     * 2.记录分类type（阅读券1、积分2、月票3、优惠券4、限免券5、净化卡6）
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
        accountRecord.setRecordReason(params.get("记录描述RecordReason").toString());
        accountRecord.setType(Integer.valueOf(params.get("type").toString()));
        accountRecord.setCount(Integer.valueOf(params.get("数量count").toString()));
        accountRecord.setTarget(params.get("target").toString());
        mongoTemplate.save(accountRecord, "accountRecords");
    }
}
