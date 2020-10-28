package com.fulln.me.dao.system;


import me.fulln.base.model.system.SysPermission;
import com.fulln.me.config.basic.MyMapper;

import java.util.List;

/**
 * @program: api
 * @description: 系统权限
 * @author: fulln
 * @create: 2018-10-19 13:38
 * @Version： 0.0.1
 **/
public interface SysPermissionDao  extends MyMapper<SysPermission> {

    /**
     * 根据角色查询权限
     * @param roleId
     * @return
     */
    List<SysPermission> findByRole(Integer roleId);

    /**
     * @Author: fulln
     * @Description: 查询全部的权限
     * @retun: List
     * @Date: 2018/10/23 0023-14:56
     */
    List<SysPermission> queryAll();




}
