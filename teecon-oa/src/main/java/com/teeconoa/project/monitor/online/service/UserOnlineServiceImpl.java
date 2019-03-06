package com.teeconoa.project.monitor.online.service;

import java.util.Date;
import java.util.List;

import com.teeconoa.project.monitor.online.domain.UserOnline;

/**
*  Created by AndyYau
*  2019年3月5日 - 上午11:17:00
*  Company: Teecon
**/
public class UserOnlineServiceImpl implements IUserOnlineService {

	@Override
	public UserOnline selectOnlineById(String sessionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteOnlineById(String sessionId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void batchDeleteOnline(List<String> sessions) {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveOnline(UserOnline online) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<UserOnline> selectUserOnlineList(UserOnline userOnline) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void forceLogout(String sessionId) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<UserOnline> selectOnlineByExpired(Date expiredDate) {
		// TODO Auto-generated method stub
		return null;
	}

}
