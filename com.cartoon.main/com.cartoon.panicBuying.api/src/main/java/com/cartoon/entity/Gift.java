package com.cartoon.entity;

import java.io.Serializable;

/***
 *
 */
public class Gift implements Serializable {
    //
    private String id;
    //类型
    private Integer type;
    //数量
    private Integer count;
    //开始时间
    private String startTime;
    //结束时间
    private String endTime;
    //抢到的用户
    private String phone;
    //状态 0没抢 1抢到 2失效
    private Integer status;

    @Override
    public String toString() {
        return "Gift{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", count=" + count +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", phone='" + phone + '\'' +
                ", status=" + status +
                '}';
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    //get set 方法

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
