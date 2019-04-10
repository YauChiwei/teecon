package com.teeconoa.project.system.user.controller;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.teeconoa.common.utils.file.FileUploadUtils;
import com.teeconoa.framework.aspectj.lang.annotation.Log;
import com.teeconoa.framework.aspectj.lang.enums.BusinessType;
import com.teeconoa.framework.config.TeeconOAConfig;
import com.teeconoa.framework.web.controller.BaseController;
import com.teeconoa.framework.web.domain.AjaxResult;
import com.teeconoa.framework.web.service.DictService;
import com.teeconoa.project.system.user.domain.User;
import com.teeconoa.project.system.user.service.IUserService;

/**
*  Created by AndyYau
*  Apr 1, 2019 - 3:38:17 PM
*  Company: Teecon
**/
@Controller
@RequestMapping("/system/user/profile")
public class ProfileController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);
	
	private final String prefix = "system/user/profile";
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private DictService dict;
	
	/**
	 * 个人信息
	 * @param map
	 * @return
	 */
	@GetMapping()
	public String profile(ModelMap map) {
		User user = getSysUser();
		user.setSex(dict.getLabel("sys_user_sex", user.getSex()));
		map.put("user", user);
		map.put("roleGroup", userService.selectUserRoleGroup(user.getUserId()));
		map.put("postGroup", userService.selectUserPostGroup(user.getUserId()));
		return prefix + "/profile";
	}
	
	@GetMapping("/checkPassword")
	@ResponseBody
	public boolean checkPassword(String password) {
		User user = getSysUser();
		String encrypt = new Md5Hash(user.getLoginName() + password + user.getSalt()).toHex().toString();
		if(user.getPassword().equals(encrypt)) {
			return true;
		}
		return false;
	}
	
	@GetMapping("/resetPwd/{userId}")
	public String resetPassword(@PathVariable("userId") Long userId, ModelMap map) {
		map.put("user", userService.selectUserById(userId));
		return prefix + "/resetPwd";
	}
	
	@Log(title = "重置密码", businessType = BusinessType.UPDATE)
	@PostMapping("/resetPwd")
	@ResponseBody
	public AjaxResult resetPwd(User user) {
		int row = userService.resetUserPwd(user);
		if(row > 0) {
			setSysUser(userService.selectUserById(user.getUserId()));
			return success();
		}
		return error();
	}
	
	/**
	 * 修改用户
	 * @param userId
	 * @param map
	 * @return
	 */
	@GetMapping("/edit/{userId}")
	public String edit(@PathVariable("userId") Long userId, ModelMap map) throws Exception{
		map.put("user", userService.selectUserById(userId));
		return prefix + "/edit";
	}
	
	/**
     * 修改头像
     */
    @GetMapping("/avatar/{userId}")
    public String avatar(@PathVariable("userId") Long userId, ModelMap mmap) throws Exception
    {
        mmap.put("user", userService.selectUserById(userId));
        return prefix + "/avatar";
    }
    
    /**
     * 修改用户
     */
    @Log(title = "个人信息", businessType = BusinessType.UPDATE)
    @PostMapping("/update")
    @ResponseBody
    public AjaxResult update(User user)
    {
        if (userService.updateUserInfo(user) > 0)
        {
            setSysUser(userService.selectUserById(user.getUserId()));
            return success();
        }
        return error();
    }
    
    @Log(title = "个人信息", businessType = BusinessType.UPDATE)
    @PostMapping("/updateAvatar")
    @ResponseBody
    public AjaxResult updateAvatar(User user,@RequestParam("avatarfile") MultipartFile file) {
    	try {
    		if(!file.isEmpty()) {
    			String avatar = FileUploadUtils.upload(TeeconOAConfig.getAvatarPath(), file);
    			user.setAvatar(avatar);
    			if(userService.updateUserInfo(user) > 0) {
    				setSysUser(userService.selectUserById(user.getUserId()));
    				return success();
    			}
    		}
    		return error();
    	}catch(Exception e) {
    		logger.error("修改头像失败", e);
    		return error(e.getMessage());
    	}
    }
}
