package com.fulln.me.api.service.search;


import com.fulln.me.api.common.entity.GlobalResult;

/**
 * @AUthor: fulln
 * @Description: 公共搜索业务逻辑层
 * @Date : Created in  21:10  2018/9/1.
 */
public interface SearchService {

    GlobalResult findAll(String text);

}
