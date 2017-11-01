package com.job.admin.log.service;

import com.job.admin.common.PageModel;
import com.job.admin.log.model.JobLogModel;

public interface LogService {
    /**
     * 
     * @Title: getPageList
     * @Description:分页数据
     * @author:sunwei
     * @createTime:2017年6月19日下午2:15:29
     * @param pageModel
     * @param jobLogModel
     * @return
     */
    public PageModel getPageList(PageModel pageModel,JobLogModel jobLogModel);
    
    
}
