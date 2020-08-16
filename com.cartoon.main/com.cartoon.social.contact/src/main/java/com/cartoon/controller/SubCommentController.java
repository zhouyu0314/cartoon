package com.cartoon.controller;

import com.cartoon.dto.Dto;
import com.cartoon.dto.DtoUtil;
import com.cartoon.entity.SubComment;
import com.cartoon.service.SubCommentService;
import com.cartoon.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/subComment")
public class SubCommentController {
    @Value("${page.size}")
    private Integer pageSize;

    @Autowired
    private SubCommentService subCmmentService;

    /**
     * 追评
     */
    @PostMapping("/addSubComment")
    public Dto addSubComment(String id,String content,String replyTarget) {
        SubComment data = subCmmentService.addSubComment(id,content,replyTarget);
        return DtoUtil.returnSuccess("提交评论成功！",data);
    }


    /**
     * 查看二级评论
     */
    @PostMapping("/findAllSubComment")
    public Dto findAllSubComment(@RequestParam(defaultValue = "0") Integer currentPage, String id){
        Map<String, String> params = new HashMap<>();
        params.put("id",id);
        params.put("pageSize", pageSize+"");
        params.put("currentPage", currentPage+"");
        PageUtil<SubComment> subComments = subCmmentService.findSubComments(params);
        return DtoUtil.returnSuccess("子评论",subComments);

    }
}
