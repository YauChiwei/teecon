package com.teeconoa.framework.datasource;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
*  Created by AndyYau
*  2018年12月30日 - 下午10:15:32
*  Company: Teecon
**/
public class DynamicDataSource extends AbstractRoutingDataSource{
	
	public DynamicDataSource(DataSource defaultTargetDataSource, Map<Object, Object> targetDataSources) {
		super.setDefaultTargetDataSource(defaultTargetDataSource);
		super.setTargetDataSources(targetDataSources);
		super.afterPropertiesSet();
	}

	@Override
	protected Object determineCurrentLookupKey() {
		return DynamicDataSourceContextHolder.getDateSoureType();
	}
	
}
