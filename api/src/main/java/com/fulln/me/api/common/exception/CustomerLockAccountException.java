/**
 * 
 */
package com.fulln.me.api.common.exception;

import org.apache.shiro.authc.AccountException;

/**
 * @Author: fulln
 * @Date: 2018/10/19 0019-17:28
 */
@SuppressWarnings("serial")
public class CustomerLockAccountException extends AccountException {

	public CustomerLockAccountException(){
		
	}
	
	public CustomerLockAccountException(String message) {
        super(message);
    }

    /**
     * Constructs a new LockedAccountException.
     *
     * @param cause the underlying Throwable that caused this exception to be thrown.
     */
    public CustomerLockAccountException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new LockedAccountException.
     *
     * @param message the reason for the exception
     * @param cause   the underlying Throwable that caused this exception to be thrown.
     */
    public CustomerLockAccountException(String message, Throwable cause) {
        super(message, cause);
    }
}
