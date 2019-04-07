package com.fulln.me.api.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @program: api
 * @description: 用户账号状态枚举
 * @author: fulln
 * @create: 2018-10-24 17:06
 * @Version： 0.0.1
 **/
public enum UserStatEnums {
    /**
     * 未激活
     */
    ACCOUNT_UNACTIVATED,
    /**
     * 正常
     */
    ACCOUNT_NORMAL,
    /**
     * 锁定
     */
    ACCOUNT_LOCK,
    /**
     * 删除
     */
    ACCOUNT_DELETED

    ;

    @JsonValue
    public int getJsonValue() {
        return ordinal();
    }

}
