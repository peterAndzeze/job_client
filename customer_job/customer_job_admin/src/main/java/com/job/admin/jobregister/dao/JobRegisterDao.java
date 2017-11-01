package com.job.admin.jobregister.dao;

import java.util.List;

import com.job.admin.jobregister.model.JobRegisterModel;


public interface JobRegisterDao {
    /**
     * 
     * @Title: removeDead
     * @Description:根据超时时间删除
     * @author:sunwei
     * @createTime:2017年8月2日下午4:14:59
     * @param timeout
     * @return
     */
    public boolean deleteDead(int timeout);
    /**
     * 
     * @Title: findAll
     * @Description:找到时间段内执行的任务
     * @author:sunwei
     * @createTime:2017年8月2日下午4:15:19
     * @param timeout
     * @return
     */
    public List<JobRegisterModel> findAll(int timeout);
    /**
     * 
     * @Title: registryUpdate
     * @Description:修改注册的值
     * @author:sunwei
     * @createTime:2017年8月2日下午4:21:53
     * @param registryGroup
     * @param registryKey
     * @param registryValue
     * @return
     */
    public int updateRegistry(String registryGroup, String registryKey, String registryValue);
    /**
     * 
     * @Title: registrySave
     * @Description:注册新任务
     * @author:sunwei
     * @createTime:2017年8月2日下午4:22:09
     * @param registryGroup
     * @param registryKey
     * @param registryValue
     * @return
     */
    public boolean saveRegistry(String registryGroup, String registryKey, String registryValue);
}
