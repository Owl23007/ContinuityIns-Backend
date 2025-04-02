package org.ContinuityIns.controller;

import org.ContinuityIns.DAO.CommentDAO;
import org.ContinuityIns.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {
    /**
     * 评论控制器
     * @Author  Oii Woof
     * @Date 2025/1/16 23:00
     * @Description 进行评论的增删改查
     */

    @Autowired
    private CommentService commentService;

    // 添加评论
    @PostMapping
    public void addComment(@RequestBody CommentDAO commentDAO) {
        commentService.addComment(commentDAO);
    }

    // 删除评论
    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Integer id) {
        commentService.deleteComment(id);
    }

    // 点赞评论
    @PostMapping("/{id}/like")
    public void likeComment(@PathVariable Integer id, @RequestParam Integer userId) {
        commentService.likeComment(id, userId);
    }

    // 取消点赞
    @PostMapping("/{id}/cancel-like")
    public void cancelLikeComment(@PathVariable Integer id, @RequestParam Integer userId) {
        commentService.cancelLikeComment(id, userId);
    }

    // 获取评论列表
    @GetMapping("/list")
    public List<CommentDAO> getCommentList(@RequestParam Integer id, @RequestParam String type) {
        return commentService.getCommentList(type, id);
    }
}