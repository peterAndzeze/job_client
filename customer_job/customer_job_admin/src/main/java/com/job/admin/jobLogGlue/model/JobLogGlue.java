package com.job.admin.jobLogGlue.model;

import com.job.admin.common.BaseModel;

/**
 * 
 * @Title: JobLogGlue.java
 * @Package: com.job.admin.jobLogGlue.model
 * @Description: log for glue, used to track job code process
 * @author: sunwei
 * @date: 2017年8月23日 下午12:44:47
 * @version: V1.0
 */
public class JobLogGlue extends BaseModel {
	
	private int jobId;				// 任务主键ID
	private String glueType;		// GLUE类型	#com.xxl.job.core.glue.GlueTypeEnum
	private String glueSource;
	private String glueRemark;
	

	public int getJobId() {
		return jobId;
	}

	public void setJobId(int jobId) {
		this.jobId = jobId;
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

	

}
