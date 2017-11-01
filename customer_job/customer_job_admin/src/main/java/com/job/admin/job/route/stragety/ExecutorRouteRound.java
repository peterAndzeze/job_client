package com.job.admin.job.route.stragety;


import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import com.job.admin.job.route.ExecutorRouter;
import com.job.admin.job.trigger.JobTrigger;
import com.job.core.biz.model.ReturnModel;
import com.job.core.biz.model.TriggerParam;

/**
 * Created by xuxueli on 17/3/10.
 */
public class ExecutorRouteRound extends ExecutorRouter {

    private static ConcurrentHashMap<Long, Integer> routeCountEachJob = new ConcurrentHashMap<Long, Integer>();
    private static long CACHE_VALID_TIME = 0;
    private static int count(Long jobId) {
        // cache clear
        if (System.currentTimeMillis() > CACHE_VALID_TIME) {
            routeCountEachJob.clear();
            CACHE_VALID_TIME = System.currentTimeMillis() + 1000*60*60*24;
        }

        // count++
        Integer count = routeCountEachJob.get(jobId);
        count = (count==null || count>1000000)?(new Random().nextInt(100)):++count;  // 初始化时主动Random一次，缓解首次压力
        routeCountEachJob.put(jobId, count);
        return count;
    }

    public String route(Long jobId, ArrayList<String> addressList) {
        return addressList.get(count(jobId)%addressList.size());
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
