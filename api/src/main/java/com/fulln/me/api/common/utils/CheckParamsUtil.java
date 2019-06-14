package com.fulln.me.api.common.utils;

import com.fulln.me.api.common.exception.ServiceException;
import org.springframework.util.StringUtils;

/**
 * @author fulln
 * @version 0.0.1
 * @program apiCloud
 * @description 参数检验实体
 * @date 2019/6/10 23:29
 **/
public class CheckParamsUtil {

    public static void checkNull(String paramsName,Object t){
        if(StringUtils.isEmpty(paramsName)){
            throw new ServiceException(paramsName+" is Empty");
        }
    }
}
