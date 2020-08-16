package com.cartoon.service;

import com.cartoon.entity.SubComment;
import com.cartoon.util.PageUtil;

import java.util.Map;

public interface SubCommentService {

    /**
     * 对主评论评论
     */
    SubComment addSubComment(String id, String content, String replyTarget);

    /**
     * 查看次评论
     */
    PageUtil<SubComment> findSubComments(Map<String, String> params);

}
