package com.fulln.me.controller.system;


import com.fulln.me.api.common.entity.GlobalResult;
import com.fulln.me.api.model.system.SysRolePermission;
import com.fulln.me.api.service.system.ISysRolePermissionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @AUthor: fulln
 * @Description: 权限-角色表
 * @Date : Created in  0:22018/10/12.
 */
@RestController
@RequestMapping("/rolePermission")
public class SysRolePermissionController {

    @Resource
    private ISysRolePermissionService sysRolePermissionService;

    /**
     * 新增
     *
     * @return
     */
    @PostMapping("/save")
    public GlobalResult save(SysRolePermission rolePermission) {
        return sysRolePermissionService.save(rolePermission);
    }

}
