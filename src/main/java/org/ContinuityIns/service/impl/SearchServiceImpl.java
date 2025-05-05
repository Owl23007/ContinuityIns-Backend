package org.ContinuityIns.service.impl;

import org.ContinuityIns.DAO.ArticleDAO;
import org.ContinuityIns.DAO.SearchDTO;
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
//
//        // 处理时间范围
//        if (searchDTO.getTimeRange() != null && !searchDTO.getTimeRange().equals("custom")) {
//            LocalDateTime now = LocalDateTime.now();
//            switch (searchDTO.getTimeRange()) {
//                case "day":
//                    searchDTO.setDateStart(now.minusDays(1));
//                    break;
//                case "week":
//                    searchDTO.setDateStart(now.minusDays(7));
//                    break;
//                case "month":
//                    searchDTO.setDateStart(now.minusDays(30));
//                    break;
//                case "year":
//                    searchDTO.setDateStart(now.minusDays(365));
//                    break;
//                case "all":
//                    searchDTO.setDateStart(null);
//                    break;
//               default:
//                   return Result.error("不支持的时间范围");
//            }
//            searchDTO.setDateEnd(now);
//        }
//
//        // 根据类型执行不同的搜索
//        List<?> resultList;
//        String type = searchDTO.getType() != null ? searchDTO.getType() : "article";
//
//        switch (type) {
//            case "article":
//                resultList = searchArticles(searchDTO);
//                break;
//            case "user":
//                resultList = searchUsers(searchDTO);
//                break;
//            default:
//                return Result.error("不支持的搜索类型");
//        }
//
//        // 构建返回结果
//        Map<String, Object> result = new HashMap<>();
//        result.put("list", resultList);
//        result.put("total", resultList.size());
//        result.put("pageSize", searchDTO.getPageSize());
//        result.put("currentPage", searchDTO.getPage());
//        boolean hasNext = (resultList.size() - (searchDTO.getPage() * searchDTO.getPageSize())) > 0;
//        result.put("hasNext", hasNext);
//
//
//        return Result.success(result);
//    }
//
//    private List<?> searchArticles(SearchDTO searchDTO) {
//        try {
//            return articleMapper.searchArticles(
//                searchDTO.getKeyword(),
//                searchDTO.getCategory(),
//                searchDTO.getTags(),
//                searchDTO.getDateStart(),
//                searchDTO.getDateEnd(),
//                searchDTO.getSort()
//            );
//        } catch (Exception e) {
//            e.printStackTrace();
//            return Collections.emptyList();
//        }
//    }
//
//    private List<?> searchUsers(SearchDTO searchDTO) {
//        try {
//            return userMapper.searchUsers(
//                searchDTO.getKeyword(),
//                searchDTO.getDateStart(),
//                searchDTO.getDateEnd()
//            );
//        } catch (Exception e) {
//            e.printStackTrace();
//            return Collections.emptyList();
//        }
//    }
        List<ArticleDAO> articleDAOList = articleMapper.selectArticlesByKeyWords(searchDTO.getKeyword());
        return Result.success(articleDAOList);
    }
}
