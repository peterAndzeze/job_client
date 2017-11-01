package com.job.admin.jobLogGlue.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.job.admin.jobLogGlue.dao.JobLogGlueDao;
import com.job.admin.jobLogGlue.model.JobLogGlue;
import com.job.admin.jobLogGlue.service.JobGlueService;
@Service
public class JobGlueServiceImpl implements JobGlueService{
    @Resource
    private JobLogGlueDao jobLogGlueDao;
    @Override
    public List<JobLogGlue> getJobLogGlueByJobId(Long jobId) {
	return jobLogGlueDao.findByJobId(jobId);
    }

}
