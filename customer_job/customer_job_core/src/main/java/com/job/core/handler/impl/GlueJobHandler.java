package com.job.core.handler.impl;

import com.job.core.biz.model.ReturnModel;
import com.job.core.handler.BaseJobHandler;
import com.job.core.log.JobLogger;
/**
 * 
 * @Title: GulejobHandler.java
 * @Package: handler.impl
 * @Description: java代码初始化job执行器
 * @author: sunwei
 * @date: 2017年5月10日 下午1:22:08
 * @version: V1.0
 */
public class GlueJobHandler extends BaseJobHandler {
    private long glueUpdateTime;
    private BaseJobHandler jobHandler;
    
    public  GlueJobHandler(Long glueUpdateTime,BaseJobHandler jobHandler) {
	this.glueUpdateTime=glueUpdateTime;
	this.jobHandler=jobHandler;
    }
    public long getGlueUpdateTime(){
	return glueUpdateTime;
    }
    
    @Override
    public ReturnModel<String> executor(String... params) throws Exception {
	JobLogger.log("jobhandlerName:"+jobHandler+"，版本："+glueUpdateTime+"，start---");
	return jobHandler.executor(params);//调用具体的job执行业务
    }

}
