package com.teeconoa.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.teeconoa.common.utils.http.HttpUtils;
import com.teeconoa.framework.config.TeeconOAConfig;

/**
*  Created by AndyYau
*  2019年1月17日 - 上午12:51:25
*  Company: Teecon
**/
public class AddressUtils {

	private static final Logger log = LoggerFactory.getLogger(AddressUtils.class);
	
	public static final String IP_URL = "http://baidu.com";
	
	public static String getRealAddressByIP(String ip) {
		String address = "XX XX";
		//内网不查询、
		if(IpUtils.internalIp(ip)) {
			return "内网IP";
		}
		if(TeeconOAConfig.isAddressEnabled()) {
			String rspStr = HttpUtils.sendPost(IP_URL, "ip="+ip);
			if(StringUtils.isEmpty(rspStr)) {
				log.error("获取地理位置异常 {}", ip);
				return address;
			}
			JSONObject obj = JSONObject.parseObject(rspStr);
			JSONObject data = obj.getObject("data", JSONObject.class);
			String region = data.getString("region");
			String city = data.getString("city");
			address = region + " " + city;
 		}
		return address;
	}
}
