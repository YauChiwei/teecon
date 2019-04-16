package com.teeconoa.project.system.role.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teeconoa.common.StringUtils;
import com.teeconoa.framework.aspectj.lang.annotation.DataScope;
import com.teeconoa.project.system.role.domain.Role;
import com.teeconoa.project.system.role.mapper.RoleDeptMapper;
import com.teeconoa.project.system.role.mapper.RoleMapper;
import com.teeconoa.project.system.role.mapper.RoleMenuMapper;
import com.teeconoa.project.system.user.mapper.UserRoleMapper;

/**
*  角色业务层处理
*  Created by AndyYau
*  Mar 25, 2019 - 2:37:16 PM
*  Company: Teecon
**/
@Service
public class RoleServiceImpl implements IRoleService {

	@Autowired
	private RoleMapper roleMapper;
	
	@Autowired
	private RoleMenuMapper roleMenuMapper;
	
    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RoleDeptMapper roleDeptMapper;
	
    /**
     * 根据条件分页查询角色数据
     * 
     * @param role 角色信息
     * @return 角色数据集合信息
     */
	@Override
	@DataScope(tableAlias = "u")
	public List<Role> selectRoleList(Role role) {
		return roleMapper.selectRoleList(role);
	}

    /**
     * 根据用户ID查询权限
     * 
     * @param userId 用户ID
     * @return 权限列表
     */
	@Override
	public Set<String> selectRoleKeys(Long userId) {
		List<Role> perms = roleMapper.selectRolesByUserId(userId);
		Set<String> set = new HashSet<>();
		for(Role role : perms) {
			if(StringUtils.isNotNull(perms)) {
				set.addAll(Arrays.asList(role.getRoleKey().trim().split(",")));
			}
		}
		return set;
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
