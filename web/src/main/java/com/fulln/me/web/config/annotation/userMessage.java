package com.fulln.me.web.config.annotation;

import java.lang.annotation.*;

/**
 * @author fulln
 * @version 0.0.1
 * @program apiCloud
 * @description 获取当前用户的注解
 * @date 2019/5/25 0:01
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface userMessage {

    String value() ;

    String methodName() default "getUser";

}
