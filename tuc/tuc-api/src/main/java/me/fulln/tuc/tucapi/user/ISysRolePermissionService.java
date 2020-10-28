package me.fulln.tuc.tucapi.user;


import me.fulln.base.common.entity.GlobalResult;
import me.fulln.base.model.system.SysRolePermission;

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
