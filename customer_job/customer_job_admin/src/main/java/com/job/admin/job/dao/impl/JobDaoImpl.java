package com.job.admin.job.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.job.admin.common.PageModel;
import com.job.admin.framework.mybatis.MybatisDao;
import com.job.admin.job.dao.JobDao;
import com.job.admin.job.model.JobInfoModel;
/**
 * 
 * @Title: JobDaoImpl.java
 * @Package: com.job.admin.job.dao.impl
 * @Description: 任务执行
 * @author: sunwei
 * @date: 2017年6月12日 下午2:41:30
 * @version: V1.0
 */
@Repository("jobDao")
public class JobDaoImpl extends MybatisDao<JobInfoModel> implements JobDao {

    @Override
    public int saveJobInfoModel(JobInfoModel jobInfoModel) {
	return saveModelReturnPk("JobInfoMapper.save", jobInfoModel);
    }

    @Override
    public void updateJobIndoModel(JobInfoModel jobInfoModel) {
	updateModel("JobInfoMapper.update", jobInfoModel);
    }
    /**
     * 
     * @Title: queryPageList
     * @Description:查询分页集合
     * @author:sunwei
     * @createTime:2017年6月12日下午5:13:51
     * @param pageModel
     * @param jobInfoModel
     * @return
     */
    @Override
    public PageModel queryPageList(PageModel pageModel,JobInfoModel jobInfoModel){
	Map<String, Object> param=new HashMap<String, Object>();
	param.put("jobGroup",jobInfoModel.getJobGroup());
	param.put("executorHandler", jobInfoModel.getExecutorHandler());
	return queryPageByMap("JobInfoMapper.pageList", param, pageModel);
    }

    @Override
    public JobInfoModel queryJobById(Long id) {
	return queryModeByProperties("JobInfoMapper.queryJobById", createParamter("id", id));
    }

    @Override
    public void deleteById(Long id){
	deleteModel("JobInfoMapper.deleteJob", createParamter("id", id));
    }

    @Override
    public void updateJobState(JobInfoModel jobInfoModel) {
	updateModel("JobInfoMapper.updateJobState", jobInfoModel);
    } 
    
    @Override
    public List<JobInfoModel> queryJobs(JobInfoModel jobInfoModel){
	return queryModels("JobInfoMapper.jobDictionary");
    }
    
}
