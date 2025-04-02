package org.ContinuityIns.service;

import org.ContinuityIns.DAO.ArticleDAO;
import org.ContinuityIns.common.Result;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ArticleService {
    /**Author:  Oii Woof
     *Date: 2025/1/16 23:10<p>
     *Name: ArticleService<p>
     *Description: 进行身份验证与实现具体的业务逻辑
     **/

    // 创建文章
    Result createArticle(ArticleDAO articleDAO);

    // 获取当前用户的文章列表
    Result<List<ArticleDAO>> getArticleListBySelf();

    // 更新文章状态
    Result updateArticleStatus(int articleId, String status);
    
    // 通过ID获取文章详情
    Result<ArticleDAO> getArticleById(Integer id);
    
    // 更新文章
    Result updateArticle(ArticleDAO articleDAO);
    
    // 删除文章
    Result deleteArticle(Integer articleId);
    
    // 获取用户文章
    Result<List<ArticleDAO>> getArticlesByUser(Integer userId);

}
