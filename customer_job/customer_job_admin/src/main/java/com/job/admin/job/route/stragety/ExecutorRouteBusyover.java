package com.job.admin.job.route.stragety;


import java.util.ArrayList;

import com.job.admin.job.route.ExecutorRouter;
import com.job.admin.job.schedule.JobDynamicScheduler;
import com.job.admin.job.trigger.JobTrigger;
import com.job.core.biz.ExecutorBiz;
import com.job.core.biz.model.ReturnModel;
import com.job.core.biz.model.TriggerParam;

/**
 * Created by xuxueli on 17/3/10.
 */
public class ExecutorRouteBusyover extends ExecutorRouter {

    public String route(int jobId, ArrayList<String> addressList) {
        return addressList.get(0);
    }

    @Override
    public ReturnModel<String> routeRun(TriggerParam triggerParam, ArrayList<String> addressList) {

        StringBuffer idleBeatResultSB = new StringBuffer();
        for (String address : addressList) {
            // beat
            ReturnModel<String> idleBeatResult = null;
            try {
                ExecutorBiz executorBiz = JobDynamicScheduler.getExecutorBiz(address);
                idleBeatResult = executorBiz.idleBeat(triggerParam.getJobId());
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                idleBeatResult = new ReturnModel<String>(ReturnModel.FAILUE_CODE, ""+e );
            }
            idleBeatResultSB.append( (idleBeatResultSB.length()>0)?"<br><br>":"")
                    .append("空闲检测：")
                    .append("<br>address：").append(address)
                    .append("<br>code：").append(idleBeatResult.getCode())
                    .append("<br>msg：").append(idleBeatResult.getMsg());

            // beat success
            if (idleBeatResult.getCode() == ReturnModel.SUCCESS_CODE) {

                ReturnModel<String> runResult = JobTrigger.runExecutor(triggerParam, address);
                idleBeatResultSB.append("<br><br>").append(runResult.getMsg());

                // result
                runResult.setMsg(idleBeatResultSB.toString());
                runResult.setContent(address);
                return runResult;
            }
        }

        return new ReturnModel<String>(ReturnModel.FAILUE_CODE, idleBeatResultSB.toString());
    }
}
