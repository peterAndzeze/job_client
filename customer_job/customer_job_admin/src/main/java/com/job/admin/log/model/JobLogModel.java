package com.job.admin.log.model;


import java.util.Date;

import com.job.admin.common.BaseModel;
/**
 * 
 * @Title: JobLogModel.java
 * @Package: com.job.admin.log.model
 * @Description: 日志信息
 * @author: sunwei
 * @date: 2017年6月19日 下午2:14:32
 * @version: V1.0
 */
public class JobLogModel extends BaseModel {
	// job info
	private Long jobGroup;
	private Long jobId;

	// glueType
	private String glueType;

	// execute info
	private String executorAddress;
	private String executorHandler;
	private String executorParam;
	
	// trigger info
	private Date triggerTime;
	private String triggerTimeStart;
	private String triggerTimeEnd;
	private int triggerCode;
	private String triggerMsg;
	
	// handle info
	private Date handleTime;
	private Integer handleCode;
	private String handleMsg;
	
	public Long getJobGroup() {
	    return jobGroup;
	}

	public void setJobGroup(Long jobGroup) {
	    this.jobGroup = jobGroup;
	}

	public Long getJobId() {
	    return jobId;
	}

	public void setJobId(Long jobId) {
	    this.jobId = jobId;
	}

	public int getTriggerCode() {
	    return triggerCode;
	}

	public void setTriggerCode(int triggerCode) {
	    this.triggerCode = triggerCode;
	}

	public void setHandleCode(Integer handleCode) {
	    this.handleCode = handleCode;
	}

	public String getGlueType() {
		return glueType;
	}

	public void setGlueType(String glueType) {
		this.glueType = glueType;
	}

	public String getExecutorAddress() {
		return executorAddress;
	}

	public void setExecutorAddress(String executorAddress) {
		this.executorAddress = executorAddress;
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


	public String getTriggerTimeStart() {
	    return triggerTimeStart;
	}

	public void setTriggerTimeStart(String triggerTimeStart) {
	    this.triggerTimeStart = triggerTimeStart;
	}

	public String getTriggerTimeEnd() {
	    return triggerTimeEnd;
	}

	public void setTriggerTimeEnd(String triggerTimeEnd) {
	    this.triggerTimeEnd = triggerTimeEnd;
	}

	public String getTriggerMsg() {
	    return triggerMsg;
	}

	public void setTriggerMsg(String triggerMsg) {
	    this.triggerMsg = triggerMsg;
	}


	public String getHandleMsg() {
	    return handleMsg;
	}

	public void setHandleMsg(String handleMsg) {
	    this.handleMsg = handleMsg;
	}

	public Integer getHandleCode() {
	    return handleCode;
	}

	public Date getTriggerTime() {
	    return triggerTime;
	}

	public void setTriggerTime(Date triggerTime) {
	    this.triggerTime = triggerTime;
	}

	public Date getHandleTime() {
	    return handleTime;
	}

	public void setHandleTime(Date handleTime) {
	    this.handleTime = handleTime;
	}

	
	
}
