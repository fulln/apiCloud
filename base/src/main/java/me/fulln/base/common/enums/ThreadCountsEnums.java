package me.fulln.base.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @program: api
 * @description: 线程池启动枚举
 * @author: fulln
 * @create: 2018-10-09 17:22
 * @Version： 0.0.1
 **/
public enum  ThreadCountsEnums {

    /**
     * 搜索专用
     */
    SEARCH;

    @JsonValue
    public int getJsonValue() {
        return ordinal();
    }

}
