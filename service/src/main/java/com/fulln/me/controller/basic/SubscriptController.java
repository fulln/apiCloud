package com.fulln.me.controller.basic;

import com.fulln.me.api.service.basic.ISubscriptService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @author fulln
 * @program api
 * @description 文档下载service
 * @Date 2019-01-08 11:47
 * @Version 0.0.1
 **/
@RestController
@RequestMapping("/download")
public class SubscriptController {

    @Resource
    private ISubscriptService subscriptService;

    /**
     * @param mine
     * @param response
     */
    @GetMapping("/{mine}")
    public void downloads(@PathVariable String mine, HttpServletResponse response) {
        subscriptService.downloads(mine, response);
    }

}
