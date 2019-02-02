package com.teeconoa.framework.manager.factory;

import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ruoyi.common.utils.AddressUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.project.monitor.online.service.IUserOnlineService;
import com.teeconoa.project.monitor.online.domain.OnlineSession;
import com.teeconoa.project.monitor.online.domain.UserOnline;

/**
*  Created by AndyYau
*  2019年1月6日 - 下午10:49:15
*  Company: Teecon
**/
public class AsyncFactory {

	private static final Logger sys_user_logger = LoggerFactory.getLogger(AsyncFactory.class);
	
	/**
     * 同步session到数据库
     * 
     * @param session 在线用户会话
     * @return 任务task
     */
	public static TimerTask syncSessionToDatabase(final OnlineSession session) {
		return new TimerTask() {

			@Override
			public void run() {
				UserOnline online = new UserOnline();
				online.setSessionId(String.valueOf(session.getId()));
				online.setDeptName(session.getDeptName());
                online.setLoginName(session.getLoginName());
                online.setStartTimestamp(session.getStartTimestamp());
                online.setLastAccessTime(session.getLastAccessTime());
                online.setExpireTime(session.getTimeout());
                online.setIpaddr(session.getHost());
                online.setLoginLocation(AddressUtils.getRealAddressByIP(session.getHost()));
                online.setBrowser(session.getBrowser());
                online.setOs(session.getOs());
                online.setStatus(session.getStatus());
                online.setSession(session);
                SpringUtils.getBean(IUserOnlineService.class).saveOnline(online);
			}
			
		};
	}
}
