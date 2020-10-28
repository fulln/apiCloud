package me.fulln.tuc.controller;


import me.fulln.base.common.entity.GlobalResult;
import me.fulln.tuc.model.user.SysUserBasic;
import me.fulln.tuc.tucapi.user.ISysUserService;
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
public class SysUserController {

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
        return  sysUserService.saveUser(sysUserBasic);
    }

    @GetMapping("/check")
    public GlobalResult  registerEmailBack(String registerCode){
        return  sysUserService.checkUserByEmail(registerCode);
    }
}
