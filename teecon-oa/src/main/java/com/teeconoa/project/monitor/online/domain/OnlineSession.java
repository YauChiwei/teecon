package com.teeconoa.project.monitor.online.domain;

import org.apache.shiro.session.mgt.SimpleSession;

/**
*  Created by AndyYau
*  2019年1月13日 - 下午1:55:27
*  Company: Teecon
**/
public class OnlineSession extends SimpleSession{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long userId;
	
	/**
	 * 部门名称
	 */
	private String deptName;
	
	/**
	 * 登录名称
	 */
	private String loginName;
	
	private String host;
	
	private String browser;
	
	private String os;
	
	private OnlineStatus status = OnlineStatus.on_line;
	
	/**
	 * 属性是否改变,优化session数据同步
	 **/
	private transient boolean attributeChanged = false;
	
	public static enum OnlineStatus{
		//用户状态
		on_line("在线"),
		off_line("离线");
		
		private final String info;
		
		private OnlineStatus(String info) {
			this.info = info;
		}

		public String getInfo() {
			return info;
		}
		
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@Override
	public String getHost() {
		// TODO Auto-generated method stub
		return super.getHost();
	}

	@Override
	public void setHost(String host) {
		// TODO Auto-generated method stub
		super.setHost(host);
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public OnlineStatus getStatus() {
		return status;
	}

	public void setStatus(OnlineStatus status) {
		this.status = status;
	}
	
	public void markAttributeChanged(){
        this.attributeChanged = true;
    }

    public void resetAttributeChanged(){
        this.attributeChanged = false;
    }

    public boolean isAttributeChanged(){
        return attributeChanged;
    }

	@Override
	public Object getAttribute(Object key) {
		// TODO Auto-generated method stub
		return super.getAttribute(key);
	}

	@Override
	public void setAttribute(Object key, Object value) {
		// TODO Auto-generated method stub
		super.setAttribute(key, value);
	}

	@Override
	public Object removeAttribute(Object key) {
		// TODO Auto-generated method stub
		return super.removeAttribute(key);
	}
	
	
}
