package org.ContinuityIns.controller;

import org.ContinuityIns.common.Result;
import org.ContinuityIns.service.RecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recommend")
public class RecommendController {

    @Autowired
    private RecommendService recommendService;

    /**
     * 获取每日推荐文章
     * 基于最近7天内的文章，根据浏览量、点赞数和收藏数综合计算热度进行推荐
     *
     * @param page 页码，默认为1
     * @param pageSize 每页大小，默认为10，最大50
     * @return 推荐文章列表和分页信息
     */
    @GetMapping("/daily")
    public Result getDailyRecommends(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return recommendService.getDailyRecommends(page, pageSize);
    }
}
