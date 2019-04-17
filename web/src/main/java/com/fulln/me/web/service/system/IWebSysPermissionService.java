package com.fulln.me.web.service.system;


import com.fulln.me.api.common.entity.GlobalResult;
import com.fulln.me.api.model.system.SysPermission;
import com.fulln.me.api.service.system.ISysPermissionService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * @AUthor: fulln
 * @Description: 权限表
 * @Date : Created in  0:25  2018/10/12.
 */
@FeignClient("${feign.url}")
public interface IWebSysPermissionService extends ISysPermissionService {

    /**
     * 根据用户查询权限
     *
     * @param id
     * @return
     */
    @GetMapping("/permission/findByUser")
    List<SysPermission> loadUserResources(Integer id);

    /**
     * 查询全部权限
     *
     * @return
     */
    @GetMapping("/permission/list")
    List<SysPermission> queryAll();
    /**
     * 新增
     * @return
     */
    @PostMapping("/permission/save")
    GlobalResult save(SysPermission permission);

}
