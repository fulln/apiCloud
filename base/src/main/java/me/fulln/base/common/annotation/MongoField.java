package me.fulln.base.common.annotation;

import me.fulln.base.common.enums.mongo.QueryType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author fulln
 * @description 用于实体Bean的属性上的注解，注解有两个属性可以设置，type表示查询类似，默认为equals<br/>
 *  * attribute表示要查询的属性，默认为空串，在使用时如果为空串，则默认为实体Bean字段的名称
 * @date   Created in  2020-07-20  10:16.
 * @param
 * @return
 **/


@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MongoField {
    /**
     * 查询的类型
     * @return
     */
	QueryType type() default QueryType.EQUALS;

    /**
     * 字段名称
     * @return
     */
	String attribute() default "";
} 