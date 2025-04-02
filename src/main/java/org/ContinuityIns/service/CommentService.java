package org.ContinuityIns.service;

import org.ContinuityIns.DAO.CommentDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {

    //添加评论
    void addComment( CommentDAO comment);

    //删除评论
    void deleteComment(int CommentId);

    //获取评论列表
    List<CommentDAO> getCommentList(String TargetType, int TargetId);

    //获取评论数据
    Integer getCommentData(int CommentId);

    //修改评论内容
    void UpdateCommentContent(CommentDAO comment);

    //点赞评论
    void likeComment(int CommentId, int userId);

    //取消点赞
    void cancelLikeComment(int CommentId, int userId);

}
