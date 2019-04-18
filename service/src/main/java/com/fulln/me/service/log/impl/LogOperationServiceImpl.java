package com.fulln.me.service.log.impl;


import com.fulln.me.api.common.utils.DateUtil;
import com.fulln.me.api.model.log.LogOperationInfo;
import com.fulln.me.dao.log.LogOperationDao;
import com.fulln.me.service.log.ILogOperationService;
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
public class LogOperationServiceImpl implements ILogOperationService {


    @Resource
    private LogOperationDao operationDao;

    @Override
    public Boolean save(LogOperationInfo info) {
        try {
            info.setOperationDate(DateUtil.getNowTimeStamp());
            return operationDao.insertSelective(info)>0;
        } catch (Exception e) {
            log.error("用户操作存入", e);
            return false;
        }
    }


}
