package com.job.core.biz.model;

import java.io.Serializable;
import java.util.Set;

/**
 * 
 * @Title: TriggerParam.java
 * @Package: com.job.core.biz.model
 * @Description: 触发执行job的model
 * @author: sunwei
 * @date: 2017年5月10日 下午3:56:27
 * @version: V1.0
 */
public class TriggerParam implements Serializable{

    /**
     *
     * @Fields serialVersionUID : TODO
     */
    private static final long serialVersionUID = 1L;
    private Long logId;//日志编号
    private Long jobId;//任务执行编号
    private String executorHandler;//执行任务
    private String executorParams;//执行任务参数
    private String glueType;//处理方式
    private String glueSource;//内容资源
    private long glueUpdatetime;//更新时间
    private String executorBlockStrategy;//阻塞策略
    private long logDateTim;//日志
    private Set<String> logAddress;//日志地址
    private int broadcastIndex;
    private int broadcastTotal;
    
    public int getBroadcastIndex() {
        return broadcastIndex;
    }
    public void setBroadcastIndex(int broadcastIndex) {
        this.broadcastIndex = broadcastIndex;
    }
    public int getBroadcastTotal() {
        return broadcastTotal;
    }
    public void setBroadcastTotal(int broadcastTotal) {
        this.broadcastTotal = broadcastTotal;
    }
    public Long getLogId() {
        return logId;
    }
    public void setLogId(Long logId) {
        this.logId = logId;
    }
    public Long getJobId() {
        return jobId;
    }
    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }
    public String getExecutorHandler() {
        return executorHandler;
    }
    public void setExecutorHandler(String executorHandler) {
        this.executorHandler = executorHandler;
    }
    public String getExecutorParams() {
        return executorParams;
    }
    public void setExecutorParams(String executorParams) {
        this.executorParams = executorParams;
    }
    public String getGlueType() {
        return glueType;
    }
    public void setGlueType(String glueType) {
        this.glueType = glueType;
    }
    public String getGlueSource() {
        return glueSource;
    }
    public void setGlueSource(String glueSource) {
        this.glueSource = glueSource;
    }
    public long getGlueUpdatetime() {
        return glueUpdatetime;
    }
    public void setGlueUpdatetime(long glueUpdatetime) {
        this.glueUpdatetime = glueUpdatetime;
    }
    public long getLogDateTim() {
        return logDateTim;
    }
    public void setLogDateTim(long logDateTim) {
        this.logDateTim = logDateTim;
    }
    public Set<String> getLogAddress() {
        return logAddress;
    }
    public void setLogAddress(Set<String> logAddress) {
        this.logAddress = logAddress;
    }
    public String getExecutorBlockStrategy() {
        return executorBlockStrategy;
    }
    public void setExecutorBlockStrategy(String executorBlockStrategy) {
        this.executorBlockStrategy = executorBlockStrategy;
    }
    
}
