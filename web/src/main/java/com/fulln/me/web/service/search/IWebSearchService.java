package com.fulln.me.web.service.search;


import com.fulln.me.api.common.entity.GlobalResult;
import com.fulln.me.api.service.search.SearchService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @AUthor: fulln
 * @Description: 公共搜索业务逻辑层
 * @Date : Created in  21:10  2018/9/1.
 */
@FeignClient("${feign.url}")
public interface IWebSearchService extends SearchService{

    @GetMapping("/search/{text}")
    GlobalResult findAll(@PathVariable String text);

}
