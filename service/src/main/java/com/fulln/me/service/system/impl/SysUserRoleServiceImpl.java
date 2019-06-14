package com.fulln.me.service.system.impl;

import com.fulln.me.api.common.entity.GlobalResult;
import com.fulln.me.api.common.enums.GlobalEnums;
import com.fulln.me.api.common.utils.CheckParamsUtil;
import com.fulln.me.api.model.user.SysUserRole;
import com.fulln.me.dao.user.SysUserRoleDao;
import com.fulln.me.service.system.ISysUserRoleService;
import lombok.extern.slf4j.Slf4j;
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
            CheckParamsUtil.checkNull(user.getUserId()+"","操作用户");
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
