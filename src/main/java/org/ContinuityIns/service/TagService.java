package org.ContinuityIns.service;

import org.ContinuityIns.DAO.TagDAO;
import org.ContinuityIns.common.Result;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TagService {
    /**
     * <h1></h1>
     * <p>
     * 功能描述：<p>
     * 1. 如果标签不存在，则添加标签，并返回新标签的ID。<p>
     * 2. 如果标签存在且状态为正常（NORMAL），则直接返回该标签的ID。<p>
     * 3. 如果标签存在但状态为禁用（BANNED），则返回null。<p>
     *
     * 注意事项：<p>
     * - 若输入的标签名（tagName）为空或null，将不会执行任何操作并返回null。<p>
     * - 标签的状态由 {@link TagDAO.TagStatus} 定义。<p>
     *
     * @param tagName 标签名（不能为空）
     * @return
     * - 标签ID：如果标签成功添加或已存在且状态为正常。<p>
     * - null：如果标签已存在但状态为禁用，或者输入无效。
     */
    Integer findOrCreateTag(String tagName);

    Result bindTag(List<String> tagList, Integer articleId);

    /**
     * <h1>获取标签列表</h1>
     * <p>
     * 功能描述：<p>
     * 1. 根据关键字（keyword）查询标签列表，返回匹配的标签列表。<p>
     * 2. 如果关键字为空或null，将返回所有标签列表。<p>
     *
     * 注意事项：<p>
     * - 关键字用于搜索标签，支持模糊匹配。<p>
     * - 返回的标签列表按照标签创建时间（create_time）降序排列。<p>
     *
     * @param keyword 关键字（用于搜索标签）
     **/
    Result<List<TagDAO>> getTagList(String keyword);

    List<TagDAO> getTagListByArticleId(Integer articleId);

    /**
     * <h1>获取热门标签列表</h1>
     * <p>
     * 功能描述：<p>
     * 1. 获取系统中使用频率最高的标签列表<p>
     * 2. 根据标签在文章中的使用次数进行降序排序<p>
     * 3. 返回前10个最热门的标签<p>
     *
     * @return 热门标签列表
     */
    Result<List<TagDAO>> getHotTags();
}
