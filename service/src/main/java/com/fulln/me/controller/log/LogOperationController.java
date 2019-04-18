package com.fulln.me.controller.log;


import com.fulln.me.api.model.log.LogOperationInfo;
import com.fulln.me.service.log.ILogOperationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author fulln
 * @program api
 * @description 用户操作业务层
 * @Date 2019-01-23 15:27
 * @Version 0.0.1
 **/

@RestController
@RequestMapping("/logOperate")
public class LogOperationController {
    @Resource
    private ILogOperationService logOperationService;

    @PostMapping("/save")
    public Boolean save(LogOperationInfo info) {
        return logOperationService.save(info);
    }

}
