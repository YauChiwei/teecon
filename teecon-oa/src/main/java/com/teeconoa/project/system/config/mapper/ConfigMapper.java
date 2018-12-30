package com.teeconoa.project.system.config.mapper;

import java.util.List;

import com.teeconoa.project.system.config.domain.Config;

/**
*  Created by AndyYau
*  2018年12月30日 - 下午11:24:16
*  Company: Teecon
**/
public interface ConfigMapper {

	/**
     * 查询参数配置信息
     * 
     * @param configId 参数配置信息
     * @return 参数配置信息
     */
    public Config selectConfig(Config config);

    /**
     * 查询参数配置列表
     * 
     * @param config 参数配置信息
     * @return 参数配置集合
     */
    public List<Config> selectConfigList(Config config);

    /**
     * 根据键名查询参数配置信息
     * 
     * @param configKey 参数键名
     * @return 参数配置信息
     */
    public Config checkConfigKeyUnique(String configKey);

    /**
     * 新增参数配置
     * 
     * @param config 参数配置信息
     * @return 结果
     */
    public int insertConfig(Config config);

    /**
     * 修改参数配置
     * 
     * @param config 参数配置信息
     * @return 结果
     */
    public int updateConfig(Config config);

    /**
     * 批量删除参数配置
     * 
     * @param configIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteConfigByIds(String[] configIds);
}
