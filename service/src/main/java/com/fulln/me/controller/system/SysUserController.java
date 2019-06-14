package com.fulln.me.controller.system;


import com.fulln.me.api.common.entity.GlobalResult;
import com.fulln.me.api.model.user.SysUserBasic;
import com.fulln.me.service.system.ISysUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @program: api
 * @description: 基础用户接口
 * @author: fulln
 * @create: 2018-09-27 17:58
 * @Version： 0.0.1
 **/
@RestController
@RequestMapping("/user")
public class  SysUserController {

    @Resource
    public ISysUserService sysUserService;
    /**
     * 根据用户名查找用户
     * @param name
     * @return
     */
    @GetMapping("/findByName")
    public SysUserBasic selectByUsername(String name){
        return  sysUserService.selectByUsername(name);
    }

    /**
     * 根据名称更新
     * @param name
     * @param count
     * @return
     */
    @PostMapping("/updateLoginFail")
    public Boolean updateLoginFail(String name, int count){
         return sysUserService.updateLoginFail(name,count);
    }

    /**
     * 更新
     * @param currentUser
     * @return
     */
    @PostMapping("/update")
    public GlobalResult Update(@RequestBody SysUserBasic currentUser){
        return sysUserService.Update(currentUser);
    }

    @PostMapping("/save")
    public GlobalResult save(@RequestBody SysUserBasic sysUserBasic){
        return  sysUserService.emailCheckForRegister(sysUserBasic);
    }

    @GetMapping("/check")
    public GlobalResult  registerEmailBack(String registerCode){
        return  sysUserService.checkRegistEmailBack(registerCode);
    }
}
