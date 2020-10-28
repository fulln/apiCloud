package com.fulln.me.web.service.system;


import me.fulln.base.common.entity.GlobalResult;
import me.fulln.base.model.system.SysRolePermission;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @AUthor: fulln
 * @Description: 权限-角色表
 * @Date : Created in  0:22018/10/12.
 */
@FeignClient(value = "${feign.url}",path = "${feign.path}")
public interface  ISysRolePermissionService {


    /**
     * 新增
     * @return
     */
    @PostMapping("/rolePermission/save")
    GlobalResult save(SysRolePermission rolePermission);
}
