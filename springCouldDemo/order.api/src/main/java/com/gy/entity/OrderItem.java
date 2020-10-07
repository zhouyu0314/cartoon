package com.gy.entity;
import java.io.Serializable;
/***
*   
*/
public class OrderItem implements Serializable {
    //
    private String id;
    //
    private String giftId;
    //
    private Integer itemCount;
    //
    private Double itemPrice;

    private String ordersId;
    //get set 方法

    public String getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(String ordersId) {
        this.ordersId = ordersId;
    }

    public void setId (String  id){
        this.id=id;
    }
    public  String getId(){
        return this.id;
    }
    public void setGiftId (String  giftId){
        this.giftId=giftId;
    }
    public  String getGiftId(){
        return this.giftId;
    }
    public void setItemCount (Integer  itemCount){
        this.itemCount=itemCount;
    }
    public  Integer getItemCount(){
        return this.itemCount;
    }
    public void setItemPrice (Double  itemPrice){
        this.itemPrice=itemPrice;
    }
    public  Double getItemPrice(){
        return this.itemPrice;
    }
}
