package me.fulln.tuc.serivce.impl;


import lombok.extern.slf4j.Slf4j;
import me.fulln.base.common.entity.GlobalResult;
import me.fulln.base.common.enums.GlobalEnums;
import me.fulln.base.common.utils.CheckParamsUtil;
import me.fulln.base.model.user.SysUserRole;
import me.fulln.tuc.dao.SysUserRoleDao;
import me.fulln.tuc.tucapi.user.ISysUserRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author fulln
 * @version 0.0.1
 * @program apiCloud
 * @description 用户权限
 * @date 2019/6/13 23:26
 **/
@Service
@Slf4j
public class SysUserRoleServiceImpl implements ISysUserRoleService {

    @Resource
    private SysUserRoleDao sysUserRoleDao;

    /**
     * 查询用户权限
     *
     * @param user
     * @return
     */
    @Override
    public GlobalResult findById(SysUserRole user) {
        try {
            CheckParamsUtil.checkParam(user);
            SysUserRole sysUserRole = sysUserRoleDao.selectById(user);
            if(sysUserRole == null){
                return GlobalEnums.QUERY_EMPTY.results();
            }
            return GlobalEnums.QUERY_SUCCESS.results(sysUserRole);
        }catch (Exception e){
            log.error("查询用户权限出现异常", e);
            return GlobalEnums.QUERY_FAIL.results();
        }
    }
}
