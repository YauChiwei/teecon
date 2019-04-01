package com.teeconoa.framework.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teeconoa.project.system.dict.domain.DictData;
import com.teeconoa.project.system.dict.service.IDictDataService;

/**
*  Created by AndyYau
*  Apr 1, 2019 - 4:21:26 PM
*  Company: Teecon
**/
@Service("dict")
public class DictService {

	@Autowired
	private IDictDataService dictDataService;
	
	/**
	 * 根据字典类型查询字典数据信息
	 * 
	 */
	public List<DictData> getType(String dictType){
		return dictDataService.selectDictDataByType(dictType);
	}
	
	/**
	 * 根据字典类型和字典键值查询字典数据信息
	 * @param dictType
	 * @param dictValue
	 * @return
	 */
	public String getLabel(String dictType, String dictValue)
    {
        return dictDataService.selectDictLabel(dictType, dictValue);
    }
}
