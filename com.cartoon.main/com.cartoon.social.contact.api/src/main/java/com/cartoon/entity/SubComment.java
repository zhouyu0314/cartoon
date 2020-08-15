package com.cartoon.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class SubComment {
    @Id
    private String id;
    //主评论id
    private String parentId;
    //用户id
    private String uid;
    //评论内容
    private String content;
    //评论时间
    private String createedTime;
    //昵称
    private String nickname;
    //vip
    private Integer vip;
    //头像
    private String headImg;
    //赞数量
    private Integer likesCount;
/*    //是否回复的楼主
    private boolean isMaster;*/
    //分组
    private String group;

    @Override
    public String toString() {
        return "SubComment{" +
                "id='" + id + '\'' +
                ", parentId='" + parentId + '\'' +
                ", uid='" + uid + '\'' +
                ", content='" + content + '\'' +
                ", createedTime='" + createedTime + '\'' +
                ", nickname='" + nickname + '\'' +
                ", vip=" + vip +
                ", headImg='" + headImg + '\'' +
                ", likesCount=" + likesCount +
                ", group='" + group + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
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


    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
