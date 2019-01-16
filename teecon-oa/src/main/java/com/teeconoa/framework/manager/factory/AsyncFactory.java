package com.teeconoa.framework.manager.factory;

import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.teeconoa.project.monitor.online.domain.OnlineSession;

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
				
				
			}
			
		};
	}
}
