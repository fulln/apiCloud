/**
 *
 */
package me.fulln.base.common.exception;

import me.fulln.base.common.enums.GlobalEnums;
import me.fulln.base.common.enums.config.EnumsConfig;

import java.text.MessageFormat;

/**
 * @Author: fulln
 * @Description: service异常
 * @Date: 2018/10/19 0019-17:28
 */
@SuppressWarnings("serial")
public class ServiceException extends RuntimeException {

    private static final String EXCEPTION_CONTEXT = "[custom-service]:exception code is [{0}],the description is {1}";

    private Integer code;
    private String message;
    private String desc;
    public ServiceException() {}

    public ServiceException(String message) {
        super(MessageFormat.format(EXCEPTION_CONTEXT,GlobalEnums.SYS_ERROR.code,message));
        this.code = GlobalEnums.SYS_ERROR.code;
        this.message = message;
    }

    public ServiceException(Integer code,String message,String desc) {
        super(desc);
        this.message = message;
        this.code = code;
        this.desc =desc;
    }

    /**
     * Constructs a new LockedAccountException.
     *
     * @param message the reason for the exception
     * @param cause   the underlying Throwable that caused this exception to be thrown.
     */
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
        this.code = GlobalEnums.SYS_ERROR.code;
        this.message = message;
    }

    public static ServiceException custom(EnumsConfig enums){
        return new ServiceException(enums.getCode(),enums.getMessage(),
                MessageFormat.format(EXCEPTION_CONTEXT,enums.getCode(),enums.getMessage()));
    }

    public static ServiceException custom(EnumsConfig enums,String msg){
        String desc = MessageFormat.format(EXCEPTION_CONTEXT,enums.getCode(),msg);
        return new ServiceException(enums.getCode(),msg,desc);
    }

    public Integer getCode() {
        return code;
    }

    /**
     * Constructs a new LockedAccountException.
     *
     * @param cause the underlying Throwable that caused this exception to be thrown.
     */
    public ServiceException(Throwable cause) {
        super(cause);
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
