package com.cartoon.serivce;

import com.cartoon.entity.AccountRecord;

import java.util.List;
import java.util.Map;

public interface AccountRecordService {


    /**
     * 查看用的消费信息（分页），查出后存redis设置过期时间
     */
    List<AccountRecord> findAccountRecords(Map<String ,Object> params);

}
