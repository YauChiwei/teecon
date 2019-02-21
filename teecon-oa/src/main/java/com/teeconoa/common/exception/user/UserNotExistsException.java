package com.teeconoa.common.exception.user;

/**
 * 用户不存在异常类
 * 
*  Created by AndyYau
*  2019年2月13日 - 上午9:03:05
*  Company: Teecon
 */
public class UserNotExistsException extends UserException
{
    private static final long serialVersionUID = 1L;

    public UserNotExistsException()
    {
        super("user.not.exists", null);
    }
}
