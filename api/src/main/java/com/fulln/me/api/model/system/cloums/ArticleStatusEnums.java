package com.fulln.me.api.model.system.cloums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fulln.me.api.common.enums.config.CloumsEnumsConfig;


/**
 * @AUthor: fulln
 * @Description:  文章状态枚举
 * @Date : Created in  23:53  2018/11/16.
 */
public enum ArticleStatusEnums implements CloumsEnumsConfig {

    SAVED,
    PUSHED,
    DELETED;

    @JsonValue
    public int getJsonValue() {
        return ordinal();
    }

}
