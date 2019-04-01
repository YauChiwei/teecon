package com.teeconoa.project.system.config.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teeconoa.common.StringUtils;
import com.teeconoa.common.constant.UserConstants;
import com.teeconoa.common.support.Convert;
import com.teeconoa.common.utils.security.ShiroUtils;
import com.teeconoa.project.system.config.domain.Config;
import com.teeconoa.project.system.config.mapper.ConfigMapper;

/**
*  Created by AndyYau
*  Mar 29, 2019 - 5:23:34 PM
*  Company: Teecon
**/
@Service
public class ConfigServiceImpl implements IConfigService {

	@Autowired
	private ConfigMapper configMapper;
	
	@Override
	public Config selectConfigById(Long configId) {
		Config config = new Config();
		config.setConfigId(configId);
		return configMapper.selectConfig(config);
	}

	@Override
	public String selectConfigByKey(String configKey) {
		Config config = new Config();
		config.setConfigKey(configKey);
		config = configMapper.selectConfig(config);
		return StringUtils.isNotNull(config) ? config.getConfigValue() : "";
	}

	@Override
	public List<Config> selectConfigList(Config config) {
		return configMapper.selectConfigList(config);
	}

	@Override
	public int insertConfig(Config config) {
		config.setCreateBy(ShiroUtils.getLoginName());
		return configMapper.insertConfig(config);
	}

	@Override
	public int updateConfig(Config config) {
		config.setUpdateBy(ShiroUtils.getLoginName());
		return configMapper.updateConfig(config);
	}

	@Override
	public int deleteConfigByIds(String ids) {
		return configMapper.deleteConfigByIds(Convert.toStrArray(ids));
	}

	@Override
	public String checkConfigKeyUnique(Config config) {
		Long configId = StringUtils.isNull(config.getConfigId()) ? -1L : config.getConfigId();
		Config result = configMapper.checkConfigKeyUnique(config.getConfigKey());
		if(StringUtils.isNotNull(result) && result.getConfigId().longValue() != configId.longValue()) {
			return UserConstants.CONFIG_KEY_NOT_UNIQUE;
		}
		return UserConstants.CONFIG_KEY_UNIQUE;
	}

}
