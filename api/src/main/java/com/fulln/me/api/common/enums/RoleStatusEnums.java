package com.fulln.me.api.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @program: api
 * @description: 角色状态枚举
 * @author: fulln
 * @create: 2018-10-24 18:10
 * @Version： 0.0.1
 **/
public enum  RoleStatusEnums {

    /**
     * 正常
     */
    ROLE_NORMAL,
    /**
     * 锁定
     */
    ROLE_LOCK,
    /**
     * 删除
     */
    ROLE_DELETED

    ;

    @JsonValue
    public int getJsonValue() {
        return ordinal();
    }
}
