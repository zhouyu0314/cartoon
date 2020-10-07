package com.gy.entity;
import java.io.Serializable;
/***
*   
*/
public class Orders implements Serializable {
    //
    private String id;
    //
    private Double totalPrice;
    //
    private Integer totalCount;
    //get set 方法
    public void setId (String  id){
        this.id=id;
    }
    public  String getId(){
        return this.id;
    }
    public void setTotalPrice (Double  totalPrice){
        this.totalPrice=totalPrice;
    }
    public  Double getTotalPrice(){
        return this.totalPrice;
    }
    public void setTotalCount (Integer  totalCount){
        this.totalCount=totalCount;
    }
    public  Integer getTotalCount(){
        return this.totalCount;
    }
}
