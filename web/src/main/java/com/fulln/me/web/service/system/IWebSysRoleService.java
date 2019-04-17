package com.fulln.me.web.service.system;

import com.fulln.me.api.model.system.SysRole;
import com.fulln.me.api.service.system.ISysRoleService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author fulln
 * @version 0.0.1
 * @program apiCloud
 * @description 系统权限业务层
 * @date 2019/4/7 230
 **/
@FeignClient("${feign.url}")
public interface IWebSysRoleService extends ISysRoleService {

    @GetMapping("/role/{id}")
    SysRole findById(@PathVariable Integer id);
    
}
