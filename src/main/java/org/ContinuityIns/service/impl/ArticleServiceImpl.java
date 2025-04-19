package org.ContinuityIns.service.impl;

import org.ContinuityIns.DAO.UserDAO;
import org.ContinuityIns.common.Result;
import org.ContinuityIns.DAO.ArticleDAO;
import org.ContinuityIns.mapper.ArticleMapper;
import org.ContinuityIns.mapper.UserMapper;
import org.ContinuityIns.mapper.ViewMapper;
import org.ContinuityIns.service.ArticleService;
import org.ContinuityIns.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ViewMapper viewMapper;

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
            UserDAO user= userMapper.getUserById(article.getUserId());
            article.setAvatarImage(user.getAvatarImage());
            article.setNickname(user.getNickname());
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
        
        Integer currentUserId = (Integer) ThreadLocalUtil.get().get("id");
        // 检查编辑权限
        Result<Boolean> permissionCheck = checkEditPermission(articleDAO.getArticleId(), currentUserId);
        if (!permissionCheck.getMessage() .equals("success") || !permissionCheck.getData()) {
            return Result.error("没有编辑权限");
        }

        articleDAO.setUserId(currentUserId);
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

        Integer currentUserId = (Integer) ThreadLocalUtil.get().get("id");
        // 检查删除权限
        Result<Boolean> permissionCheck = checkEditPermission(articleId, currentUserId);
        if (!permissionCheck.getMessage() .equals("success")|| !permissionCheck.getData()) {
            return Result.error("没有删除权限");
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

    @Override
    public Result<Map<String, Object>> getArticlePage(Integer pageNum, Integer pageSize, String sortBy) {
        // 参数验证
        if (pageNum == null || pageNum < 1 || pageSize == null || pageSize < 1) {
            return Result.error("无效的分页参数");
        }

        try {
            // 计算偏移量
            int offset = (pageNum - 1) * pageSize;
            
            // 获取分页数据
            List<ArticleDAO> articles = articleMapper.selectArticlesPage(offset, pageSize, sortBy);
            
            // 获取总记录数
            int total = articleMapper.selectTotalArticleCount();
            
            // 计算总页数
            int totalPages = (total + pageSize - 1) / pageSize;
            
            // 为每篇文章添加用户名
            for (ArticleDAO article : articles) {
                UserDAO user= userMapper.getUserById(article.getUserId());
                article.setAvatarImage(user.getAvatarImage());
                article.setNickname(user.getNickname());
            }
            
            // 构建返回结果
            Map<String, Object> result = new HashMap<>();
            result.put("articles", articles);
            result.put("total", total);
            result.put("totalPages", totalPages);
            result.put("currentPage", pageNum);
            result.put("pageSize", pageSize);
            result.put("hasNext", pageNum < totalPages);
            result.put("hasPrevious", pageNum > 1);
            
            return Result.success(result);
        } catch (Exception e) {
            return Result.error("获取文章列表失败：" + e.getMessage());
        }
    }

    @Override
    public Result<Map<String, Object>> searchArticles(String keyword, Integer pageNum, Integer pageSize) {
        // 参数验证
        if (pageNum == null || pageNum < 1 || pageSize == null || pageSize < 1) {
            return Result.error("无效的分页参数");
        }

        try {
            // 计算偏移量
            int offset = (pageNum - 1) * pageSize;
            
            // 获取搜索结果
            List<ArticleDAO> articles = articleMapper.searchArticles(keyword, offset, pageSize);
            
            // 获取符合搜索条件的总记录数
            int total = articleMapper.searchArticlesCount(keyword);
            
            // 计算总页数
            int totalPages = (total + pageSize - 1) / pageSize;
            
            // 为每篇文章添加用户名
            for (ArticleDAO article : articles) {
                UserDAO user= userMapper.getUserById(article.getUserId());
                article.setAvatarImage(user.getAvatarImage());
                article.setNickname(user.getNickname());
            }
            
            // 构建返回结果
            Map<String, Object> result = new HashMap<>();
            result.put("articles", articles);
            result.put("total", total);
            result.put("totalPages", totalPages);
            result.put("currentPage", pageNum);
            result.put("pageSize", pageSize);
            result.put("keyword", keyword);
            result.put("hasNext", pageNum < totalPages);
            result.put("hasPrevious", pageNum > 1);
            
            return Result.success(result);
        } catch (Exception e) {
            return Result.error("搜索文章失败：" + e.getMessage());
        }
    }

    @Override
    public Result<String> likeArticle(Integer articleId, Integer userId) {
        if (articleId == null || userId == null) {
            return Result.error("文章ID和用户ID不能为空");
        }

        try {
            // 检查文章是否存在
            ArticleDAO article = articleMapper.selectArticleById(articleId);
            if (article == null) {
                return Result.error("文章不存在");
            }

            // 检查是否已经点赞
            if (articleMapper.checkUserLiked(articleId, userId) > 0) {
                return Result.error("您已经点赞过这篇文章了");
            }

            // 添加点赞记录
            articleMapper.insertArticleLike(articleId, userId);
            // 增加点赞数
            articleMapper.incrementLikeCount(articleId);

            return Result.success("点赞成功");
        } catch (Exception e) {
            return Result.error("点赞失败：" + e.getMessage());
        }
    }

    @Override
    public Result<String> unlikeArticle(Integer articleId, Integer userId) {
        if (articleId == null || userId == null) {
            return Result.error("文章ID和用户ID不能为空");
        }

        try {
            // 检查文章是否存在
            ArticleDAO article = articleMapper.selectArticleById(articleId);
            if (article == null) {
                return Result.error("文章不存在");
            }

            // 检查是否已经点赞
            if (articleMapper.checkUserLiked(articleId, userId) == 0) {
                return Result.error("您还没有点赞这篇文章");
            }

            // 删除点赞记录
            articleMapper.deleteArticleLike(articleId, userId);
            // 减少点赞数
            articleMapper.decrementLikeCount(articleId);

            return Result.success("取消点赞成功");
        } catch (Exception e) {
            return Result.error("取消点赞失败：" + e.getMessage());
        }
    }

    @Override
    public Result<Boolean> checkUserLiked(Integer articleId, Integer userId) {
        if (articleId == null || userId == null) {
            return Result.error("文章ID和用户ID不能为空");
        }

        try {
            boolean isLiked = articleMapper.checkUserLiked(articleId, userId) > 0;
            return Result.success(isLiked);
        } catch (Exception e) {
            return Result.error("检查点赞状态失败：" + e.getMessage());
        }
    }

    @Override
    public Result<Map<String, Object>> getUserArticles(Integer userId, String status, Integer pageNum, Integer pageSize) {
        if (userId == null || pageNum == null || pageSize == null) {
            return Result.error("参数不能为空");
        }

        try {
            // 计算偏移量
            int offset = (pageNum - 1) * pageSize;
            
            // 获取文章列表
            List<ArticleDAO> articles = articleMapper.selectUserArticles(userId, status, offset, pageSize);
            
            // 获取总记录数
            int total = articleMapper.selectUserArticlesCount(userId, status);
            
            // 计算总页数
            int totalPages = (total + pageSize - 1) / pageSize;
            
            // 构建返回结果
            Map<String, Object> result = new HashMap<>();
            result.put("articles", articles);
            result.put("total", total);
            result.put("totalPages", totalPages);
            result.put("currentPage", pageNum);
            result.put("pageSize", pageSize);
            result.put("hasNext", pageNum < totalPages);
            result.put("hasPrevious", pageNum > 1);
            
            return Result.success(result);
        } catch (Exception e) {
            return Result.error("获取用户文章列表失败：" + e.getMessage());
        }
    }

    @Override
    public Result<ArticleDAO> viewArticle(Integer articleId, Integer viewerId) {
        if (articleId == null) {
            return Result.error("文章ID不能为空");
        }

        try {
            // 获取文章信息
            ArticleDAO article = articleMapper.selectArticleById(articleId);
            if (article == null) {
                return Result.error("文章不存在");
            }

            // 检查文章状态和访问权限
            if (!article.getStatus().equals(ArticleDAO.ArticleStatus.PUBLISHED) && 
                (viewerId == null || !viewerId.equals(article.getUserId()))) {
                return Result.error("没有权限查看该文章");
            }

            // 如果是登录用户查看，并且不是自己的文章，记录浏览记录并更新阅读量
            if (viewerId != null && !viewerId.equals(article.getUserId())) {
                // 检查是否已经浏览过
                if (viewMapper.checkUserViewed(articleId, viewerId) == 0) {
                    // 记录浏览记录
                    viewMapper.insertArticleView(articleId, viewerId);
                    // 更新阅读量
                    articleMapper.incrementViewCount(articleId);
                } else {
                    // 已经浏览过，仅更新浏览时间
                    viewMapper.updateArticleView(articleId, viewerId);
                }
            }

            // 获取作者信息
            UserDAO user= userMapper.getUserById(article.getUserId());
            article.setAvatarImage(user.getAvatarImage());
            article.setNickname(user.getNickname());

            // 设置文章的统计数据（如果需要实时计算）
            int viewCount = viewMapper.getArticleViewCount(articleId);
            article.setViewCount(viewCount);

            return Result.success(article);
        } catch (Exception e) {
            log.error("获取文章详情失败：", e);
            return Result.error("获取文章详情失败：" + e.getMessage());
        }
    }

    @Override
    public Result checkEditPermission(Integer articleId, Integer userId) {
        if (articleId == null || userId == null) {
            log.error("检查文章编辑权限失败：参数错误 articleId={}, userId={}", articleId, userId);
            return Result.error("参数不能为空");
        }

        try {
            ArticleDAO article = articleMapper.selectArticleById(articleId);
            if (article == null) {
                log.error("检查文章编辑权限失败：文章不存在 articleId={}", articleId);
                return Result.error("文章不存在");
            }

            // 检查是否是文章作者
            boolean isAuthor = article.getUserId().equals(userId);
            
            // TODO: 如果后续需要支持管理员角色，可以在这里添加管理员权限检查
            // boolean isAdmin = userService.isAdmin(userId);
            // boolean hasPermission = isAuthor || isAdmin;

            if (!isAuthor) {
                log.warn("用户尝试操作非本人文章：userId={}, articleId={}, articleAuthor={}", 
                        userId, articleId, article.getUserId());
            }

            return Result.success(isAuthor);
        } catch (Exception e) {
            log.error("检查文章编辑权限发生异常：articleId={}, userId={}, error={}", 
                    articleId, userId, e.getMessage(), e);
            return Result.error("检查编辑权限失败：" + e.getMessage());
        }
    }
}
