package com.job;

import com.job.core.biz.model.ReturnModel;
import com.job.core.handler.BaseJobHandler;
/**
 * 
 * @Title: GlueJobHandler.java
 * @Package: com.job
 * @Description: GLue类型job
 * @author: sunwei
 * @date: 2017年10月31日 上午11:33:55
 * @version: V1.0
 */
public class GlueTestJobHandler extends BaseJobHandler {

    static{
	System.out.println("glue job 初始化");
    }

    @Override
    public ReturnModel<String> executor(String... params) throws Exception {
	System.out.println("glue job 执行参数："+params);
	return null;
    }
    

}
