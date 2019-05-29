package com.teeconoa.project.monitor.schedulejob.task;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.teeconoa.common.constant.Constants;
import com.teeconoa.common.constant.ScheduleConstants;
import com.teeconoa.common.utils.ExceptionUtil;
import com.teeconoa.common.utils.bean.BeanUtils;
import com.teeconoa.common.utils.spring.SpringUtils;
import com.teeconoa.project.monitor.schedulejob.domain.Job;
import com.teeconoa.project.monitor.schedulejob.domain.JobLog;
import com.teeconoa.project.monitor.schedulejob.service.IJobLogService;

/**
*  Created by AndyYau
*  May 27, 2019 - 9:39:46 AM
*  Company: Teecon
**/
public abstract class AbstractQuartzJob implements org.quartz.Job {

	private static final Logger logger = LoggerFactory.getLogger(AbstractQuartzJob.class);

	private static ThreadLocal<Date> threadLocal = new ThreadLocal<>();
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		Job job = new Job();
		BeanUtils.copyBeanProps(job, context.getMergedJobDataMap().get(ScheduleConstants.TASK_PROPERTIES));
		try {
			before(context, job);
			if(job != null) {
				this.doExecute(context, job);
			}
			after(context, job, null);
		}catch(Exception e) {
			logger.error("任务执行异常  - ：", e);
			after(context, job, e);
		}
	}
	
	protected void before(JobExecutionContext context, Job job) {
		threadLocal.set(new Date());
	}
	
	protected void after(JobExecutionContext context, Job job, Exception e) {
		Date startTime = threadLocal.get();
		threadLocal.remove();
		
		final JobLog jobLog = new JobLog();
		jobLog.setJobName(job.getJobName());
		jobLog.setJobGroup(job.getJobGroup());
		jobLog.setMethodName(job.getMethodName());
		jobLog.setMethodParams(job.getMethodParams());
		jobLog.setStartTime(startTime);
		jobLog.setEndTime(new Date());
		
		long totalTime = jobLog.getEndTime().getTime() - jobLog.getStartTime().getTime();
		jobLog.setJobMessage(jobLog.getJobName() + " 总共耗时:" + totalTime + "毫秒");
		if(e != null) {
			jobLog.setStatus(Constants.FAIL);
			String errorMsg = StringUtils.substring(ExceptionUtil.getExceptionMessage(e), 0, 2000);
			jobLog.setExceptionInfo(errorMsg);
		}else {
			jobLog.setStatus(Constants.SUCCESS);
		}
		
		SpringUtils.getBean(IJobLogService.class).addJobLog(jobLog);
	}
	
    /**
     * 执行方法，由子类重载
     *
     * @param context 工作执行上下文对象
     * @param job 系统计划任务
     * @throws Exception 执行过程中的异常
     */
	protected abstract void doExecute(JobExecutionContext context, Job job) throws Exception;
}
