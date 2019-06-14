package com.fulln.me.dao.system;


import com.fulln.me.api.model.system.SysRole;
import com.fulln.me.config.basic.MyMapper;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @program: api
 * @description: 用户角色数据交互层
 * @author: fulln
 * @create: 2018-10-19 14:56
 * @Version： 0.0.1
 **/
@Repository
public interface SysRoleDao extends MyMapper<SysRole> {

    @Query("SELECT * FROM SYS_ROLE SR LEFT JOIN SYS_USER_ROLE SU ON SR.role_id = su.roleId where su.user_id = #{userId}")
    SysRole findByUserId(Integer userId);

}
