package com.teeconoa.framework.manager.factory;

import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.teeconoa.common.utils.AddressUtils;
import com.teeconoa.common.utils.spring.SpringUtils;
import com.teeconoa.project.monitor.online.domain.OnlineSession;
import com.teeconoa.project.monitor.online.domain.UserOnline;
import com.teeconoa.project.monitor.online.service.IUserOnlineService;
import com.teeconoa.project.monitor.operlog.domain.OperLog;
import com.teeconoa.project.monitor.operlog.service.IOperLogService;

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
	
	/**
     * 操作日志记录
     * 
     * @param operLog 操作日志信息
     * @return 任务task
     */
	public static TimerTask logOperation(final OperLog operLog) {
		return new TimerTask() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				operLog.setOperLocation(AddressUtils.getRealAddressByIP(operLog.getOperIp()));
				SpringUtils.getBean(IOperLogService.class).insertOperlog(operLog);
			}
		};
	}
	
    /**
     * 记录登陆信息
     * 
     * @param username 用户名
     * @param status 状态
     * @param message 消息
     * @param args 列表
     * @return 任务task
     */
	public static TimerTask recordLogininfor(final String username, final String status, final String message, final Object... args)
    {
		return new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				
			}
			
		};
    }
}
