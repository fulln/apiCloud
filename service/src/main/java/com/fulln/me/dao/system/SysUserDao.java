package com.fulln.me.dao.system;


import com.fulln.me.api.model.system.SysUserBasic;
import com.fulln.me.config.basic.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @program: api
 * @description: 用户数据交互层
 * @author: fulln
 * @create: 2018-09-27 17:55
 * @Version： font.font.1
 **/
public interface SysUserDao extends MyMapper<SysUserBasic> {

    /**
     * 登录异常的次数统计
     * @param name
     * @param count
     */
    @Update("update sys_user_basic set LOGIN_FAIL_COUNTS = #{loginFailCount} where user_name = #{userName}")
    int updateLoginFail(@Param("userName") String name, @Param("loginFailCount") Integer count);





}
