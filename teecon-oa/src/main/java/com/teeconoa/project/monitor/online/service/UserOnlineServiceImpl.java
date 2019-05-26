package com.teeconoa.project.monitor.online.service;

import java.util.Date;
import java.util.List;

import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teeconoa.common.DateUtils;
import com.teeconoa.framework.shiro.session.OnlineSessionDAO;
import com.teeconoa.project.monitor.online.domain.UserOnline;
import com.teeconoa.project.monitor.online.mapper.UserOnlineMapper;

/**
*  Created by AndyYau
*  2019年3月5日 - 上午11:17:00
*  Company: Teecon
**/
@Service
public class UserOnlineServiceImpl implements IUserOnlineService {

	@Autowired
    private UserOnlineMapper userOnlineMapper;

    @Autowired
    private OnlineSessionDAO onlineSessionDAO;
    
	@Override
	public UserOnline selectOnlineById(String sessionId) {
		return userOnlineMapper.selectOnlineById(sessionId);
	}

	@Override
	public void deleteOnlineById(String sessionId) {
		UserOnline userOnline = selectOnlineById(sessionId);
		if(userOnline != null) {
			userOnlineMapper.deleteOnlineById(sessionId);
		}
	}

	@Override
	public void batchDeleteOnline(List<String> sessions) {
		for(String sessionId : sessions) {
			UserOnline userOnline = selectOnlineById(sessionId);
			if(userOnline != null) {
				userOnlineMapper.deleteOnlineById(sessionId);
			}
		}
	}

	@Override
	public void saveOnline(UserOnline online) {
		userOnlineMapper.saveOnline(online);
	}

	@Override
	public List<UserOnline> selectUserOnlineList(UserOnline userOnline) {
		return userOnlineMapper.selectUserOnlineList(userOnline);
	}

	@Override
	public void forceLogout(String sessionId) {
		Session session = onlineSessionDAO.readSession(sessionId);
		if(session == null) {
			return;
		}
		session.setTimeout(1000);
		userOnlineMapper.deleteOnlineById(sessionId);
	}

	@Override
	public List<UserOnline> selectOnlineByExpired(Date expiredDate) {
		String lastAccessTime = DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, expiredDate);
		return userOnlineMapper.selectOnlineByExpired(lastAccessTime);
	}

}
