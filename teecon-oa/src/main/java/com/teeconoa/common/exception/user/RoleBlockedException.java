package com.teeconoa.common.exception.user;
/**
 * 角色锁定异常类
*  Created by AndyYau
*  2019年2月13日 - 上午9:04:45
*  Company: Teecon
**/
public class RoleBlockedException extends UserException {

	private static final long serialVersionUID = 1L;

    public RoleBlockedException(String reason)
    {
        super("role.blocked", new Object[] { reason });
    }
}
