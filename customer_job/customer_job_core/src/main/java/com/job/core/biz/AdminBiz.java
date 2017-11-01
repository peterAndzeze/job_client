package com.job.core.biz;

import java.util.List;

import com.job.core.biz.model.HandleCallBackParam;
import com.job.core.biz.model.RegistryParam;
import com.job.core.biz.model.ReturnModel;


/**
 * 
 * @Title: AdminBiz.java
 * @Package: com.job.core.biz
 * @Description: 共有业务服务层
 * @author: sunwei
 * @date: 2017年5月15日 上午11:02:59
 * @version: V1.0
 */
public interface AdminBiz {
    public static final String MAPPING = "/api.do";
    /**
     * 
     * @Title: callback
     * @Description:job执行后的返回信息处理
     * @author:sunwei
     * @createTime:2017年5月15日上午11:05:28
     * @param handleCallbackParam 回调函数参数
     * @return 返回信息
     */
    public ReturnModel<String> callback(List<HandleCallBackParam> handleCallbackParam);
    /**
     * registry
     *
     * @param registryParam
     * @return
     */
    public ReturnModel<String> registry(RegistryParam registryParam);
}
