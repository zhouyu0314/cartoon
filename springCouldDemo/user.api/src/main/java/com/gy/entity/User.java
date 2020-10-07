package com.gy.entity;
import java.io.Serializable;
/***
*   
*/
public class User implements Serializable {
    //
    private Long id;
    //
    private String username;
    //
    private Double money;
    //get set 方法
    public void setId (Long  id){
        this.id=id;
    }
    public  Long getId(){
        return this.id;
    }
    public void setUsername (String  username){
        this.username=username;
    }
    public  String getUsername(){
        return this.username;
    }
    public void setMoney (Double  money){
        this.money=money;
    }
    public  Double getMoney(){
        return this.money;
    }
}
