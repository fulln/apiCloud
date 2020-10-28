package me.fulln.base.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 设置了之后可以通过 @Enumerated 进行匹配。减少代码的不规范性
 */
public enum SexEnums {
    MAN,WOMAN;

    @JsonValue
    public int getJsonValue() {
        return ordinal();
    }

}
