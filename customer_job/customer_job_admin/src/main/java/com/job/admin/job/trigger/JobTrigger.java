package com.job.admin.job.trigger;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.job.admin.job.model.JobInfoModel;
import com.job.admin.job.route.ExecutorRouteStrategyEnum;
import com.job.admin.job.schedule.JobDynamicScheduler;
import com.job.admin.job.thread.JobFailMonitorHelper;
import com.job.admin.jobGroup.model.JobGroupModel;
import com.job.admin.log.model.JobLogModel;
import com.job.core.biz.ExecutorBiz;
import com.job.core.biz.model.ReturnModel;
import com.job.core.biz.model.TriggerParam;
import com.job.core.enums.ExecutorBlockStrategyEnum;
import com.job.core.enums.ExecutorFailStrategyEnum;

import java.util.ArrayList;
import java.util.Date;

/**
 * 
 * @Title: JobTrigger.java
 * @Package: com.job.admin.job.trigger
 * @Description: 触发job执行
 * @author: sunwei
 * @date: 2017年8月17日 下午2:46:30
 * @version: V1.0
 */
public class JobTrigger {
    private static Logger logger = LoggerFactory.getLogger(JobTrigger.class);

    /**
     * trigger job
     *
     * @param jobId
     */
    public static void trigger(Long jobId) {

        // load data
        JobInfoModel jobInfo = JobDynamicScheduler.jobDao.queryJobById(jobId);// job info
        JobGroupModel group = JobDynamicScheduler.jobGroupDao.queryJobGoupById(jobInfo.getJobGroup());  // group info

        ExecutorBlockStrategyEnum blockStrategy = ExecutorBlockStrategyEnum.match(jobInfo.getExecutorBlockStrategy(), ExecutorBlockStrategyEnum.SERIAL_EXECUTION);  // block strategy
        ExecutorFailStrategyEnum failStrategy = ExecutorFailStrategyEnum.match(jobInfo.getExecutorFailStrategy(), ExecutorFailStrategyEnum.FAIL_ALARM);    // fail strategy
        ExecutorRouteStrategyEnum executorRouteStrategyEnum = ExecutorRouteStrategyEnum.match(jobInfo.getExecutorRouteStrategy(), null);    // route strategy
        ArrayList<String> addressList = (ArrayList<String>) group.getRegistryList();

        // broadcast
        if (ExecutorRouteStrategyEnum.SHARDING_BROADCAST == executorRouteStrategyEnum && CollectionUtils.isNotEmpty(addressList)) {
            for (int i = 0; i < addressList.size(); i++) {
                String address = addressList.get(i);

                // 1、save log-id
                JobLogModel jobLog = new JobLogModel();
                jobLog.setJobGroup(jobInfo.getJobGroup());
                jobLog.setJobId(jobInfo.getId());
                JobDynamicScheduler.logDao.save(jobLog);
                logger.debug(">>>>>>>>>>> xxl-job trigger start, jobId:{}", jobLog.getId());

                // 2、prepare trigger-info
                //jobLog.setExecutorAddress(executorAddress);
                jobLog.setGlueType(jobInfo.getGlueType());
                jobLog.setExecutorHandler(jobInfo.getExecutorHandler());
                jobLog.setExecutorParam(jobInfo.getExecutorParam());
                jobLog.setTriggerTime(new Date());

                ReturnModel<String> triggerResult = new ReturnModel<String>(null);
                StringBuffer triggerMsgSb = new StringBuffer();
                triggerMsgSb.append("注册方式：").append( (group.getAddressType() == 0)?"自动注册":"手动录入" );
                triggerMsgSb.append("<br>阻塞处理策略：").append(blockStrategy.getTitle());
                triggerMsgSb.append("<br>失败处理策略：").append(failStrategy.getTitle());
                triggerMsgSb.append("<br>地址列表：").append(group.getRegistryList());
                triggerMsgSb.append("<br>路由策略：").append(executorRouteStrategyEnum.getTitle()).append("("+i+"/"+addressList.size()+")"); // update01

                // 3、trigger-valid
                if (triggerResult.getCode()==ReturnModel.SUCCESS_CODE && CollectionUtils.isEmpty(addressList)) {
                    triggerResult.setCode(ReturnModel.FAILUE_CODE);
                    triggerMsgSb.append("<br>----------------------<br>").append("调度失败：").append("执行器地址为空");
                }

                if (triggerResult.getCode() == ReturnModel.SUCCESS_CODE) {
                    // 4.1、trigger-param
                    TriggerParam triggerParam = new TriggerParam();
                    triggerParam.setJobId(jobInfo.getId());
                    triggerParam.setExecutorHandler(jobInfo.getExecutorHandler());
                    triggerParam.setExecutorParams(jobInfo.getExecutorParam());
                    triggerParam.setExecutorBlockStrategy(jobInfo.getExecutorBlockStrategy());
                    triggerParam.setLogId(jobLog.getId());
                    triggerParam.setLogDateTim(jobLog.getTriggerTime().getTime());
                    triggerParam.setGlueType(jobInfo.getGlueType());
                    triggerParam.setGlueSource(jobInfo.getGlueSource());
                    triggerParam.setGlueUpdatetime(jobInfo.getGlueUpdateTime().getTime());
                    triggerParam.setBroadcastIndex(i);
                    triggerParam.setBroadcastTotal(addressList.size()); // update02

                    // 4.2、trigger-run (route run / trigger remote executor)
                    triggerResult = runExecutor(triggerParam, address);     // update03
                    triggerMsgSb.append("<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>").append(triggerResult.getMsg());

                    // 4.3、trigger (fail retry)
                    if (triggerResult.getCode()!=ReturnModel.SUCCESS_CODE && failStrategy == ExecutorFailStrategyEnum.FAIL_RETRY) {
                        triggerResult = runExecutor(triggerParam, address);  // update04
                        triggerMsgSb.append("<br><br><span style=\"color:#F39C12;\" > >>>>>>>>>>>失败重试<<<<<<<<<<< </span><br>").append(triggerResult.getMsg());
                    }
                }

                // 5、save trigger-info
                jobLog.setExecutorAddress(triggerResult.getContent());
                jobLog.setTriggerCode(triggerResult.getCode());
                jobLog.setTriggerMsg(triggerMsgSb.toString());
                JobDynamicScheduler.logDao.updateTriggerInfo(jobLog);

                // 6、monitor triger
                JobFailMonitorHelper.monitor(jobLog.getId());
                logger.debug(">>>>>>>>>>> xxl-job trigger end, jobId:{}", jobLog.getId());

            }
            return;
        }

        // 1、save log-id
        JobLogModel jobLog = new JobLogModel();
        jobLog.setJobGroup(jobInfo.getJobGroup());
        jobLog.setJobId(jobInfo.getId());
        JobDynamicScheduler.logDao.save(jobLog);
        logger.debug(">>>>>>>>>>> xxl-job trigger start, jobId:{}", jobLog.getId());

        // 2、prepare trigger-info
        //jobLog.setExecutorAddress(executorAddress);
        jobLog.setGlueType(jobInfo.getGlueType());
        jobLog.setExecutorHandler(jobInfo.getExecutorHandler());
        jobLog.setExecutorParam(jobInfo.getExecutorParam());
        jobLog.setTriggerTime(new Date());

        ReturnModel<String> triggerResult = new ReturnModel<String>(null);
        StringBuffer triggerMsgSb = new StringBuffer();
        triggerMsgSb.append("注册方式：").append( (group.getAddressType() == 0)?"自动注册":"手动录入" );
        triggerMsgSb.append("<br>阻塞处理策略：").append(blockStrategy.getTitle());
        triggerMsgSb.append("<br>失败处理策略：").append(failStrategy.getTitle());
        triggerMsgSb.append("<br>地址列表：").append(group.getRegistryList());
        triggerMsgSb.append("<br>路由策略：").append(executorRouteStrategyEnum.getTitle());
        // 3、trigger-valid
        if (triggerResult.getCode()==ReturnModel.SUCCESS_CODE && CollectionUtils.isEmpty(addressList)) {
            triggerResult.setCode(ReturnModel.FAILUE_CODE);
            triggerMsgSb.append("<br>----------------------<br>").append("调度失败：").append("执行器地址为空");
        }

        if (triggerResult.getCode() == ReturnModel.SUCCESS_CODE) {
            // 4.1、trigger-param
            TriggerParam triggerParam = new TriggerParam();
            triggerParam.setJobId(jobInfo.getId());
            triggerParam.setExecutorHandler(jobInfo.getExecutorHandler());
            triggerParam.setExecutorParams(jobInfo.getExecutorParam());
            triggerParam.setExecutorBlockStrategy(jobInfo.getExecutorBlockStrategy());
            triggerParam.setLogId(jobLog.getId());
            triggerParam.setLogDateTim(jobLog.getTriggerTime().getTime());
            triggerParam.setGlueType(jobInfo.getGlueType());
            triggerParam.setGlueSource(jobInfo.getGlueSource());
            triggerParam.setGlueUpdatetime(jobInfo.getGlueUpdateTime().getTime());
            triggerParam.setBroadcastIndex(0);
            triggerParam.setBroadcastTotal(1);

            // 4.2、trigger-run (route run / trigger remote executor)
            triggerResult = executorRouteStrategyEnum.getRouter().routeRun(triggerParam, addressList);
            triggerMsgSb.append("<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>").append(triggerResult.getMsg());

            // 4.3、trigger (fail retry)
            if (triggerResult.getCode()!=ReturnModel.SUCCESS_CODE && failStrategy == ExecutorFailStrategyEnum.FAIL_RETRY) {
                triggerResult = executorRouteStrategyEnum.getRouter().routeRun(triggerParam, addressList);
                triggerMsgSb.append("<br><br><span style=\"color:#F39C12;\" > >>>>>>>>>>>失败重试<<<<<<<<<<< </span><br>").append(triggerResult.getMsg());
            }
        }

        // 5、save trigger-info
        jobLog.setExecutorAddress(triggerResult.getContent());
        jobLog.setTriggerCode(triggerResult.getCode());
        jobLog.setTriggerMsg(triggerMsgSb.toString());
        JobDynamicScheduler.logDao.updateTriggerInfo(jobLog);

        // 6、monitor triger
        JobFailMonitorHelper.monitor(jobLog.getId());
        logger.debug(">>>>>>>>>>> xxl-job trigger end, jobId:{}", jobLog.getId());
    }

    /**
     * run executor
     * @param triggerParam
     * @param address
     * @return  ReturnModel.content: final address
     */
    public static ReturnModel<String> runExecutor(TriggerParam triggerParam, String address){
	ReturnModel<String> runResult = null;
        try {
            ExecutorBiz executorBiz = JobDynamicScheduler.getExecutorBiz(address);
            runResult = executorBiz.run(triggerParam);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            runResult = new ReturnModel<String>(ReturnModel.FAILUE_CODE, ""+e );
        }

        StringBuffer runResultSB = new StringBuffer("触发调度：");
        runResultSB.append("<br>address：").append(address);
        runResultSB.append("<br>code：").append(runResult.getCode());
        runResultSB.append("<br>msg：").append(runResult.getMsg());

        runResult.setMsg(runResultSB.toString());
        runResult.setContent(address);
        return runResult;
    }

}
