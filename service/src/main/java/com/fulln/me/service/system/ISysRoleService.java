package com.fulln.me.service.system;

import com.fulln.me.api.common.entity.GlobalResult;
import com.fulln.me.api.model.system.SysRole;

/**
 * @author fulln
 * @version 0.0.1
 * @program apiCloud
 * @description 系统权限业务层
 * @date 2019/4/7 23:30
 **/
public interface ISysRoleService {

    SysRole findById(Integer id);


    GlobalResult findByUserId(Integer userId);

}
