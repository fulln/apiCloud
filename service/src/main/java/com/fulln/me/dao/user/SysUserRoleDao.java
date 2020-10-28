package com.fulln.me.dao.user;

import me.fulln.base.model.user.SysUserRole;
import com.fulln.me.config.basic.MyMapper;
import org.springframework.stereotype.Repository;

/**
 * @author fulln
 * @version 0.0.1
 * @program apiCloud
 * @description 用户权限
 * @date 2019/6/13 23:24
 **/
@Repository
public interface SysUserRoleDao extends MyMapper<SysUserRole> {


    SysUserRole selectById(SysUserRole user);
}
