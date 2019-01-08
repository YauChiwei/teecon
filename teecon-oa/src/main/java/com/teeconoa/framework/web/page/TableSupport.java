package com.teeconoa.framework.web.page;

import com.teeconoa.common.Constants;
import com.teeconoa.common.ServletUtils;

/**
*  Created by AndyYau
*  2019年1月5日 - 下午12:00:56
*  Company: Teecon
**/
public class TableSupport {

	public static PageDomain getPageDomain() {
		PageDomain  pageDomain = new PageDomain();
		pageDomain.setPageNum(ServletUtils.getParameterToInt(Constants.PAGE_NUM));
		pageDomain.setPageSize(ServletUtils.getParameterToInt(Constants.PAGE_SIZE));
		pageDomain.setOrderByColumn(ServletUtils.getParameter(Constants.ORDER_BY_COLUMN));
		pageDomain.setIsAsc(ServletUtils.getParameter(Constants.IS_ASC));
		return pageDomain;
	}
	
	public static PageDomain buildPageRequest() {
		return getPageDomain();
	}
}
