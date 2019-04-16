package com.fulln.me.web.service.basic;

import com.fulln.me.api.service.basic.ISubscriptService;
import org.springframework.cloud.openfeign.FeignClient;

import javax.servlet.http.HttpServletResponse;

/**
 * @author fulln
 * @program api
 * @description 文档下载service
 * @Date 2019-01-08 11:47
 * @Version 0.0.1
 **/
@FeignClient("${feign.url}")
public interface IWebSubscriptService extends ISubscriptService {

    /**
     *
      * @param mine
     * @param response
     */
    void downloads(String mine, HttpServletResponse response);
}
