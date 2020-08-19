package com.cartoon.serivce.impl;

import com.cartoon.entity.AccountRecord;
import com.cartoon.exceptions.DataNotFoundException;
import com.cartoon.serivce.AccountRecordService;
import com.cartoon.util.PageUtil;
import com.cartoon.util.TokenDecode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AccountRecordServiceImpl implements AccountRecordService {
    @Autowired
    private MongoTemplate mongoTemplate;

    //查询账户记录 记录分类type（元宝（gold）1、阅读券(coupon)2、积分(score)3、月票(ticket)4、vip5）
    @Override
    public PageUtil<AccountRecord> findAccountRecord(Map<String,Object> params) throws DataNotFoundException{
        String phone = TokenDecode.getUserInfo().get("username");
        params.put("phone",phone);
        Integer accountRecordCount = findAccountRecordCount(params);
        if (accountRecordCount == 0) {
            throw new DataNotFoundException("未找到数据");
        }
        //创建分页对象
        PageUtil<AccountRecord> pageUtil = new PageUtil();
        //设置总条数
        pageUtil.setTotalCount(accountRecordCount);
        //设置页面显示数量
        pageUtil.sPageSize(Integer.valueOf(Integer.valueOf( params.get("pageSize").toString())));
        //设置当前页面数
        pageUtil.sCurrentPage(Integer.valueOf(params.get("currentPage").toString()));
        //获取起始页数
        params.put("beginPos", pageUtil.gStartRow() + "");
        List<AccountRecord> accountRecordPage = findAccountRecordPage(params);
        pageUtil.setLists(accountRecordPage);
        return pageUtil;
    }

    //-----------------------private------------------------------------

    /**
     * 分页查数量
     */
    private Integer findAccountRecordCount(Map<String,Object> params){
        String phone = params.get("phone").toString();
        Integer type = Integer.valueOf(params.get("type").toString());
        Criteria criteria = new Criteria();
        criteria.andOperator(Criteria.where("phone").is(phone),Criteria.where("type").is(type));
        Query query = new Query(criteria);
        return (int)mongoTemplate.count(query, AccountRecord.class, "accountRecords");
    }

    /**
     * 分页查
     */
    private List<AccountRecord> findAccountRecordPage(Map<String,Object> params){
        String phone = params.get("phone").toString();
        Integer type = Integer.valueOf(params.get("type").toString());
        Criteria criteria = new Criteria();
        criteria.andOperator(Criteria.where("phone").is(phone),Criteria.where("type").is(type));
        Query query = new Query(criteria);
        query.skip(Long.valueOf(Integer.valueOf( params.get("beginPos").toString()))).
                limit(Integer.valueOf(Integer.valueOf(params.get("pageSize").toString())));
        return mongoTemplate.find(query, AccountRecord.class, "accountRecords");
    }

}
