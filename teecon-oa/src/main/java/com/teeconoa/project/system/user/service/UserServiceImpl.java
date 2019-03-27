package com.teeconoa.project.system.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teeconoa.project.system.user.domain.User;
import com.teeconoa.project.system.user.mapper.UserMapper;
import com.teeconoa.project.system.user.mapper.UserPostMapper;
import com.teeconoa.project.system.user.mapper.UserRoleMapper;

/**
*  Created by AndyYau
*  2019年3月15日 - 下午5:05:53
*  Company: Teecon
**/
@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UserPostMapper userPostMapper;
	@Autowired
	private UserRoleMapper userRoleMapper;
	
	@Override
	public List<User> selectUserList(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User selectUserByLoginName(String userName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User selectUserByPhoneNumber(String phoneNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User selectUserByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User selectUserById(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteUserById(Long userId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteUserByIds(String ids) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertUser(User user) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateUser(User user) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateUserInfo(User user) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int resetUserPwd(User user) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String checkLoginNameUnique(String loginName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String checkPhoneUnique(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String checkEmailUnique(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String selectUserRoleGroup(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String selectUserPostGroup(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

}
