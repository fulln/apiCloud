package me.fulln.base.model.system.cloums;

import com.fasterxml.jackson.annotation.JsonValue;
import me.fulln.base.common.enums.config.CloumsEnumsConfig;


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
