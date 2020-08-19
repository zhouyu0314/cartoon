package com.cartoon.serivce.impl;

import com.cartoon.entity.AccountRecord;
import com.cartoon.serivce.AccountRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AccountRecordServiceImpl implements AccountRecordService {
    @Autowired
    private MongoTemplate mongoTemplate;


    @Override
    public List<AccountRecord> findAccountRecords(Map<String, Object> params) {

        return null;
    }

    //查询用户消费记录
}
