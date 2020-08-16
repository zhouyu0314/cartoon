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
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/addlikes")
    public Dto addlikes(String id){
        commentService.addLikes(id);
        return DtoUtil.returnSuccess("点赞成功！");
    }



}
