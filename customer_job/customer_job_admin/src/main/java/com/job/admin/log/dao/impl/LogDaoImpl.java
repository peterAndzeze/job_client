package com.job.admin.log.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.job.admin.common.PageModel;
import com.job.admin.framework.mybatis.MybatisDao;
import com.job.admin.log.dao.LogDao;
import com.job.admin.log.model.JobLogModel;
/**
 * 
 * @Title: LogDaoImpl.java
 * @Package: com.job.admin.log.dao.impl
 * @Description: log
 * @author: sunwei
 * @date: 2017年6月19日 下午2:18:25
 * @version: V1.0
 */
@Repository
public class LogDaoImpl extends MybatisDao<JobLogModel> implements LogDao {
    
    @Override
    public PageModel getPageList(PageModel pageModel, JobLogModel jobLogModel) {
	pageModel.setCountSql("JobLogMapper.pageListCount");
	Map<String, Object> paramters=new HashMap<String, Object>();
	paramters.put("jobGroup", jobLogModel.getJobGroup());
	paramters.put("jobId", jobLogModel.getJobId());
	return queryPageByMap("JobLogMapper.pageList", paramters, pageModel);
    }

    @Override
    public JobLogModel getJobLogModelByJobId(long jobId) {
	return null;
    }

    @Override
    public JobLogModel load(Long id) {
	return queryModeByProperties("JobLogMapper.load", createParamter("id", id));
    }

    @Override
    public boolean save(JobLogModel JobLogModel) {
	return saveModel("JobLogMapper.save", JobLogModel);
    }

    @Override
    public boolean updateTriggerInfo(JobLogModel JobLogModel) {
	return updateModel("JobLogMapper.updateTriggerInfo", JobLogModel);
    }

    @Override
    public boolean updateHandleInfo(JobLogModel JobLogModel) {
	return updateModel("JobLogMapper.updateHandleInfo", JobLogModel);
    }

    @Override
    public boolean delete(Long jobId) {
	return deleteModel("JobLogMapper.delete", createParamter("jobId", jobId));
    }

    @Override
    public int triggerCountByHandleCode(int handleCode) {
	return (Integer)queryByProperty("JobLogMapper.triggerCountByHandleCode", handleCode);
    }
    /**
     * 
     * @Title: triggerCountByDay
     * @Description:
     * @author:sunwei
     * @createTime:2017年8月7日下午2:43:16
     * @see com.job.admin.log.dao.LogDao#triggerCountByDay(java.util.Date, java.util.Date, int)
     * @param from
     * @param to
     * @param handleCode
     * @return
     */
    @Override
    public List<Map<String, Object>> triggerCountByDay(Date from, Date to,int handleCode) {
	Map<String, Object> paramter=new HashMap<String, Object>();
	paramter.put("from", from);
	paramter.put("to", to);
	paramter.put("handleCode", handleCode);
	return (List<Map<String, Object>>)queryReturnMap("JobLogMapper.triggerCountByDay", paramter);
    }

    @Override
    public boolean clearLog(int jobGroup, int jobId, Date clearBeforeTime,int clearBeforeNum) {
	Map<String, Object> paramter=new HashMap<String, Object>();
	paramter.put("clearBeforeTime", clearBeforeTime);
	paramter.put("clearBeforeNum", clearBeforeNum);
	paramter.put("jobId", jobId);
	paramter.put("jobGroup", jobGroup);
	return deleteModel("JobLogMapper.clearLog", paramter);
    }

}
