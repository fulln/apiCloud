package com.fulln.me.api.common.enums.config;

import com.fasterxml.jackson.annotation.JsonValue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.EnumSet;

/**
 * @program: api
 * @description: 枚举序列配置类
 * @author: fulln
 * @create: 2018-10-24 17:56
 * @Version： 0.0.1
 **/
public interface OrdinaryConfig {


    @JsonValue
    static <E extends Enum<E>> int getJsonValue(Class<E> enumClass) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Method method = enumClass.getDeclaredMethod("ordinal");
        return (int) method.invoke(enumClass);
    }


    static <E extends Enum<E>>  int chooses(Class<E> enumClass, String name){
        for (  E t : EnumSet.allOf(enumClass)){
            if(t.name().toLowerCase().equals(name)){
                return t.ordinal();
            }
        }
        return 0;
    }
}
