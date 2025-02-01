package org.ContinuityIns.service;

import org.ContinuityIns.DTO.CommentDTO;

import java.util.List;

public interface CommentService {

    //添加评论
    void addComment( CommentDTO comment);

    //删除评论
    void deleteComment(int CommentId);

    //获取评论列表
    List<CommentDTO> getCommentList(String TargetType, int TargetId);

    //获取评论数据
    Integer getCommentData(int CommentId);

    //修改评论内容
    void UpdateCommentContent(CommentDTO comment);

    //点赞评论
    void likeComment(int CommentId, int userId);

    //取消点赞
    void cancelLikeComment(int CommentId, int userId);

}
