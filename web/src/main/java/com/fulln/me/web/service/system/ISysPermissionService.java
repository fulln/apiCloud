package com.fulln.me.web.service.system;


import com.fulln.me.api.common.entity.GlobalResult;
import com.fulln.me.api.model.system.SysPermission;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @AUthor: fulln
 * @Description: 权限表
 * @Date : Created in  0:25  2018/10/12.
 */
@FeignClient(value = "${feign.url}" ,path = "${feign.path}")
public interface  ISysPermissionService {

    /**
     * 根据角色查询权限
     *
     * @param id
     * @return
     */
    @PostMapping("/permission/findByUser")
    List<SysPermission> loadUserResources(@RequestParam("id")Integer id);

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
