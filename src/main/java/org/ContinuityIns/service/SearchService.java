package org.ContinuityIns.service;

import org.ContinuityIns.dto.SearchDTO;
import org.ContinuityIns.common.Result;

public interface SearchService {
    /**
     * 搜索内容
     * @param searchDTO 搜索参数
     * @return 搜索结果，包含分页信息和内容列表
     */
    Result searchContent(SearchDTO searchDTO);
}
