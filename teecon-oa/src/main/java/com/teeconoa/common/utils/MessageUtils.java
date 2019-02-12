package com.teeconoa.common.utils;

import org.springframework.context.MessageSource;

import com.teeconoa.common.utils.spring.SpringUtils;

/**
*  获取i18n资源文件
*  Created by AndyYau
*  2019年2月6日 - 下午5:41:28
*  Company: Teecon
**/
public class MessageUtils {

	/**
     * 根据消息键和参数 获取消息 委托给spring messageSource
     *
     * @param code 消息键
     * @param args 参数
     * @return
     */
    public static String message(String code, Object... args)
    {
        MessageSource messageSource = SpringUtils.getBean(MessageSource.class);
        return messageSource.getMessage(code, args, null);
    }
}
