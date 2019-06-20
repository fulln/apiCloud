package com.fulln.me.web.controller.system.user;

import com.fulln.me.api.common.entity.GlobalResult;
import com.fulln.me.api.model.user.SysUserBasic;
import com.fulln.me.web.service.system.ISysUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author fulln
 * @version 0.0.1
 * @program apiCloud
 * @description 用户控制层
 * @date 2019/6/10 23:14
 **/
@Controller
@RequestMapping("/registered")
public class RegisteredController {

    @Resource
    private ISysUserService sysUserService;

    @GetMapping
    public String registered() {
        return "regist";
    }

    @PostMapping("/save")
    @ResponseBody
    public GlobalResult saveUser(SysUserBasic userBasic){
        return sysUserService.saveUser(userBasic);
    }

    @PostMapping("/{registerCode}")
    @ResponseBody
    public GlobalResult confirmCOde(@PathVariable String registerCode){
        return sysUserService.CheckUserByEmail(registerCode);
    }




}
