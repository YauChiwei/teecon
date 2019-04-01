package com.teeconoa.framework.shiro.service;

import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.teeconoa.common.constant.Constants;
import com.teeconoa.common.exception.user.UserPasswordNotMatchException;
import com.teeconoa.common.exception.user.UserPasswordRetryLimitExceedException;
import com.teeconoa.common.utils.MessageUtils;
import com.teeconoa.framework.manager.AsyncManager;
import com.teeconoa.framework.manager.factory.AsyncFactory;
import com.teeconoa.project.system.user.domain.User;

/**
*  Created by AndyYau
*  Mar 25, 2019 - 3:50:45 PM
*  Company: Teecon
**/

@Component
public class PwdService {

	@Autowired
	private CacheManager cacheManager;
	
	private Cache<String, AtomicInteger> loginRecordCache;
	
	@Value(value="${user.password.maxRetryCount}")
	private String maxRetryCount;
	
	/**
	 * Spring IOC容器初始化创建之后，Bean初始化之前和销毁之前，执行@PostConstruct注解的方法。一般用于一些项目初始化的设定。
	 * 比如Spring IOC Container 初始化之后，用@PostConstruct注解Quartz的 CronTrigger 用于初始化定时器（向定时器中添加定时启动的JOB）。
	 * 那么项目运行时就能自动的运行CronTrigger 中的job了
	 */
	@PostConstruct
	public void init() {
		loginRecordCache = cacheManager.getCache("loginRecordCache");
	}
	
	public void volidateUser(User user, String password) {
		String loginName = user.getLoginName();
		AtomicInteger retryCount = loginRecordCache.get(loginName);
		if(retryCount == null) {
			retryCount = new AtomicInteger(0);
			loginRecordCache.put(loginName, retryCount);
		}
		if(retryCount.incrementAndGet() > Integer.valueOf(maxRetryCount).intValue()) {
			AsyncManager.newInstance().executeTask(AsyncFactory.recordLogininfor(loginName, Constants.LOGIN_FAIL, MessageUtils.message("user.password.retry.limit.exceed", maxRetryCount)));
			throw new UserPasswordRetryLimitExceedException(Integer.valueOf(maxRetryCount).intValue());
		}
		if(!matches(user, password)) {
			AsyncManager.newInstance().executeTask(AsyncFactory.recordLogininfor(loginName, Constants.LOGIN_FAIL, MessageUtils.message("user.password.retry.limit.count", retryCount)));
			loginRecordCache.put(loginName, retryCount);
			throw new UserPasswordNotMatchException();
		}else {
			clearLoginRecordCache(loginName);
		}
	}
	
	private boolean matches(User user, String password) {
		return user.getPassword().equals(encryptPassword(user.getLoginName(), password, user.getSalt()));
	}
	
	private String encryptPassword(String username, String password, String salt) {
		return new Md5Hash(username + password + salt).toHex().toString();
	}
	
	public void clearLoginRecordCache(String username) {
		loginRecordCache.remove(username);
	}
	
	public static void main(String[] args)
    {
        System.out.println(new PwdService().encryptPassword("admin", "admin", "111111"));
    }
}
