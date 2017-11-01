package com.job.core.biz;

import com.job.core.biz.model.LogResult;
import com.job.core.biz.model.ReturnModel;
import com.job.core.biz.model.TriggerParam;
/**
 * 
 * @Title: ExecutorBiz.java
 * @Package: com.job.core.biz
 * @Description: 执行器服务层
 * @author: sunwei
 * @date: 2017年5月15日 上午11:38:24
 * @version: V1.0
 */
public interface ExecutorBiz {
    /**
     * 
     * @Title: beat
     * @Description:控制？
     * @author:sunwei
     * @createTime:2017年5月15日上午11:39:05
     * @return
     */
    public ReturnModel<String> beat();
    /**
     * 
     * @Title: kill
     * @Description: 杀掉job
     * @author:sunwei
     * @createTime:2017年5月15日上午11:39:58
     * @param jobId job任务编号
     * @return
     */
    public ReturnModel<String> kill(Long jobId);
    /**
     * idle beat
     *
     * @param jobId
     * @return
     */
    public ReturnModel<String> idleBeat(Long jobId);


    
    /**
     * log
     * @param logDateTim
     * @param logId
     * @param fromLineNum
     * @return
     */
    public ReturnModel<LogResult> log(long logDateTim, Long logId, int fromLineNum);

    /**
     * run
     * @param triggerParam
     * @return
     */
    public ReturnModel<String> run(TriggerParam triggerParam);
}
