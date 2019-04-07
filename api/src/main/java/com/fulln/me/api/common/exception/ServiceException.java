/**
 *
 */
package com.fulln.me.api.common.exception;

/**
 * @Author: fulln
 * @Description: service异常
 * @Date: 2018/10/19 0019-17:28
 */
@SuppressWarnings("serial")
public class ServiceException extends Exception {

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
}
