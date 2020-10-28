package com.fulln.me.service.system.impl;

import me.fulln.base.common.entity.GlobalResult;
import me.fulln.base.common.enums.GlobalEnums;
import me.fulln.base.model.system.SysRolePermission;
import com.fulln.me.dao.system.SysRolePermissionDao;
import com.fulln.me.service.system.ISysRolePermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author fulln
 * @version 0.0.1
 * @program apiCloud
 * @description 权限角色表
 * @date 2019/4/16 23:33
 **/
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class SysRolePermissionServiceImpl implements ISysRolePermissionService {

    @Resource
    private SysRolePermissionDao sysRolePermissionDao;

    /**
     * 新增
     *
     * @param rolePermission
     * @return
     */
    @Override
    public GlobalResult save(SysRolePermission rolePermission) {
        try {
            int b =  sysRolePermissionDao.insertUseGeneratedKeys(rolePermission);
            return b>0? GlobalEnums.INSERT_SUCCESS.results(): GlobalEnums.INSERT_ERROR.results();
        }catch (Exception e){
            log.error("权限角色表-新增",e);
            return GlobalEnums.INSERT_ERROR.results();
        }
    }
}
