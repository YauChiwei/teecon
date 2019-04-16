package com.teeconoa.project.system.role.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.teeconoa.common.utils.poi.ExcelUtil;
import com.teeconoa.framework.aspectj.lang.annotation.Log;
import com.teeconoa.framework.aspectj.lang.enums.BusinessType;
import com.teeconoa.framework.web.controller.BaseController;
import com.teeconoa.framework.web.domain.AjaxResult;
import com.teeconoa.framework.web.page.TableDataInfo;
import com.teeconoa.project.system.role.domain.Role;
import com.teeconoa.project.system.role.service.IRoleService;

/**
*  Created by AndyYau
*  Apr 11, 2019 - 3:21:42 PM
*  Company: Teecon
**/
@Controller
@RequestMapping("/system/role")
public class RoleController extends BaseController {

	private static final String prefix = "system/role";
	
	@Autowired
	private IRoleService roleService;
	
	@RequiresPermissions("system:role:view")
	@GetMapping()
	public String role() {
		return prefix + "/role";
	}
	
	@RequiresPermissions("system:role:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(Role role) {
		startPage();
		List<Role> list = roleService.selectRoleList(role);
		return getDataTable(list);
	}
	
	/**
     * 新增保存角色
     */
    @RequiresPermissions("system:role:add")
    @Log(title = "角色管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
	public AjaxResult addSave(Role role) {
		return toAjax(roleService.insertRole(role));
	}
    
    @RequiresPermissions("system:role:export")
    @Log(title = "角色管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Role role) {
    	List<Role> list = roleService.selectRoleList(role);
    	ExcelUtil<Role> excel = new ExcelUtil<Role>(Role.class);
    	return excel.exportExcel(list, "Role");
    }
    
    /**
     * 新增角色
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }
    
    /**
     * 修改角色
     * @param roleId
     * @param map
     * @return
     */
    @GetMapping("/edit/{roleId}")
    public String edit(@PathVariable("roleId") Long roleId, ModelMap map) {
    	map.put("role", roleService.selectRoleById(roleId));
    	return prefix + "/edit";
    }
    
    /**
     * 修改保存角色
     */
    @RequiresPermissions("system:role:edit")
    @Log(title = "角色管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult editSave(Role role) {
    	return toAjax(roleService.updateRole(role));
    }
    
    /**
     * 新增数据权限
     */
    @GetMapping("/rule/{roleId}")
    public String rule(@PathVariable("roleId") Long roleId, ModelMap mmap)
    {
        mmap.put("role", roleService.selectRoleById(roleId));
        return prefix + "/rule";
    }
    
    /**
     * 修改保存数据权限
     */
    @RequiresPermissions("system:role:edit")
    @Log(title = "角色管理", businessType = BusinessType.UPDATE)
    @PostMapping("/rule")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult ruleSave(Role role)
    {
        return toAjax(roleService.updateRule(role));
    }

    @RequiresPermissions("system:role:remove")
    @Log(title = "角色管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        try
        {
            return toAjax(roleService.deleteRoleByIds(ids));
        }
        catch (Exception e)
        {
            return error(e.getMessage());
        }
    }

    /**
     * 校验角色名称
     */
    @PostMapping("/checkRoleNameUnique")
    @ResponseBody
    public String checkRoleNameUnique(Role role)
    {
        return roleService.checkRoleNameUnique(role);
    }

    /**
     * 校验角色权限
     */
    @PostMapping("/checkRoleKeyUnique")
    @ResponseBody
    public String checkRoleKeyUnique(Role role)
    {
        return roleService.checkRoleKeyUnique(role);
    }

    /**
     * 选择菜单树
     */
    @GetMapping("/selectMenuTree")
    public String selectMenuTree()
    {
        return prefix + "/tree";
    }
}
