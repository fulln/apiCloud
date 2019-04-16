package com.fulln.me.web.service.system;


import com.fulln.me.api.common.entity.GlobalResult;
import com.fulln.me.api.model.system.SysUserBasic;
import com.fulln.me.api.service.system.ISysUserService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @program: api
 * @description: 基础用户接口
 * @author: fulln
 * @create: 2018-09-27 17:58
 * @Version： 0.0.1
 **/
@FeignClient("${feign.url}")
public interface IWebSysUserService  extends ISysUserService {

    /**
     * 根据用户名查找用户
     * @param name
     * @return
     */
    @GetMapping("/user/findByName")
    SysUserBasic selectByUsername(String name);

    /**
     * 根据名称更新
     * @param name
     * @param count
     * @return
     */
    @PostMapping("/user/updateLoginFail")
    boolean updateLoginFail(String name, int count);

    /**
     * 更新
     * @param currentUser
     * @return
     */
    @PostMapping("/user/update")
    GlobalResult Update(SysUserBasic currentUser);


}
