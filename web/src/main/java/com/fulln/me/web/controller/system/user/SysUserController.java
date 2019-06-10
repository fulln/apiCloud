package com.fulln.me.web.controller.system.user;

import com.fulln.me.api.common.entity.GlobalResult;
import com.fulln.me.api.model.system.SysUserBasic;
import com.fulln.me.web.service.system.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fulln
 * @version 0.0.1
 * @program apiCloud
 * @description 用户控制层
 * @date 2019/6/10 23:14
 **/
@RestController
@RequestMapping("/user")
public class SysUserController {

    @Autowired
    private ISysUserService sysUserService;

    @GetMapping("/save")
    public GlobalResult saveUser(SysUserBasic userBasic){
        return sysUserService.saveUser(userBasic);
    }
}
