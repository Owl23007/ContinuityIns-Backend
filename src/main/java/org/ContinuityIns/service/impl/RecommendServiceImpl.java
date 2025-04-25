package org.ContinuityIns.service.impl;

import org.ContinuityIns.common.Result;
import org.ContinuityIns.mapper.ArticleMapper;
import org.ContinuityIns.mapper.UserMapper;
import org.ContinuityIns.service.RecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RecommendServiceImpl implements RecommendService {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public Result getDailyRecommends(Integer page, Integer pageSize) {
        // 参数校验
        if (page == null || page < 1) {
            page = 1;
        }
        if (pageSize == null || pageSize < 1 || pageSize > 50) {
            pageSize = 10;
        }

        // 计算偏移量
        int offset = (page - 1) * pageSize;

        try {
            // 获取推荐文章列表
            List<Map<String, Object>> articles = articleMapper.getDailyRecommends(offset, pageSize);

            // 添加用户名与userId
            for (Map<String, Object> article : articles) {
                Integer userId = (Integer) article.get("user_id");
                String username = userMapper.getUserById(userId).getUsername();
                article.put("author", username);
                article.put("userId", userId);
                article.put("articleId", article.get("article_id"));
            }

            // 简化内容
            for (Map<String, Object> article : articles) {
                String content = (String) article.get("content");
                if (content != null && content.length() > 50) {
                    content = content.substring(0, 50) + "..."; // 截取前100个字符并添加省略号
                }
                article.put("content", content);
            }
            // 获取总记录数
            int total = articleMapper.getDailyRecommendsCount();

            Map<String, Object> data = new HashMap<>();
            data.put("articles", articles);
            data.put("total", total);
            data.put("page", page);
            data.put("pageSize", pageSize);

            return Result.success(data);
        } catch (Exception e) {
            e.printStackTrace(); // 打印详细错误信息到日志
            return Result.error("获取推荐文章失败：" + e.getMessage());
        }
    }
}
