package com.cartoon.serivce.impl;

import com.cartoon.entity.AccountRecord;
import com.cartoon.entity.Comment;
import com.cartoon.serivce.AccountRecordService;
import com.cartoon.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountRecordServiceImpl implements AccountRecordService {
    @Autowired
    private MongoTemplate mongoTemplate;

    //查询账户记录 记录分类type（元宝（gold）1、阅读券(coupon)2、积分(score)3、月票(ticket)4、vip5）
    @Override
    public List<AccountRecord> findAccountRecord(String id, Integer type) {
        Integer accountRecordCount = findAccountRecordCount(id, type);
        if (accountRecordCount == 0) {
            throw
        }
        //创建分页对象
        PageUtil<Comment> pageUtil = new PageUtil();



        return null;
    }

    //-----------------------private------------------------------------

    /**
     * 分页查数量
     * @param id
     * @param type
     * @return
     */
    private Integer findAccountRecordCount(String id, Integer type){
        Criteria criteria = new Criteria();
        criteria.andOperator(Criteria.where("id").is(id),Criteria.where("type").is(type));
        Query query = new Query(criteria);
        return (int)mongoTemplate.count(query, AccountRecord.class, "accountRecords");
    }


}
