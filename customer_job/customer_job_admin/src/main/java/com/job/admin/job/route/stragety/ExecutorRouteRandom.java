package com.job.admin.job.route.stragety;

import java.util.ArrayList;
import java.util.Random;

import com.job.admin.job.route.ExecutorRouter;
import com.job.admin.job.trigger.JobTrigger;
import com.job.core.biz.model.ReturnModel;
import com.job.core.biz.model.TriggerParam;

/**
 * Created by xuxueli on 17/3/10.
 */
public class ExecutorRouteRandom extends ExecutorRouter {

    private static Random localRandom = new Random();

    public String route(Long jobId, ArrayList<String> addressList) {
        // Collections.shuffle(addressList);
        return addressList.get(localRandom.nextInt(addressList.size()));
    }

    @Override
    public ReturnModel<String> routeRun(TriggerParam triggerParam, ArrayList<String> addressList) {
        // address
        String address = route(triggerParam.getJobId(), addressList);

        // run executor
        ReturnModel<String> runResult = JobTrigger.runExecutor(triggerParam, address);
        runResult.setContent(address);
        return runResult;
    }

}
