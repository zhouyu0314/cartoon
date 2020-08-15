package com.cartoon.service.impl;

import com.cartoon.entity.Comment;
import com.cartoon.entity.SubComment;
import com.cartoon.entity.User;
import com.cartoon.exceptions.InsertDataException;
import com.cartoon.feign.UserFeignClient;
import com.cartoon.service.CommentService;
import com.cartoon.util.IdWorker;
import com.cartoon.util.PageUtil;
import com.cartoon.util.SimpleDate;
import com.cartoon.util.TokenDecode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 评论
     *
     * @param comment
     * @return
     * @throws InsertDataException
     */
    @Override
    public Comment addComment(Comment comment) throws InsertDataException {
        comment.setId(IdWorker.getId());
        String phone = TokenDecode.getUserInfo().get("username");
        comment.setUid(phone);
        comment.setCreateedTime(SimpleDate.getDate(new Date()));
        comment.setSubCommentCount(0);
        User userInfo = userFeignClient.getUserInfo(phone);
        comment.setNickname(userInfo.getNickname());
        comment.setVip(userInfo.getVip());
        comment.setHeadImg(userInfo.getHeadImg());
        comment.setLikesCount(0);
        return mongoTemplate.save(comment, "comment");
    }

    @Override
    public SubComment addSubComment(SubComment subComment) {
        subComment.setId(IdWorker.getId());
        String phone = TokenDecode.getUserInfo().get("username");
        subComment.setUid(phone);
        subComment.setCreateedTime(SimpleDate.getDate(new Date()));
        User userInfo = userFeignClient.getUserInfo(phone);
        subComment.setNickname(userInfo.getNickname());
        subComment.setVip(userInfo.getVip());
        subComment.setHeadImg(userInfo.getHeadImg());
        subComment.setLikesCount(0);
        subComment.setGroup(phone);
        return mongoTemplate.save(subComment, "subComment");
    }

    @Override
    public PageUtil<Comment> findComments(Map<String, String> params) {
        Integer commentsCount = findCommentCounts(params);
        //创建分页对象
        PageUtil<Comment> pageUtil = new PageUtil();
        //如果没评论直接返回
        if (commentsCount == 0) {
            pageUtil.setLists(null);
            return pageUtil;
        }
        //设置总条数
        pageUtil.setTotalCount(commentsCount);
        //设置页面显示数量
        pageUtil.sPageSize(Integer.valueOf(params.get("pageSize")));
        //设置当前页面数
        pageUtil.sCurrentPage(Integer.valueOf(params.get("currentPage")));
        //获取起始页数
        params.put("beginPos", pageUtil.gStartRow() + "");

        List<Comment> commentList = findComment(params);
        pageUtil.setLists(commentList);
        return pageUtil;
    }

    @Override
    public PageUtil<SubComment> findSubComments(Map<String, String> params) {
        return null;
    }

    //------------------------private-------------------------------

    /**
     * 分页查List<Comment>
     */
    private List<Comment> findComment(Map<String, String> params) {
        String cartoonId = params.get("cartoonId");
        Query query = null;
        if (cartoonId == null) {
            query = new Query(Criteria.where("friendsId").is(params.get("friendsId")));
        } else {
            query = new Query(Criteria.where("cartoonId").is(params.get("cartoonId")));
        }
        query.skip(Long.valueOf(params.get("beginPos"))).
                limit(Integer.valueOf(params.get("pageSize")));
        return mongoTemplate.find(query, Comment.class, "comment");
    }

    /**
     * 分页查（count）
     */
    private Integer findCommentCounts(Map<String, String> params) {
        String cartoonId = params.get("cartoonId");
        Query query = null;
        if (cartoonId == null) {
            query = new Query(Criteria.where("friendsId").is(params.get("friendsId")));
        } else {
            query = new Query(Criteria.where("cartoonId").is(params.get("cartoonId")));
        }
        Integer count = (int) mongoTemplate.count(query, Comment.class, "comment");
        return count;
    }

}