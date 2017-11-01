package com.job.admin.job.service;

import java.util.List;

import com.job.admin.common.PageModel;
import com.job.admin.framework.exception.JobException;
import com.job.admin.job.model.JobInfoModel;

/**
 * 
 * @Title: JobService.java
 * @Package: com.job.admin.job
 * @Description: 任务管理service
 * @author: sunwei
 * @date: 2017年6月2日 下午2:07:58
 * @version: V1.0
 */
public interface JobService {
    /**
     * 
     * @Title: saveJobInfoModel
     * @Description:保存
     * @author:sunwei
     * @createTime:2017年6月12日下午2:42:38
     * @param jobInfoModel
     * @throws Exception 
     */
    public void saveJobInfoModel(JobInfoModel jobInfoModel) throws Exception;
    /**
     * 
     * @Title: updateJobIndoModel
     * @Description:更新
     * @author:sunwei
     * @createTime:2017年6月12日下午2:43:10
     * @param jobInfoModel
     * @throws Exception 
     */
    public void updateJobIndoModel(JobInfoModel jobInfoModel) throws Exception;
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
    public void deleteById(Long id) throws Exception;
    /**
     * 
     * @Title: updateJobState
     * @Description:修改job状态
     * @author:sunwei
     * @createTime:2017年6月13日下午2:26:44
     * @param jobInfoModel
     */
    public void updatePauseAndNormalJob(JobInfoModel jobInfoModel) throws Exception;
    /**
     * 
     * @Title: queryJobInfoModels
     * @Description:
     * @author:sunwei
     * @createTime:2017年7月3日下午4:16:05
     * @param jobInfoModel
     * @return
     */
    public List<JobInfoModel> queryJobInfoModels(JobInfoModel jobInfoModel);
    /**
     * 
     * @Title: executorJob
     * @Description:执行业务job
     * @author:sunwei
     * @createTime:2017年8月2日下午3:44:42
     * @param id
     */
    public void executorJob(Long id) throws JobException;
}
