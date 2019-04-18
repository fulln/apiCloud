package com.fulln.me.controller.search;


import com.fulln.me.api.common.entity.GlobalResult;
import com.fulln.me.service.search.SearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @AUthor: fulln
 * @Description: 公共搜索业务逻辑层
 * @Date : Created in  21:10  2018/9/1.
 */
@RestController
@RequestMapping("/search")
public class SearchController{
    @Resource
    private SearchService searchService;

    @GetMapping("/{text}")
    public GlobalResult findAll(@PathVariable String text ) {
        return searchService.findAll(text);
    }

}
