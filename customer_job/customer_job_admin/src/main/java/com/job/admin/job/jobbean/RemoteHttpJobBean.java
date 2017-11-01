package com.job.admin.job.jobbean;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import com.job.admin.job.trigger.JobTrigger;
/**
 * 
 * @Title: RemoteHttpJobBean.java
 * @Package: com.job.admin.job.jobbean
 * http job bean
 * “@DisallowConcurrentExecution” diable concurrent, thread size can not be only one, better given more
 * @author: sunwei
 * @date: 2017年8月6日 下午9:55:04
 * @version: V1.0
 */
public class RemoteHttpJobBean extends QuartzJobBean {
    private static Logger logger = LoggerFactory.getLogger(RemoteHttpJobBean.class);

    @Override
    protected void executeInternal(JobExecutionContext context)throws JobExecutionException {
	// load jobId
	JobKey jobKey = context.getTrigger().getJobKey();
	Long jobId = Long.valueOf(jobKey.getName());
	// trigger
	JobTrigger.trigger(jobId);
    }
}
