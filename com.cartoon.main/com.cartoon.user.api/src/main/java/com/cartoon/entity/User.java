package com.cartoon.entity;
import java.io.Serializable;
/***
*   
*/
public class User implements Serializable {
    //
    private String id;
    //
    private String headImg;
    //
    private String nickname;
    //
    private String username;
    //
    private Integer gender;
    //
    private String born;
    //
    private String address;
    //
    private String password;
    //
    private String phone;
    //
    private String email;
    //
    private Integer isDelete;
    //
    private Integer gold;
    //
    private Integer ticket;
    //
    private Integer score;
    //
    private Integer coupon;
    //
    private Integer vip;
    //
    private String qq;
    //
    private String createdTime;
    //
    private String modifiyTime;
    //
    private Integer checkUsername;
//vip到期时间
    private String vipExpire;
    //推荐人
    private String recommender;

    private Integer fansCount;

    private Integer focusCount;

    public Integer getFansCount() {
        return fansCount;
    }

    public void setFansCount(Integer fansCount) {
        this.fansCount = fansCount;
    }

    public Integer getFocusCount() {
        return focusCount;
    }

    public void setFocusCount(Integer focusCount) {
        this.focusCount = focusCount;
    }

    public String getRecommender() {
        return recommender;
    }

    public void setRecommender(String recommender) {
        this.recommender = recommender;
    }

    public String getVipExpire() {
        return vipExpire;
    }

    public void setVipExpire(String vipExpire) {
        this.vipExpire = vipExpire;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getBorn() {
        return born;
    }

    public void setBorn(String born) {
        this.born = born;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getGold() {
        return gold;
    }

    public void setGold(Integer gold) {
        this.gold = gold;
    }

    public Integer getTicket() {
        return ticket;
    }

    public void setTicket(Integer ticket) {
        this.ticket = ticket;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getCoupon() {
        return coupon;
    }

    public void setCoupon(Integer coupon) {
        this.coupon = coupon;
    }

    public Integer getVip() {
        return vip;
    }

    public void setVip(Integer vip) {
        this.vip = vip;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getModifiyTime() {
        return modifiyTime;
    }

    public void setModifiyTime(String modifiyTime) {
        this.modifiyTime = modifiyTime;
    }

    public Integer getCheckUsername() {
        return checkUsername;
    }

    public void setCheckUsername(Integer checkUsername) {
        this.checkUsername = checkUsername;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", headImg='" + headImg + '\'' +
                ", nickname='" + nickname + '\'' +
                ", username='" + username + '\'' +
                ", gender=" + gender +
                ", born='" + born + '\'' +
                ", address='" + address + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", isDelete=" + isDelete +
                ", gold=" + gold +
                ", ticket=" + ticket +
                ", score=" + score +
                ", coupon=" + coupon +
                ", vip=" + vip +
                ", qq='" + qq + '\'' +
                ", createdTime='" + createdTime + '\'' +
                ", modifiyTime='" + modifiyTime + '\'' +
                ", checkUsername=" + checkUsername +
                ", vipExpire='" + vipExpire + '\'' +
                ", recommender='" + recommender + '\'' +
                '}';
    }
}