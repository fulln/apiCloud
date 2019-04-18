package com.fulln.me.service.log.impl;


import com.fulln.me.api.common.entity.GlobalResult;
import com.fulln.me.api.model.log.LogLoginInfo;
import com.fulln.me.config.enums.GlobalEnums;
import com.fulln.me.dao.log.LogLoginDao;
import com.fulln.me.service.log.ILogLoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author fulln
 * @version 0.0.1
 * @program api
 * @description 用户操作业务层实现
 * @date 2019-01-23 15:29
 **/
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class LogLoginServiceImpl implements ILogLoginService {

    @Resource
    private LogLoginDao logLoginDao;

    /**
     * 修改
     *
     * @param loginInfo
     * @return
     */
    @Override
    public GlobalResult update(LogLoginInfo loginInfo) {
        try {
            logLoginDao.updateByPrimaryKeySelective(loginInfo);
            return GlobalEnums.UPDATE_SUCCESS.results();
        } catch (Exception e) {
            log.error("登陆日志-修改", e);
            return GlobalEnums.UPDATE_ERROR.results();
        }
    }

    /**
     * 新增
     *
     * @param loginInfo
     */
    @Override
    public GlobalResult insert(LogLoginInfo loginInfo) {
        try {
            logLoginDao.insertSelective(loginInfo);
            return GlobalEnums.INSERT_SUCCESS.results();
        } catch (Exception e) {
            log.error("登陆日志-新增", e);
            return GlobalEnums.INSERT_ERROR.results();
        }
    }
}
