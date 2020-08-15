package com.cartoon.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;


@Document
public class Comment implements Serializable {
    @Id
    private String id;
    //漫画id
    private String cartoonId;
    //抖漫id
    private Integer friendsId;
    //用户id
    private String uid;
    //评论内容
    private String content;
    //评论时间
    private String createedTime;
/*    //子评论集合
    private List<SubComment> subComment;*/
    //子评论数量
    private Integer subCommentCount;
    //昵称
    private String nickname;
    //vip
    private Integer vip;
    //头像
    private String headImg;
    //赞数量
    private Integer likesCount;

    //get set 方法

    @Override
    public String toString() {
        return "Comment{" +
                "id='" + id + '\'' +
                ", cartoonId='" + cartoonId + '\'' +
                ", friendsId=" + friendsId +
                ", uid='" + uid + '\'' +
                ", content='" + content + '\'' +
                ", createedTime='" + createedTime + '\'' +
                ", subCommentCount=" + subCommentCount +
                ", nickname='" + nickname + '\'' +
                ", vip=" + vip +
                ", headImg='" + headImg + '\'' +
                ", likesCount=" + likesCount +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCartoonId() {
        return cartoonId;
    }

    public void setCartoonId(String cartoonId) {
        this.cartoonId = cartoonId;
    }

    public Integer getFriendsId() {
        return friendsId;
    }

    public void setFriendsId(Integer friendsId) {
        this.friendsId = friendsId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateedTime() {
        return createedTime;
    }

    public void setCreateedTime(String createedTime) {
        this.createedTime = createedTime;
    }

    public Integer getSubCommentCount() {
        return subCommentCount;
    }

    public void setSubCommentCount(Integer subCommentCount) {
        this.subCommentCount = subCommentCount;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getVip() {
        return vip;
    }

    public void setVip(Integer vip) {
        this.vip = vip;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public Integer getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(Integer likesCount) {
        this.likesCount = likesCount;
    }
}
