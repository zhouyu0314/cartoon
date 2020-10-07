package com.gy.entity;
import java.io.Serializable;

public class Gift implements Serializable {
    //
    private String id;
    //
    private String giftname;
    //
    private Double price;

    private Integer count;
    //get set 方法
    public void setId (String  id){
        this.id=id;
    }
    public  String getId(){
        return this.id;
    }
    public void setGiftname (String  giftname){
        this.giftname=giftname;
    }
    public  String getGiftname(){
        return this.giftname;
    }
    public void setPrice (Double  price){
        this.price=price;
    }
    public  Double getPrice(){
        return this.price;
    }
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
