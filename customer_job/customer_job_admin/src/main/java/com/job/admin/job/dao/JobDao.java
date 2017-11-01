package com.job.admin.job.dao;

import java.util.List;

import com.job.admin.common.PageModel;
import com.job.admin.job.model.JobInfoModel;

public interface JobDao {
    /**
     * 
     * @Title: saveJobInfoModel
     * @Description:保存
     * @author:sunwei
     * @createTime:2017年6月12日下午2:42:38
     * @param jobInfoModel
     */
    public int saveJobInfoModel(JobInfoModel jobInfoModel);
    /**
     * 
     * @Title: updateJobIndoModel
     * @Description:更新
     * @author:sunwei
     * @createTime:2017年6月12日下午2:43:10
     * @param jobInfoModel
     */
    public void updateJobIndoModel(JobInfoModel jobInfoModel);
    
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
    public PageModel queryPageList(PageModel pageModel,JobInfoModel jobInfoModel);
    
    /**
     * 
     * @Title: queryJobById
     * @Description:明细
     * @author:sunwei
     * @createTime:2017年6月13日下午2:25:26
     * @param id
     * @return
     */
    public JobInfoModel queryJobById(Long id);
    
    /**
     * 
     * @Title: deleteById
     * @Description:删除
     * @author:sunwei
     * @createTime:2017年6月13日下午2:26:06
     * @param id
     */
    public void deleteById(Long id);
    /**
     * 
     * @Title: updateJobState
     * @Description:修改job状态
     * @author:sunwei
     * @createTime:2017年6月13日下午2:26:44
     * @param jobInfoModel
     */
    public void updateJobState(JobInfoModel jobInfoModel);
    /**
     * 
     * @Title: queryJobs
     * @Description:查询
     * @author:sunwei
     * @createTime:2017年7月3日下午4:10:58
     * @param jobInfoModel
     * @return
     */
    public List<JobInfoModel> queryJobs(JobInfoModel jobInfoModel);
    
}
