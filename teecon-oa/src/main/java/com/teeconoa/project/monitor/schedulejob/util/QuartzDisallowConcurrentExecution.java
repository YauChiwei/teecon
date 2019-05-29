package com.teeconoa.project.monitor.schedulejob.util;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;

import com.teeconoa.project.monitor.schedulejob.domain.Job;
import com.teeconoa.project.monitor.schedulejob.task.AbstractQuartzJob;

/**
 * 定时任务处理（禁止并发执行）
 * 
 *
 */
@DisallowConcurrentExecution
public class QuartzDisallowConcurrentExecution extends AbstractQuartzJob
{
    @Override
    protected void doExecute(JobExecutionContext context, Job job) throws Exception
    {
        JobInvokeUtil.invokeMethod(job);
    }
}
