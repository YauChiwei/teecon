package com.teeconoa.framework.web.page;

import java.io.Serializable;
import java.util.List;

/**
*  Created by AndyYau
*  2019年1月5日 - 上午11:49:18
*  Company: Teecon
**/
public class TableDataInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//总记录数
	private long total;
	
	//列表数据
	private List<?> rows;
	
	//消息状态码
	private int code;
	
	public TableDataInfo() {
		
	}
	
	public TableDataInfo(List<?> list, int total) {
		this.rows = list;
		this.total = total;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	
}
