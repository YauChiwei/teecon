package com.teeconoa.project.monitor.schedulejob.task;

import org.springframework.stereotype.Component;

/**
 * 定时任务调度测试
 * 
 * @author ruoyi
 */
@Component("teeconTask")
public class TeeconTask
{
    public void teeconParams(String params)
    {
        System.out.println("执行有参方法：" + params);
    }

    public void teeconNoParams()
    {
        System.out.println("执行无参方法");
    }
}
