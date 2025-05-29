package org.ContinuityIns.service.impl;


import org.ContinuityIns.po.CommentPO;
import org.ContinuityIns.mapper.CommentMapper;
import org.ContinuityIns.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {


    @Autowired
    private CommentMapper commentMapper;

    @Override
    public void addComment(CommentPO commentPO) {
        commentMapper.addComment(commentPO);
    }

    @Override
    public void deleteComment(int commentDTOId) {
        commentMapper.deleteComment(commentDTOId);
    }

    @Override
    public List<CommentPO> getCommentList(String targetType, int targetId) {
        return commentMapper.getCommentList(targetType, targetId);
    }

    @Override
    public Integer getCommentData(int commentDTOId) {
        return commentMapper.getCommentData(commentDTOId);
    }

    @Override
    public void UpdateCommentContent(CommentPO commentPO) {
        commentMapper.UpdateCommentContent(commentPO);
    }

    @Override
    public void likeComment(int commentDTOId, int userId) {
        commentMapper.likeComment(commentDTOId, userId);
    }

    @Override
    public void cancelLikeComment(int commentDTOId, int userId) {
        commentMapper.cancelLikeComment(commentDTOId, userId);
    }
}