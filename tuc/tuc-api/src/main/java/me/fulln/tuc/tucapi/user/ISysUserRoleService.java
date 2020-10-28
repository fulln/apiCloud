package me.fulln.tuc.tucapi.user;

import me.fulln.base.common.entity.GlobalResult;
import me.fulln.base.model.user.SysUserRole;

/**
 * @author fulln
 * @version 0.0.1
 * @program apiCloud
 * @description 用户权限
 * @date 2019/6/13 23:26
 **/
public interface ISysUserRoleService {
    /**
     * 查询用户权限
     * @return
     */
    GlobalResult findById(SysUserRole user);

}
