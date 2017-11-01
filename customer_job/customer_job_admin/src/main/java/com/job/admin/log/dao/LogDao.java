package com.job.admin.log.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.job.admin.common.PageModel;
import com.job.admin.log.model.JobLogModel;
/***
 * 
 * @Title: LogDao.java
 * @Package: com.job.admin.log.dao
 * @Description:log
 * @author: sunwei
 * @date: 2017年6月19日 下午2:16:46
 * @version: V1.0
 */
public interface LogDao {
    /**
     * 
     * @Title: getPageList
     * @Description:分页数据
     * @author:sunwei
     * @createTime:2017年6月19日下午2:17:29
     * @param pageModel
     * @param jobLogModel
     * @return
     */
    public PageModel getPageList(PageModel pageModel,JobLogModel jobLogModel);
    /**
     * 
     * @Title: getJobLogModelByJobId
     * @Description:
     * @author:sunwei
     * @createTime:2017年8月2日下午4:42:33
     * @param jobId
     * @return
     */
    public JobLogModel getJobLogModelByJobId(long jobId);
    /**
     * 
     * @Title: load
     * @Description:获取log
     * @author:sunwei
     * @createTime:2017年8月6日下午10:00:38
     * @param id
     * @return
     */
    public JobLogModel load(Long id);
    /**
     * 
     * @Title: save
     * @Description:添加log
     * @author:sunwei
     * @createTime:2017年8月6日下午10:00:49
     * @param JobLogModel
     * @return
     */
    public boolean save(JobLogModel JobLogModel);
    /**
     * 
     * @Title: updateTriggerInfo
     * @Description:更改触发器信息
     * @author:sunwei
     * @createTime:2017年8月6日下午10:01:03
     * @param JobLogModel
     * @return
     */
    public boolean updateTriggerInfo(JobLogModel JobLogModel);
    /**
     * 
     * @Title: updateHandleInfo
     * @Description:更新执行器信息
     * @author:sunwei
     * @createTime:2017年8月7日上午8:34:22
     * @param JobLogModel
     * @return
     */
    public boolean updateHandleInfo(JobLogModel JobLogModel);
    /**
     * 
     * @Title: delete
     * @Description:删除
     * @author:sunwei
     * @createTime:2017年8月7日上午8:34:35
     * @param jobId
     * @return
     */
    public boolean delete(Long jobId);
    
    public int triggerCountByHandleCode(int handleCode);
    /**
     * 
     * @Title: triggerCountByDay
     * @Description:一段时间内 handler 执行情况统计
     * @author:sunwei
     * @createTime:2017年8月7日上午8:35:18
     * @param from
     * @param to
     * @param handleCode
     * @return
     */
    public List<Map<String, Object>> triggerCountByDay(Date from, Date to,int handleCode);
    /**
     * 
     * @Title: clearLog
     * @Description:清空日志
     * @author:sunwei
     * @createTime:2017年8月7日上午8:35:03
     * @param jobGroup
     * @param jobId
     * @param clearBeforeTime
     * @param clearBeforeNum
     * @return
     */
    public boolean clearLog(int jobGroup, int jobId, Date clearBeforeTime,int clearBeforeNum);
    
}
