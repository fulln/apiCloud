package com.fulln.me.api.common.utils;

import com.fulln.me.api.common.enums.GlobalEnums;
import com.fulln.me.api.common.exception.ServiceException;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * @author fulln
 * @version 0.0.1
 * @program apiCloud
 * @description 参数检验实体
 * @date 2019/6/10 23:29
 **/
public class CheckParamsUtil {

    private static Validator validator;

    public static void checkNull(String paramsName,Object t){
        if(Objects.isNull(t)){
            throw ServiceException.custom(GlobalEnums.EMPTY_PARAMETER,paramsName);
        }
    }

    public static <T> String checkParam(T object) {
        if (validator == null) {
            ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
            validator = validatorFactory.getValidator();
        }

        if(Objects.isNull(object)){
            return GlobalEnums.EMPTY_PARAMETER.getMessage();
        }
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<T>> validate = validator.validate(object);
        Optional<ConstraintViolation<T>> optional = validate.stream().findAny();
        if (optional.isPresent()) {
            ConstraintViolation<T> memberRefundQueryConstraintViolation = optional.get();
            return  memberRefundQueryConstraintViolation.getPropertyPath() + memberRefundQueryConstraintViolation.getMessage();
        }
        return null;
    }

    /**
     * 验证object对象的部分属性
     *
     * @param object 被验证对象
     * @param args   需要验证的属性列表
     * @param <T>
     * @return
     */
    public static <T> String checkParam(T object, String... args) {
        StringBuilder strBuff = new StringBuilder("");
        if (validator == null) {
            ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
            validator = validatorFactory.getValidator();
        }

        if (args == null) {
            return null;
        }

        for (String param : args) {
            Set<ConstraintViolation<T>> validSet = validator.validateProperty(object, param);
            if (validSet != null && validSet.size() > 0) {
                for (ConstraintViolation<T> cv : validSet) {
                    strBuff.append(cv.getPropertyPath()).append(":").append(cv.getMessage()).append(";");
                }
            }
        }
        return StringUtils.isEmpty(strBuff.toString()) ? null : strBuff.toString();
    }
}
