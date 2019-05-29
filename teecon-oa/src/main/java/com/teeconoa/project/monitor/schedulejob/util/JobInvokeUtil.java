package com.teeconoa.project.monitor.schedulejob.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.teeconoa.common.StringUtils;
import com.teeconoa.common.utils.spring.SpringUtils;
import com.teeconoa.project.monitor.schedulejob.domain.Job;

/**
*  Created by AndyYau
*  May 27, 2019 - 2:47:52 PM
*  Company: Teecon
**/
public class JobInvokeUtil {

	/**
     * 执行方法
     *
     * @param sysJob 系统任务
    */
	public static void invokeMethod(Job job) throws Exception{
		Object bean = SpringUtils.getBean(job.getJobName());
		String methodName = job.getMethodName();
		String methodParams = job.getMethodParams();
		invokeSpringBean(bean, methodName, methodParams);
	}
	
	/**
	 * 调用任务方法
     *
     * @param bean 目标对象
     * @param methodName 方法名称
     * @param methodParams 方法参数
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static void invokeSpringBean(Object bean, String methodName, String params) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
    InvocationTargetException{
		if(StringUtils.isNotEmpty(params)) {
			Method method = bean.getClass().getDeclaredMethod(methodName, String.class);
			method.invoke(bean, params);
		}else {
			Method method = bean.getClass().getDeclaredMethod(methodName);
			method.invoke(bean);
		}
	}
}
