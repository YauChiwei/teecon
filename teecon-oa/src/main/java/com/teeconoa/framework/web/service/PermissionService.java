package com.teeconoa.framework.web.service;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

/**
*  Created by AndyYau
*  Apr 17, 2019 - 5:05:05 PM
*  Company: Teecon
**/
@Service("permission")
public class PermissionService {

	public String hasPermi(String permission) {
		return isPermittedOperator(permission)? "" : "hidden";
	}
	
	private boolean isPermittedOperator(String permission)
    {
        return SecurityUtils.getSubject().isPermitted(permission);
    }
}
