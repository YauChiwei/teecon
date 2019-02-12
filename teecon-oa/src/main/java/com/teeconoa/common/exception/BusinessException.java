package com.teeconoa.common.exception;
/**
 * 业务异常
*  Created by AndyYau
*  2019年2月7日 - 下午4:20:00
*  Company: Teecon
**/
public class BusinessException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected final String message;
	
	public BusinessException(String message)
    {
        this.message = message;
    }

    @Override
    public String getMessage()
    {
        return message;
    }
}
