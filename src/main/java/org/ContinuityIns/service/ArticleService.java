package org.ContinuityIns.service;

import org.ContinuityIns.DAO.ArticleDAO;
import org.ContinuityIns.DAO.CategoryDAO;
import org.ContinuityIns.common.Result;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
    Result<List<ArticleDAO>> getArticleProfileList(Integer userId);

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

    /**
     * 分页获取文章列表
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @param sortBy 排序字段（可选）
     * @return 包含分页信息和文章列表的结果
     */
    Result<Map<String, Object>> getArticlePage(Integer pageNum, Integer pageSize, String sortBy);

    /**
     * 搜索文章
     * @param keyword 搜索关键词
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return 包含搜索结果和分页信息的结果
     */
    Result<Map<String, Object>> searchArticles(String keyword, Integer pageNum, Integer pageSize);

    /**
     * 点赞文章
     * @param articleId 文章ID
     * @param userId 用户ID
     * @return 操作结果
     */
    Result<String> likeArticle(Integer articleId, Integer userId);

    /**
     * 取消点赞文章
     * @param articleId 文章ID
     * @param userId 用户ID
     * @return 操作结果
     */
    Result<String> unlikeArticle(Integer articleId, Integer userId);

    /**
     * 检查用户是否已点赞文章
     * @param articleId 文章ID
     * @param userId 用户ID
     * @return 包含是否已点赞信息的结果
     */
    Result<Boolean> checkUserLiked(Integer articleId, Integer userId);

    /**
     * 获取用户自己的文章列表
     * @param userId 用户ID
     * @param status 文章状态（可选）
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return 包含文章列表和分页信息的结果
     */
    Result<Map<String, Object>> getUserArticles(Integer userId, String status, Integer pageNum, Integer pageSize);

    /**
     * 查看文章详情
     * @param articleId 文章ID
     * @param viewerId 查看者ID
     * @return 文章详情
     */
    Result<ArticleDAO> viewArticle(Integer articleId, Integer viewerId);

    /**
     * 检查用户是否有权限编辑文章
     * @param articleId 文章ID
     * @param userId 用户ID
     * @return 是否有编辑权限
     */
    Result<Boolean> checkEditPermission(Integer articleId, Integer userId);

    Result<List<CategoryDAO>> getCategories();
    /**
     * 获取文章统计信息
     * @return 包含统计信息的结果
     */
    Result<Map<String, Object>> getArticleStats();
}