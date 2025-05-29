package org.ContinuityIns.controller;

import jakarta.validation.Valid;
import org.ContinuityIns.po.CategoryPO;
import org.ContinuityIns.common.Result;
import org.ContinuityIns.po.ArticlePO;
import org.ContinuityIns.service.ArticleService;
import org.ContinuityIns.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/article")
@Validated
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/page")
    public Result getArticlePage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String sortBy) {
        if (pageNum < 1 || pageSize < 1) {
            return Result.error("无效的分页参数");
        }
        return articleService.getArticlePage(pageNum, pageSize, sortBy);
    }

    @GetMapping("/search")
    public Result searchArticles(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        if (pageNum < 1 || pageSize < 1) {
            return Result.error("无效的分页参数");
        }
        if (keyword == null || keyword.trim().isEmpty()) {
            return Result.error("搜索关键词不能为空");
        }
        return articleService.searchArticles(keyword, pageNum, pageSize);
    }

    @GetMapping("/my")
    public Result getMyArticles(
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        // 从ThreadLocal中获取当前登录用户ID
        Integer userId = (Integer) ThreadLocalUtil.get().get("id");
        if (userId == null) {
            return Result.error("用户未登录");
        }
        return articleService.getUserArticles(userId, status, pageNum, pageSize);
    }

    @GetMapping("/{id}")
    public Result getArticleById(@PathVariable("id") Integer articleId) {
        if (articleId == null || articleId <= 0) {
            return Result.error("无效的文章ID");
        }
        // 从ThreadLocal中获取当前登录用户ID（可能为null，表示未登录用户）
        Map<String, Object> userInfo = ThreadLocalUtil.get();
        Integer viewerId = userInfo != null ? (Integer) userInfo.get("id") : 0;
        return articleService.viewArticle(articleId, viewerId);
    }

    @GetMapping("/{id}/check-edit-permission")
    public Result checkEditPermission(@PathVariable("id") Integer articleId) {
        Integer userId = ThreadLocalUtil.get() != null ? (Integer) ThreadLocalUtil.get().get("id") : null;
        if (userId == null) {
            return Result.error("用户未登录");
        }
        return articleService.checkEditPermission(articleId, userId);
    }

    @GetMapping("/profile")
    public Result<List<ArticlePO>> getMyArticle() {
        Integer userId = (Integer) ThreadLocalUtil.get().get("id");
        System.out.println("userId: " + userId);
        return articleService.getArticleProfileList(userId);
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
    public Result updateArticle(@PathVariable("id") Integer articleId,
                                @RequestBody @Valid ArticlePO articlePO) {
        if (articleId == null || articleId <= 0) {
            return Result.error("无效的文章ID");
        }
        // 检查编辑权限
        Integer currentUserId = (Integer) ThreadLocalUtil.get().get("id");
        if (currentUserId == null) {
            return Result.error("用户未登录");
        }
        Result<Boolean> permissionCheck = articleService.checkEditPermission(articleId, currentUserId);
        if (!permissionCheck.getData()) {
            return Result.error("没有编辑权限");
        }

        articlePO.setArticleId(articleId);
        return articleService.updateArticle(articlePO);
    }

    @DeleteMapping("/{id}")
    public Result deleteArticle(@PathVariable("id") Integer articleId) {
        if (articleId == null || articleId <= 0) {
            return Result.error("无效的文章ID");
        }
        // 检查删除权限
        Integer currentUserId = (Integer) ThreadLocalUtil.get().get("id");
        if (currentUserId == null) {
            return Result.error("用户未登录");
        }
        Result<Boolean> permissionCheck = articleService.checkEditPermission(articleId, currentUserId);
        if (!permissionCheck.getData()) {
            return Result.error("没有删除权限");
        }

        return articleService.deleteArticle(articleId);
    }

    @GetMapping("/user/{userId}")
    public Result getArticlesByUser(@PathVariable("userId") Integer userId) {
        if (userId == null || userId <= 0) {
            return Result.error("无效的用户ID");
        }
        return articleService.getArticlesByUser(userId);
    }

    @PostMapping("/create")
    public Result createArticle(@RequestBody ArticlePO articlePO) {
        if (articlePO == null) {
            return Result.error("无效的文章数据");
        }
        System.out.println(articlePO);
        return articleService.createArticle(articlePO);
    }

    @GetMapping("/categories")
    public Result<List<CategoryPO>> getCategories() {
        return articleService.getCategories();
    }

    /**
     * 获取文章统计信息
     * @return 包含统计信息的结果
     */
    @GetMapping("/stats")
    public Result<Map<String, Object>> getArticleStats() {
        return articleService.getArticleStats();
    }
}