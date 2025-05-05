package org.ContinuityIns.controller;

import org.ContinuityIns.DAO.TagDAO;
import org.ContinuityIns.common.Result;
import org.ContinuityIns.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @RequestMapping("/list")
    public Result<List<TagDAO>> onGetTagList(String keyword) {
        return tagService.getTagList(keyword);
    }

    @RequestMapping("/listByArticleId")
    public Result onGetTagListByArticleId(Integer articleId) {
       List<TagDAO> list= tagService.getTagListByArticleId(articleId);
        if (list == null || list.isEmpty()) {
            return Result.error("没有找到相关标签");
        }
        return Result.success(list);
    }

    /**
     * <h1>获取热门标签</h1>
     * <p>
     * 功能描述：获取系统中使用频率最高的前10个标签<p>
     * 
     * @return Result 包含热门标签列表的结果对象
     */
    @GetMapping("/hot")
    public Result<List<TagDAO>> onTagsHot() {
        return tagService.getHotTags();
    }
}
