package org.ContinuityIns.controller;

import org.ContinuityIns.DAO.TagDAO;
import org.ContinuityIns.common.Result;
import org.ContinuityIns.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
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
}
