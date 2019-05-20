package com.fulln.me.controller.system;


import com.fulln.me.api.common.entity.GlobalResult;
import com.fulln.me.api.model.system.SysPermission;
import com.fulln.me.service.system.ISysPermissionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @AUthor: fulln
 * @Description: 权限表
 * @Date : Created in  0:25  2018/10/12.
 */
@RestController
@RequestMapping("/permission")
public class SysPermissionController {

    @Resource
    private ISysPermissionService sysPermissionService;

    /**
     * 根据用户查询权限
     *
     * @param id
     * @return
     */
    @PostMapping("/findByUser")
    public List<SysPermission> loadUserResources(@RequestParam("id") Integer id) {
        return sysPermissionService.loadUserResources(id);
    }

    /**
     * 查询全部权限
     *
     * @return
     */
    @GetMapping("/list")
    public List<SysPermission> queryAll() {
        return sysPermissionService.queryAll();
    }

    /**
     * 新增
     *
     * @return
     */
    @PostMapping("/save")
    public GlobalResult save(SysPermission permission) {
        return sysPermissionService.save(permission);
    }

}
