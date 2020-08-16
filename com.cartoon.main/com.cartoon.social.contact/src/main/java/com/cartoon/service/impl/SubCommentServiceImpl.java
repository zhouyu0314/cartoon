package com.cartoon.service.impl;

import com.cartoon.entity.Comment;
import com.cartoon.entity.SubComment;
import com.cartoon.entity.User;
import com.cartoon.exceptions.DataNotFoundException;
import com.cartoon.feign.UserFeignClient;
import com.cartoon.service.CommentService;
import com.cartoon.service.SubCommentService;
import com.cartoon.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
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

    @Autowired
    private RedisUtil redisUtil;

    //子评论
    @Override
    public SubComment addSubComment(String id, String content) {


        //先查找主评论
        List<Comment> comment = commentService.findByIdFromComment(id);
        SubComment subComment = new SubComment();
        String phone = TokenDecode.getUserInfo().get("username");
        User userInfo = userFeignClient.getUserInfo(phone);
        if (comment == null || comment.size()==0) {
            //如果不是对主评论的回复，而是对二级评论的回复
            List<SubComment> data = findByIdFromSubComment(id);
            if (data==null || data.size()==0) {
                throw new DataNotFoundException();
            }else{
                //不是对主评论的评论
                subComment.setParentId(data.get(0).getParentId());
                //设置回复目标，主评论回复设为null
                subComment.setReplyTarget(data.get(0).getUid());
                String uid = commentService.findByIdFromComment(data.get(0).getParentId()).get(0).getUid();
                subComment.setMaster(phone.equals(uid)?true:false);

            }
        }else{
            //设置回复目标，主评论回复设为null
            subComment.setReplyTarget(null);
            subComment.setParentId(id);
            subComment.setMaster(phone.equals(comment.get(0).getUid())?true:false);
        }

        subComment.setId(IdWorker.getId());

        subComment.setUid(phone);
        subComment.setCreateedTime(SimpleDate.getDate(new Date()));

        subComment.setNickname(userInfo.getNickname());
        subComment.setVip(userInfo.getVip());
        subComment.setHeadImg(userInfo.getHeadImg());
        subComment.setLikesCount(0);
        subComment.setContent(content);

        //修改主评论数量
        commentService.updateComment(subComment.getParentId());
        //当评论完后会有通知 存list 一个月
        if (subComment.getReplyTarget()==null) {
            redisUtil.lSet("comment to:"+comment.get(0).getUid(),1,60*60*24*30);
        }else{
            redisUtil.lSet("comment to:"+subComment.getReplyTarget(),1,60*60*24*30);
        }

        return mongoTemplate.save(subComment, "subComment");
    }

    @Override
    public PageUtil<SubComment> findSubComments(Map<String,String> params) {
        //先查询评论是否还在
        List<Comment> comment = commentService.findByIdFromComment(params.get("id"));
        if (comment == null || comment.size()==0) {
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

    //点赞
    @Override
    public void addLikes(String id) throws DataNotFoundException {
        List<SubComment> subComments = findByIdFromSubComment(id);
        if (subComments==null || subComments.size()==0) {
            throw new DataNotFoundException("评论游走了！");
        }

        SubComment subComment = subComments.get(0);
        String phone = TokenDecode.getUserInfo().get("username");
        User userInfo = userFeignClient.getUserInfo(phone);
        SubComment redisLikes = new SubComment();
        redisLikes.setUid(phone);
        redisLikes.setNickname(userInfo.getNickname());
        redisLikes.setVip(userInfo.getVip());
        redisLikes.setHeadImg(userInfo.getHeadImg());
        redisLikes.setCreateedTime(SimpleDate.getDate1(new Date()));
        if (!redisUtil.sHasKey("like to:"+subComment.getUid(),redisLikes)) {
            //如果没有点过赞
            Query query = new Query(Criteria.where("_id").is(id));
            mongoTemplate.updateFirst(query, new Update().inc("likesCount", 1), SubComment.class, "subComment");
        }
        redisUtil.sSetAndTime("like to:"+subComment.getUid(),60*60*24*30,redisLikes);
        //再存一个提醒redis
        redisUtil.sSetAndTime("like to dead:"+subComment.getUid(),60*60*24*30,redisLikes);
    }

    //-------------------------private----------------------------------

    /**
     * 分页查List<SubComment>
     */
    private List<SubComment> findSubComment(Map<String, String> params) {
        String id = params.get("id");
        Query query = new Query(Criteria.where("parentId").is(id));
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
        Query query = new Query(Criteria.where("parentId").is(id));
        Integer count = (int) mongoTemplate.count(query, SubComment.class, "subComment");
        return count;
    }

    /**
     * 根据id查
     */
    public List<SubComment> findByIdFromSubComment(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        List<SubComment> subCommentList = mongoTemplate.find(query, SubComment.class, "subComment");
        return subCommentList;
    }




}
