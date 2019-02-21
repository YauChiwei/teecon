package com.teeconoa.common.exception.user;

/**
 * 用户密码不正确或不符合规范异常类
 * 
*  Created by AndyYau
*  2019年2月13日 - 上午9:03:05
*  Company: Teecon
 */
public class UserPasswordNotMatchException extends UserException
{
    private static final long serialVersionUID = 1L;

    public UserPasswordNotMatchException()
    {
        super("user.password.not.match", null);
    }
}
