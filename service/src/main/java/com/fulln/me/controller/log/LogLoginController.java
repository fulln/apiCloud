package com.fulln.me.controller.log;

import com.fulln.me.api.common.entity.GlobalResult;
import com.fulln.me.api.model.log.LogLoginInfo;
import com.fulln.me.service.log.ILogLoginService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author fulln
 * @version 0.0.1
 * @program apiCloud
 * @description 登陆日志记录
 * @date 2019/4/7 23:18
 **/
@RestController
@RequestMapping("/logLogin")
public class LogLoginController {

    @Resource
    private ILogLoginService logLoginService;

    /**
     * 修改
     *
     * @param loginInfo
     * @return
     */
    @PostMapping("/update")
    public GlobalResult update(LogLoginInfo loginInfo) {
        return logLoginService.update(loginInfo);
    }

    /**
     * 新增
     */
    @PostMapping("/insert")
    public GlobalResult insert(LogLoginInfo loginInfo) {
        return logLoginService.insert(loginInfo);
    }

}
