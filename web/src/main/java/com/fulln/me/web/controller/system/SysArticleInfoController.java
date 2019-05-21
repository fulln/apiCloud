package com.fulln.me.web.controller.system;


import com.fulln.me.api.common.entity.GlobalResult;
import com.fulln.me.api.model.system.DTO.SysArticleInfoDTO;
import com.fulln.me.api.model.system.SysArticleInfo;
import com.fulln.me.api.model.system.cloums.ArticleStatusEnums;
import com.fulln.me.web.config.base.method.BaseController;
import com.fulln.me.web.service.system.ISysArticleInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author fulln
 * @description 文章视图层
 * @date Created in  21:55  2018/11/13.
 */
@RestController
@Api(tags = {"文章操作api"}, value = "文章操作")
@RequestMapping("/article")
public class SysArticleInfoController extends BaseController {

    @Resource
    private ISysArticleInfoService infoService;

    @GetMapping("/find")
    @ApiOperation(value = "查询文章列表" )
    public GlobalResult findAll(SysArticleInfoDTO info) {
        info.setUser(getUser());
        return infoService.findAll(info);
    }

    @PostMapping("/insert")
    @ApiOperation(value = "文章新增或者修改")
    public GlobalResult insertOne(SysArticleInfo info) {
        if (StringUtils.isEmpty(info.getId())) {
            return infoService.insertOne(info, getUser());
        } else {
            return infoService.updateOne(info, getUser());
        }
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "文章删除")
    public GlobalResult delete(@PathVariable Integer id, String name) {
        SysArticleInfo info = new SysArticleInfo();
        info.setId(id.longValue());
        info.setArticlePushStatus(ArticleStatusEnums.DELETED);
        info.setArticleFileName(name);
        return infoService.updateOne(info, getUser());
    }

    @PostMapping("/read")
    @ApiOperation(value = "读取文件中文章")
    public GlobalResult read() {
        return infoService.findOne(getUser());
    }


}
