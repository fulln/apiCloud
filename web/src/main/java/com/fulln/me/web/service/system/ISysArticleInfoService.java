package com.fulln.me.web.service.system;

import com.fulln.me.api.common.entity.GlobalResult;
import com.fulln.me.api.model.system.DTO.SysArticleInfoDTO;
import com.fulln.me.api.model.system.SysArticleInfo;
import com.fulln.me.api.model.system.SysUserBasic;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @program: api
 * @description: 文章业务逻辑层
 * @author: fulln
 * @create: 2018-11-13 17:17
 * @Versn： 0.0.1
 **/
@FeignClient("${feign.url}")
public interface ISysArticleInfoService {
    /**
     * 批量查询
     * @param info
     * @param user
     * @return
     */
    @GetMapping("/article/list")
    GlobalResult findAll(SysArticleInfoDTO info, SysUserBasic user);

    /**
     * 单个查询
     * @param info
     * @param user
     * @return
     */
    @GetMapping("/article/findOne")
    GlobalResult findOne(SysUserBasic user);

    /**
     * 插入
     * @param info 实体类
     * @param basic 用户实体
     * @return
     */
    @PostMapping("/article/insertOne")
    GlobalResult insertOne(SysArticleInfo info, SysUserBasic basic);

    /**
     * 更新
     * @param info
     * @return
     */
    @PostMapping("/article/update")
    GlobalResult updateOne(SysArticleInfo info, SysUserBasic basic);

}
