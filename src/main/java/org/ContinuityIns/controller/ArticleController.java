package org.ContinuityIns.controller;

import jakarta.validation.Valid;
import org.ContinuityIns.common.Result;
import org.ContinuityIns.DAO.ArticleDAO;
import org.ContinuityIns.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article")
@Validated
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/mine")
    public Result<List<ArticleDAO>> getMyArticle() {
        return articleService.getArticleListBySelf();
    }

    @GetMapping("/{id}")
    public Result<ArticleDAO> getArticleById(@PathVariable("id") Integer id) {
        if (id == null || id <= 0) {
            return Result.error("无效的文章ID");
        }
        return articleService.getArticleById(id);
    }

    @PutMapping("/{id}/status")
    public Result updateArticleStatus(@PathVariable("id") Integer articleId, 
                                 @RequestParam String status) {
        if (articleId == null || articleId <= 0 || status == null || status.isEmpty()) {
            return Result.error("无效的文章ID或状态");
        }
        return articleService.updateArticleStatus(articleId, status);
    }

    @PutMapping("/{id}")
    public Result updateArticle(@PathVariable("id") Long articleId,
                           @RequestBody @Valid ArticleDAO articleDAO) {
        if (articleId == null || articleId <= 0) {
            return Result.error("无效的文章ID");
        }
        articleDAO.setArticleId(articleId);
        return articleService.updateArticle(articleDAO);
    }

    @DeleteMapping("/{id}")
    public Result deleteArticle(@PathVariable("id") Integer articleId) {
        if (articleId == null || articleId <= 0) {
            return Result.error("无效的文章ID");
        }
        return articleService.deleteArticle(articleId);
    }

    @GetMapping("/user/{userId}")
    public Result<List<ArticleDAO>> getArticlesByUser(@PathVariable("userId") Integer userId) {
        if (userId == null || userId <= 0) {
            return Result.error("无效的用户ID");
        }
        return articleService.getArticlesByUser(userId);
    }

    @PostMapping("/create")
    public Result createArticle(@RequestBody @Valid ArticleDAO articleDAO) {
        if (articleDAO == null || articleDAO.getTitle() == null || articleDAO.getContent() == null ) {
            return Result.error("无效的文章数据");
        }
        return articleService.createArticle(articleDAO);
    }
}