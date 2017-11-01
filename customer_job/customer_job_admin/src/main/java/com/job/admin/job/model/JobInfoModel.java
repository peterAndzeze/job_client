package com.job.admin.job.model;

import java.util.Date;

import com.job.admin.common.BaseModel;

public class JobInfoModel extends BaseModel {
    /**执行器编号*/
    private Long jobGroup;
    /**任务执行表达式****/
    private String jobCron;
    private String jobDesc;
    private String author;
    private String alarmEmail;
    /**路由执行策略**/
    private String executorRouteStrategy;
    /**执行任务handler*/
    private String executorHandler;
    /**执行任务参数***/
    private String executorParam;
    /*执行环境**/
    private  String glueType;
    /***执行源代码**/
    private  String glueSource;
    /****执行备注*/
    private String glueRemark;
    /***glue信息更新时间****/
    private  Date glueUpdateTime;
    /***子任务key***/
    private  String childJobKey;
    /**阻塞策略**/
    private String executorBlockStrategy;
    /***失败执行策略****/
    private String  executorFailStrategy;
 // copy from quartz
    private String jobStatus;		// 任务状态 【base on quartz】
    public Long getJobGroup() {
        return jobGroup;
    }
    public void setJobGroup(Long jobGroup) {
        this.jobGroup = jobGroup;
    }
    public String getJobCron() {
        return jobCron;
    }
    public void setJobCron(String jobCron) {
        this.jobCron = jobCron;
    }
    public String getJobDesc() {
        return jobDesc;
    }
    public void setJobDesc(String jobDesc) {
        this.jobDesc = jobDesc;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getAlarmEmail() {
        return alarmEmail;
    }
    public void setAlarmEmail(String alarmEmail) {
        this.alarmEmail = alarmEmail;
    }
    public String getExecutorRouteStrategy() {
        return executorRouteStrategy;
    }
    public void setExecutorRouteStrategy(String executorRouteStrategy) {
        this.executorRouteStrategy = executorRouteStrategy;
    }
    public String getExecutorHandler() {
        return executorHandler;
    }
    public void setExecutorHandler(String executorHandler) {
        this.executorHandler = executorHandler;
    }
    public String getExecutorParam() {
        return executorParam;
    }
    public void setExecutorParam(String executorParam) {
        this.executorParam = executorParam;
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
    public String getGlueRemark() {
        return glueRemark;
    }
    public void setGlueRemark(String glueRemark) {
        this.glueRemark = glueRemark;
    }
    public Date getGlueUpdateTime() {
        return glueUpdateTime;
    }
    public void setGlueUpdateTime(Date glueUpdateTime) {
        this.glueUpdateTime = glueUpdateTime;
    }
    public String getChildJobKey() {
        return childJobKey;
    }
    public void setChildJobKey(String childJobKey) {
        this.childJobKey = childJobKey;
    }
    public String getExecutorBlockStrategy() {
        return executorBlockStrategy;
    }
    public void setExecutorBlockStrategy(String executorBlockStrategy) {
        this.executorBlockStrategy = executorBlockStrategy;
    }
    public String getExecutorFailStrategy() {
        return executorFailStrategy;
    }
    public void setExecutorFailStrategy(String executorFailStrategy) {
        this.executorFailStrategy = executorFailStrategy;
    }
    public String getJobStatus() {
        return jobStatus;
    }
    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }
    
    
}
