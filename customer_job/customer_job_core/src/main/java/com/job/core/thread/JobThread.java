package com.job.core.thread;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.util.ConcurrentHashSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.job.core.biz.model.HandleCallBackParam;
import com.job.core.biz.model.ReturnModel;
import com.job.core.biz.model.TriggerParam;
import com.job.core.executor.JobExecutor;
import com.job.core.handler.BaseJobHandler;
import com.job.core.log.JobLogAppender;
import com.job.core.log.JobLogger;
import com.job.core.util.ShardingUtil;
/**
 * 
 * @Title: JobThread.java
 * @Package: com.job.core.thread
 * @Description: job执行线程
 * @author: sunwei
 * @date: 2017年5月10日 下午3:54:35
 * @version: V1.0
 */
public class JobThread  extends Thread{
    private static Logger logger = LoggerFactory.getLogger(JobThread.class);

	private Long jobId;
	private BaseJobHandler handler;
	private LinkedBlockingQueue<TriggerParam> triggerQueue;
	private ConcurrentHashSet<Long> triggerLogIdSet;		// avoid repeat trigger for the same TRIGGER_LOG_ID
	private boolean toStop = false;
	private String stopReason;
	private boolean running = false;    // if running job
	private int idleTimes = 0;			// idel times

	public JobThread(Long jobId, BaseJobHandler handler) {
		this.jobId = jobId;
		this.handler = handler;
		this.triggerQueue = new LinkedBlockingQueue<TriggerParam>();
		this.triggerLogIdSet = new ConcurrentHashSet<Long>();
	}
	public BaseJobHandler getHandler() {
		return handler;
	}

    /**
     * new trigger to queue
     *
     * @param triggerParam
     * @return
     */
    public ReturnModel<String> pushTriggerQueue(TriggerParam triggerParam) {
	// avoid repeat
	if (triggerLogIdSet.contains(triggerParam.getLogId())) {
	    logger.debug("repeate trigger job, logId:{}",triggerParam.getLogId());
	    return new ReturnModel<String>(ReturnModel.FAILUE_CODE,"repeate trigger job, logId:" + triggerParam.getLogId());
	}
	triggerLogIdSet.add(triggerParam.getLogId());
	triggerQueue.add(triggerParam);
	return ReturnModel.SUCCESS;
    }

    /**
     * kill job thread
     *
     * @param stopReason
     */
    public void toStop(String stopReason) {
	/**
	 * Thread.interrupt只支持终止线程的阻塞状态(wait、join、sleep)，
	 * 在阻塞出抛出InterruptedException异常,但是并不会终止运行的线程本身；
	 * 所以需要注意，此处彻底销毁本线程，需要通过共享变量方式；
	 */
	this.toStop = true;
	this.stopReason = stopReason;
    }

    /**
     * is running job
     * 
     * @return
     */
    public boolean isRunningOrHasQueue() {
	return running || triggerQueue.size() > 0;
    }

    @Override
    public void run() {
	while (!toStop) {
	    running = false;
	    idleTimes++;
	    try {
		// to check toStop signal, we need cycle, so wo cannot use
		// queue.take(), instand of poll(timeout)
		TriggerParam triggerParam = triggerQueue.poll(3L,TimeUnit.SECONDS);
		if (triggerParam != null) {
		    running = true;
		    idleTimes = 0;
		    triggerLogIdSet.remove(triggerParam.getLogId());
		    // parse param
		    String[] handlerParams = (triggerParam.getExecutorParams() != null && triggerParam
			    .getExecutorParams().trim().length() > 0) ? (String[]) (Arrays
			    .asList(triggerParam.getExecutorParams().split(","))
			    .toArray())
			    : null;

		    // handle job
		    ReturnModel<String> executeResult = null;
		    try {
			// log filename: yyyy-MM-dd/9999.log
			String logFileName =JobLogAppender.makeLogFileName(new Date(triggerParam.getLogDateTim()),triggerParam.getLogId());

			JobLogAppender.contextHolder.set(logFileName);
			ShardingUtil.setShardingVo(new ShardingUtil.ShardingVO(
				triggerParam.getBroadcastIndex(), triggerParam
					.getBroadcastTotal()));
			JobLogger.log("<br>----------- customer_job job execute start -----------<br>----------- Params:"+ Arrays.toString(handlerParams));
			executeResult = handler.executor(handlerParams);
			if (executeResult == null) {
			    executeResult = ReturnModel.FAILUE;
			}

			JobLogger.log("<br>----------- customer_job job execute end(finish) -----------<br>----------- ReturnT:"
					+ executeResult);
		    } catch (Exception e) {
			if (toStop) {
			    JobLogger.log("<br>----------- JobThread toStop, stopReason:" + stopReason);
			}

			StringWriter stringWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stringWriter));
			String errorMsg = stringWriter.toString();
			executeResult = new ReturnModel<String>(ReturnModel.FAILUE_CODE,errorMsg);
			JobLogger.log("<br>----------- JobThread Exception:"+ errorMsg+ "<br>----------- customer_job job execute end(error) -----------");
		    }
		    // callback handler info
		    if (!toStop) {
			// commonm
			TriggerCallBackThread.pushCallBack(new HandleCallBackParam(triggerParam.getLogId(), executeResult));
		    } else {
			// is killed
			ReturnModel<String> stopResult = new ReturnModel<String>(ReturnModel.FAILUE_CODE, stopReason+ " [业务运行中，被强制终止]");
			TriggerCallBackThread.pushCallBack(new HandleCallBackParam(triggerParam.getLogId(), stopResult));
		    }
		} else {
		    if (idleTimes > 30) {
			JobExecutor.removeJobThread(jobId,"excutor idel times over limit.");
		    }
		}
	    } catch (Throwable e) {
		if (toStop) {
		    JobLogger.log("<br>----------- customer_job toStop, stopReason:" + stopReason);
		}

		StringWriter stringWriter = new StringWriter();
		e.printStackTrace(new PrintWriter(stringWriter));
		String errorMsg = stringWriter.toString();
		JobLogger.log("----------- customer_job JobThread Exception:"+ errorMsg);
	    }
	}

	// callback trigger request in queue
	while (triggerQueue != null && triggerQueue.size() > 0) {
	    TriggerParam triggerParam = triggerQueue.poll();
	    if (triggerParam != null) {
		// is killed
		ReturnModel<String> stopResult = new ReturnModel<String>(ReturnModel.FAILUE_CODE, stopReason + " [任务尚未执行，在调度队列中被终止]");
		TriggerCallBackThread.pushCallBack(new HandleCallBackParam(triggerParam.getLogId(), stopResult));
	    }
	}

	logger.info(">>>>>>>>>>>> customer_job JobThread stoped, hashCode:{}",Thread.currentThread());
    }
}
