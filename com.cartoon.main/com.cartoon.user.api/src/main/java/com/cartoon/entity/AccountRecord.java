package com.cartoon.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * 账户记录
 */
@Document
public class AccountRecord implements Serializable {
    //记录id
    @Id
    private String id;
    //用户phone
    private String phone;
    //记录分类（元宝、vip、阅读券、积分、月票、优惠券、限免券、净化卡）
    private Integer type;
    //记录描述
    private String RecordReason;
    //创建时间
    private String CreateTime;
    //数量
    private Integer count;
    //消费目标/获得来源
    private String target;

    @Override
    public String toString() {
        return "AccountRecord{" +
                "id='" + id + '\'' +
                ", phone='" + phone + '\'' +
                ", type=" + type +
                ", RecordReason='" + RecordReason + '\'' +
                ", CreateTime='" + CreateTime + '\'' +
                ", count=" + count +
                ", target='" + target + '\'' +
                '}';
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getRecordReason() {
        return RecordReason;
    }

    public void setRecordReason(String recordReason) {
        RecordReason = recordReason;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
