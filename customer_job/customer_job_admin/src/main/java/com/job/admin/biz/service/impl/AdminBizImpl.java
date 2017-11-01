package com.job.admin.biz.service.impl;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.job.admin.job.dao.JobDao;
import com.job.admin.job.model.JobInfoModel;
import com.job.admin.job.schedule.JobDynamicScheduler;
import com.job.admin.jobregister.dao.JobRegisterDao;
import com.job.admin.log.dao.LogDao;
import com.job.admin.log.model.JobLogModel;
import com.job.core.biz.AdminBiz;
import com.job.core.biz.model.HandleCallBackParam;
import com.job.core.biz.model.RegistryParam;
import com.job.core.biz.model.ReturnModel;
@Service
public class AdminBizImpl implements AdminBiz {

    private static Logger logger = LoggerFactory.getLogger(AdminBizImpl.class);

    @Resource
    public LogDao logDao;
    @Resource
    private  JobDao jobDao;
    @Resource
    private JobRegisterDao jobRegisterDao;

    @Override
    public ReturnModel<String> callback(List<HandleCallBackParam> callbackParamList) {
        for (HandleCallBackParam handleCallbackParam: callbackParamList) {
            ReturnModel<String> callbackResult = callback(handleCallbackParam);
            logger.info("JobApiController.callback {}, handleCallbackParam={}, callbackResult={}",
                    (callbackResult.getCode()==ReturnModel.SUCCESS_CODE?"success":"fail"), handleCallbackParam, callbackResult);
        }

        return ReturnModel.SUCCESS;
    }

    private ReturnModel<String> callback(HandleCallBackParam handleCallbackParam) {
        // valid log item
        JobLogModel log = logDao.load(handleCallbackParam.getLogId());
        if (log == null) {
            return new ReturnModel<String>(ReturnModel.FAILUE_CODE, "log item not found.");
        }
        // trigger success, to trigger child job, and avoid repeat trigger child job
        String childTriggerMsg = null;
        if (ReturnModel.SUCCESS_CODE==handleCallbackParam.getReturnModel().getCode() && ReturnModel.SUCCESS_CODE!=log.getHandleCode()) {
            JobInfoModel jobInfoModel = jobDao.queryJobById(log.getJobId());
            if (jobInfoModel!=null && StringUtils.isNotBlank(jobInfoModel.getChildJobKey())) {
                childTriggerMsg = "<hr>";
                String[] childJobKeys = jobInfoModel.getChildJobKey().split(",");
                for (int i = 0; i < childJobKeys.length; i++) {
                    String[] jobKeyArr = childJobKeys[i].split("_");
                    if (jobKeyArr!=null && jobKeyArr.length==2) {
                        JobInfoModel childJobInfo = jobDao.queryJobById(Long.valueOf(jobKeyArr[1]));
                        if (childJobInfo!=null) {
                            try {
                                boolean ret = JobDynamicScheduler.triggerJob(String.valueOf(childJobInfo.getId()), String.valueOf(childJobInfo.getJobGroup()));
                                // add msg
                                childTriggerMsg += MessageFormat.format("<br> {0}/{1} 触发子任务成功, 子任务Key: {2}, status: {3}, 子任务描述: {4}",
                                        (i+1), childJobKeys.length, childJobKeys[i], ret, childJobInfo.getJobDesc());
                            } catch (SchedulerException e) {
                                logger.error("", e);
                            }
                        } else {
                            childTriggerMsg += MessageFormat.format("<br> {0}/{1} 触发子任务失败, 子任务xxlJobInfo不存在, 子任务Key: {2}",
                                    (i+1), childJobKeys.length, childJobKeys[i]);
                        }
                    } else {
                        childTriggerMsg += MessageFormat.format("<br> {0}/{1} 触发子任务失败, 子任务Key格式错误, 子任务Key: {2}",
                                (i+1), childJobKeys.length, childJobKeys[i]);
                    }
                }

            }
        }

        // handle msg
        StringBuffer handleMsg = new StringBuffer();
        if (log.getHandleMsg()!=null) {
            handleMsg.append(log.getHandleMsg()).append("<br>");
        }
        if (handleCallbackParam.getReturnModel().getMsg() != null) {
            handleMsg.append(handleCallbackParam.getReturnModel().getMsg());
        }
        if (childTriggerMsg !=null) {
            handleMsg.append("<br>子任务触发备注：").append(childTriggerMsg);
        }

        // success, save log
        log.setHandleTime(new Date());
        log.setHandleCode(handleCallbackParam.getReturnModel().getCode());
        log.setHandleMsg(handleMsg.toString());
        logDao.updateHandleInfo(log);

        return ReturnModel.SUCCESS;
    }

    @Override
    public ReturnModel<String> registry(RegistryParam registryParam) {
        int ret = jobRegisterDao.updateRegistry(registryParam.getRegistGroup(), registryParam.getRegistryKey(), registryParam.getRegistryValue());
        if (ret < 1) {
            jobRegisterDao.saveRegistry(registryParam.getRegistGroup(), registryParam.getRegistryKey(), registryParam.getRegistryValue());
        }
        return ReturnModel.SUCCESS;
    }


}
