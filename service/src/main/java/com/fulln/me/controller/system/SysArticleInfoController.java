package com.fulln.me.controller.system;

import com.fulln.me.api.common.entity.GlobalResult;
import com.fulln.me.api.common.utils.GsonUtil;
import com.fulln.me.api.model.system.DTO.SysArticleInfoDTO;
import com.fulln.me.api.model.system.SysArticleInfo;
import com.fulln.me.api.model.system.SysUserBasic;
import com.fulln.me.service.system.ISysArticleInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;

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
     * @param map  参数map
     * @return
     */
    @PostMapping("/list")
    public GlobalResult findAll(@RequestParam HashMap<String, Object> map) {
        SysArticleInfoDTO info=  GsonUtil.gsonToBean(map.get("info").toString(), SysArticleInfoDTO.class);

        SysUserBasic user = GsonUtil.gsonToBean(map.get("user").toString(),SysUserBasic.class);

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
