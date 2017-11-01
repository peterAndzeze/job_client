package com.job.admin.jobLogGlue.service;

import java.util.List;

import com.job.admin.jobLogGlue.model.JobLogGlue;

/**
 * 
 * @Title: JobGlueService.java
 * @Package: com.job.admin.jobLogGlue.service
 * @Description:
 * @author: sunwei
 * @date: 2017年10月31日 下午3:59:27
 * @version: V1.0
 */
public interface JobGlueService {
    public List<JobLogGlue> getJobLogGlueByJobId(Long jobId);
}
