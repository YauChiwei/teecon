package com.teeconoa.common.utils.bean;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Bean 工具类
*  Created by AndyYau
*  2019年2月13日 - 下午2:35:18
*  Company: Teecon
**/
public class BeanUtils {

    /** Bean方法名中属性名开始的下标 */
    private static final int BEAN_METHOD_PROP_INDEX = 3;

    /** * 匹配getter方法的正则表达式 */
    private static final Pattern GET_PATTERN = Pattern.compile("get(\\p{javaUpperCase}\\w*)");

    /** * 匹配setter方法的正则表达式 */
    private static final Pattern SET_PATTERN = Pattern.compile("set(\\p{javaUpperCase}\\w*)");

    public static void copyBeanProps(Object dest, Object src) {
    	List<Method> destSetters = getSetterMethods(dest);
    	List<Method> srcGetters = getGetterMethods(src);
    	try {
    		for(Method setter : destSetters) {
    			for(Method getter : srcGetters) {
    				if(isMethodPropEquals(setter.getName(), getter.getName()) &&
    						setter.getParameterTypes()[0].equals(getter.getReturnType())) {
    					setter.invoke(dest, getter.invoke(src));
    				}
    			}
    		}
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    /**
     * 获取对象setter方法
     * @param obj
     * @return
     */
    public static List<Method> getSetterMethods(Object obj){
    	List<Method> setterMethodList = new ArrayList<Method>();
    	Method[] methods = obj.getClass().getMethods();
    	for(Method method : methods) {
    		Matcher matcher = SET_PATTERN.matcher(method.getName());
    		if(matcher.matches() && (method.getParameterTypes().length == 1)) {
    			setterMethodList.add(method);
    		}
    	}
    	return setterMethodList;
    }
    
    /**
     * 获取对象getter方法
     * @param obj
     * @return
     */
    public static List<Method> getGetterMethods(Object obj){
    	List<Method> getterMethodList = new ArrayList<Method>();
    	Method[] methods = obj.getClass().getMethods();
    	for(Method method : methods) {
    		Matcher matcher = GET_PATTERN.matcher(method.getName());
    		if(matcher.matches() && (method.getParameterTypes().length == 0)) {
    			getterMethodList.add(method);
    		}
    	}
    	return getterMethodList;
    }
    
    /**
     * 检查Bean方法名中的属性名是否相等
     * 如getName()和setName()属性名一样，getName()和setAge()就不一样
     * @param m1
     * @param m2
     * @return
     */
    public static boolean isMethodPropEquals(String m1, String m2) {
    	return m1.substring(BEAN_METHOD_PROP_INDEX).equals(m2.substring(BEAN_METHOD_PROP_INDEX));
    }
}
