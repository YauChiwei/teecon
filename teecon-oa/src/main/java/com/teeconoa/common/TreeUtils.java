package com.teeconoa.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.teeconoa.project.system.menu.domain.Menu;

/**
*  Created by AndyYau
*  Mar 23, 2019 - 6:05:59 PM
*  Company: Teecon
**/
public class TreeUtils {

	List<Menu> returnList = new ArrayList<Menu>();
	/**
	 * 根据父节点ID获取所有子节点
	 * @param list
	 * @param parentId
	 * @return
	 */
	public static List<Menu> getChildNodesList(List<Menu> list, int parentId){
		List<Menu> resultList = new ArrayList<Menu>();
		for(Iterator<Menu> iterator = list.iterator(); iterator.hasNext();) {
			Menu m = (Menu)iterator.next();
			if(m.getParentId() == parentId) {
				recursionFunction(list, m);
				resultList.add(m);
			}
		}
		
		return resultList;
	}
	
	public List<Menu> getChildNodesList(List<Menu> list, int typeId, String prefix){
		if(list == null) {
			return null;
		}
		
		for(Iterator<Menu> iterator = list.iterator(); iterator.hasNext();) {
			Menu m = (Menu)iterator.next();
			if(m.getParentId() == typeId) {
				recursionFunction(list, m, prefix);
			}
		}
		
		return returnList;
	}
	
	/**
	 * 获取子节点列表
	 * @param list
	 * @param m
	 * @return
	 */
	private static List<Menu> getChildList(List<Menu> list, Menu m){
		List<Menu> tList = new ArrayList<Menu>();
		Iterator<Menu> it = list.iterator();
		while(it.hasNext()){
			Menu menu = (Menu)it.next();
			if(menu.getParentId().longValue() == m.getMenuId().longValue()) {
				tList.add(menu);
			}
		}
		return tList;
	}
	
	private static void recursionFunction(List<Menu> list, Menu m) {
		List<Menu> childList = getChildList(list, m);
		m.setChildren(childList);
		for(Menu child : childList) {
			if(hasChild(list, child)) {
				Iterator<Menu> it = childList.iterator();
				while(it.hasNext()) {
					Menu menu = (Menu)it.next();
					recursionFunction(list, menu);
				}
			}
		}
	}
	
	private void recursionFunction(List<Menu> list, Menu m, String prefix) {
		List<Menu> childList = getChildList(list, m);
		if (hasChild(list, m)) {
			returnList.add(m);
			Iterator<Menu> it = childList.iterator();
			while (it.hasNext()) {
				Menu menu = (Menu) it.next();
				menu.setMenuName(prefix + menu.getMenuName());
				recursionFunction(list, menu, prefix + prefix);
			}
		} else {
			returnList.add(m);
		}
	}
	
	/**
	 * 判断是否存在子节点
	 * @param list
	 * @param m
	 * @return
	 */
	private static boolean hasChild(List<Menu> list, Menu m) {
		return getChildList(list, m).size() > 0 ? true : false;
	}
}
