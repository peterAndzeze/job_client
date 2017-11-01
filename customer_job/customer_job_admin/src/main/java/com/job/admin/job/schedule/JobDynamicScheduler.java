package com.job.admin.job.schedule;

import java.util.Date;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.Trigger.TriggerState;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.Assert;

import com.job.admin.job.dao.JobDao;
import com.job.admin.job.jobbean.RemoteHttpJobBean;
import com.job.admin.job.model.JobInfoModel;
import com.job.admin.job.thread.JobFailMonitorHelper;
import com.job.admin.job.thread.JobRegistryMonitorHelper;
import com.job.admin.jobGroup.dao.JobGroupDao;
import com.job.admin.jobregister.dao.JobRegisterDao;
import com.job.admin.log.dao.LogDao;
import com.job.core.biz.AdminBiz;
import com.job.core.biz.ExecutorBiz;
import com.job.core.rpc.NetComClientProxy;
import com.job.core.rpc.NetComServerFactory;
/**
 * 
 * @Title: JobDynamicScheduler.java
 * @Package: com.job.admin.job.schedule
 * @Description: job执行中心
 * @author: sunwei
 * @date: 2017年6月19日 下午1:22:58
 * @version: V1.0
 */
public final class JobDynamicScheduler implements ApplicationContextAware {
    private static Logger logger=LoggerFactory.getLogger(JobDynamicScheduler.class);
    private static Scheduler scheduler;
	// accessToken
    private static String accessToken;
    private String callBackIp;
    private String callBackPort;
    
    public String getCallBackPort() {
        return callBackPort;
    }
    public void setCallBackPort(String callBackPort) {
        this.callBackPort = callBackPort;
    }
    public String getCallBackIp() {
        return callBackIp;
    }
    public void setCallBackIp(String callBackIp) {
        this.callBackIp = callBackIp;
    }
    // dao
    public static LogDao logDao;
    public static JobDao jobDao;
    public static JobRegisterDao jobRegisterDao;
    public static JobGroupDao jobGroupDao;
    public static AdminBiz adminBiz;
    
    public void setScheduler(Scheduler scheduler) {
	JobDynamicScheduler.scheduler = scheduler;
    }
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    // ---------------------- applicationContext ----------------------
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
	JobDynamicScheduler.logDao = applicationContext.getBean(LogDao.class);
	JobDynamicScheduler.jobDao = applicationContext.getBean(JobDao.class);
        JobDynamicScheduler.jobRegisterDao = applicationContext.getBean(JobRegisterDao.class);
        JobDynamicScheduler.jobGroupDao = applicationContext.getBean(JobGroupDao.class);
        JobDynamicScheduler.adminBiz = applicationContext.getBean(AdminBiz.class);
    }
    // ---------------------- init + destroy ----------------------
    /**
     * 
     * @Title: init
     * @Description:初始化注册信息
     * @author:sunwei
     * @createTime:2017年8月17日下午3:05:22
     * @throws Exception
     */
    public void init() throws Exception {
        // admin registry monitor run
        JobRegistryMonitorHelper.getInstance().start();

        // admin monitor run
        JobFailMonitorHelper.getInstance().start();

        // admin-server(spring-mvc)
        NetComServerFactory.putService(AdminBiz.class, JobDynamicScheduler.adminBiz);
        NetComServerFactory.setAccessToken(accessToken);

        // valid
        Assert.notNull(scheduler, "quartz scheduler is null");
        logger.info(">>>>>>>>> init quartz scheduler success.[{}]", scheduler);
    }
    /**
     * 
     * @Title: destroy
     * @Description:销毁
     * @author:sunwei
     * @createTime:2017年8月17日下午3:04:58
     */
    public void destroy(){
        // admin registry stop
        JobRegistryMonitorHelper.getInstance().toStop();
        // admin monitor stop
        JobFailMonitorHelper.getInstance().toStop();
    }
    // ---------------------- executor-client ----------------------
    private static ConcurrentHashMap<String, ExecutorBiz> executorBizRepository = new ConcurrentHashMap<String, ExecutorBiz>();
    public static ExecutorBiz getExecutorBiz(String address) throws Exception {
        // valid
        if (address==null || address.trim().length()==0) {
            return null;
        }
        // load-cache
        address = address.trim();
        ExecutorBiz executorBiz = executorBizRepository.get(address);
        if (executorBiz != null) {
            return executorBiz;
        }
        // set-cache
        executorBiz = (ExecutorBiz) new NetComClientProxy(ExecutorBiz.class, address, accessToken).getObject();
        executorBizRepository.put(address, executorBiz);
        return executorBiz;
    }
    // ---------------------- schedule util ----------------------

    /**
     * fill job info
     *填充job
     * @param jobInfo
     */
    public static void fillJobInfo(JobInfoModel jobInfo) {
	// TriggerKey : name + group
	String group = String.valueOf(jobInfo.getJobGroup());
	String name = String.valueOf(jobInfo.getId());
	TriggerKey triggerKey = TriggerKey.triggerKey(name, group);
	try {
	    Trigger trigger = scheduler.getTrigger(triggerKey);
	    TriggerState triggerState = scheduler.getTriggerState(triggerKey);
	    // parse params
	    if (trigger != null && trigger instanceof CronTriggerImpl) {
		String cronExpression = ((CronTriggerImpl) trigger).getCronExpression();
		jobInfo.setJobCron(cronExpression);
	    }
	    if (triggerState != null) {
		jobInfo.setJobStatus(triggerState.name());
	    }
	} catch (SchedulerException e) {
	    e.printStackTrace();
	}
    }
	
    /**
     * check if exists
     *
     * @param jobName
     * @param jobGroup
     * @return
     * @throws SchedulerException
     */
    public static boolean checkExists(String jobName, String jobGroup)throws SchedulerException {
	TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
	return scheduler.checkExists(triggerKey);
    }

    /**
     * addJob
     *
     * @param jobName
     * @param jobGroup
     * @param cronExpression
     * @return
     * @throws SchedulerException
     */
    public static boolean addJob(String jobName, String jobGroup,String cronExpression) throws SchedulerException {
	// TriggerKey : name + group
	TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
	JobKey jobKey = new JobKey(jobName, jobGroup);
	// TriggerKey valid if_exists
	if (checkExists(jobName, jobGroup)) {
	    logger.info( ">>>>>>>>> addJob fail, job already exist, jobGroup:{}, jobName:{}",jobGroup, jobName);
	    return false;
	}
	// CronTrigger : TriggerKey + cronExpression //
	// withMisfireHandlingInstructionDoNothing 忽略掉调度终止过程中忽略的调度
	CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression).withMisfireHandlingInstructionDoNothing();
	CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withSchedule(cronScheduleBuilder).build();
	// JobDetail : jobClass
	Class<? extends Job> jobClass_ = RemoteHttpJobBean.class; // Class.forName(jobInfo.getJobClass());
//	Class<? extends Job> jobClass_ = null;
//	try {
//	    jobClass_ = (Class<? extends Job>) Class.forName("demoJobHandler");
//	} catch (ClassNotFoundException e) {
//	    // TODO Auto-generated catch block
//	    e.printStackTrace();
//	}
	JobDetail jobDetail = JobBuilder.newJob(jobClass_).withIdentity(jobKey).build();
	/*
	 * if (jobInfo.getJobData()!=null) { JobDataMap jobDataMap =
	 * jobDetail.getJobDataMap();
	 * jobDataMap.putAll(JacksonUtil.readValue(jobInfo.getJobData(),
	 * Map.class)); // JobExecutionContext
	 * context.getMergedJobDataMap().get("mailGuid"); }
	 */
	// schedule : jobDetail + cronTrigger
	Date date = scheduler.scheduleJob(jobDetail, cronTrigger);
	logger.info(">>>>>>>>>>> addJob success, jobDetail:{}, cronTrigger:{}, date:{}",jobDetail, cronTrigger, date);
	return true;
    }

    /**
     * rescheduleJob
     *重新安排job
     * @param jobGroup
     * @param jobName
     * @param cronExpression
     * @return
     * @throws SchedulerException
     */
    public static boolean rescheduleJob(String jobGroup, String jobName,String cronExpression) throws SchedulerException {
	// TriggerKey valid if_exists
	if (!checkExists(jobName, jobGroup)) {
	    logger.info(">>>>>>>>>>> rescheduleJob fail, job not exists, JobGroup:{}, JobName:{}",jobGroup, jobName);
	    return false;
	}
	// TriggerKey : name + group
	TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
	CronTrigger oldTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
	if (oldTrigger != null) {
	    // avoid repeat
	    String oldCron = oldTrigger.getCronExpression();
	    if (oldCron.equals(cronExpression)) {
		return true;
	    }
	    // CronTrigger : TriggerKey + cronExpression
	    CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression).withMisfireHandlingInstructionDoNothing();
	    oldTrigger = oldTrigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(cronScheduleBuilder).build();
	    // rescheduleJob
	    scheduler.rescheduleJob(triggerKey, oldTrigger);
	} else {
	    // CronTrigger : TriggerKey + cronExpression
	    CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression).withMisfireHandlingInstructionDoNothing();
	    CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withSchedule(cronScheduleBuilder).build();
	    // JobDetail-JobDataMap fresh
	    JobKey jobKey = new JobKey(jobName, jobGroup);
	    JobDetail jobDetail = scheduler.getJobDetail(jobKey);
	    /*
	     * JobDataMap jobDataMap = jobDetail.getJobDataMap();
	     * jobDataMap.clear();
	     * jobDataMap.putAll(JacksonUtil.readValue(jobInfo.getJobData(),
	     * Map.class));
	     */
	    // Trigger fresh
	    HashSet<Trigger> triggerSet = new HashSet<Trigger>();
	    triggerSet.add(cronTrigger);
	    scheduler.scheduleJob(jobDetail, triggerSet, true);
	}
        logger.info(">>>>>>>>>>> resumeJob success, JobGroup:{}, JobName:{}", jobGroup, jobName);
        return true;
    }
    /**
     * unscheduleJob
     * 删除job
     * @param jobName
     * @param jobGroup
     * @return
     * @throws SchedulerException
     */
    public static boolean removeJob(String jobName, String jobGroup) throws SchedulerException {
    	// TriggerKey : name + group
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
        boolean result = false;
        if (checkExists(jobName, jobGroup)) {
            result = scheduler.unscheduleJob(triggerKey);//TODO 待验证
            logger.info(">>>>>>>>>>> removeJob, triggerKey:{}, result [{}]", triggerKey, result);
        }
        return true;
    }

    /**
     * pause
     *暂停job
     * @param jobName
     * @param jobGroup
     * @return
     * @throws SchedulerException
     */
    public static boolean pauseJob(String jobName, String jobGroup) throws SchedulerException {
    	// TriggerKey : name + group
    	TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
        
        boolean result = false;
        if (checkExists(jobName, jobGroup)) {
            scheduler.pauseTrigger(triggerKey);
            result = true;
            logger.info(">>>>>>>>>>> pauseJob success, triggerKey:{}", triggerKey);
        } else {
        	logger.info(">>>>>>>>>>> pauseJob fail, triggerKey:{}", triggerKey);
        }
        return result;
    }
    /**
     * resume
     *恢复job执行
     * @param jobName
     * @param jobGroup
     * @return
     * @throws SchedulerException
     */
    public static boolean resumeJob(String jobName, String jobGroup) throws SchedulerException {
    	// TriggerKey : name + group
    	TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
        boolean result = false;
        if (checkExists(jobName, jobGroup)) {
            scheduler.resumeTrigger(triggerKey);
            result = true;
            logger.info(">>>>>>>>>>> resumeJob success, triggerKey:{}", triggerKey);
        } else {
        	logger.info(">>>>>>>>>>> resumeJob fail, triggerKey:{}", triggerKey);
        }
        return result;
    }
    
    /**
     * run
     *执行job
     * @param jobName
     * @param jobGroup
     * @return
     * @throws SchedulerException
     */
    public static boolean triggerJob(String jobName, String jobGroup) throws SchedulerException {
	// TriggerKey : name + group
	JobKey jobKey = new JobKey(jobName, jobGroup);

	boolean result = false;
	if (checkExists(jobName, jobGroup)) {
	    scheduler.triggerJob(jobKey);
	    result = true;
	    logger.info(">>>>>>>>>>> runJob success, jobKey:{}", jobKey);
	} else {
	    logger.info(">>>>>>>>>>> runJob fail, jobKey:{}", jobKey);
	}
	return result;
    }

}
