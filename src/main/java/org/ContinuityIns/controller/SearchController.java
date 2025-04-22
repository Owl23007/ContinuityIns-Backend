package org.ContinuityIns.controller;

import org.ContinuityIns.DAO.SearchDTO;
import org.ContinuityIns.common.Result;
import org.ContinuityIns.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @PostMapping("/content")
    public Result searchContent(@RequestBody SearchDTO searchDTO) {
        return searchService.searchContent(searchDTO);
    }
}
