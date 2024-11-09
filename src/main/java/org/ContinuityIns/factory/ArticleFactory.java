package org.ContinuityIns.factory;

import org.ContinuityIns.mapper.ArticleMapper;
import org.ContinuityIns.pojo.Article;
import org.ContinuityIns.pojo.PreviewArticle;
import org.ContinuityIns.pojo.User;
import org.ContinuityIns.pojo.ViewArticle;
import org.ContinuityIns.service.CommentService;
import org.ContinuityIns.service.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ArticleFactory {
    /**
     * 文章对象加工类
     * @Author: Oii Woof
     * @Date: 2025/1/12
     * @Description: 用于对文章对象进行加工处理<p>
     * 1.实现将文章对象转换为预览文章对象<p>
     * 2.实现将文章对象转换为查看文章对象
     * @Tips： 工厂类主要目的是将文章对象转换为预览文章对象和查看文章对象，以便于前端展示
     */
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private CommentService commentService;

    //将文章对象转换为预览文章对象
    public PreviewArticle toPreviewArticle(@NotNull Article article) {
        PreviewArticle previewArticle = new PreviewArticle(article);
        //设置作者昵称
        previewArticle.setAuthor((userService.getUserById(article.getUserId())).getNickname());
        //设置文章浏览量、点赞量、收藏量
        previewArticle.setViewCount(articleMapper.getArticleData(article.getArticleId()).getViewCount());
        previewArticle.setStarCount(articleMapper.getArticleData(article.getArticleId()).getStarCount());
        previewArticle.setLikeCount(articleMapper.getArticleData(article.getArticleId()).getLikeCount());

        return previewArticle;
    }

    //将文章对象转换为查看文章对象
    public ViewArticle toViewArticle(@NotNull Article article) {
        ViewArticle viewArticle = new ViewArticle(article);
        //设置作者昵称
        viewArticle.setAuthor((userService.getUserById(article.getUserId())).getNickname());
        //设置文章浏览量、点赞量、收藏量
        viewArticle.setViewCount(articleMapper.getArticleData(article.getArticleId()).getViewCount());
        viewArticle.setStarCount(articleMapper.getArticleData(article.getArticleId()).getStarCount());
        viewArticle.setLikeCount(articleMapper.getArticleData(article.getArticleId()).getLikeCount());
        //设置文章评论列表
        viewArticle.setComments(commentService.getCommentList("article", article.getArticleId()));

        return viewArticle;
    }
}