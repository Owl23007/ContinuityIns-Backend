package org.ContinuityIns.service.impl;

import org.ContinuityIns.common.Result;
import org.ContinuityIns.mapper.ArticleMapper;
import org.ContinuityIns.service.RecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RecommendServiceImpl implements RecommendService {

    @Autowired
    private ArticleMapper articleMapper;

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
            // 获取总记录数
            int total = articleMapper.getDailyRecommendsCount();

            Map<String, Object> data = new HashMap<>();
            data.put("articles", articles);
            data.put("total", total);
            data.put("page", page);
            data.put("pageSize", pageSize);

            return Result.success(data);
        } catch (Exception e) {
            return Result.error("获取推荐文章失败：" + e.getMessage());
        }
    }
}
