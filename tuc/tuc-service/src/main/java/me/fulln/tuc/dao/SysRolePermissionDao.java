package me.fulln.tuc.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.fulln.base.model.system.SysRolePermission;

/**
 * @program: api
 * @description: 权限-角色表
 * @author: fulln
 * @create: 2018-11-14 14:50
 * @Version： 0.0.1
 **/
public interface SysRolePermissionDao extends BaseMapper<SysRolePermission> {


	int insertUseGeneratedKeys(SysRolePermission rolePermission);
}
