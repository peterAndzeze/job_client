package com.job.core.handler;

import com.job.core.biz.model.ReturnModel;

/**
 * 
 * @Title: BaseJobHandler.java
 * @Package: com.job.core
 * @Description: job执行基础类
 * @author: sunwei
 * @date: 2017年5月10日 下午1:01:08
 * @version: V1.0
 */
public abstract class BaseJobHandler {
    /**
     * 
     * @Title: executor
     * @Description:执行job的方法
     * @author:sunwei
     * @createTime:2017年5月10日下午1:03:24
     * @param params 参数
     * @return job执行返回值
     */
    public abstract ReturnModel<String> executor(String...params) throws Exception;
}
