package com.cartoon.controller;

import com.cartoon.dto.Dto;
import com.cartoon.dto.DtoUtil;
import com.cartoon.entity.SubComment;
import com.cartoon.service.SubCommentService;
import com.cartoon.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

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
    public Dto addSubComment(String id, String content) {
        SubComment data = subCmmentService.addSubComment(id, content);
        return DtoUtil.returnSuccess("提交评论成功！", data);
    }


    /**
     * 查看二级评论
     */
    @PostMapping("/findAllSubComment")
    public Dto findAllSubComment(@RequestParam(defaultValue = "0") Integer currentPage, String id) {
        Map<String, String> params = new HashMap<>();
        params.put("id", id);
        params.put("pageSize", pageSize + "");
        params.put("currentPage", currentPage + "");
        PageUtil<SubComment> subComments = subCmmentService.findSubComments(params);
        return DtoUtil.returnSuccess("子评论", subComments);

    }

    /**
     * 点赞
     * @param id
     * @return
     */
    @GetMapping("/addlikes")
    public Dto addlikes(String id){
        subCmmentService.addLikes(id);
        return DtoUtil.returnSuccess("点赞成功！");
    }

    /**
     * 前端间隔访问获取通知
     */
    @GetMapping("/getNoticesCount")
    public Dto getNoticesCount(){
        return DtoUtil.returnSuccess("当前通知数量",subCmmentService.getNoticesCount());
    }

    /**
     * 查看谁给我点过赞和评论
     */
    @GetMapping("/getNotices")
    public Dto getNotices(@RequestParam(defaultValue = "0") Integer currentPage){
        Map<String,Object> params = new HashMap<>();
        params.put("pageSize", pageSize + "");
        params.put("currentPage", currentPage + "");
        return DtoUtil.returnSuccess("最近的通知",subCmmentService.getNotices(params));
    }

}
