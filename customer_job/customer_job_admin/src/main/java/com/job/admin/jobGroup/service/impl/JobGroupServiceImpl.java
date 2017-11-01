package com.job.admin.jobGroup.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.job.admin.common.PageModel;
import com.job.admin.jobGroup.dao.JobGroupDao;
import com.job.admin.jobGroup.model.JobGroupModel;
import com.job.admin.jobGroup.service.JobGroupService;
/**
 * 
 * @Title: JobGroupServiceImpl.java
 * @Package: com.job.admin.jobGroup.service.impl
 * @Description: 执行器管理
 * @author: sunwei
 * @date: 2017年6月2日 下午3:20:22
 * @version: V1.0
 */
@Service
public class JobGroupServiceImpl implements JobGroupService {
    @Resource
    private JobGroupDao jobGroupDao;
    /**
     * 
     * @Title: queryPageList
     * @Description:
     * @author:sunwei
     * @createTime:2017年6月2日下午3:20:48
     * @see com.job.admin.jobGroup.service.JobGroupService#queryPageList(com.job.admin.common.PageModel, com.job.admin.job.model.JobGroupModel)
     * @param pageModel
     * @param jobGroupModel
     * @return
     */
    @Override
    public PageModel queryPageList(PageModel pageModel,JobGroupModel jobGroupModel) {
	return jobGroupDao.queryPageList(pageModel, jobGroupModel);
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
	return jobGroupDao.queryJobGoupDetail(jobGroupModel);
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
    @Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
    @Override
    public void updateJobGroup(JobGroupModel jobGroupModel) throws Exception{
	try {
	    jobGroupDao.updateJobGroup(jobGroupModel);
	} catch (Exception e) {
	    e.printStackTrace();
	    throw new Exception("修改【"+jobGroupModel.getId()+"】异常");
	}
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
    @Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
    @Override
    public void saveJobGroup(JobGroupModel jobGroupModel) throws Exception{
	try {
	    jobGroupDao.saveJobGroup(jobGroupModel);
	} catch (Exception e) {
	    e.printStackTrace();
	    throw new Exception("保存【"+jobGroupModel.getAppName()+"】异常");
	}
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
    @Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
    @Override
    public void deleteJobGroup(JobGroupModel jobGroupModel) throws Exception{
	try {
	    jobGroupDao.deleteJobGroup(jobGroupModel);
	} catch (Exception e) {
	    e.printStackTrace();
	    throw new Exception("删除【"+jobGroupModel.getId()+"】异常");
	}
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
	return jobGroupDao.jobGroupModels(jobGroupModel);
    }
}
