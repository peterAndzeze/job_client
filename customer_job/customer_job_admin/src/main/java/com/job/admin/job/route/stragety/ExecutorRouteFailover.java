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
public class ExecutorRouteFailover extends ExecutorRouter {

    public String route(int jobId, ArrayList<String> addressList) {
        return addressList.get(0);
    }

    @Override
    public ReturnModel<String> routeRun(TriggerParam triggerParam, ArrayList<String> addressList) {

        StringBuffer beatResultSB = new StringBuffer();
        for (String address : addressList) {
            // beat
            ReturnModel<String> beatResult = null;
            try {
                ExecutorBiz executorBiz = JobDynamicScheduler.getExecutorBiz(address);
                beatResult = executorBiz.beat();
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                beatResult = new ReturnModel<String>(ReturnModel.FAILUE_CODE, ""+e );
            }
            beatResultSB.append( (beatResultSB.length()>0)?"<br><br>":"")
                    .append("心跳检测：")
                    .append("<br>address：").append(address)
                    .append("<br>code：").append(beatResult.getCode())
                    .append("<br>msg：").append(beatResult.getMsg());

            // beat success
            if (beatResult.getCode() == ReturnModel.SUCCESS_CODE) {

        	ReturnModel<String> runResult = JobTrigger.runExecutor(triggerParam, address);
                beatResultSB.append("<br><br>").append(runResult.getMsg());

                // result
                runResult.setMsg(beatResultSB.toString());
                runResult.setContent(address);
                return runResult;
            }
        }
        return new ReturnModel<String>(ReturnModel.FAILUE_CODE, beatResultSB.toString());

    }
}
