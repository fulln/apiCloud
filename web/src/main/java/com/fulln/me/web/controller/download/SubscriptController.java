package com.fulln.me.web.controller.download;


import com.fulln.me.api.service.basic.ISubscriptService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @AUthor: fulln
 * @Description:点击下载文档
 * @Date : Created in  18:14  2018/8/4.
 */
@RestController
@RequestMapping("/REST/down")
@Slf4j
@Api(value = "下载的控制层", tags = "下载api")
public class SubscriptController {

    @Resource
    private ISubscriptService subscriptService;

    @GetMapping("/{mine}")
    public void getMyself(@PathVariable String mine, HttpServletResponse response) {
        subscriptService.downloads(mine, response);
    }

}
