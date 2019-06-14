package com.fulln.me.service.system;


import com.fulln.me.api.common.entity.GlobalResult;
import com.fulln.me.api.model.user.SysUserBasic;

/**
 * @program: api
 * @description: 基础用户接口
 * @author: fulln
 * @create: 2018-09-27 17:58
 * @Version： 0.0.1
 **/
public interface ISysUserService {

    /**
     * 根据用户名查找用户
     * @param name
     * @return
     */
    SysUserBasic selectByUsername(String name);

    /**
     * 根据名称更新
     * @param name
     * @param count
     * @return
     */
    boolean updateLoginFail(String name, int count);

    /**
     * 更新
     * @param currentUser
     * @return
     */
    GlobalResult Update(SysUserBasic currentUser);

    /**
     * 新增用户
     * @param sysUserBasic
     * @return
     */
    GlobalResult add(SysUserBasic sysUserBasic);

    /**
     * 邮件注册校验当前邮件是本人邮件
     * @param sysUserBasic
     * @return
     */
    GlobalResult emailCheckForRegister(SysUserBasic sysUserBasic);

    /**
     * 邮件返回值之后获取当前的值进行校验
     * @return
     */
    GlobalResult checkRegistEmailBack(String registerCode);
}
