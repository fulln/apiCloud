package com.fulln.me.service.system.impl;


import com.fulln.me.api.model.system.SysRole;
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

}
