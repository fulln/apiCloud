package com.fulln.me.api.common.utils;

import com.fulln.me.api.common.exception.ServiceException;

import java.util.Objects;

/**
 * @author fulln
 * @version 0.0.1
 * @program apiCloud
 * @description 参数检验实体
 * @date 2019/6/10 23:29
 **/
public class CheckParamsUtil {

    public static void checkNull(String paramsName,Object t){
        if(Objects.isNull(t)){
            throw new ServiceException(paramsName+" is Empty");
        }
    }




}
