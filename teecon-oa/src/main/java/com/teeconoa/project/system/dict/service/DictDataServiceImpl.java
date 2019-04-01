package com.teeconoa.project.system.dict.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teeconoa.common.support.Convert;
import com.teeconoa.common.utils.security.ShiroUtils;
import com.teeconoa.project.system.dict.domain.DictData;
import com.teeconoa.project.system.dict.mapper.DictDataMapper;

/**
*  Created by AndyYau
*  Apr 1, 2019 - 5:32:28 PM
*  Company: Teecon
**/
@Service
public class DictDataServiceImpl implements IDictDataService {
	
	@Autowired
	private DictDataMapper dictDataMapper;

	/**
	 * 根据条件分页查询字典数据
	 */
	@Override
	public List<DictData> selectDictDataList(DictData dictData) {
		return dictDataMapper.selectDictDataList(dictData);
	}

	/**
	 * 根据字典类型查询字典数据
	 */
	@Override
	public List<DictData> selectDictDataByType(String dictType) {
		return dictDataMapper.selectDictDataByType(dictType);
	}

	/**
	 * 根据字典类型和字典键值查询字典数据信息
	 */
	@Override
	public String selectDictLabel(String dictType, String dictValue) {
		// TODO Auto-generated method stub
		return dictDataMapper.selectDictLabel(dictType, dictValue);
	}

	/**
	 * 根据字典数据ID查询信息
	 */
	@Override
	public DictData selectDictDataById(Long dictCode) {
		// TODO Auto-generated method stub
		return dictDataMapper.selectDictDataById(dictCode);
	}

	/**
	 * 通过字典ID删除字典数据信息
	 */
	@Override
	public int deleteDictDataById(Long dictCode) {
		// TODO Auto-generated method stub
		return dictDataMapper.deleteDictDataById(dictCode);
	}

	/**
	 * 批量删除字典数据
	 */
	@Override
	public int deleteDictDataByIds(String ids) {
		// TODO Auto-generated method stub
		return dictDataMapper.deleteDictDataByIds(Convert.toStrArray(ids));
	}

	@Override
	public int insertDictData(DictData dictData) {
		dictData.setCreateBy(ShiroUtils.getLoginName());
		return dictDataMapper.insertDictData(dictData);
	}

	@Override
	public int updateDictData(DictData dictData) {
		dictData.setUpdateBy(ShiroUtils.getLoginName());
        return dictDataMapper.updateDictData(dictData);
	}

}
