package com.teeconoa.framework.web.page;

import com.teeconoa.common.StringUtils;

/**
*  Created by AndyYau
*  2019年1月3日 - 上午1:26:44
*  Company: Teecon
**/
public class PageDomain {

	/** 当前记录起始索引 */
	private Integer pageNum;
	/** 每页显示记录数 */
	private Integer pageSize;
	/** 排序列 */
    private String orderByColumn;
    /** 排序的方向 "desc" 或者 "asc". */
    private String isAsc;
    
    public String getOrderBy() {
    	if(StringUtils.isEmpty(orderByColumn)) {
    		return "";
    	}
    	return StringUtils.toUnderScoreCase(orderByColumn) + " " + isAsc;
    }
	
}
