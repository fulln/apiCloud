package me.fulln.tuc.tucapi.user;


import me.fulln.base.common.entity.GlobalResult;
import me.fulln.base.model.system.SysPermission;

import java.util.List;

/**
 * @AUthor: fulln
 * @Description: 权限表
 * @Date : Created in  0:25  2018/10/12.
 */
public interface ISysPermissionService {

    /**
     * 根据用户查询权限
     *
     * @param id
     * @return
     */
    List<SysPermission> loadUserResources(Integer id);

    /**
     * 查询全部权限
     *
     * @return
     */
    List<SysPermission> queryAll();
    /**
     * 新增
     * @return
     */
    GlobalResult save(SysPermission permission);

}
