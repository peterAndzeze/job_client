package com.job.admin.jobGroup.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.job.admin.common.PageModel;
import com.job.admin.jobGroup.model.JobGroupModel;

/**
 * 
 * @Title: JobGroupDao.java
 * @Package: com.job.admin.jobGroup.dao
 * @Description: 执行器dao
 * @author: sunwei
 * @date: 2017年6月2日 下午3:21:07
 * @version: V1.0
 */
public interface JobGroupDao {
    /**
     * 
     * @Title: queryPageList
     * @Description:执行器信息查询
     * @author:sunwei
     * @createTime:2017年6月2日下午3:21:29
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
     * @Title: queryJobGoupDetail
     * @Description:查询jobgroup明细
     * @author:sunwei
     * @createTime:2017年6月8日下午3:15:54
     * @param jobGroupModel
     * @return
     */
    public JobGroupModel queryJobGoupById(Long id);
    
    /**
     * 
     * @Title: updateJobGroup
     * @Description:修改
     * @author:sunwei
     * @createTime:2017年6月9日下午3:50:20
     * @param jobGroupModel
     * @throws Exception
     */
    public void updateJobGroup(JobGroupModel jobGroupModel) ;
    /**
     * 
     * @Title: saveJobGroupModel
     * @Description:新增
     * @author:sunwei
     * @createTime:2017年6月9日下午3:51:02
     * @param jobGroupModel
     * @throws Exception
     */
    public void saveJobGroup(JobGroupModel jobGroupModel) ;
    /**
     * 
     * @Title: deleteJobGroup
     * @Description:删除
     * @author:sunwei
     * @createTime:2017年6月9日下午3:51:55
     * @param jobGroupModel
     * @throws Exception
     */
    public void deleteJobGroup(JobGroupModel jobGroupModel) ;
    /**
     * 
     * @Title: groupModels
     * @Description:
     * @author:sunwei
     * @createTime:2017年6月12日上午11:03:52
     * @return
     */
    public List<JobGroupModel> jobGroupModels(JobGroupModel jobGroupModel);
//    public List<JobGroupModel> findByAddressType(@Param("addressType") int addressType);
    
    public List<JobGroupModel> findByAddressType( int addressType);
}
