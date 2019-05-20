package com.fulln.me.web.service.search;


import com.fulln.me.api.common.entity.GlobalResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @AUthor: fulln
 * @Description: 公共搜索业务逻辑层
 * @Date : Created in  21:10  2018/9/1.
 */
@FeignClient(value = "${feign.url}",path = "${feign.path}")
public interface  SearchService{

    @GetMapping("/search/{text}")
    GlobalResult findAll(@PathVariable String text);

}
