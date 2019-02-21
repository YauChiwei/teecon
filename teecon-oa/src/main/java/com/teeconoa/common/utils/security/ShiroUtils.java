package com.teeconoa.common.utils.security;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;

import com.teeconoa.common.StringUtils;
import com.teeconoa.common.utils.bean.BeanUtils;
import com.teeconoa.framework.shiro.realm.UserRealm;
import com.teeconoa.project.system.user.domain.User;


/**
*  Created by AndyYau
*  2019年2月15日 - 下午5:27:16
*  Company: Teecon
**/
public class ShiroUtils {

	public static Subject getSubject() {
		return SecurityUtils.getSubject();
	}
	
	public static Session getSession() {
		return SecurityUtils.getSubject().getSession();
	}
	
	public static void logout() {
		getSubject().logout();
	}
	
	public static User getSysUser() {
		User user = null;
		Object obj = getSubject().getPrincipal();
		if(StringUtils.isNotNull(obj)) {
			user = new User();
			BeanUtils.copyBeanProps(user, obj);
		}
		return user;
	}
	
	public static void setSysUser(User user) {
		Subject subject = getSubject();
		PrincipalCollection princlipalCollection = subject.getPrincipals();
		String realName = princlipalCollection.getRealmNames().iterator().next();
		PrincipalCollection simplePrincipalCollection = new SimplePrincipalCollection(user, realName);
		subject.runAs(simplePrincipalCollection);
	}
	
	public static void clearCachedAuthorizationInfo() {
		RealmSecurityManager rsm = (RealmSecurityManager)SecurityUtils.getSecurityManager();
		UserRealm  uRealm = (UserRealm) rsm.getRealms().iterator().next();
		uRealm.clearCachedAuthorizationInfo();
	}
	
	public static Long getUserId()
    {
        return getSysUser().getUserId().longValue();
    }

    public static String getLoginName()
    {
        return getSysUser().getLoginName();
    }

    public static String getIp()
    {
        return getSubject().getSession().getHost();
    }

    public static String getSessionId()
    {
        return String.valueOf(getSubject().getSession().getId());
    }
}
