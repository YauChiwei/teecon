package com.teeconoa.common.exception.user;

/**
 * 用户错误记数异常类
 * 
*  Created by AndyYau
*  2019年2月13日 - 上午9:03:05
*  Company: Teecon
 */
public class UserPasswordRetryLimitCountException extends UserException
{
    private static final long serialVersionUID = 1L;

    public UserPasswordRetryLimitCountException(int retryLimitCount)
    {
        super("user.password.retry.limit.count", new Object[] { retryLimitCount });
    }
}
