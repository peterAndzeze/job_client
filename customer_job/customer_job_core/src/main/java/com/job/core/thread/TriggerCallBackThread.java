package com.job.core.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.job.core.biz.AdminBiz;
import com.job.core.biz.model.HandleCallBackParam;
import com.job.core.biz.model.ReturnModel;
import com.job.core.executor.JobExecutor;
import com.job.core.rpc.NetComClientProxy;
/**
 * 
 * @Title: TriggerCallBackThread.java
 * @Package: com.job.core.thread
 * @Description:执行器执行线程回调函数
 * @author: sunwei
 * @date: 2017年5月15日 上午10:33:31
 * @version: V1.0
 */
public class TriggerCallBackThread {
    private static Logger logger = LoggerFactory.getLogger(TriggerCallBackThread.class);
    private static TriggerCallBackThread instance = new TriggerCallBackThread();
    public static TriggerCallBackThread getInstance(){
        return instance;
    }
    private LinkedBlockingQueue<HandleCallBackParam> callbackQueue=new LinkedBlockingQueue<HandleCallBackParam>();
    private Thread triggerCallbackThread;
    private boolean toStop=false;
    public void start(){
	triggerCallbackThread=new Thread(new Runnable() {
	    @Override
	    public void run() {
		while(!toStop){//如果没有停止
		    try {
			HandleCallBackParam callback = getInstance().callbackQueue.take();
                        if (callback != null) {
                            // callback list param
                            List<HandleCallBackParam> callbackParamList = new ArrayList<HandleCallBackParam>();
//                            int drainToNum = getInstance().callbackQueue.drainTo(callbackParamList);
                            callbackParamList.add(callback);
                            // valid
                            if (JobExecutor.getAdminBizList()==null) {
                                logger.warn(">>>>>>>>>>>> xxl-job callback fail, adminAddresses is null, callbackParamList：{}", callbackParamList);
                                continue;
                            }

                            // callback, will retry if error
                            for (AdminBiz adminBiz: JobExecutor.getAdminBizList()) {
                                try {
                                    ReturnModel<String> callbackResult = adminBiz.callback(callbackParamList);
                                    if (callbackResult!=null && ReturnModel.SUCCESS_CODE == callbackResult.getCode()) {
                                        callbackResult = ReturnModel.SUCCESS;
                                        logger.info(">>>>>>>>>>> xxl-job callback success, callbackParamList:{}, callbackResult:{}", new Object[]{callbackParamList, callbackResult});
                                        break;
                                    } else {
                                        logger.info(">>>>>>>>>>> xxl-job callback fail, callbackParamList:{}, callbackResult:{}", new Object[]{callbackParamList, callbackResult});
                                    }
                                } catch (Exception e) {
                                    logger.error(">>>>>>>>>>> xxl-job callback error, callbackParamList：{}", callbackParamList, e);
                                    //getInstance().callBackQueue.addAll(callbackParamList);
                                }
                            }
                        }
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                }
            }
        });
        triggerCallbackThread.setDaemon(true);
        triggerCallbackThread.start();
    }
    public void toStop(){
        toStop = true;
    }
    /**
     * 
     * @Title: pushCallBack
     * @Description:job执行返回信息放入队列
     * @author:sunwei
     * @createTime:2017年5月15日上午11:01:04
     * @param callback
     */
    public static void pushCallBack(HandleCallBackParam callback){
        getInstance().callbackQueue.add(callback);
        logger.debug(">>>>>>>>>>>job, push callback request, logId:{}", callback.getLogId());
    }

    
}
