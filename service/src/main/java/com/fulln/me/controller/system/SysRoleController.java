package com.fulln.me.controller.system;

import com.fulln.me.api.model.system.SysRole;
import com.fulln.me.api.service.system.ISysRoleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author fulln
 * @version 0.0.1
 * @program apiCloud
 * @description 系统权限业务层
 * @date 2019/4/7 230
 **/
@RestController
@RequestMapping("/role")
public class SysRoleController {

    @Resource
    private ISysRoleService sysRoleService;

    @GetMapping("/{id}")
    public SysRole findById(@PathVariable Integer id){
        return sysRoleService.findById(id);
    }
}
