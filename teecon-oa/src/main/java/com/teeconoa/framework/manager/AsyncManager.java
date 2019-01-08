package com.teeconoa.framework.manager;

import java.util.TimerTask;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
*  Created by AndyYau
*  2019年1月9日 - 上午12:44:37
*  Company: Teecon
**/
public class AsyncManager {

	/**
     * 操作延迟10毫秒
     */
    private final int OPERATE_DELAY_TIME = 10;
    
    /**
     * 异步操作任务调度线程池
     */
    private ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(5);
    
    AsyncManager me = null;
    
    /**
     * 单例模式
     **/
    public AsyncManager newInstance() {
    	if(me == null) {
    		me = new AsyncManager();
    	}
    	return me;
    }
    
    public void executeTask(TimerTask task) {
    	executor.schedule(task, OPERATE_DELAY_TIME, TimeUnit.MILLISECONDS);
    }
}
