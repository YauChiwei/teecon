package com.teeconoa.project.system.user.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teeconoa.common.StringUtils;
import com.teeconoa.common.constant.UserConstants;
import com.teeconoa.common.exception.BusinessException;
import com.teeconoa.common.support.Convert;
import com.teeconoa.common.utils.security.ShiroUtils;
import com.teeconoa.framework.shiro.service.PwdService;
import com.teeconoa.project.system.post.domain.Post;
import com.teeconoa.project.system.post.mapper.PostMapper;
import com.teeconoa.project.system.role.domain.Role;
import com.teeconoa.project.system.role.mapper.RoleMapper;
import com.teeconoa.project.system.user.domain.User;
import com.teeconoa.project.system.user.domain.UserPost;
import com.teeconoa.project.system.user.domain.UserRole;
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
	@Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PostMapper postMapper;
    @Autowired
    private PwdService pwdService;
	
	@Override
	public List<User> selectUserList(User user) {
		return userMapper.selectUserList(user);
	}

	@Override
	public User selectUserByLoginName(String userName) {
		return userMapper.selectUserByLoginName(userName);
	}

	@Override
	public User selectUserByPhoneNumber(String phoneNumber) {
		return userMapper.selectUserByPhoneNumber(phoneNumber);
	}

	@Override
	public User selectUserByEmail(String email) {
		return userMapper.selectUserByEmail(email);
	}

	@Override
	public User selectUserById(Long userId) {
		return userMapper.selectUserById(userId);
	}

	@Override
	public int deleteUserById(Long userId) {
		userRoleMapper.deleteUserRoleByUserId(userId);
		userPostMapper.deleteUserPostByUserId(userId);
		return userMapper.deleteUserById(userId);
	}

	@Override
	public int deleteUserByIds(String ids) throws Exception {
		Long[] userIds = Convert.toLongArray(ids);
		for(Long userId:userIds) {
			if(User.isAdmin(userId)) {
				throw new BusinessException("不允许删除超级管理员用户");
			}
		}
		return userMapper.deleteUserByIds(userIds);
	}

	@Override
	public int insertUser(User user) {
		user.randomSalt();
		user.setCreateBy(ShiroUtils.getLoginName());
		user.setPassword(pwdService.encryptPassword(user.getLoginName(), user.getPassword(), user.getSalt()));
		//新增用户信息
		int rows = userMapper.insertUser(user);
		//新增用户角色信息
		insertUserRole(user);
		//新增用户岗位信息
		insertUserPost(user);
		return rows;
	}

	@Override
	public int updateUser(User user) {
		Long userId = user.getUserId();
		user.setUpdateBy(ShiroUtils.getLoginName());
		//删除用户与角色关联
		userRoleMapper.deleteUserRoleByUserId(userId);
		insertUserRole(user);
		//删除用户与岗位关联
		userPostMapper.deleteUserPostByUserId(userId);
		insertUserPost(user);
		return userMapper.updateUser(user);
	}

	@Override
	public int updateUserInfo(User user) {
		return userMapper.updateUser(user);
	}

	@Override
	public int resetUserPwd(User user) {
		user.randomSalt();
		user.setPassword(pwdService.encryptPassword(user.getLoginName(), user.getPassword(), user.getSalt()));
		return updateUserInfo(user);
	}

	@Override
	public String checkLoginNameUnique(String loginName) {
		int count = userMapper.checkLoginNameUnique(loginName);
		if(count > 0) {
			return UserConstants.USER_NAME_NOT_UNIQUE;
		}
		return UserConstants.USER_NAME_UNIQUE;
	}

	@Override
	public String checkPhoneUnique(User user) {
		Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
		User userInfo = userMapper.checkPhoneUnique(user.getPhonenumber());
		if(StringUtils.isNotNull(userInfo) && userInfo.getUserId().longValue() != userId.longValue()) {
			return UserConstants.USER_PHONE_NOT_UNIQUE;
		}
		return UserConstants.USER_PHONE_UNIQUE;
	}

	@Override
	public String checkEmailUnique(User user) {
		Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
		User userInfo = userMapper.checkEmailUnique(user.getEmail());
		if(StringUtils.isNotNull(userInfo) && userInfo.getUserId().longValue() != userId.longValue()) {
			return UserConstants.USER_EMAIL_NOT_UNIQUE;
		}
		return UserConstants.USER_EMAIL_UNIQUE;
	}

	/**
	 * 查询用户所属角色组
	 */
	@Override
	public String selectUserRoleGroup(Long userId) {
		List<Role> roleList = roleMapper.selectRolesByUserId(userId);
		StringBuffer idsStr = new StringBuffer();
        for (Role role : roleList)
        {
            idsStr.append(role.getRoleName()).append(",");
        }
        if (StringUtils.isNotEmpty(idsStr.toString()))
        {
            return idsStr.substring(0, idsStr.length() - 1);
        }
        return idsStr.toString();
	}

	/**
	 * 查询用户所属岗位组
	 */
	@Override
	public String selectUserPostGroup(Long userId) {
		List<Post> postList = postMapper.selectPostsByUserId(userId);
		StringBuffer idsStr = new StringBuffer();
        for (Post post : postList)
        {
            idsStr.append(post.getPostName()).append(",");
        }
        if (StringUtils.isNotEmpty(idsStr.toString()))
        {
            return idsStr.substring(0, idsStr.length() - 1);
        }
        return idsStr.toString();
	}
	
	/**
	 * 新增用户角色信息
	 * @param user
	 */
	public void insertUserRole(User user) {
		List<UserRole> roleList = new ArrayList<UserRole>();
		for(Long roleId : user.getRoleIds()) {
			UserRole userRole = new UserRole();
			userRole.setRoleId(roleId);
			userRole.setUserId(user.getUserId());
			roleList.add(userRole);
		}
		if(roleList.size() > 0) {
			userRoleMapper.batchUserRole(roleList);
		}
	}

	/**
	 * 新增用户岗位信息
	 * @param user
	 */
	public void insertUserPost(User user) {
		List<UserPost> postList = new ArrayList<UserPost>();
		for(Long postId : user.getPostIds()) {
			UserPost userpost = new UserPost();
			userpost.setPostId(postId);
			userpost.setUserId(user.getUserId());
			postList.add(userpost);
		}
		if(postList.size() > 0) {
			userPostMapper.batchUserPost(postList);
		}
	}
}
