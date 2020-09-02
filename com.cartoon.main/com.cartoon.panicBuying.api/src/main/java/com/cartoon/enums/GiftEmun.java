package com.cartoon.enums;

public enum GiftEmun {

    //秒杀时段
    rushPurchaseTime("rushPurchaseTime:"),
    //秒杀商品令牌队列
    rushPurchaseToken("rushPurchaseToken:"),
    //用户队列
    userQueue("userQueue:"),
    //购买状态，前端读取后自动删除
    userRushStatus("userRushStatus:"),
    //提交次数
    commitCount("commitCount-");

    private String name;

    private GiftEmun(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
