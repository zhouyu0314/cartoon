package com.cartoon.service;

import com.cartoon.entity.Comment;
import com.cartoon.entity.SubComment;
import com.cartoon.exceptions.DataNotFoundException;
import com.cartoon.util.PageUtil;
import org.springframework.data.mongodb.core.mapreduce.GroupByResults;

import java.util.List;
import java.util.Map;

public interface CommentService {
    /**
     * 对漫画进行评论
     */
    Comment addComment(Comment comment);


    /**
     * 查看主评论
     */
    PageUtil<Comment> findComments(Map<String,String> params);



    /**
     * 根据id查看评论
     */
    List<Comment> findByIdFromComment(String id);

    /**
     * 修改评论数量
     */
    void updateComment(String id);

    /**
     * 点赞
     */
    void addLikes(String id)throws DataNotFoundException;



















    /*    *//**
     * 对评论点赞
     * @param id 评论id
     * @return
     *//*
    void updateLikeCount(String id);

    *//**
     * 查询所有评论
     *//*
    PageUtil<Comment> findAllComment(Map<String,Object> params);

    *//**
     * 根据id查
     *//*
    Comment findById(String id);

    *//**
     * 查询redis有多少的此用户通知
     *//*
    Long findMsgCount();

    *//**
     * 查询回复我的和给我点赞的，按照时间排序
     *//*
    List<Comment> findLikeAndCommentById();

    *//**
     * 消费redis中的评论和点赞的通知
     *//*
    void consumeRedis();*/


}
