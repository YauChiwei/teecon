package com.teeconoa.project.system.role.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.teeconoa.project.system.role.domain.Role;

/**
*  角色业务层处理
*  Created by AndyYau
*  Mar 25, 2019 - 2:37:16 PM
*  Company: Teecon
**/
@Service
public class RoleServiceImpl implements IRoleService {

	@Override
	public List<Role> selectRoleList(Role role) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> selectRoleKeys(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Role> selectRolesByUserId(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Role> selectRoleAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Role selectRoleById(Long roleId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteRoleById(Long roleId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int deleteRoleByIds(String ids) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertRole(Role role) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateRole(Role role) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateRule(Role role) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String checkRoleNameUnique(Role role) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String checkRoleKeyUnique(Role role) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int countUserRoleByRoleId(Long roleId) {
		// TODO Auto-generated method stub
		return 0;
	}

}
