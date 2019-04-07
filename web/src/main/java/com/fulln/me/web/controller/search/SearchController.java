package com.fulln.me.web.controller.search;


import com.fulln.me.api.common.entity.GlobalResult;
import com.fulln.me.api.service.basic.IThreadStartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @AUthor: fulln
 * @Description：输入要搜索出来的结果
 * @Date : Created in  20:17  2018/9/1.
 */
@Api(tags = {"视频搜索Api"}, value = "视频搜索")
@RestController
@RequestMapping("/Search")
public class SearchController {

    @Resource
    private IThreadStartService startService;

    @PostMapping("/{text}")
    @ApiOperation(value = "输入要搜索出来的结果")
    public GlobalResult findAll(@PathVariable("text") String text) {
        return startService.dispatch(text);
    }
}
