package com.job.core.biz.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.job.core.biz.ExecutorBiz;
import com.job.core.biz.model.LogResult;
import com.job.core.biz.model.ReturnModel;
import com.job.core.biz.model.TriggerParam;
import com.job.core.enums.ExecutorBlockStrategyEnum;
import com.job.core.executor.JobExecutor;
import com.job.core.glue.GlueFactory;
import com.job.core.glue.GlueTypeEnum;
import com.job.core.handler.BaseJobHandler;
import com.job.core.handler.impl.GlueJobHandler;
import com.job.core.handler.impl.ScriptJobHandler;
import com.job.core.log.JobLogAppender;
import com.job.core.thread.JobThread;
/**
 * 
 * @Title: ExecutorBizImpl.java
 * @Package: com.job.core.biz.impl
 * @Description: TODO
 * @author: sunwei
 * @date: 2017年5月15日 上午11:41:17
 * @version: V1.0
 */
public class ExecutorBizImpl implements ExecutorBiz {
    private static Logger logger = LoggerFactory.getLogger(ExecutorBizImpl.class);

    @Override
    public ReturnModel<String> beat() {
        return ReturnModel.SUCCESS;
    }
    /**
     * 
     * @Title: kill
     * @Description:手工执行job结束
     * @author:sunwei
     * @createTime:2017年5月15日下午2:39:14
     * @see com.job.core.biz.ExecutorBiz#kill(int)
     * @param jobId 执行任务编号
     * @return 结果信息
     */
    @Override
    public ReturnModel<String> kill(Long jobId) {
	JobThread jobThread=JobExecutor.loadJobThread(jobId);
	if(null!=jobThread){//存在
	    //拿到job所在handler 没使用
//	    BaseJobHandler jobHandler=jobThread.getHandler();
	    //停止jobThread
	    JobExecutor.removeJobThread(jobId,"人工手动终止");
	    logger.info("jobId【"+jobId+"】 is killed");
	    return ReturnModel.SUCCESS;
	}
	return new ReturnModel<String>(ReturnModel.SUCCESS_CODE, "jobId【"+jobId+"】 is killed already!!!!");
    }

    @Override
    public ReturnModel<LogResult> log(long logDateTim, Long logId, int fromLineNum) {
	 // log filename: yyyy-MM-dd/9999.log
        String logFileName = JobLogAppender.makeLogFileName(new Date(logDateTim), logId);
        LogResult logResult =JobLogAppender.readLog(logFileName, fromLineNum);
        return new ReturnModel<LogResult>(logResult);
    }
    /**
     * 
     * @Title: run
     * @Description:
     * @author:sunwei
     * @createTime:2017年5月15日下午3:09:24
     * @see com.job.core.biz.ExecutorBiz#run(com.job.core.biz.model.TriggerParam)
     * @param triggerParam
     * @return
     */
    @Override
    public ReturnModel<String> run(TriggerParam triggerParam) {
	 // load old：jobHandler + jobThread
        JobThread jobThread =JobExecutor.loadJobThread(triggerParam.getJobId());
        BaseJobHandler jobHandler = jobThread!=null?jobThread.getHandler():null;
        String removeOldReason = null;
        // valid：jobHandler + jobThread
        if (GlueTypeEnum.BEAN==GlueTypeEnum.match(triggerParam.getGlueType())) {
            // new jobhandler
            BaseJobHandler newJobHandler = JobExecutor.loadJobHandler(triggerParam.getExecutorHandler());
            // valid old jobThread
            if (jobThread!=null && jobHandler != newJobHandler) {
                // change handler, need kill old thread
                removeOldReason = "更换JobHandler或更换任务模式,终止旧任务线程";
                jobThread = null;
                jobHandler = null;
            }
            // valid handler
            if (jobHandler == null) {
                jobHandler = newJobHandler;
                if (jobHandler == null) {
                    return new ReturnModel<String>(ReturnModel.FAILUE_CODE, "job handler [" + triggerParam.getExecutorHandler() + "] not found.");
                }
            }

        } else if (GlueTypeEnum.GLUE_GROOVY==GlueTypeEnum.match(triggerParam.getGlueType())) {

            // valid old jobThread
            if (jobThread != null &&!(jobThread.getHandler() instanceof GlueJobHandler
                        && ((GlueJobHandler) jobThread.getHandler()).getGlueUpdateTime()==triggerParam.getGlueUpdatetime() )) {
                // change handler or gluesource updated, need kill old thread
                removeOldReason = "更新任务逻辑或更换任务模式,终止旧任务线程";
                jobThread = null;
                jobHandler = null;
            }

            // valid handler
            if (jobHandler == null) {
                try {
                    BaseJobHandler originJobHandler = GlueFactory.getGlueFactory().loadNewInstance(triggerParam.getGlueSource());
                    jobHandler = new GlueJobHandler(triggerParam.getGlueUpdatetime(),originJobHandler);
                } catch (Exception e) {
                    logger.error("", e);
                    return new ReturnModel<String>(ReturnModel.FAILUE_CODE, e.getMessage());
                }
            }
        } else if (GlueTypeEnum.GLUE_SHELL==GlueTypeEnum.match(triggerParam.getGlueType())
                || GlueTypeEnum.GLUE_PYTHON==GlueTypeEnum.match(triggerParam.getGlueType()) ) {

            // valid old jobThread
            if (jobThread != null &&
                    !(jobThread.getHandler() instanceof ScriptJobHandler
                            && ((ScriptJobHandler) jobThread.getHandler()).getGlueUpdatetime()==triggerParam.getGlueUpdatetime() )) {
                // change script or gluesource updated, need kill old thread
                removeOldReason = "更新任务逻辑或更换任务模式,终止旧任务线程";

                jobThread = null;
                jobHandler = null;
            }

            // valid handler
            if (jobHandler == null) {
                jobHandler = new ScriptJobHandler(triggerParam.getJobId(), triggerParam.getGlueUpdatetime(), triggerParam.getGlueSource(), GlueTypeEnum.match(triggerParam.getGlueType()));
            }
        } else {
            return new ReturnModel<String>(ReturnModel.FAILUE_CODE, "glueType[" + triggerParam.getGlueType() + "] is not valid.");
        }

        // executor block strategy
        if (jobThread != null) {
            ExecutorBlockStrategyEnum blockStrategy = ExecutorBlockStrategyEnum.match(triggerParam.getExecutorBlockStrategy(), null);
            if (ExecutorBlockStrategyEnum.DISCARD_LATER == blockStrategy) {
                // discard when running
                if (jobThread.isRunningOrHasQueue()) {
                    return new ReturnModel<String>(ReturnModel.FAILUE_CODE, "阻塞处理策略-生效："+ExecutorBlockStrategyEnum.DISCARD_LATER.getTitle());
                }
            } else if (ExecutorBlockStrategyEnum.COVER_EARLY == blockStrategy) {
                // kill running jobThread
                if (jobThread.isRunningOrHasQueue()) {
                    removeOldReason = "阻塞处理策略-生效：" + ExecutorBlockStrategyEnum.COVER_EARLY.getTitle();

                    jobThread = null;
                }
            } else {
                // just queue trigger
            }
        }

        // replace thread (new or exists invalid)
        if (jobThread == null) {
            jobThread = JobExecutor.registryJobThread(triggerParam.getJobId(), jobHandler, removeOldReason);
        }
        // push data to queue
        ReturnModel<String> pushResult = jobThread.pushTriggerQueue(triggerParam);
        return pushResult;
    }
    
    @Override
    public ReturnModel<String> idleBeat(Long jobId) {
        // isRunningOrHasQueue
        boolean isRunningOrHasQueue = false;
        JobThread jobThread = JobExecutor.loadJobThread(jobId);
        if (jobThread != null && jobThread.isRunningOrHasQueue()) {
            isRunningOrHasQueue = true;
        }
        if (isRunningOrHasQueue) {
            return new ReturnModel<String>(ReturnModel.FAILUE_CODE, "job thread is running or has trigger queue.");
        }
        return ReturnModel.SUCCESS;
    }
    
    public static void main(String[] args) {
	System.out.println();
    }
}
