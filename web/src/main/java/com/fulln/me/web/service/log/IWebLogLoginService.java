package com.fulln.me.web.service.log;

import com.fulln.me.api.common.entity.GlobalResult;
import com.fulln.me.api.model.log.LogLoginInfo;
import com.fulln.me.api.service.log.ILogLoginService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author fulln
 * @version 0.0.1
 * @program apiCloud
 * @description 登陆日志记录
 * @date 2019/4/7 23:18
 **/
@FeignClient("${feign.url}")
public interface IWebLogLoginService extends ILogLoginService {

    /**
     * 修改
     * @param loginInfo
     * @return
     */
    @PostMapping("/logLogin/update")
    GlobalResult update(LogLoginInfo loginInfo);

    /**
     * 新增
     */
    @PostMapping("/logLogin/insert")
    GlobalResult insert(LogLoginInfo loginInfo);

}
