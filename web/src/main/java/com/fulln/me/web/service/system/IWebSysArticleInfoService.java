package com.fulln.me.web.service.system;

import com.fulln.me.api.common.entity.GlobalResult;
import com.fulln.me.api.model.system.DTO.SysArticleInfoDTO;
import com.fulln.me.api.model.system.SysArticleInfo;
import com.fulln.me.api.model.system.SysUserBasic;
import com.fulln.me.api.service.system.ISysArticleInfoService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @program: api
 * @description: 文章业务逻辑层
 * @author: fulln
 * @create: 2018-11-13 17:17
 * @Versn： 0.0.1
 **/
@FeignClient("${feign.url}")
public interface IWebSysArticleInfoService extends ISysArticleInfoService {
    /**
     * 批量查询
     * @param info
     * @param user
     * @return
     */
    GlobalResult findAll(SysArticleInfoDTO info, SysUserBasic user);

    /**
     * 单个查询
     * @param info
     * @param user
     * @return
     */
    GlobalResult findOne(SysUserBasic user);

    /**
     * 插入
     * @param info 实体类
     * @param basic 用户实体
     * @return
     */
    GlobalResult insertOne(SysArticleInfo info, SysUserBasic basic);

    /**
     * 更新
     * @param info
     * @return
     */
    GlobalResult updateOne(SysArticleInfo info, SysUserBasic basic);

}
