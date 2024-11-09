package org.ContinuityIns.service;

import org.ContinuityIns.pojo.Comment;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CommentService {

    //添加评论
    public void addComment(Comment comment);

    //删除评论
    public void deleteComment(int CommentId);

    //获取评论列表
    public List<Comment> getCommentList(String TargetType, int TargetId);

    //获取评论数据
    public Integer getCommentData(int CommentId);

    //修改评论内容
    public void UpdateCommentContent(Comment comment);

    //点赞评论
    public void likeComment(int CommentId,int userId);

    //取消点赞
    public void cancelLikeComment(int CommentId,int userId);

}
