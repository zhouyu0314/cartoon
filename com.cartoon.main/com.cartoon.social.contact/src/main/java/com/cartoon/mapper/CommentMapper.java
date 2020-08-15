package com.cartoon.mapper;
import com.cartoon.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface CommentMapper {

	Comment getCommentById(@Param(value = "id") String id);

	List<Comment> getCommentListByMap(Map<String,Object> param);

	Integer getCommentCountByMap(Map<String,Object> param);

	Integer insertComment(Comment comment);

	Integer updateComment(Comment comment);

	Integer updateLikeCount(Long id);






}
