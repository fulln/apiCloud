package com.fulln.me.api.common.enums.config;


import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * @AUthor: fulln
 * @Description:  枚举配置类  对应global枚举
 * @Date : Created in  18:46  2018/8/4.
 */
public interface EnumsConfig {

    String DEFAULT_CODE_NAME = "code";
    String DEFAULT_DATA_NAME = "datas";
    String DEFAULT_MESSAGE_NAME = "message";

    default Integer getCode() {
        Field field = ReflectionUtils.findField(this.getClass(), DEFAULT_CODE_NAME);
        if (field == null){
            return null;
        }
        try {
            field.setAccessible(true);
            return Integer.parseInt(field.get(this).toString());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


    @JsonValue
    default String getData() {
        return getField(DEFAULT_DATA_NAME);
    }

    @JsonValue
    default String getMessage() {
        return getField(DEFAULT_MESSAGE_NAME);
    }

    /**
     * 获取指定字段
     * @param defaultMessageName
     * @return
     */
    default String getField(String defaultMessageName) {
        Field field = ReflectionUtils.findField(this.getClass(), defaultMessageName);
        if (field == null){
            return null;
        }
        try {
            field.setAccessible(true);
            return field.get(this).toString();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据id查找对应的枚举
     * handle 类型转换器
     * @param enumClass
     * @param code  数据库查出的类型
     * @param <T>
     * @return T
     */
    static <T extends Enum<T>> T valueOfEnum(Class<T> enumClass, Integer code) {
        if (code == null){
            throw new IllegalArgumentException("DisplayedEnum value should not be null");
        }
        if (enumClass.isAssignableFrom(EnumsConfig.class)){
            throw new IllegalArgumentException("illegal DisplayedEnum type");
        }

        T[] enums = enumClass.getEnumConstants();

        for (T t : enums) {
            EnumsConfig enumsConfig = (EnumsConfig) t;
            if (enumsConfig.getCode().equals(code)){
                return (T) enumsConfig;
            }
        }
        throw new IllegalArgumentException("cannot parse integer: " + code + " to " + enumClass.getName());
    }



}
