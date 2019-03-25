package com.teeconoa.project.system.menu.service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teeconoa.common.StringUtils;
import com.teeconoa.common.TreeUtils;
import com.teeconoa.common.constant.UserConstants;
import com.teeconoa.common.utils.security.ShiroUtils;
import com.teeconoa.project.system.menu.domain.Menu;
import com.teeconoa.project.system.menu.mapper.MenuMapper;
import com.teeconoa.project.system.role.domain.Role;
import com.teeconoa.project.system.role.mapper.RoleMenuMapper;
import com.teeconoa.project.system.user.domain.User;

/**
*  菜单业务层处理 
* 
*  Created by AndyYau
*  Mar 23, 2019 - 5:13:01 PM
*  Company: Teecon
**/
@Service
public class MenuServiceImpl implements IMenuService {
	
	public static final String PREMISSION_STRING = "perms[\"{0}\"]";
	
	@Autowired
	private MenuMapper menuMapper;
	
	@Autowired
	private RoleMenuMapper roleMenuMapper;

	/**
     * 根据用户查询菜单
     * 
     * @param user 用户信息
     * @return 菜单列表
     */
	@Override
	public List<Menu> selectMenusByUser(User user) {
		List<Menu> menuList = new ArrayList<Menu>();
		if(user.isAdmin()) {
			menuList = menuMapper.selectMenuNormalAll();
		}else {
			menuList = menuMapper.selectMenusByUserId(user.getUserId());
		}
		return TreeUtils.getChildNodesList(menuList, 0);
	}

	@Override
	public List<Menu> selectMenuList(Menu menu) {
		return menuMapper.selectMenuList(menu);
	}

	@Override
	public List<Menu> selectMenuAll() {
		return menuMapper.selectMenuAll();
	}

	@Override
	public Set<String> selectPermsByUserId(Long userId) {
		List<String> perms = menuMapper.selectPermsByUserId(userId);
		Set<String> permSet = new HashSet<>();
		for(String perm : perms) {
			if(StringUtils.isNotEmpty(perm)) {
				permSet.addAll(Arrays.asList(perm.trim().split(",")));
			}
		}
		return permSet;
	}

	/**
	 * 根据角色ID查看菜单
	 */
	@Override
	public List<Map<String, Object>> roleMenuTreeData(Role role) {
		Long roleId = role.getRoleId();
		List<Map<String, Object>> trees = new ArrayList<Map<String, Object>>();
		List<Menu> menuList = menuMapper.selectMenuAll();
		if(StringUtils.isNotNull(roleId)) {
			List<String> roleMenuList = menuMapper.selectMenuTree(roleId);
			trees = getTrees(menuList, true, roleMenuList, true);
		}else {
			trees = getTrees(menuList, false, null, true);
		}
		return trees;
	}

	@Override
	public List<Map<String, Object>> menuTreeData() {
		List<Map<String, Object>> trees = new ArrayList<Map<String, Object>>();
		List<Menu> menuList = menuMapper.selectMenuAll();
		trees = getTrees(menuList, false, null, false);
		return trees;
	}

	@Override
	public Map<String, String> selectPermsAll() {
		LinkedHashMap<String, String> linkedMap = new LinkedHashMap<>();
		List<Menu> permissionList = menuMapper.selectMenuAll();
		if(StringUtils.isNotEmpty(permissionList)) {
			for(Menu menu : permissionList) {
				linkedMap.put(menu.getUrl(), MessageFormat.format(PREMISSION_STRING, menu.getPerms()));
			}
		}
		return linkedMap;
	}

	@Override
	public int deleteMenuById(Long menuId) {
		ShiroUtils.clearCachedAuthorizationInfo();
		return menuMapper.deleteMenuById(menuId);
	}

	@Override
	public Menu selectMenuById(Long menuId) {
		return menuMapper.selectMenuById(menuId);
	}

	@Override
	public int selectCountMenuByParentId(Long parentId) {
		return menuMapper.selectCountMenuByParentId(parentId);
	}

	@Override
	public int selectCountRoleMenuByMenuId(Long menuId) {
		return roleMenuMapper.selectCountRoleMenuByMenuId(menuId);
	}

	@Override
	public int insertMenu(Menu menu) {
		menu.setCreateBy(ShiroUtils.getLoginName());
		ShiroUtils.clearCachedAuthorizationInfo();
		return menuMapper.insertMenu(menu);
	}

	@Override
	public int updateMenu(Menu menu) {
		menu.setUpdateBy(ShiroUtils.getLoginName());
		ShiroUtils.clearCachedAuthorizationInfo();
		return menuMapper.updateMenu(menu);
	}

	/**
	 * 检验菜单名称是否唯一
	 */
	@Override
	public String checkMenuNameUnique(Menu menu) {
		Long menuId = StringUtils.isNull(menu.getMenuId()) ? -1L : menu.getMenuId();
		Menu menuInfo = menuMapper.checkMenuNameUnique(menu.getMenuName(),  menu.getParentId());
		if(StringUtils.isNotNull(menuInfo) && menuInfo.getMenuId().longValue() != menuId.longValue()) {
			return UserConstants.MENU_NAME_NOT_UNIQUE;
		}
		return UserConstants.MENU_NAME_UNIQUE;
	}

	public List<Map<String, Object>> getTrees(List<Menu> menuList, boolean isCheck, List<String> roleMenuList, boolean permFlag){
		List<Map<String, Object>> trees = new ArrayList<Map<String, Object>>();
		for(Menu menu : menuList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", menu.getMenuId());
			map.put("pId", menu.getParentId());
			map.put("name", transMenuName(menu, roleMenuList, permFlag));
			map.put("title", menu.getMenuName());
			if(isCheck) {
				map.put("checked", roleMenuList.contains(menu.getMenuId() + menu.getPerms()));
			}else {
				map.put("checked", false);
			}
			trees.add(map);
		}
		return trees;
	}
	
    public String transMenuName(Menu menu, List<String> roleMenuList, boolean permsFlag)
    {
        StringBuffer sb = new StringBuffer();
        sb.append(menu.getMenuName());
        if (permsFlag)
        {
            sb.append("<font color=\"#888\">&nbsp;&nbsp;&nbsp;" + menu.getPerms() + "</font>");
        }
        return sb.toString();
    }
}
