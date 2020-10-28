package com.fulln.me.service.log;

import me.fulln.base.common.entity.GlobalResult;
import me.fulln.base.model.log.LogLoginInfo;

/**
 * @author fulln
 * @version 0.0.1
 * @program apiCloud
 * @description 登陆日志记录
 * @date 2019/4/7 23:18
 **/
public interface ILogLoginService {

    /**
     * 修改
     * @param loginInfo
     * @return
     */
    GlobalResult update(LogLoginInfo loginInfo);

    /**
     * 新增
     */
    GlobalResult insert(LogLoginInfo loginInfo);

}
