package com.fulln.me.service.system.impl;


import me.fulln.base.common.entity.GlobalResult;
import me.fulln.base.common.enums.GlobalEnums;
import me.fulln.base.common.utils.CheckParamsUtil;
import me.fulln.base.model.system.SysRole;
import com.fulln.me.dao.system.SysRoleDao;
import com.fulln.me.service.system.ISysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @program: api
 * @description: 用户接口层实现
 * @author: fulln
 * @create: 2018-09-27 17:59
 * @Version： 0.0.1
 **/
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class SysRoleServiceImpl implements ISysRoleService {

    @Resource
    private SysRoleDao sysRoleDao;


    @Override
    public SysRole findById(Integer id) {
        return sysRoleDao.selectByPrimaryKey(id);
    }

    @Override
    public GlobalResult findByUserId(Integer userId) {
        try {
            CheckParamsUtil.checkNull("用户id",userId);
            SysRole sysRole = sysRoleDao.findByUserId(userId);

            if(sysRole == null){
                return GlobalEnums.QUERY_EMPTY.results();
            }
            return GlobalEnums.QUERY_SUCCESS.results(sysRole);
        }catch (Exception e){
            log.error("查询用户权限出现异常", e);
            return GlobalEnums.QUERY_FAIL.results();
        }

    }
}
