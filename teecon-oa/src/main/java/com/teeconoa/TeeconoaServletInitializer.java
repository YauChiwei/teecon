package com.teeconoa;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


/**
*  Created by AndyYau
*  2019年3月11日 - 上午11:27:20
*  Company: Teecon
**/
public class TeeconoaServletInitializer extends SpringBootServletInitializer{

	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application)
    {
        return application.sources(TeeconoaApplication.class);
    }
}
