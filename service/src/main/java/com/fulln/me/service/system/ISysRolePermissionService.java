package com.fulln.me.service.system;


import com.fulln.me.api.common.entity.GlobalResult;
import com.fulln.me.api.model.system.SysRolePermission;

/**
 * @AUthor: fulln
 * @Description: 权限-角色表
 * @Date : Created in  0:25  2018/10/12.
 */
public interface ISysRolePermissionService {


    /**
     * 新增
     * @return
     */
    GlobalResult save(SysRolePermission rolePermission);
}
