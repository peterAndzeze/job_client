package com.job.admin.jobGroup.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.job.admin.common.PageModel;
import com.job.admin.framework.mybatis.MybatisDao;
import com.job.admin.jobGroup.dao.JobGroupDao;
import com.job.admin.jobGroup.model.JobGroupModel;
/**
 * 
 * @Title: JobGroupDaoImpl.java
 * @Package: com.job.admin.jobGroup.dao.impl
 * @Description: 执行器
 * @author: sunwei
 * @date: 2017年6月2日 下午3:22:57
 * @version: V1.0
 */
@Repository("jobGroupDao")
public class JobGroupDaoImpl extends MybatisDao<JobGroupModel> implements JobGroupDao {
    /**
     * 
     * @Title: queryPageList
     * @Description:
     * @author:sunwei
     * @createTime:2017年6月7日下午3:30:40
     * @see com.job.admin.jobGroup.dao.JobGroupDao#queryPageList(com.job.admin.common.PageModel, com.job.admin.job.model.JobGroupModel)
     * @param pageModel
     * @param jobGroupModel
     * @return
     */
    @Override
    public PageModel queryPageList(PageModel pageModel,JobGroupModel jobGroupModel) {
	Map<String, Object> paramter=new HashMap<String, Object>();
	paramter.put("appName", jobGroupModel.getAppName());
	pageModel.setCountSql("JobGroupModelMapper.count");
	return queryPageByMap("JobGroupModelMapper.queryPage", paramter , pageModel);
    }
    /**
     * 
     * @Title: queryJobGoupDetail
     * @Description:查询jobgroup明细
     * @author:sunwei
     * @createTime:2017年6月8日下午3:15:54
     * @param jobGroupModel
     * @return
     */
    public JobGroupModel queryJobGoupDetail(JobGroupModel jobGroupModel){
	return queryModel("JobGroupModelMapper.queryJobGoupDetail", jobGroupModel);
    }
    
    
    /**
     * 
     * @Title: updateJobGroup
     * @Description:修改
     * @author:sunwei
     * @createTime:2017年6月9日下午3:50:20
     * @param jobGroupModel
     * @throws Exception
     */
    public void updateJobGroup(JobGroupModel jobGroupModel){
	updateModel("JobGroupModelMapper.updateJobGroup", jobGroupModel);
    }
    /**
     * 
     * @Title: saveJobGroupModel
     * @Description:新增
     * @author:sunwei
     * @createTime:2017年6月9日下午3:51:02
     * @param jobGroupModel
     * @throws Exception
     */
    public void saveJobGroup(JobGroupModel jobGroupModel) {
	saveModel("JobGroupModelMapper.insertJobGroup", jobGroupModel);
    }
    /**
     * 
     * @Title: deleteJobGroup
     * @Description:删除
     * @author:sunwei
     * @createTime:2017年6月9日下午3:51:55
     * @param jobGroupModel
     * @throws Exception
     */
    public void deleteJobGroup(JobGroupModel jobGroupModel) {
	deleteModel("JobGroupModelMapper.deleteJobGroup", jobGroupModel);
    }
    /**
     * 
     * @Title: groupModels
     * @Description:
     * @author:sunwei
     * @createTime:2017年6月12日上午11:03:52
     * @return
     */
    @Override
    public List<JobGroupModel> jobGroupModels(JobGroupModel jobGroupModel){
	return queryModels("JobGroupModelMapper.jobGroups");
    }
    
    @Override
    public JobGroupModel queryJobGoupById(Long id) {
	JobGroupModel jobGroupModel=new JobGroupModel();
	jobGroupModel.setId(id);
	return queryModel("JobGroupModelMapper.queryJobGoupDetail", jobGroupModel);
    }
    @Override
    public List<JobGroupModel> findByAddressType(int addressType){
	return queryModels("JobGroupModelMapper.findByAddressType", createParamter("addressType", addressType));
    }

}
