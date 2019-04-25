package com.teeconoa.project.system.user.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authc.credential.PasswordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teeconoa.common.StringUtils;
import com.teeconoa.common.constant.UserConstants;
import com.teeconoa.common.exception.BusinessException;
import com.teeconoa.common.support.Convert;
import com.teeconoa.common.utils.security.ShiroUtils;
import com.teeconoa.framework.shiro.service.PwdService;
import com.teeconoa.project.system.config.service.IConfigService;
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

	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
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
    @Autowired
    private IConfigService configService;
	
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

	@Override
	public String importUser(List<User> userList, Boolean isUpdateSupport) {
        if (StringUtils.isNull(userList) || userList.size() == 0)
        {
            throw new BusinessException("导入用户数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String operName = ShiroUtils.getLoginName();
        String password = configService.selectConfigByKey("sys.user.initPassword");
        for (User user : userList)
        {
            try
            {
                // 验证是否存在这个用户
                User u = userMapper.selectUserByLoginName(user.getLoginName());
                if (StringUtils.isNull(u))
                {
                    user.setPassword(password);
                    user.setCreateBy(operName);
                    this.insertUser(user);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + user.getLoginName() + " 导入成功");
                }
                else if (isUpdateSupport)
                {
                    user.setUpdateBy(operName);
                    this.updateUser(user);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + user.getLoginName() + " 更新成功");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、账号 " + user.getLoginName() + " 已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、账号 " + user.getLoginName() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new BusinessException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
	}

	/**
     * 用户状态修改
     * 
     * @param user 用户信息
     * @return 结果
     */
	@Override
	public int changeStatus(User user) {
		if (User.isAdmin(user.getUserId()))
        {
            throw new BusinessException("不允许修改超级管理员用户");
        }
        return userMapper.updateUser(user);
	}
	
}
