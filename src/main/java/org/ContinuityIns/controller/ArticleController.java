package org.ContinuityIns.controller;

import jakarta.validation.constraints.Pattern;
import org.ContinuityIns.factory.ArticleFactory;
import org.ContinuityIns.pojo.ViewArticle;
import org.ContinuityIns.pojo.PreviewArticle;
import org.ContinuityIns.pojo.Result;
import org.ContinuityIns.service.ArticleService;
import jakarta.validation.constraints.NotNull;
import org.ContinuityIns.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/article")
@Validated
public class ArticleController {
    /**
     *@Author:  Oii Woof
     *@Date: 2025/1/16 23:00<p>
     *@Description: 进行参数验证,并实现对文章的增删改查
     **/
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleFactory articleFactory;

    // 新增文章
    @PostMapping("/add")
    public Result onAdd(@RequestParam @Pattern(regexp = "^.{1,100}$") String title,
                        @RequestParam @Pattern(regexp = "^.{1,64000}$") String content,
                        @RequestParam String url,
                        @RequestParam String status){
        if (url == null){
            url = "";
        }
        articleService.add(title, content, url, status);
        return Result.success();
    }

    // 修改文章
    @PutMapping("/update")
    public Result onUpdate(@RequestParam Integer articleId,
                           @RequestParam @Pattern(regexp = "^.{1,100}$") String title,
                           @RequestParam @Pattern(regexp = "^.{1,64000}$") String content,
                           @RequestParam String url,
                           @RequestParam String status){
        if (title == null || content == null){
            return Result.error("标题或内容不能为空");
        }
        if (url == null){
            url = "";
        }
        articleService.update(articleId, title, content, url, status);
        return Result.success();
    }

    // 修改文章状态
    @PutMapping("/updateStatus")
    public Result onUpdateStatus(@RequestParam Integer articleId, @RequestParam String status) {
        if(articleId == null){
            return Result.error("文章id不能为空");
        }
        if(articleService.getArticleByArticleId(articleId) == null){
            return Result.error("文章不存在");
        }
        //参数验证
        if (status == null) {
            return Result.error("状态不能为空");
        }
        if (!Objects.equals(status, "发布")
                && !Objects.equals(status, "草稿")
                && !Objects.equals(status, "私密")
                && !Objects.equals(status, "封禁")) {
            return Result.error("状态不合法");
        }
        articleService.updateStatus(articleId, status);
        return Result.success();
    }

    // 删除文章
    @DeleteMapping("/delete")
    public Result onDelete(@RequestParam Integer articleId){
        articleService.delete(articleId);
        return Result.success();

    }

    // 获取文章详情byArticleId
    @GetMapping("/article/{articleId}")
    public Result onGetArticle(@PathVariable Integer articleId) {
        ViewArticle article = articleFactory.toViewArticle(articleService.getArticleByArticleId(articleId));
        if (article == null) {
            return Result.error("文章不存在");
        }
        return Result.success(article);
    }

    //  获取自己的文章列表
    @GetMapping("/myArticle")
    public Result onGetMyArticle(@RequestParam @NotNull Integer articleId){
        ViewArticle article = articleFactory.toViewArticle(articleService.getArticleByArticleId(articleId));
        if (article == null) {
            return Result.error("文章不存在");
        }
        // 权限验证
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        if (Objects.equals(article.getUserId(), id)) {
            return Result.success(article);
        }
        return Result.error("文章不存在");
    }

    // 获取用户文章预览
    @GetMapping("/userArticle")
    public Result<List<PreviewArticle>> onGetUserArticle(@RequestParam Integer userId){
        List<PreviewArticle> articles = articleService.getUserArticleByUserId(userId);
        return Result.success(articles);
    }

    // 获取每日推荐
    @GetMapping("/daily")
    public Result<List<PreviewArticle>> onGetDaily(){
        List<PreviewArticle> articles = articleService.getDaily();
        return Result.success(articles);
    }
}