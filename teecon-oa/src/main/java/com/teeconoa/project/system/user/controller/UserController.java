package com.teeconoa.project.system.user.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.teeconoa.common.StringUtils;
import com.teeconoa.common.utils.poi.ExcelUtil;
import com.teeconoa.framework.aspectj.lang.annotation.Log;
import com.teeconoa.framework.aspectj.lang.enums.BusinessType;
import com.teeconoa.framework.web.controller.BaseController;
import com.teeconoa.framework.web.domain.AjaxResult;
import com.teeconoa.framework.web.page.TableDataInfo;
import com.teeconoa.project.system.post.service.IPostService;
import com.teeconoa.project.system.role.service.IRoleService;
import com.teeconoa.project.system.user.domain.User;
import com.teeconoa.project.system.user.service.IUserService;

/**
*  Created by AndyYau
*  Apr 22, 2019 - 2:45:22 PM
*  Company: Teecon
**/
@Controller
@RequestMapping("/system/user")
public class UserController extends BaseController {

	private String prefix = "system/user";
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IRoleService roleService;
	
	@Autowired
	private IPostService postService;
	
    @RequiresPermissions("system:user:view")
    @GetMapping()
    public String user()
    {
        return prefix + "/user";
    }
    
    @RequiresPermissions("system:user:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(User user) {
    	startPage();
    	List<User> userList = userService.selectUserList(user);
    	return getDataTable(userList);
    }
    
    @Log(title = "用户管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("system:user:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(User user) {
    	List<User> userList = userService.selectUserList(user);
    	ExcelUtil<User> util = new ExcelUtil<User>(User.class);
    	return util.exportExcel(userList, "用户数据");
    }
    
    @Log(title = "用户管理", businessType = BusinessType.IMPORT)
    @RequiresPermissions("system:user:import")
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception{
    	ExcelUtil<User> util = new ExcelUtil<User>(User.class);
    	List<User> userList = util.importExcel(file.getInputStream());
    	String message = userService.importUser(userList, updateSupport);
    	return AjaxResult.success(message);
    }
    
    @RequiresPermissions("system:user:view")
    @GetMapping("/importTemplate")
    @ResponseBody
    public AjaxResult importTemplate() {
    	ExcelUtil<User> util = new ExcelUtil<User>(User.class);
    	return util.importTemplateExcel("用户数据");
    }
    
    /**
     * 新增用户
     * @param map
     * @return
     */
    @GetMapping("/add")
    public String add(ModelMap map) {
    	map.put("roles", roleService.selectRoleAll());
    	map.put("posts", postService.selectPostAll());
    	return prefix + "/add";
    }
    
    /**
     * 新增保存用户
     */
    @RequiresPermissions("system:user:add")
    @Log(title = "用户管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(User user)
    {
        if (StringUtils.isNotNull(user.getUserId()) && User.isAdmin(user.getUserId()))
        {
            return error("不允许修改超级管理员用户");
        }
        return toAjax(userService.insertUser(user));
    }

    /**
     * 修改用户
     */
    @GetMapping("/edit/{userId}")
    public String edit(@PathVariable("userId") Long userId, ModelMap mmap)
    {
        mmap.put("user", userService.selectUserById(userId));
        mmap.put("roles", roleService.selectRolesByUserId(userId));
        mmap.put("posts", postService.selectPostsByUserId(userId));
        return prefix + "/edit";
    }

    /**
     * 修改保存用户
     */
    @RequiresPermissions("system:user:edit")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(User user)
    {
        if (StringUtils.isNotNull(user.getUserId()) && User.isAdmin(user.getUserId()))
        {
            return error("不允许修改超级管理员用户");
        }
        return toAjax(userService.updateUser(user));
    }

    @RequiresPermissions("system:user:resetPwd")
    @Log(title = "重置密码", businessType = BusinessType.UPDATE)
    @GetMapping("/resetPwd/{userId}")
    public String resetPwd(@PathVariable("userId") Long userId, ModelMap mmap)
    {
        mmap.put("user", userService.selectUserById(userId));
        return prefix + "/resetPwd";
    }

    @RequiresPermissions("system:user:resetPwd")
    @Log(title = "重置密码", businessType = BusinessType.UPDATE)
    @PostMapping("/resetPwd")
    @ResponseBody
    public AjaxResult resetPwd(User user)
    {
        return toAjax(userService.resetUserPwd(user));
    }

    @RequiresPermissions("system:user:remove")
    @Log(title = "用户管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        try
        {
            return toAjax(userService.deleteUserByIds(ids));
        }
        catch (Exception e)
        {
            return error(e.getMessage());
        }
    }

    /**
     * 校验用户名
     */
    @PostMapping("/checkLoginNameUnique")
    @ResponseBody
    public String checkLoginNameUnique(User user)
    {
        return userService.checkLoginNameUnique(user.getLoginName());
    }

    /**
     * 校验手机号码
     */
    @PostMapping("/checkPhoneUnique")
    @ResponseBody
    public String checkPhoneUnique(User user)
    {
        return userService.checkPhoneUnique(user);
    }

    /**
     * 校验email邮箱
     */
    @PostMapping("/checkEmailUnique")
    @ResponseBody
    public String checkEmailUnique(User user)
    {
        return userService.checkEmailUnique(user);
    }

    /**
     * 用户状态修改
     */
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("system:user:edit")
    @PostMapping("/changeStatus")
    @ResponseBody
    public AjaxResult changeStatus(User user)
    {
        return toAjax(userService.changeStatus(user));
    }
}
