package com.fulln.me.web.service.system;

import me.fulln.base.model.system.SysRole;
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
@FeignClient(value = "${feign.url}",path = "${feign.path}")
public interface  ISysRoleService {

    @GetMapping("/role/{id}")
    SysRole findById(@PathVariable Integer id);
    
}
