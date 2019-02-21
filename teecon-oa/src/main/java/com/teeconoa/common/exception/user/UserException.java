package com.teeconoa.common.exception.user;

import com.teeconoa.common.exception.base.BaseException;

/**
*  Created by AndyYau
*  2019年2月13日 - 上午9:03:05
*  Company: Teecon
**/
public class UserException extends BaseException {

	private static final long serialVersionUID = 1L;

    public UserException(String code, Object[] args)
    {
        super("user", code, args, null);
    }
}
