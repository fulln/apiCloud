package com.fulln.me.api.common.annotation;

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
@Target(ElementType.PARAMETER)
public @interface CurrentUser {

    /**
     * this value can focus on which params you want to added to
     * when you dont put anything in  ,it will use the first param
     * @return
     */
    String value() default "";

    String methodName() default "getUser";

}
