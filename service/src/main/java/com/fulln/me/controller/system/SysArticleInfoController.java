package com.fulln.me.controller.system;

import com.fulln.me.api.common.entity.GlobalResult;
import com.fulln.me.api.model.system.DTO.SysArticleInfoDTO;
import com.fulln.me.api.model.system.SysArticleInfo;
import com.fulln.me.api.model.system.SysUserBasic;
import com.fulln.me.api.service.system.ISysArticleInfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @program: api
 * @description: 文章业务逻辑层
 * @author: fulln
 * @create: 2018-11-13 17:17
 * @Versn： 0.0.1
 **/
@RestController
@RequestMapping("/article")
public class SysArticleInfoController {

    @Resource
    private ISysArticleInfoService sysArticleInfoService;

    /**
     * 批量查询
     *
     * @param info
     * @param user
     * @return
     */
    @GetMapping("/list")
    public GlobalResult findAll(SysArticleInfoDTO info, SysUserBasic user) {
        return sysArticleInfoService.findAll(info, user);
    }

    /**
     * 单个查询
     *
     * @param user
     * @return
     */
    @GetMapping("/findOne")
    public GlobalResult findOne(SysUserBasic user) {
        return sysArticleInfoService.findOne(user);
    }

    /**
     * 插入
     *
     * @param info  实体类
     * @param basic 用户实体
     * @return
     */
    @PostMapping("/insertOne")
    public GlobalResult insertOne(SysArticleInfo info, SysUserBasic basic) {
        return sysArticleInfoService.insertOne(info, basic);
    }

    /**
     * 更新
     *
     * @param info
     * @return
     */
    @PostMapping("/update")
    public GlobalResult updateOne(SysArticleInfo info, SysUserBasic basic) {
        return sysArticleInfoService.updateOne(info, basic);
    }

}
