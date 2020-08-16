package com.cartoon.service.impl;

import com.cartoon.entity.Comment;
import com.cartoon.entity.SubComment;
import com.cartoon.entity.User;
import com.cartoon.exceptions.DataNotFoundException;
import com.cartoon.feign.UserFeignClient;
import com.cartoon.service.CommentService;
import com.cartoon.service.SubCommentService;
import com.cartoon.util.IdWorker;
import com.cartoon.util.PageUtil;
import com.cartoon.util.SimpleDate;
import com.cartoon.util.TokenDecode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class SubCommentServiceImpl implements SubCommentService {
    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CommentService commentService;

    //子评论
    @Override
    public SubComment addSubComment(String id, String content, String replyTarget) {
        //先查找主评论
        Comment comment = commentService.findByIdFromComment(id);
        if (comment == null) {
            throw new DataNotFoundException("评论游走了！");
        }
        SubComment subComment = new SubComment();
        subComment.setParentId(id);
        subComment.setId(IdWorker.getId());
        String phone = TokenDecode.getUserInfo().get("username");
        subComment.setUid(phone);
        subComment.setCreateedTime(SimpleDate.getDate(new Date()));
        User userInfo = userFeignClient.getUserInfo(phone);
        subComment.setNickname(userInfo.getNickname());
        subComment.setVip(userInfo.getVip());
        subComment.setHeadImg(userInfo.getHeadImg());
        subComment.setLikesCount(0);
        subComment.setReplyTarget(replyTarget);
        subComment.setContent(content);
        commentService.updateComment(subComment.getParentId());
        return mongoTemplate.save(subComment, "subComment");
    }

    @Override
    public PageUtil<SubComment> findSubComments(Map<String,String> params) {
        //先查询评论是否还在
        Comment comment = commentService.findByIdFromComment(params.get("parentId"));
        if (comment == null) {
            throw new DataNotFoundException("评论游走了！");
        }
        Integer SubCommentCounts = findSubCommentCounts(params);
        //创建分页对象
        PageUtil<SubComment> pageUtil = new PageUtil();
        //如果没评论直接返回
        if (SubCommentCounts == 0) {
            pageUtil.setLists(null);
            return pageUtil;
        }
        //设置总条数
        pageUtil.setTotalCount(SubCommentCounts);
        //设置页面显示数量
        pageUtil.sPageSize(Integer.valueOf(params.get("pageSize")));
        //设置当前页面数
        pageUtil.sCurrentPage(Integer.valueOf(params.get("currentPage")));
        //获取起始页数
        params.put("beginPos", pageUtil.gStartRow() + "");

        List<SubComment> subCommentList = findSubComment(params);
        pageUtil.setLists(subCommentList);
        return pageUtil;
    }

    //-------------------------private----------------------------------

    /**
     * 分页查List<SubComment>
     */
    private List<SubComment> findSubComment(Map<String, String> params) {
        String id = params.get("id");
        Query query = new Query(Criteria.where("parentId").is(params.get(id)));
        query.skip(Long.valueOf(params.get("beginPos"))).
                limit(Integer.valueOf(params.get("pageSize")));
        query.with(Sort.by(
                Sort.Order.desc("likesCount")));
        return mongoTemplate.find(query, SubComment.class, "subComment");
    }

    /**
     * 分页查（count）
     */
    private Integer findSubCommentCounts(Map<String, String> params) {
        String id = params.get("id");
        Query query = new Query(Criteria.where("parentId").is(params.get(id)));
        query.skip(Long.valueOf(params.get("beginPos"))).
                limit(Integer.valueOf(params.get("pageSize")));
        query.with(Sort.by(
                Sort.Order.desc("likesCount")));
        Integer count = (int) mongoTemplate.count(query, SubComment.class, "subComment");
        return count;
    }




}
