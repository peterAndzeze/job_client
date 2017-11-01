package com.job.admin.jobLogGlue.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.job.admin.framework.mybatis.MybatisDao;
import com.job.admin.jobLogGlue.dao.JobLogGlueDao;
import com.job.admin.jobLogGlue.model.JobLogGlue;

@Repository
public class JobLogGlueDaoImpl extends MybatisDao<JobLogGlue>  implements JobLogGlueDao {

    @Override
    public int save(JobLogGlue jobLogGlue) {
	return saveModelReturnPk("JobLogGlueMapper.save", jobLogGlue);
    }

    @Override
    public List<JobLogGlue> findByJobId(Long jobId) {
	return queryModels("JobLogGlueMapper.findByJobId", createParamter("jobId", jobId));
    }

    @Override
    public int removeOld(Long jobId, int limit) {
	Map<String, Object> params=new HashMap<String, Object>();
	params.put("jobId", jobId);
	params.put("limit", limit);
	return deleteModelReturnCount("JobLogGlueMapper.removeOld", params);
    }

    @Override
    public int deleteByJobId(Long jobId) {
	return deleteModelReturnCount("JobLogGlueMapper.deleteByJobId", createParamter("jobId", jobId));
    }

}
