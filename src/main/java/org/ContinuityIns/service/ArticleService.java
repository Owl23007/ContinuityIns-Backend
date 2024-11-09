package org.ContinuityIns.service;

import org.ContinuityIns.pojo.PreviewArticle;
import org.ContinuityIns.pojo.ViewArticle;

import java.util.List;

public interface ArticleService {
    /**Author:  Oii Woof
     *Date: 2025/1/16 23:10<p>
     *Name: ArticleService<p>
     *Description: 进行身份验证与实现具体的业务逻辑
     **/

    // 增加文章
    void add(String title, String content, String cover, String status);

    // 修改文章
    void update(Integer articleId, String title, String content, String cover, String status);

    //修改文章状态
    void updateStatus(Integer articleId, String status);

    // 删除文章
    void delete(Integer articleId);

    // 根据文章id获取文章
    ViewArticle getArticleByArticleId(Integer articleId);

    // 获取用户的文章预览信息
    List<PreviewArticle> getUserArticle();

    // 获取用户的文章预览信息ByUserId
    List<PreviewArticle> getUserArticleByUserId(Integer userId);


    // 获取每日推荐
    List<PreviewArticle> getDaily();
}
