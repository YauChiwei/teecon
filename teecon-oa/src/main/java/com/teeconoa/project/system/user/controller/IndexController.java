package com.teeconoa.project.system.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import com.teeconoa.framework.config.TeeconOAConfig;
import com.teeconoa.framework.web.controller.BaseController;
import com.teeconoa.project.system.menu.domain.Menu;
import com.teeconoa.project.system.menu.service.IMenuService;
import com.teeconoa.project.system.user.domain.User;

/**
*  Created by AndyYau
*  Mar 28, 2019 - 1:02:36 AM
*  Company: Teecon
**/
@Controller
public class IndexController extends BaseController {

	@Autowired
	private IMenuService menuService;
	
	@Autowired
	private TeeconOAConfig teeconOAConfig;
	
	// 系统首页
    @GetMapping("/index")
	public String index(ModelMap map) {
		User user = getSysUser();
		List<Menu> mList = menuService.selectMenusByUser(user);
        map.put("menus", mList);
        map.put("user", user);
        map.put("copyrightYear", teeconOAConfig.getCopyrightYear());
		return "index";
	}
	
    // 系统介绍
    @GetMapping("/system/main")
    public String main(ModelMap mmap)
    {
        mmap.put("version", teeconOAConfig.getVersion());
        return "main";
    }
}
