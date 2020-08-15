package com.cartoon.entity;
import java.io.Serializable;
/***
*   
*/
public class Productcategory implements Serializable {
    //
    private Integer id;
    //
    private String categoryName;
    //get set 方法
    public void setId (Integer  id){
        this.id=id;
    }
    public  Integer getId(){
        return this.id;
    }
    public void setCategoryName (String  categoryName){
        this.categoryName=categoryName;
    }
    public  String getCategoryName(){
        return this.categoryName;
    }
}
