package com.teeconoa.framework.shiro.realm;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.teeconoa.common.exception.user.CaptchaException;
import com.teeconoa.common.exception.user.RoleBlockedException;
import com.teeconoa.common.exception.user.UserBlockedException;
import com.teeconoa.common.exception.user.UserNotExistsException;
import com.teeconoa.common.exception.user.UserPasswordNotMatchException;
import com.teeconoa.common.exception.user.UserPasswordRetryLimitExceedException;
import com.teeconoa.common.utils.security.ShiroUtils;
import com.teeconoa.framework.shiro.service.LoginService;
import com.teeconoa.project.system.menu.service.IMenuService;
import com.teeconoa.project.system.role.service.IRoleService;
import com.teeconoa.project.system.user.domain.User;

/**
 * 自定义Realm 处理登录 权限
*  Created by AndyYau
*  2019年2月20日 - 下午3:15:00
*  Company: Teecon
**/
public class UserRealm extends AuthorizingRealm {
	
	private static final Logger loggger = LoggerFactory.getLogger(UserRealm.class);
	
	@Autowired
	private IMenuService menuService;
	
	@Autowired
	private IRoleService roleService;
	
	@Autowired
	private LoginService loginService;

	/**
	 * 授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		User user = ShiroUtils.getSysUser();
		//角色列表
		Set<String> roleList = new HashSet<String>();
		//功能列表
		Set<String> menuList = new HashSet<String>();
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		//管理员拥有所有权限
		if(user.isAdmin()) {
			info.addRole("admin");
			info.addStringPermission("*:*:*");
		}else {
			roleList = roleService.selectRoleKeys(user.getUserId());
			menuList = menuService.selectPermsByUserId(user.getUserId());
			info.setRoles(roleList);
			info.setStringPermissions(menuList);
		}
		return info;
	}

	/**
	 * 登录认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken upToken = (UsernamePasswordToken)token;
		String username = upToken.getUsername();
		String password = "";
		if(upToken.getPassword() != null) {
			password = new String(upToken.getPassword());
		}
		User user = null;
		try {
			user = loginService.login(username, password);
		}catch (CaptchaException e)
        {
            throw new AuthenticationException(e.getMessage(), e);
        }
        catch (UserNotExistsException e)
        {
            throw new UnknownAccountException(e.getMessage(), e);
        }
        catch (UserPasswordNotMatchException e)
        {
            throw new IncorrectCredentialsException(e.getMessage(), e);
        }
        catch (UserPasswordRetryLimitExceedException e)
        {
            throw new ExcessiveAttemptsException(e.getMessage(), e);
        }
        catch (UserBlockedException e)
        {
            throw new LockedAccountException(e.getMessage(), e);
        }
        catch (RoleBlockedException e)
        {
            throw new LockedAccountException(e.getMessage(), e);
        }
        catch (Exception e)
        {
        	loggger.info("对用户[" + username + "]进行登录验证..验证未通过{}", e.getMessage());
            throw new AuthenticationException(e.getMessage(), e);
        }
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password, getName());
		return info;
	}

	/**
     * 清理缓存权限
     */
    public void clearCachedAuthorizationInfo()
    {
        this.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
    }
}
