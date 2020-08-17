package com.cartoon.service.impl;

import com.cartoon.entity.Comment;
import com.cartoon.entity.SubComment;
import com.cartoon.entity.User;
import com.cartoon.exceptions.DataNotFoundException;
import com.cartoon.exceptions.InsertDataException;
import com.cartoon.feign.UserFeignClient;
import com.cartoon.service.CommentService;
import com.cartoon.util.*;
import com.rabbitmq.client.ListAddressResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.mapreduce.GroupByResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    RedisUtil redisUtil;

    //评论
    @Override
    public Comment addComment(Comment comment) throws InsertDataException {
        //先查找此漫画
        //需要远程调用feign
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


    //查找评论
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


//根据id查询评论

    @Override
    public List<Comment> findByIdFromComment(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        List<Comment> commentList = mongoTemplate.find(query, Comment.class, "comment");
        return commentList;
    }


    //修改comment子评论数量

    @Override
    public void updateComment(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        mongoTemplate.updateFirst(query, new Update().inc("subCommentCount", 1), Comment.class, "comment");
    }

    //点赞
    @Override
    public void addLikes(String id)throws DataNotFoundException {
        List<Comment> comments = findByIdFromComment(id);
        if (comments==null || comments.size()==0) {
            throw new DataNotFoundException("评论游走了！");
        }

        Comment comment = comments.get(0);
        String phone = TokenDecode.getUserInfo().get("username");
        User userInfo = userFeignClient.getUserInfo(phone);
        Comment redisLikes = new Comment();
        redisLikes.setUid(phone);
        redisLikes.setNickname(userInfo.getNickname());
        redisLikes.setVip(userInfo.getVip());
        redisLikes.setHeadImg(userInfo.getHeadImg());
        redisLikes.setCreateedTime(SimpleDate.getDate1(new Date()));
        if (redisUtil.zGetScore("notices:" + comment.getUid(), redisLikes) == null) {
            //如果没有点过赞
            Query query = new Query(Criteria.where("_id").is(id));
            mongoTemplate.updateFirst(query, new Update().inc("likesCount", 1), Comment.class, "comment");
        }
        //redisUtil.sSetAndTime("like to:"+comment.getUid(),60*60*24*30,redisLikes);
        redisUtil.zadd("notices:"+comment.getUid(),redisLikes,(double)System.currentTimeMillis()*-1);
        //再存一个提醒redis
        redisUtil.sSetAndTime("like to dead:"+comment.getUid(),60*60*24*30,redisLikes);

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
        query.with(Sort.by(
                Sort.Order.desc("likesCount"),
                Sort.Order.desc("subCommentCount")));
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
