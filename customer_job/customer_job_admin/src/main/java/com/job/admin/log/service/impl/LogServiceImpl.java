package com.job.admin.log.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.job.admin.common.PageModel;
import com.job.admin.log.dao.LogDao;
import com.job.admin.log.model.JobLogModel;
import com.job.admin.log.service.LogService;
/**
 * 
 * @Title: LogServiceImpl.java
 * @Package: com.job.admin.log.service.impl
 * @Description: log
 * @author: sunwei
 * @date: 2017年6月19日 下午2:16:18
 * @version: V1.0
 */
@Service
public class LogServiceImpl implements LogService {
    @Resource
    private LogDao logDao;
    /**
     * 
     * @Title: getPageList
     * @Description:
     * @author:sunwei
     * @createTime:2017年6月20日下午2:11:02
     * @see com.job.admin.log.service.LogService#getPageList(com.job.admin.common.PageModel, com.job.admin.log.model.JobLogModel)
     * @param pageModel
     * @param jobLogModel
     * @return
     */
    @Override
    public PageModel getPageList(PageModel pageModel, JobLogModel jobLogModel) {
	return logDao.getPageList(pageModel, jobLogModel);
    }

}
