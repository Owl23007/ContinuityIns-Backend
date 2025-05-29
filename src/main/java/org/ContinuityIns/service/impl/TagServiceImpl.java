package org.ContinuityIns.service.impl;

import org.ContinuityIns.po.TagPO;
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
    public Result<List<TagPO>> getTagList(String keyword) {
        List<TagPO> tagList = tagMapper.getTagListByKeyword(keyword);
        if (tagList == null || tagList.isEmpty()) {
            return Result.custom(-1, "没有找到相关标签", (List<TagPO>) null);
        }
        return Result.success(tagList);
    }

    @Override
    public List<TagPO> getTagListByArticleId(Integer articleId) {
        return List.of();
    }

    @Override
    public Integer findOrCreateTag(String tagName) {
        if (tagName == null || tagName.trim().isEmpty()) {
            return null; // 输入无效，直接返回null
        }

        TagPO tagPO = tagMapper.getTag(tagName);
        if (tagPO == null) {
            // 标签不存在，创建新标签
            Integer userId = (Integer) ThreadLocalUtil.get().get("id");
            tagMapper.addTag(tagName, userId);
            return tagMapper.getTagId(tagName);
        }

        // 标签存在，检查状态
        if (tagPO.getTagStatus().equals(TagPO.TagStatus.BANNED)) {
            return null; // 标签被禁用，返回null
        }

        // 标签存在且状态正常，返回标签ID
        return tagMapper.getTagId(tagName);
    }

    @Override
    public Result<Void> bindTag(List<String> tagList, Integer articleId) {
        for (String tagName : tagList) {
            Integer tagId = findOrCreateTag(tagName);
            if (tagId != null) {
                tagMapper.bindTag(tagId, articleId);
            }
        }
        return Result.custom(0, "success", (Void) null);
    }

    @Override
    public Result<List<TagPO>> getHotTags() {
        List<TagPO> hotTags = tagMapper.getHotTags();
        if (hotTags == null || hotTags.isEmpty()) {
            return Result.custom(-1, "暂无热门标签", (List<TagPO>) null);
        }
        return Result.success(hotTags);
    }
}
