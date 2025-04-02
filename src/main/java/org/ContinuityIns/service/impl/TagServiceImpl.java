package org.ContinuityIns.service.impl;

import org.ContinuityIns.DAO.TagDAO;
import org.ContinuityIns.common.Result;
import org.ContinuityIns.mapper.TagMapper;
import org.ContinuityIns.service.TagService;
import org.ContinuityIns.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagMapper tagMapper;

    @Override
    public Result getTagList(String keyword){
        List<TagDAO> tagList = tagMapper.getTagListByKeyword(keyword);
        if (tagList == null || tagList.isEmpty()) {
            return Result.error("没有找到相关标签");
        }
        return Result.success(tagList);
    }

    @Override
    public List<TagDAO> getTagListByArticleId(Integer articleId) {
        return List.of();
    }


    @Override
    public Integer findOrCreateTag(String tagName) {
        if (tagName == null || tagName.trim().isEmpty()) {
            return null; // 输入无效，直接返回null
        }

        TagDAO tagDAO = tagMapper.getTag(tagName);
        if (tagDAO == null) {
            // 标签不存在，创建新标签
            Integer userId = (Integer) ThreadLocalUtil.get().get("id");
            tagMapper.addTag(tagName, userId);
            return tagMapper.getTagId(tagName);
        }

        // 标签存在，检查状态
        if (tagDAO.getTagStatus().equals(TagDAO.TagStatus.BANNED)) {
            return null; // 标签被禁用，返回null
        }

        // 标签存在且状态正常，返回标签ID
        return tagMapper.getTagId(tagName);
    }

    @Override
    public Result bindTag(List<String> tagList, Integer articleId) {
        for (String tagName : tagList) {
            Integer tagId = findOrCreateTag(tagName);
            if (tagId != null) {
                 tagMapper.bindTag(tagId, articleId);
            }
        }

        return null;
    }
}
