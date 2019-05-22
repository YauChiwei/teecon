package com.teeconoa.project.monitor.operlog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teeconoa.common.support.Convert;
import com.teeconoa.project.monitor.operlog.domain.OperLog;
import com.teeconoa.project.monitor.operlog.mapper.OperLogMapper;

/**
*  Created by AndyYau
*  May 21, 2019 - 12:29:45 PM
*  Company: Teecon
**/
@Service
public class OperLogServiceImpl implements IOperLogService {
	@Autowired
	private OperLogMapper operLogMapper;

	@Override
	public void insertOperlog(OperLog operLog) {
		operLogMapper.insertOperlog(operLog);
	}

	@Override
	public List<OperLog> selectOperLogList(OperLog operLog) {
		return operLogMapper.selectOperLogList(operLog);
	}

	@Override
	public int deleteOperLogByIds(String ids) {
		return operLogMapper.deleteOperLogByIds(Convert.toStrArray(ids));
	}

	@Override
	public OperLog selectOperLogById(Long operId) {
		return operLogMapper.selectOperLogById(operId);
	}

	@Override
	public void cleanOperLog() {
		operLogMapper.cleanOperLog();
	}

}
