package com.fulln.me.web.service.search;


import com.fulln.me.api.common.entity.GlobalResult;
import com.fulln.me.api.service.search.SearchService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @AUthor: fulln
 * @Description: 公共搜索业务逻辑层
 * @Date : Created in  21:10  2018/9/1.
 */
@FeignClient("${feign.url}")
public interface IWebSearchService extends SearchService{

    GlobalResult findAll(String text);

}
