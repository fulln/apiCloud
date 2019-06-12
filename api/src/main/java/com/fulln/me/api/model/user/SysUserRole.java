package com.fulln.me.api.model.user;

import com.fulln.me.api.common.annotation.MongoField;
import lombok.Data;

/**
 * @author fulln
 * @version 0.0.1
 * @program apiCloud
 * @description 用户角色关联表
 * @date 2019/6/12 22:55
 **/
@Data
public class SysUserRole {

    @MongoField
    private Long userId;
    @MongoField
    private String userName;
    @MongoField
    private Long roleId;
    private Long createTime;
    private Long updateTime;
    private String createBy;
    private String updateBy;
    private Integer isDelete;

}
