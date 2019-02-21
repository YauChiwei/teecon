package com.teeconoa.framework.web.controller;

import java.beans.PropertyEditorSupport;
import java.sql.Date;
import java.util.List;

import org.springframework.web.bind.WebDataBinder;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.teeconoa.common.DateUtils;
import com.teeconoa.common.StringUtils;
import com.teeconoa.common.utils.security.ShiroUtils;
import com.teeconoa.framework.web.domain.AjaxResult;
import com.teeconoa.framework.web.page.PageDomain;
import com.teeconoa.framework.web.page.TableDataInfo;
import com.teeconoa.framework.web.page.TableSupport;
import com.teeconoa.project.system.user.domain.User;

/**
*  Created by AndyYau
*  2019年1月2日 - 上午12:14:22
*  Company: Teecon
**/
public class BaseController {

	/**
     * 将前台传递过来的日期格式的字符串，自动转化为Date类型
     */
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {

			@Override
			public void setAsText(String text) {
					super.setValue(DateUtils.parseDate(text));
			}
		});
	}
	
	/**
	 * 设置请求分页数据
	 */
	protected void startPage() {
		PageDomain pageDomain = TableSupport.buildPageRequest();
		Integer pageNum = pageDomain.getPageNum();
		Integer pageSize = pageDomain.getPageSize();
		if(StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize)) {
			String orderBy = pageDomain.getOrderBy();
			PageHelper.startPage(pageNum, pageSize, orderBy);
		}
	}
	
	/**
	 * 响应请求分页数据
	 * @param list
	 * @return
	 */
	protected TableDataInfo getDataTable(List<?> list) {
		TableDataInfo responseData = new TableDataInfo();
		responseData.setCode(0);
		responseData.setRows(list);
		responseData.setTotal(new PageInfo(list).getTotal());
		return responseData;
	}
	
	protected AjaxResult toAjax(int rows) {
		return rows > 0 ? success() : error();
	}
	
	/**
	 * 返回成功
	 * @return
	 */
	public AjaxResult success() {
		return AjaxResult.success();
	}
	
	/**
	 * 返回失败
	 * @return
	 */
	public AjaxResult error() {
		return AjaxResult.error();
	}
	
	/**
     * 返回成功消息
     */
    public AjaxResult success(String message)
    {
        return AjaxResult.success(message);
    }

    /**
     * 返回失败消息
     */
    public AjaxResult error(String message)
    {
        return AjaxResult.error(message);
    }

    /**
     * 返回错误码消息
     */
    public AjaxResult error(int code, String message)
    {
        return AjaxResult.error(code, message);
    }

    /**
     * 页面跳转
     */
    public String redirect(String url)
    {
        return StringUtils.format("redirect:{}", url);
    }

    public User getSysUser()
    {
        return ShiroUtils.getSysUser();
    }

    public void setSysUser(User user)
    {
        ShiroUtils.setSysUser(user);
    }

    public Long getUserId()
    {
        return getSysUser().getUserId();
    }

    public String getLoginName()
    {
        return getSysUser().getLoginName();
    }
}
