package com.teeconoa.common.exception.user;
/**
*  Created by AndyYau
*  2019年2月13日 - 上午9:03:57
*  Company: Teecon
**/
public class CaptchaException extends UserException {

	private static final long serialVersionUID = 1L;

    public CaptchaException()
    {
        super("user.jcaptcha.error", null);
    }
}
