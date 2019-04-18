package com.teeconoa.project.system.dept.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teeconoa.common.StringUtils;
import com.teeconoa.common.constant.UserConstants;
import com.teeconoa.common.exception.BusinessException;
import com.teeconoa.common.utils.security.ShiroUtils;
import com.teeconoa.framework.aspectj.lang.annotation.DataScope;
import com.teeconoa.project.system.dept.domain.Dept;
import com.teeconoa.project.system.dept.mapper.DeptMapper;
import com.teeconoa.project.system.role.domain.Role;

/**
*  Created by AndyYau
*  Apr 18, 2019 - 3:41:45 PM
*  Company: Teecon
**/
@Service
public class DeptServiceImpl implements IDeptService {
	
	@Autowired
	private DeptMapper deptMapper;

	@Override
	@DataScope(tableAlias = "d")
	public List<Dept> selectDeptList(Dept dept) {
		return deptMapper.selectDeptList(dept);
	}

	@Override
	public List<Map<String, Object>> selectDeptTree(Dept dept) {
		List<Map<String, Object>> trees = new ArrayList<Map<String, Object>>();
		List<Dept> deptList = deptMapper.selectDeptList(dept);
		trees = getTrees(deptList, false, null);
		return trees;
	}

	/**
	 * 根据角色ID查找部门
	 */
	@Override
	public List<Map<String, Object>> roleDeptTreeData(Role role) {
		Long roleId = role.getRoleId();
		List<Map<String, Object>> trees = new ArrayList<Map<String, Object>>();
		List<Dept> deptList = selectDeptList(new Dept());
		if(StringUtils.isNotNull(roleId)) {
			List<String> roleDeptList = deptMapper.selectRoleDeptTree(roleId);
			trees = getTrees(deptList, true, roleDeptList);
		}else {
			trees = getTrees(deptList, false, null);
		}
		return trees;
	}

	@Override
	public int selectDeptCount(Long parentId) {
		Dept dept = new Dept();
		dept.setParentId(parentId);
		return deptMapper.selectDeptCount(dept);
	}

	@Override
	public boolean checkDeptExistUser(Long deptId) {
		int result = deptMapper.checkDeptExistUser(deptId);
		return result > 0 ? true : false;
	}

	@Override
	public int deleteDeptById(Long deptId) {
		return deptMapper.deleteDeptById(deptId);
	}

	@Override
	public int insertDept(Dept dept) {
		Dept info = deptMapper.selectDeptById(dept.getParentId());
		// 如果父节点不为"正常"状态,则不允许新增子节点
		if(!UserConstants.DEPT_NORMAL.equals(info.getStatus())) {
			throw new BusinessException("部门状态异常，不允许新增");
		}
		dept.setCreateBy(ShiroUtils.getLoginName());
		dept.setAncestors(info.getAncestors() + "," + dept.getParentId());
		return deptMapper.insertDept(dept);
	}

    /**
     * 修改保存部门信息
     * 
     * @param dept 部门信息
     * @return 结果
     */
	@Override
	public int updateDept(Dept dept) {
		Dept info = deptMapper.selectDeptById(dept.getParentId());
		if(StringUtils.isNotNull(info)) {
			String ancestors = info.getAncestors() + "," + info.getDeptId();
			dept.setAncestors(ancestors);
			updateDeptChildren(dept.getDeptId(), ancestors);
		}
		dept.setUpdateBy(ShiroUtils.getLoginName());
		int result = deptMapper.updateDept(dept);
		if(UserConstants.DEPT_NORMAL.equals(dept.getStatus())) {
			// 如果该部门是启用状态，则启用该部门的所有上级部门
			updateParentDeptStatus(dept);
		}
		return result;
	}
	
    /**
     * 修改子元素关系
     * 
     * @param deptId 部门ID
     * @param ancestors 元素列表
     */
	public void updateDeptChildren(Long deptId, String ancestors) {
		Dept dept = new Dept();
		dept.setParentId(deptId);
		List<Dept> childrenList = deptMapper.selectDeptList(dept);
		for(Dept children : childrenList) {
			children.setAncestors(ancestors + "," + dept.getParentId());
		}
		if(childrenList.size() > 0) {
			deptMapper.updateDeptChildren(childrenList);
		}
	}
	
    /**
     * 修改该部门的父级部门状态
     * 
     * @param dept 当前部门
     */
	private void updateParentDeptStatus(Dept dept) {
		String updateBy = dept.getUpdateBy();
		dept = deptMapper.selectDeptById(dept.getDeptId());
		dept.setUpdateBy(updateBy);
		deptMapper.updateDeptStatus(dept);
	}

	@Override
	public Dept selectDeptById(Long deptId) {
		return deptMapper.selectDeptById(deptId);
	}

	@Override
	public String checkDeptNameUnique(Dept dept) {
		Long deptId = StringUtils.isNull(dept.getDeptId()) ? -1L : dept.getDeptId();
        Dept info = deptMapper.checkDeptNameUnique(dept.getDeptName(), dept.getParentId());
        if (StringUtils.isNotNull(info) && info.getDeptId().longValue() != deptId.longValue())
        {
            return UserConstants.DEPT_NAME_NOT_UNIQUE;
        }
        return UserConstants.DEPT_NAME_UNIQUE;
	}

	public List<Map<String, Object>> getTrees(List<Dept> deptList, boolean isCheck, List<String> roleDeptList){
		List<Map<String, Object>> trees = new ArrayList<Map<String, Object>>();
		for(Dept dept : deptList) {
			if(UserConstants.DEPT_NORMAL.equals(dept.getStatus())) {
				Map<String, Object> deptMap = new HashMap<String,Object>();
				deptMap.put("id", dept.getDeptId());
				deptMap.put("pId", dept.getParentId());
				deptMap.put("name", dept.getDeptName());
                deptMap.put("title", dept.getDeptName());
                if(isCheck) {
                	deptMap.put("checked", roleDeptList.contains(dept.getDeptId() + dept.getDeptName()));
                }else {
                	deptMap.put("checked", false);
                }
                trees.add(deptMap);
			}
		}
		return trees;
	}
}
