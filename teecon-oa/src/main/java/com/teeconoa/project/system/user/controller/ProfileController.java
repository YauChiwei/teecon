package com.teeconoa.project.system.user.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.teeconoa.framework.web.controller.BaseController;
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
		User user = this.getSysUser();
		user.setSex(dict.getLabel("sys_user_sex", user.getSex()));
		map.put("user", user);
		map.put("roleGroup", userService.selectUserRoleGroup(user.getUserId()));
		map.put("postGroup", userService.selectUserPostGroup(user.getUserId()));
		return prefix + "/profile";
	}
	
}
