package com.fulln.me.web.service.system;


import com.fulln.me.api.common.entity.GlobalResult;
import com.fulln.me.api.model.system.SysRolePermission;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @AUthor: fulln
 * @Description: 权限-角色表
 * @Date : Created in  0:22018/10/12.
 */
@FeignClient("${feign.url}")
public interface  ISysRolePermissionService {


    /**
     * 新增
     * @return
     */
    @PostMapping("/rolePermission/save")
    GlobalResult save(SysRolePermission rolePermission);
}
