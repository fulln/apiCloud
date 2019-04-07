package com.fulln.me.api.service.log;


import com.fulln.me.api.model.log.LogOperationInfo;

/**
 * @author fulln
 * @program api
 * @description 用户操作业务层
 * @Date 2019-01-23 15:27
 * @Version 0.0.1
 **/
public interface ILogOperationService {

    void save(LogOperationInfo info);

}
