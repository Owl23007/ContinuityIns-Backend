package org.ContinuityIns.service.impl;

import org.ContinuityIns.mapper.CommentMapper;
import org.ContinuityIns.entity.Comment;
import org.ContinuityIns.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {


    @Autowired
    private CommentMapper commentMapper;

    @Override
    public void addComment(Comment comment) {
        commentMapper.addComment(comment);
    }

    @Override
    public void deleteComment(int commentId) {
        commentMapper.deleteComment(commentId);
    }

    @Override
    public List<Comment> getCommentList(String targetType, int targetId) {
        return commentMapper.getCommentList(targetType, targetId);
    }

    @Override
    public Integer getCommentData(int commentId) {
        return commentMapper.getCommentData(commentId);
    }

    @Override
    public void UpdateCommentContent(Comment comment) {
        commentMapper.UpdateCommentContent(comment);
    }

    @Override
    public void likeComment(int commentId, int userId) {
        commentMapper.likeComment(commentId, userId);
    }

    @Override
    public void cancelLikeComment(int commentId, int userId) {
        commentMapper.cancelLikeComment(commentId, userId);
    }
}