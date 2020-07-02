/**
 *
 */
package com.fulln.me.api.common.exception;

import com.fulln.me.api.common.enums.GlobalEnums;

import java.text.MessageFormat;

/**
 * @Author: fulln
 * @Description: service异常
 * @Date: 2018/10/19 0019-17:28
 */
@SuppressWarnings("serial")
public class ServiceException extends RuntimeException {

    private static final String EXCEPTION_CONTEXT = "[custom-service]:exception code is [{0}],the description is {1}";

    public ServiceException() {

    }

    public ServiceException(String message) {
        super(message);
    }

    /**
     * Constructs a new LockedAccountException.
     *
     * @param cause the underlying Throwable that caused this exception to be thrown.
     */
    public ServiceException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new LockedAccountException.
     *
     * @param message the reason for the exception
     * @param cause   the underlying Throwable that caused this exception to be thrown.
     */
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public static ServiceException custom(GlobalEnums enums){
        String message = MessageFormat.format(EXCEPTION_CONTEXT,enums.code,enums.message);
        return new ServiceException(message);
    }

    public static ServiceException custom(GlobalEnums enums,String msg){
        String message = MessageFormat.format(EXCEPTION_CONTEXT,enums.code,msg);
        return new ServiceException(message);
    }

}
