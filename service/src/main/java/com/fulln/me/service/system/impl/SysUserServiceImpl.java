package com.fulln.me.service.system.impl;


import com.fulln.me.api.common.entity.GlobalResult;
import com.fulln.me.api.model.system.SysUserBasic;
import com.fulln.me.config.enums.GlobalEnums;
import com.fulln.me.dao.system.SysUserDao;
import com.fulln.me.service.system.ISysUserService;
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
public class SysUserServiceImpl implements ISysUserService {

    @Resource
    private SysUserDao userDao;

    /**
     * 根据用户名查找用户
     *
     * @param name
     * @return
     */
    @Override
    public SysUserBasic selectByUsername(String name) {
        try {
            SysUserBasic userBasic = new SysUserBasic();
            userBasic.setUserName(name);
            return userDao.selectOne(userBasic);
        } catch (Exception e) {
            log.error("根据用户名查找异常", e);
            return null;
        }
    }

    /**
     * 根据名称更新
     *
     * @param name
     * @param count
     * @return
     */
    @Override
    public boolean updateLoginFail(String name, int count) {
        return userDao.updateLoginFail(name,count)>0;
    }

    /**
     * 更新
     *
     * @param currentUser
     * @return
     */
    @Override
    public GlobalResult Update(SysUserBasic currentUser) {
        try {
            userDao.updateByPrimaryKeySelective(currentUser);
            return GlobalEnums.UPDATE_SUCCESS.results();
        } catch (Exception e) {
            log.error("根据用户名更新", e);
            return GlobalEnums.UPDATE_ERROR.results();
        }
    }
}