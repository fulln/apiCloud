/**
 * 
 */
package me.fulln.base.common.exception;

import org.apache.shiro.authc.AccountException;

/**
 * @Author: fulln
 * @Description
 * @Date: 2018/10/19 0019-17:28
 */
@SuppressWarnings("serial")
public class DisableCustomerException extends AccountException {

	public DisableCustomerException(){
		
	}
	
	public DisableCustomerException(String message) {
        super(message);
    }

    /**
     * Constructs a new LockedAccountException.
     *
     * @param cause the underlying Throwable that caused this exception to be thrown.
     */
    public DisableCustomerException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new LockedAccountException.
     *
     * @param message the reason for the exception
     * @param cause   the underlying Throwable that caused this exception to be thrown.
     */
    public DisableCustomerException(String message, Throwable cause) {
        super(message, cause);
    }
}
