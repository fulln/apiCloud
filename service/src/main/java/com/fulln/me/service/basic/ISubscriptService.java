package com.fulln.me.service.basic;

import javax.servlet.http.HttpServletResponse;

/**
 * @author fulln
 * @program api
 * @description 文档下载service
 * @Date 2019-01-08 11:47
 * @Version 0.0.1
 **/
public interface ISubscriptService {

    /**
     *
      * @param mine
     * @param response
     */
    void downloads(String mine, HttpServletResponse response);
}
