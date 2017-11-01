package com.job.core.biz.model;

import java.io.Serializable;
/**
 * 
 * @Title: HandleCallBackParam.java
 * @Package: com.job.core.biz.model
 * @Description: 回调函数model
 * @author: sunwei
 * @date: 2017年5月15日 上午10:36:13
 * @version: V1.0
 */
public class HandleCallBackParam implements Serializable {
    private static final long serialVersionUID = -6567063502104727888L;
    private Long logId;
    private ReturnModel<String> returnModel;
    public HandleCallBackParam(Long logId,  ReturnModel<String> returnModel) {
	super();
	this.logId = logId;
	this.returnModel = returnModel;
    }
    public Long getLogId() {
        return logId;
    }
    public void setLogId(Long logId) {
        this.logId = logId;
    }
    public ReturnModel<String> getReturnModel() {
        return returnModel;
    }
    public void setReturnModel(ReturnModel<String> returnModel) {
        this.returnModel = returnModel;
    }
    
}
