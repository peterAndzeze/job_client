package com.job.admin.jobLogGlue.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.job.admin.jobLogGlue.model.JobLogGlue;


public interface JobLogGlueDao {
    	public int save(JobLogGlue xxlJobLogGlue);
	
	public List<JobLogGlue> findByJobId(Long jobId);

	public int removeOld(Long jobId, @Param("limit") int limit);

	public int deleteByJobId(Long jobId);
}
