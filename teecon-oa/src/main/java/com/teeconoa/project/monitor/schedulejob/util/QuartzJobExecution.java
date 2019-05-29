package com.teeconoa.project.monitor.schedulejob.util;

import org.quartz.JobExecutionContext;

import com.teeconoa.project.monitor.schedulejob.domain.Job;
import com.teeconoa.project.monitor.schedulejob.task.AbstractQuartzJob;

/**
 * 定时任务处理（允许并发执行）
 * 
 *
 */
public class QuartzJobExecution extends AbstractQuartzJob
{
    @Override
    protected void doExecute(JobExecutionContext context, Job job) throws Exception
    {
        JobInvokeUtil.invokeMethod(job);
    }
}
