package com.teeconoa.framework.config;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.collect.Maps;
import com.teeconoa.common.StringUtils;
import com.teeconoa.common.xss.XssFilter;

/**
*  Created by AndyYau
*  2019年2月27日 - 下午11:52:17
*  Company: Teecon
**/
@Configuration
public class FilterConfig {

	@Value("${xss.enabled}")
	private String enabled;
	
	@Value("${xss.excludes}")
	private String excludes;
	
	@Value("${xss.urlPatterns}")
	private String urlPatterns;
	
	@Bean
	public FilterRegistrationBean xssFilterRegistration() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(new XssFilter());
        registration.addUrlPatterns(StringUtils.split(urlPatterns, ","));
        registration.setName("xssFilter");
        registration.setOrder(Integer.MAX_VALUE);
        Map<String, String> initParameters = Maps.newHashMap();
        initParameters.put("excludes", excludes);
        initParameters.put("enabled", enabled);
        registration.setInitParameters(initParameters);
        return registration;
	}
}
