package com.cartoon.controller;

import com.cartoon.dto.Dto;
import com.cartoon.dto.DtoUtil;
import com.cartoon.entity.Comment;
import com.cartoon.entity.SubComment;
import com.cartoon.service.CommentService;
import com.cartoon.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    @Value("${page.size}")
    private Integer pageSize;

    @Autowired
    private CommentService commentService;


    /**
     * 对漫画进行评论
     */
    @PostMapping("/addComment")
    public Dto addComment(Comment comment) {
        Comment data = commentService.addComment(comment);
        return DtoUtil.returnSuccess("提交评论成功！",data);
    }

    /**
     * 追评
     */
    @PostMapping("/addSubComment")
    public Dto addSubComment(String id,String content,String replyTarget) {
        SubComment data = commentService.addSubComment(id,content,replyTarget);
        return DtoUtil.returnSuccess("提交评论成功！",data);
    }

    /**
     * 查询所有评论
     * @param currentPage
     * @param cartoonId
     * @param friendsId
     * @return
     */
    @PostMapping("/findAllComment")
    public Dto findAllComment(@RequestParam(defaultValue = "0") Integer currentPage, String cartoonId,String friendsId) {
        Map<String, String> params = new HashMap<>();
        params.put("cartoonId",cartoonId);
        params.put("friendsId",friendsId);
        params.put("pageSize", pageSize+"");
        params.put("currentPage", currentPage+"");
        PageUtil<Comment> allComment = commentService.findComments(params);
        return DtoUtil.returnSuccess("success", allComment);
    }

    /**
     * 查看二级评论
     */
    @PostMapping("/findAllSubComment")
    public Dto findAllSubComment(String commentId){
        return DtoUtil.returnSuccess("子评论",commentService.findSubComments(commentId));

    }


/*    *//**
     * 对评论点赞
     *
     * @param id 评论id
     * @return
     *//*
    @GetMapping("/updateLikeCount")
    public Dto updateLikeCount(Long id) {
        commentService.updateLikeCount(id.toString());
        return DtoUtil.returnSuccess("点赞成功！");
    }

    *//**
     * 查询所有评论
     *//*
    @PostMapping("/findAllComment")
    public Dto findAllComment(@RequestParam(defaultValue = "0") Integer currentPage,String cartoonId,String parentId) {
        Map<String, Object> params = new HashMap<>();
        params.put("cartoonId",cartoonId);
        params.put("parentId",parentId);
        params.put("pageSize", pageSize);
        params.put("currentPage", currentPage);
        PageUtil<Comment> allComment = commentService.findAllComment(params);
        return DtoUtil.returnSuccess("success", allComment);
    }


    *//**
     * 测试方法
     *//*
    @GetMapping("/findLikeAndCommentById")
    public List<Comment> findLikeAndCommentById(){
        return commentService.findLikeAndCommentById();

    }


    //-------------------------------feign--------------------------------------------------------

    *//**
     * 前端间隔访问后端接口（查询当前用户redis中有多少通知消息）
     * @return
     *//*
    @GetMapping("/findMsgCount")
    public Long findMsgCount(){
        return commentService.findMsgCount();
    }

    @GetMapping("/consumeRedis")
    public void consumeRedis(){
        commentService.consumeRedis();
    }*/
}
