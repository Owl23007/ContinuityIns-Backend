package org.ContinuityIns.service.impl;

import org.ContinuityIns.factory.ArticleFactory;
import org.ContinuityIns.mapper.ArticleMapper;
import org.ContinuityIns.pojo.Article;
import org.ContinuityIns.pojo.PreviewArticle;
import org.ContinuityIns.pojo.ViewArticle;
import org.ContinuityIns.service.ArticleService;
import org.ContinuityIns.service.UserService;
import org.ContinuityIns.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private ArticleFactory articleFactory;

    // 添加文章
    @Override
    public void add(String title, String content, String coverImage, String status) {
        //权限验证
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");

        // 获取文章字数
        Integer duration = content.length();

        articleMapper.addArticle(title,id, coverImage,content , duration, status);
    }

    // 修改文章
    @Override
    public void update(Integer articleId, String title, String content, String coverImage, String status) {
        //权限验证
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        Article article = articleMapper.getArticleById(articleId);
        if (!Objects.equals(article.getUserId(), id)){
            return;
        }
        // 获取文章字数
        Integer duration = content.length();
        articleMapper.updateArticle(articleId, title, coverImage, content, duration, status);
    }

    // 修改文章状态
    @Override
    public void updateStatus(Integer articleId, String status) {
        //权限验证
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        Article article = articleMapper.getArticleById(articleId);
        if (!Objects.equals(article.getUserId(), id)){
            return;
        }
        articleMapper.updateStatus(articleId, status);
    }



    // 根据Id获取文章详情
    @Override
    public ViewArticle getArticleByArticleId(Integer id) {
        Article article = articleMapper.getArticleById(id);
        if (article == null) {
            return null;
        }

        //身份验证
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        if (!Objects.equals(article.getUserId(), userId) && !Objects.equals(article.getStatus(), "发布")){
            return null;
        }
        return articleFactory.toViewArticle(article);
    }

    // 获取用户的文章
    @Override
    public List<PreviewArticle> getUserArticle() {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        List<Article> articleList = articleMapper.getArticleListByUser(id);

        List<PreviewArticle> previewArticleList = new ArrayList<>();

        for (Article article : articleList) {
            PreviewArticle previewArticle = articleFactory.toPreviewArticle(article);
            previewArticleList.add(previewArticle);
        }

        return previewArticleList;
    }

    @Override
    public List<PreviewArticle> getUserArticleByUserId(Integer userId) {
        List<Article> articleList = articleMapper.getArticleListByUser(userId);
        List<PreviewArticle> previewArticleList = new ArrayList<>();
        for (Article article : articleList) {
            PreviewArticle previewArticle = articleFactory.toPreviewArticle(article);
            previewArticleList.add(previewArticle);
        }

        //身份验证-只访问已发布文章
        previewArticleList.removeIf(previewArticle -> !Objects.equals(previewArticle.getStatus(), "发布"));
        return previewArticleList;
    }

    // 删除文章
    @Override
    public void delete(Integer articleId) {
        //权限验证
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        Article article = articleMapper.getArticleById(articleId);
        if (!Objects.equals(article.getUserId(), id)){
            return;
        }
        articleMapper.deleteArticle(articleId);
    }

    // 获取每日推荐
    @Override
    public List<PreviewArticle> getDaily() {
        //TODO: 实现每日推荐-对是否登录进行判断，未登录返回默认推荐，这里需要写一个默认推荐的方法和一个个性化推荐的方法
      return null;
    }

}
