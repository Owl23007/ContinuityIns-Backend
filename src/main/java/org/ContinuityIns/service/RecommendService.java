package org.ContinuityIns.service;

import org.ContinuityIns.common.Result;

public interface RecommendService {
    /**
     * 获取每日推荐文章
     * @param page 页码
     * @param pageSize 每页大小
     * @return 推荐文章列表
     */
    Result getDailyRecommends(Integer page, Integer pageSize);
}
