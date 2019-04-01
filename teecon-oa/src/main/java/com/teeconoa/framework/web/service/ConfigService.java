package com.teeconoa.framework.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teeconoa.project.system.config.service.IConfigService;

/**
*  Created by AndyYau
*  Apr 1, 2019 - 3:08:51 PM
*  Company: Teecon
**/
@Service("config")
public class ConfigService {

	@Autowired
	private IConfigService configService;
	
	/**
	 * 根据键名查询参数配置信息
	 * @param configKey
	 * @return
	 */
	public String getKey(String configKey) {
		return configService.selectConfigByKey(configKey);
	}
}
