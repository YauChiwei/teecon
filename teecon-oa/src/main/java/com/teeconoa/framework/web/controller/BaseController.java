package com.teeconoa.framework.web.controller;

import java.beans.PropertyEditorSupport;
import java.sql.Date;

import com.teeconoa.common.DateUtils;
import org.springframework.web.bind.WebDataBinder;

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
}
