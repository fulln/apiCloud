package me.fulln.tuc.tucapi.user;


import me.fulln.base.common.entity.GlobalResult;
import me.fulln.tuc.model.user.SysUserBasic;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



/**
 * @program: api
 * @description: 基础用户接口
 * @author: fulln
 * @create: 2018-09-27 17:58
 * @Version： 0.0.1
 **/
@FeignClient(value = "${feign.url}",path = "${feign.path}")
public interface ISysUserService {

    /**
     * 根据用户名查找用户
     * @param name
     * @return
     */
    @GetMapping("/user/findByName")
    SysUserBasic selectByUsername(@RequestParam("name") String name);
    /**
     * 更新
     * @param currentUser
     * @return
     */
    @PostMapping("/user/update")
    GlobalResult Update(@RequestBody SysUserBasic currentUser);

    /**
     * 新增用户
     * @param sysUserBasic
     * @return
     */
    @PostMapping("/user/save")
    GlobalResult saveUser(@RequestBody SysUserBasic sysUserBasic);

    /**
     * 邮箱确认
     * @param registerCode
     * @return
     */
    @GetMapping("/user/check")
    GlobalResult checkUserByEmail(@RequestParam("registerCode") String registerCode);

}
