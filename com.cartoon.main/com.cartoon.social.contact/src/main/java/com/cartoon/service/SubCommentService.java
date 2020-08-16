package com.cartoon.service;

import com.cartoon.entity.SubComment;
import com.cartoon.exceptions.DataNotFoundException;
import com.cartoon.util.PageUtil;

import java.util.Map;

public interface SubCommentService {

    /**
     * 对主评论评论
     */
    SubComment addSubComment(String id, String content);

    /**
     * 查看次评论
     */
    PageUtil<SubComment> findSubComments(Map<String, String> params);


    /**
     * 点赞
     */
    void addLikes(String id)throws DataNotFoundException;

}
