package com.teeconoa.common.exception.user;

/**
 * 用户锁定异常类
 * 
*  Created by AndyYau
*  2019年2月13日 - 上午9:04:45
*  Company: Teecon
 */
public class UserBlockedException extends UserException
{
    private static final long serialVersionUID = 1L;

    public UserBlockedException(String reason)
    {
        super("user.blocked", new Object[] { reason });
    }
}
