package com.fulln.me.controller.system;


import com.fulln.me.api.common.entity.GlobalResult;
import com.fulln.me.api.model.system.SysPermission;
import com.fulln.me.api.service.system.ISysPermissionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping("/findByUser")
    public List<SysPermission> loadUserResources(Integer id) {
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
