package com.job.admin.job.route.stragety;


import java.util.ArrayList;

import com.job.admin.job.route.ExecutorRouter;
import com.job.admin.job.trigger.JobTrigger;
import com.job.core.biz.model.ReturnModel;
import com.job.core.biz.model.TriggerParam;

/**
 * Created by xuxueli on 17/3/10.
 */
public class ExecutorRouteLast extends ExecutorRouter {

    public String route(Long jobId, ArrayList<String> addressList) {
        return addressList.get(addressList.size()-1);
    }

    @Override
    public ReturnModel<String> routeRun(TriggerParam triggerParam, ArrayList<String> addressList) {
        // address
        String address = route(triggerParam.getJobId(), addressList);

        // run executor
        ReturnModel<String> runResult =JobTrigger.runExecutor(triggerParam, address);
        runResult.setContent(address);
        return runResult;
    }
}
