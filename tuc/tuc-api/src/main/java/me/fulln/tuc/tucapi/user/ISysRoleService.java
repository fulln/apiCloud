package me.fulln.tuc.tucapi.user;

import me.fulln.base.common.entity.GlobalResult;
import me.fulln.tuc.model.user.SysRole;


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
