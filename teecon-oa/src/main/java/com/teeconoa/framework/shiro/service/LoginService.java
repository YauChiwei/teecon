package com.teeconoa.framework.shiro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.teeconoa.common.DateUtils;
import com.teeconoa.common.ServletUtils;
import com.teeconoa.common.constant.Constants;
import com.teeconoa.common.constant.ShiroConstants;
import com.teeconoa.common.constant.UserConstants;
import com.teeconoa.common.exception.user.CaptchaException;
import com.teeconoa.common.exception.user.UserDeleteException;
import com.teeconoa.common.exception.user.UserNotExistsException;
import com.teeconoa.common.exception.user.UserPasswordNotMatchException;
import com.teeconoa.common.utils.MessageUtils;
import com.teeconoa.common.utils.security.ShiroUtils;
import com.teeconoa.framework.manager.AsyncManager;
import com.teeconoa.framework.manager.factory.AsyncFactory;
import com.teeconoa.project.system.user.domain.User;
import com.teeconoa.project.system.user.domain.UserStatus;
import com.teeconoa.project.system.user.service.IUserService;

/**
*  Created by AndyYau
*  Mar 26, 2019 - 12:55:15 AM
*  Company: Teecon
**/
@Component
public class LoginService {

	@Autowired
	private PwdService pwdService;
	
	@Autowired
	private IUserService userService;
	
	public User login(String username, String password) {
		//验证码校验
		if(!StringUtils.isEmpty(ServletUtils.getRequest().getAttribute(ShiroConstants.CURRENT_CAPTCHA))) {
			AsyncManager.newInstance().executeTask(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error")));
			throw new CaptchaException();
		}
		//用户名或密码为空校验
		if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
			AsyncManager.newInstance().executeTask(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("not.null")));
			throw new UserNotExistsException();
		}
		//用户密码长度不符合校验
		if(password.length() < UserConstants.PASSWORD_MIN_LENGTH || 
				password.length() > UserConstants.PASSWORD_MAX_LENGTH) {
			AsyncManager.newInstance().executeTask(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
			throw new UserPasswordNotMatchException();
		}
		//用户名长度不符合校验
		if(username.length() < UserConstants.USERNAME_MIN_LENGTH || 
				username.length() > UserConstants.USERNAME_MAX_LENGTH) {
			AsyncManager.newInstance().executeTask(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
			throw new UserPasswordNotMatchException();
		}
		
		User user = userService.selectUserByLoginName(username);
		
		if(user == null && loginByEmail(username)) {
			user = userService.selectUserByEmail(username);
		}
		if(user ==null && loginByTelephone(username)) {
			user = userService.selectUserByPhoneNumber(username);
		}
		
		if(user == null) {
			AsyncManager.newInstance().executeTask(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.not.exists")));
			throw new UserNotExistsException();
		}
		if(UserStatus.DELETED.getCode().equals(user.getDelFlag())) {
			AsyncManager.newInstance().executeTask(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.delete")));
			throw new UserDeleteException();
		}
		if(UserStatus.DISABLE.getCode().equals(user.getStatus())) {
			AsyncManager.newInstance().executeTask(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.blocked", user.getRemark())));
			throw new UserDeleteException();
		}
		
		pwdService.volidateUser(user, password);
		AsyncManager.newInstance().executeTask(AsyncFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
		recordLoginInfo(user);
		return user;
	}
	
	private boolean loginByEmail(String username) {
		return username.matches(UserConstants.EMAIL_PATTERN) ? true : false;
	}
	
	private boolean loginByTelephone(String username) {
		return username.matches(UserConstants.MOBILE_PHONE_NUMBER_PATTERN) ? true : false;
	}
	
	 /**
     * 记录登录信息
     */
    public void recordLoginInfo(User user)
    {
        user.setLoginIp(ShiroUtils.getIp());
        user.setLoginDate(DateUtils.getNowDate());
        userService.updateUserInfo(user);
    }
}
