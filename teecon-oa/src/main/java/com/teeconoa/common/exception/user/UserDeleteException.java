package com.teeconoa.common.exception.user;

/**
 * 用户账号已被删除
 * 
*  Created by AndyYau
*  2019年2月13日 - 上午9:04:45
*  Company: Teecon
 */
public class UserDeleteException extends UserException
{
    private static final long serialVersionUID = 1L;

    public UserDeleteException()
    {
        super("user.password.delete", null);
    }
}
