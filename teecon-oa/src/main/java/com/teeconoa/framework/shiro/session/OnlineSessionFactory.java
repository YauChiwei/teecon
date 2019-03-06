package com.teeconoa.framework.shiro.session;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionFactory;
import org.apache.shiro.web.session.mgt.WebSessionContext;

import com.teeconoa.common.ServletUtils;
import com.teeconoa.common.StringUtils;
import com.teeconoa.common.utils.IpUtils;
import com.teeconoa.project.monitor.online.domain.OnlineSession;
import com.teeconoa.project.monitor.online.domain.UserOnline;

import eu.bitwalker.useragentutils.UserAgent;

/**
*  Created by AndyYau
*  2019年3月1日 - 上午12:12:22
*  Company: Teecon
**/
public class OnlineSessionFactory implements SessionFactory {

	public Session createSession(UserOnline userOnline) {
		OnlineSession onlineSession = userOnline.getSession();
		if(StringUtils.isNotNull(onlineSession) && onlineSession.getId() == null) {
			onlineSession.setId(userOnline.getSessionId());
		}
		return userOnline.getSession();
	}
	
	@Override
	public Session createSession(SessionContext initData) {
		OnlineSession session = new OnlineSession();
		if(initData != null && initData instanceof WebSessionContext) {
			WebSessionContext sessionContext = (WebSessionContext)initData;
			HttpServletRequest request = (HttpServletRequest)sessionContext.getServletRequest();
			if(request != null) {
				UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
                // 获取客户端操作系统
                String os = userAgent.getOperatingSystem().getName();
                // 获取客户端浏览器
                String browser = userAgent.getBrowser().getName();
                session.setHost(IpUtils.getIpAddr(request));
                session.setBrowser(browser);
                session.setOs(os);
			}
		}
		return session;
	}

}
