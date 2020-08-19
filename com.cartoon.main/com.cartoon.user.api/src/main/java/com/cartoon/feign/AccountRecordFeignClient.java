package com.cartoon.feign;

import com.cartoon.entity.AccountRecord;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("com.cartoon.user")
public interface AccountRecordFeignClient {

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
    AccountRecord addAccountRecords(@RequestBody AccountRecord accountRecord);
}
