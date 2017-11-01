package com.job;

import org.springframework.stereotype.Component;

import com.customer.json.JsonUtil;
import com.job.core.biz.model.ReturnModel;
import com.job.core.handler.BaseJobHandler;
import com.job.core.handler.annotation.JobHandler;
@JobHandler("testJobHandler")
@Component
public class TestJobHandler extends BaseJobHandler {
    static{
	System.out.println("初始化自定义job");
    }
    @Override
    public ReturnModel<String> executor(String... params) throws Exception {
	System.out.println("接收处理参数："+JsonUtil.objectToJson(params));
	return null;
    }

}
