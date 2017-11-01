package com.job.admin.jobGroup.service;

import java.util.List;

import com.job.admin.common.PageModel;
import com.job.admin.jobGroup.model.JobGroupModel;

/**
 * 
 * @Title: JobGroupService.java
 * @Package: com.job.admin.jobGroup
 * @Description: 执行器
 * @author: sunwei
 * @date: 2017年6月2日 下午3:03:38
 * @version: V1.0
 */
public interface JobGroupService {
    /**
     * 
     * @Title: queryPageList
     * @Description:查询执行器信息
     * @author:sunwei
     * @createTime:2017年6月2日下午3:19:20
     * @param pageModel
     * @param jobGroupModel
     * @return
     */
    public PageModel queryPageList(PageModel pageModel,JobGroupModel jobGroupModel);
    
    /**
     * 
     * @Title: queryJobGoupDetail
     * @Description:查询jobgroup明细
     * @author:sunwei
     * @createTime:2017年6月8日下午3:15:54
     * @param jobGroupModel
     * @return
     */
    public JobGroupModel queryJobGoupDetail(JobGroupModel jobGroupModel);
    
    /**
     * 
     * @Title: updateJobGroup
     * @Description:修改
     * @author:sunwei
     * @createTime:2017年6月9日下午3:50:20
     * @param jobGroupModel
     * @throws Exception
     */
    public void updateJobGroup(JobGroupModel jobGroupModel) throws Exception;
    /**
     * 
     * @Title: saveJobGroupModel
     * @Description:新增
     * @author:sunwei
     * @createTime:2017年6月9日下午3:51:02
     * @param jobGroupModel
     * @throws Exception
     */
    public void saveJobGroup(JobGroupModel jobGroupModel) throws Exception;
    /**
     * 
     * @Title: deleteJobGroup
     * @Description:删除
     * @author:sunwei
     * @createTime:2017年6月9日下午3:51:55
     * @param jobGroupModel
     * @throws Exception
     */
    public void deleteJobGroup(JobGroupModel jobGroupModel) throws Exception;
    /**
     * 
     * @Title: groupModels
     * @Description:
     * @author:sunwei
     * @createTime:2017年6月12日上午11:03:52
     * @return
     */
    public List<JobGroupModel> jobGroupModels(JobGroupModel jobGroupModel);
}
