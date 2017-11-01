package com.job.admin.job.thread;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.job.admin.common.CustmerJobConst;
import com.job.admin.job.model.JobInfoModel;
import com.job.admin.job.schedule.JobDynamicScheduler;
import com.job.admin.jobGroup.model.JobGroupModel;
import com.job.admin.log.model.JobLogModel;

/**
 * 
 * @Title: JobFailMonitorHelper.java
 * @Package: com.job.admin.job.thread
 * @Description: job执行结果处理
 * @author: sunwei
 * @date: 2017年8月6日 上午11:59:01
 * @version: V1.0
 */
public class JobFailMonitorHelper {
	private static Logger logger = LoggerFactory.getLogger(JobFailMonitorHelper.class);
//	
	private static JobFailMonitorHelper instance = new JobFailMonitorHelper();
	public static JobFailMonitorHelper getInstance(){
		return instance;
	}
//
	private LinkedBlockingQueue<Long> queue = new LinkedBlockingQueue<Long>(0xfff8);
//
	private Thread monitorThread;
	private boolean toStop = false;
	public void start(){
	    monitorThread = new Thread(new Runnable() {
		@Override
		public void run() {
		    while (!toStop) {
			try {
			    logger.debug(">>>>>>>>>>> job monitor beat ... ");
			    Long jobLogId = JobFailMonitorHelper.instance.queue.take();
			    if (jobLogId != null && jobLogId > 0) {
				logger.debug(">>>>>>>>>>> job monitor heat success, JobLogId:{}", jobLogId);
				JobLogModel log = JobDynamicScheduler.logDao.getJobLogModelByJobId(jobLogId);
				if (log!=null) {
				    if (CustmerJobConst.SUCCESS_CODE==log.getTriggerCode() && log.getHandleCode()==CustmerJobConst.HANDLER_EXECUTORING_CODE) {
					// running
					try {
					    TimeUnit.SECONDS.sleep(10);
					} catch (InterruptedException e) {
					    e.printStackTrace();
					}
					JobFailMonitorHelper.monitor(jobLogId);
				    }
				    if (CustmerJobConst.SUCCESS_CODE==log.getTriggerCode() && CustmerJobConst.SUCCESS_CODE==log.getHandleCode()) {
					// pass
				    }
				    //执行失败
				    if (CustmerJobConst.FAIL_CODE == log.getTriggerCode()|| CustmerJobConst.FAIL_CODE==log.getHandleCode()) {
    					JobInfoModel info = JobDynamicScheduler.jobDao.queryJobById(log.getJobId());
    					if (info!=null && info.getAlarmEmail()!=null && info.getAlarmEmail().trim().length()>0) {
    					    Set<String> emailSet = new HashSet<String>(Arrays.asList(info.getAlarmEmail().split(",")));
    					    for (String email: emailSet) {
    						String title = "《调度监控报警》(任务调度中心XXL-JOB)";
    						JobGroupModel groupModel=new JobGroupModel();
    						groupModel.setId(info.getJobGroup());
    						JobGroupModel group = JobDynamicScheduler.jobGroupDao.queryJobGoupDetail(groupModel);
    						String content = MessageFormat.format("任务调度失败, 执行器名称:{0}, 任务描述:{1}.", group!=null?group.getTitle():"null", info.getJobDesc());
    //						//邮件发送
    //						MailUtil.sendMail(email, title, content, false, null);
    					    }
    					}
				    }
				}
			    }
			} catch (Exception e) {
			    logger.error("job monitor error:{}", e);
			}
		    }
		}
	    });
		monitorThread.setDaemon(true);
		monitorThread.start();
	}

	public void toStop(){
		toStop = true;
		//monitorThread.interrupt();
	}
//	// producer
	public static void monitor(Long jobLogId){
		getInstance().queue.offer(jobLogId);
	}
	
}
