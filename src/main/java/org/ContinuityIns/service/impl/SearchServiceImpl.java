package org.ContinuityIns.service.impl;

import org.ContinuityIns.po.ArticlePO;
import org.ContinuityIns.dto.SearchDTO;
import org.ContinuityIns.common.Result;
import org.ContinuityIns.mapper.ArticleMapper;
import org.ContinuityIns.mapper.UserMapper;
import org.ContinuityIns.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public Result searchContent(SearchDTO searchDTO) {
        System.out.println("searchDTO: " + searchDTO);
        // 参数校验
        if (!StringUtils.hasText(searchDTO.getKeyword())) {
            return Result.error("搜索关键词不能为空");
        }

        List<ArticlePO> articlePOList = articleMapper.selectArticlesByKeyWords(searchDTO.getKeyword());
        return Result.success(articlePOList);
    }
}
