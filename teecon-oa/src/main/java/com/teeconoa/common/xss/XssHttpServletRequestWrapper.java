package com.teeconoa.common.xss;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

/**
*  Created by AndyYau
*  2019年2月28日 - 上午9:12:38
*  Company: Teecon
**/
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper{

	public XssHttpServletRequestWrapper(HttpServletRequest request) {
		super(request);
	}

    @Override
    public String[] getParameterValues(String name)
    {
        String[] values = super.getParameterValues(name);
        if (values != null)
        {
            int length = values.length;
            String[] escapseValues = new String[length];
            for (int i = 0; i < length; i++)
            {
                // 防xss攻击和过滤前后空格
                escapseValues[i] = Jsoup.clean(values[i], Whitelist.relaxed()).trim();
            }
            return escapseValues;
        }
        return super.getParameterValues(name);
    }	
}
