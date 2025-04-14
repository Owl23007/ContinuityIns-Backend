package org.ContinuityIns.service.impl;

import org.ContinuityIns.common.Result;
import org.ContinuityIns.DAO.ArticleDAO;
import org.ContinuityIns.mapper.ArticleMapper;
import org.ContinuityIns.mapper.UserMapper;
import org.ContinuityIns.service.ArticleService;
import org.ContinuityIns.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.apache.catalina.security.SecurityUtil.remove;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public Result createArticle(ArticleDAO articleDAO) {
        // 获取当前用户的ID
        Integer userId = (Integer) ThreadLocalUtil.get().get("id");
        articleDAO.setUserId(userId);

        articleDAO.setDuration(articleDAO.getContent().length());
        int rowsAffected = articleMapper.insertArticle(articleDAO);
        if (rowsAffected > 0) {
            return Result.success("文章创建成功");
        } else {
            return Result.error("创建文章失败");
        }
    }

    @Override
    public Result<List<ArticleDAO>> getArticleProfileList(Integer userId) {
        if (userId == 0 || userId == null) {
            userId = (Integer) ThreadLocalUtil.get().get("id");
        }

        List<ArticleDAO> articles = articleMapper.selectArticlesByUser(userId);

        // 过滤掉非Published状态的文章
        articles.removeIf(article -> !article.getStatus().equals(ArticleDAO.ArticleStatus.PUBLISHED));
        //获取最新的10篇文章
        articles = articles.subList(0, Math.min(articles.size(), 10));
        // 只保留文本前20个字符
        for (ArticleDAO article : articles) {
            String content = article.getContent();
            if (content.length() > 20) {
                article.setContent(content.substring(0, 20)+"...");
            }
            String username = userMapper.getUserById(article.getUserId()).getUsername();
            article.setUsername(username);
        }
        // 添加文章浏览数据
        articles.forEach(article -> {
            article.setViewCount(article.getViewCount());
            article.setLikeCount(article.getLikeCount());
            article.setCommentCount(article.getCommentCount());
            article.setCollectionCount(article.getCollectionCount());
        });
        return Result.success(articles);
    }

    @Override
    public Result updateArticleStatus(int articleId, String status) {
        if (status == null || status.isEmpty()) {
            return Result.error("无效的状态");
        }
        int rowsAffected = articleMapper.updateArticleStatus(articleId, status);
        if (rowsAffected > 0) {
            return Result.success("文章状态更新成功");
        } else {
            return Result.error("更新文章状态失败");
        }
    }

    @Override
    public Result<ArticleDAO> getArticleById(Integer articleId) {
        if (articleId == null || articleId <= 0) {
            return Result.error("无效的文章ID");
        }
        ArticleDAO article = articleMapper.selectArticleById(articleId);
        if (article != null) {
            return Result.success(article);
        } else {
            return Result.error("文章未找到");
        }
    }

    @Override
    public Result updateArticle(ArticleDAO articleDAO) {
        if (articleDAO == null || articleDAO.getArticleId() == null || articleDAO.getArticleId() <= 0) {
            return Result.error("无效的文章数据");
        }
        articleDAO.setUserId((Integer) ThreadLocalUtil.get().get("id"));
        articleDAO.setDuration(articleDAO.getContent().length());
        int rowsAffected = articleMapper.updateArticle(articleDAO);
        if (rowsAffected > 0) {
            return Result.success("文章更新成功");
        } else {
            return Result.error("更新文章失败");
        }
    }

    @Override
    public Result deleteArticle(Integer articleId) {
        if (articleId == null || articleId <= 0) {
            return Result.error("无效的文章ID");
        }
        int rowsAffected = articleMapper.deleteArticle(articleId);
        if (rowsAffected > 0) {
            return Result.success("文章删除成功");
        } else {
            return Result.error("删除文章失败");
        }
    }

    @Override
    public Result<List<ArticleDAO>> getArticlesByUser(Integer userId) {
        if (userId == null || userId <= 0) {
            return Result.error("无效的用户ID");
        }
        List<ArticleDAO> articles = articleMapper.selectArticlesByUser(userId);

        // 返回Published
        articles.forEach(article -> {
            if (article.getStatus() == null || !article.getStatus().equals(ArticleDAO.ArticleStatus.PUBLISHED)) {
                articles.remove(article);
            }
        });

        return Result.success(articles);
    }
}