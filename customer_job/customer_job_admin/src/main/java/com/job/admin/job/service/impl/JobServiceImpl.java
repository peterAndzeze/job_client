package com.job.admin.job.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.quartz.CronExpression;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.job.admin.common.PageModel;
import com.job.admin.framework.exception.JobException;
import com.job.admin.job.dao.JobDao;
import com.job.admin.job.model.JobInfoModel;
import com.job.admin.job.route.ExecutorRouteStrategyEnum;
import com.job.admin.job.schedule.JobDynamicScheduler;
import com.job.admin.job.service.JobService;
import com.job.admin.jobGroup.model.JobGroupModel;
import com.job.admin.jobGroup.service.JobGroupService;
import com.job.admin.jobLogGlue.dao.JobLogGlueDao;
import com.job.admin.log.dao.LogDao;
import com.job.core.enums.ExecutorBlockStrategyEnum;
import com.job.core.enums.ExecutorFailStrategyEnum;
import com.job.core.glue.GlueTypeEnum;
@Service
public class JobServiceImpl implements JobService {
    private Logger logger=LoggerFactory.getLogger(JobServiceImpl.class);
    @Resource
    private JobDao jobDao;
    @Resource
    private LogDao logDao;
    @Resource
    private JobLogGlueDao jobLogGlueDao;
    @Resource
    private JobGroupService jobGroupService;
    @Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
    @Override
    public void saveJobInfoModel(JobInfoModel jobInfoModel) throws Exception {
	try {
	    validateJobData(jobInfoModel);
	    //脚本定时
	    if (GlueTypeEnum.GLUE_SHELL==GlueTypeEnum.match(jobInfoModel.getGlueType()) && jobInfoModel.getGlueSource()!=null) {
		jobInfoModel.setGlueSource(jobInfoModel.getGlueSource().replaceAll("\r", ""));
	    }
	    if(GlueTypeEnum.GLUE_GROOVY==GlueTypeEnum.match(jobInfoModel.getGlueType()) && jobInfoModel.getGlueSource()==null){
		String sourceCode="package com.job;import com.job.core.biz.model.ReturnModel;import com.job.core.handler.BaseJobHandler;"+
				   "/**"+
                                     "*"+ 
                                     "* @Title: GlueJobHandler.java"+
                                     "* @Package: com.job"+
                                     "* @Description: GLue类型job"+
                                     "* @author: sunwei"+
                                     "* @date: 2017年10月31日 上午11:33:55"+
                                     "* @version: V1.0"+
                                     "*/"+
                                    "public class GlueTestJobHandler extends BaseJobHandler {"+
                                        "static{"+
                                        "System.out.println(\"glue job 初始化\");"+
                                        "}"+
                                        " @Override"+
                                        "public ReturnModel<String> executor(String... params) throws Exception {"+
                                        "System.out.println(\"glue job 执行参数：\"+params);"+
                                        "return null;"+
                                        " }"+
                                        "}";
            		jobInfoModel.setGlueSource(sourceCode);//默认
	    }
	    jobDao.saveJobInfoModel(jobInfoModel);
	    if(jobInfoModel.getId()<1){
		throw new Exception("保存【"+jobInfoModel.getExecutorHandler()+"】异常");
	    }
	    addQuartz(jobInfoModel);
	} catch (Exception e) {
	    e.printStackTrace();
	    throw new Exception("保存【"+jobInfoModel.getExecutorHandler()+"】异常");
	}
	
    }
    private void addQuartz(JobInfoModel jobInfoModel) throws Exception{
	String qz_group = String.valueOf(jobInfoModel.getJobGroup());
	String qz_name = String.valueOf(jobInfoModel.getId());
	try {
	    JobDynamicScheduler.addJob(qz_name, qz_group,jobInfoModel.getJobCron());
	    // XxlJobDynamicScheduler.pauseJob(qz_name, qz_group);
	} catch (SchedulerException e) {
	    logger.error("", e);
	    try {
		JobDynamicScheduler.removeJob(qz_name, qz_group);
	    } catch (SchedulerException e1) {
		logger.error("", e1);
	    }
	    throw new Exception("添加任务失败");
	}
    }
    @Override
    public void updateJobIndoModel(JobInfoModel jobInfoModel) throws Exception {
	try {
	    validateJobData(jobInfoModel);
	    JobInfoModel exists_jobInfo = jobDao.queryJobById(jobInfoModel.getId());
	    if (exists_jobInfo == null) {
		throw new Exception("不存在");
	    }
	    exists_jobInfo.setJobCron(jobInfoModel.getJobCron());
	    exists_jobInfo.setJobDesc(jobInfoModel.getJobDesc());
	    exists_jobInfo.setAuthor(jobInfoModel.getAuthor());
	    exists_jobInfo.setAlarmEmail(jobInfoModel.getAlarmEmail());
	    exists_jobInfo.setExecutorRouteStrategy(jobInfoModel.getExecutorRouteStrategy());
	    exists_jobInfo.setExecutorHandler(jobInfoModel.getExecutorHandler());
	    exists_jobInfo.setExecutorParam(jobInfoModel.getExecutorParam());
	    exists_jobInfo.setExecutorBlockStrategy(jobInfoModel.getExecutorBlockStrategy());
	    exists_jobInfo.setExecutorFailStrategy(jobInfoModel.getExecutorFailStrategy());
	    exists_jobInfo.setChildJobKey(jobInfoModel.getChildJobKey());
	    jobDao.updateJobIndoModel(jobInfoModel);
	 // fresh quartz
	    String qz_group = String.valueOf(exists_jobInfo.getJobGroup());
	    String qz_name = String.valueOf(exists_jobInfo.getId());
	    try {
		boolean ret = JobDynamicScheduler.rescheduleJob(qz_group,qz_name, exists_jobInfo.getJobCron());
		if(!ret){
		    throw new Exception("修改【"+jobInfoModel.getExecutorHandler()+"】异常");
		}
	    } catch (SchedulerException e) {
		logger.error("", e);
		throw new Exception("修改【"+jobInfoModel.getExecutorHandler()+"】异常");
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	    throw new Exception("修改【"+jobInfoModel.getExecutorHandler()+"】异常");
	}
    }
    /**
     * 
     * @Title: validateJobData
     * @Description:验证job信息
     * @author:sunwei
     * @createTime:2017年6月19日下午1:16:14
     * @param infoModel
     * @throws Exception 
     */
    private void validateJobData(JobInfoModel infoModel) throws Exception{
	JobGroupModel jobGroupModel=new JobGroupModel();
	jobGroupModel.setId(infoModel.getJobGroup());
	JobGroupModel group = jobGroupService.queryJobGoupDetail(jobGroupModel);
	if (group == null) {
	    new Exception( "请选择“执行器”");
	}
	if (!CronExpression.isValidExpression(infoModel.getJobCron())) {
		new Exception("请输入格式正确的“Cron”");
	}
	if (StringUtils.isBlank(infoModel.getJobDesc())) {
		new Exception("请输入“任务描述”");
	}
	if (StringUtils.isBlank(infoModel.getAuthor())) {
		new Exception("请输入“负责人”");
	}
	if (ExecutorRouteStrategyEnum.match(infoModel.getExecutorRouteStrategy(), null) == null) {
		new Exception("路由策略非法");
	}
	if (ExecutorBlockStrategyEnum.match(infoModel.getExecutorBlockStrategy(), null) == null) {
		new Exception("阻塞处理策略非法");
	}
	if (ExecutorFailStrategyEnum.match(infoModel.getExecutorFailStrategy(), null) == null) {
		new Exception("失败处理策略非法");
	}
	if (GlueTypeEnum.match(infoModel.getGlueType()) == null) {
		new Exception("运行模式非法非法");
	}
	if (GlueTypeEnum.BEAN==GlueTypeEnum.match(infoModel.getGlueType()) && StringUtils.isBlank(infoModel.getExecutorHandler())) {
		new Exception("请输入“JobHandler”");
	}
	if (!CronExpression.isValidExpression(infoModel.getJobCron())) {
		throw new Exception("请输入格式正确的“Cron”");
	}
	// childJobKey valid
	if (StringUtils.isNotBlank(infoModel.getChildJobKey())) {
	    String[] childJobKeys = infoModel.getChildJobKey().split(",");
	    for (String childJobKeyItem : childJobKeys) {
		String[] childJobKeyArr = childJobKeyItem.split("_");
		if (childJobKeyArr.length != 2) {
		    throw new Exception("【"+childJobKeyItem+"】子任务Key({0})格式错误");
		}
		JobInfoModel childJobInfo = jobDao.queryJobById(Long.valueOf(childJobKeyArr[1]));
		if (childJobInfo == null) {
		   throw new Exception ("【"+childJobKeyItem+"】子任务Key({0})无效");
		}
	    }
	}
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
    @SuppressWarnings("unchecked")
    @Override
    public PageModel queryPageList(PageModel pageModel,JobInfoModel jobInfoModel){
	pageModel.setCountSql("JobInfoMapper.pageListCount");
	
	PageModel model=jobDao.queryPageList(pageModel, jobInfoModel);
	List<JobInfoModel> jobInfoModels=(List<JobInfoModel>) model.getRecords();
	for (JobInfoModel jobInfo : jobInfoModels) {
	    JobDynamicScheduler.fillJobInfo(jobInfo);
	}
	return model;
    }

    @Override
    public JobInfoModel queryJobById(Long id) {
	return jobDao.queryJobById(id);
    }
    /**
     * 
     * @Title: deleteById
     * @Description:删除任务
     * @author:sunwei
     * @createTime:2017年8月23日上午11:54:56
     * @see com.job.admin.job.service.JobService#deleteById(java.lang.Long)
     * @param id
     * @throws Exception
     */
    @Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
    @Override
    public void deleteById(Long id) throws Exception {
	JobInfoModel jobInfoModel =jobDao.queryJobById(id);
        String group = String.valueOf(jobInfoModel.getJobGroup());
        String name = String.valueOf(jobInfoModel.getId());

		try {
			JobDynamicScheduler.removeJob(name, group);
			jobDao.deleteById(id);
			logDao.delete(id);
			jobLogGlueDao.deleteByJobId(id);
		} catch (SchedulerException e) {
			e.printStackTrace();
			throw new Exception("删除失败");
		}
    }
    @Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
    @Override
    public void updatePauseAndNormalJob(JobInfoModel jobInfoModel) throws Exception {
	JobInfoModel xxlJobInfo = jobDao.queryJobById(jobInfoModel.getId());
	 String group = String.valueOf(xxlJobInfo.getJobGroup());
	 String name = String.valueOf(xxlJobInfo.getId());
	try {
	    boolean ret =true;
	    if(jobInfoModel.getState().equals("0")){
		ret= JobDynamicScheduler.pauseJob(name, group); // jobStatus
	    }else{
		ret = JobDynamicScheduler.resumeJob(name, group);
	    }
	    if(!ret){
		throw new Exception("停止或启用失败");
	    }
	    jobDao.updateJobState(jobInfoModel);
	} catch (SchedulerException e) {
	    e.printStackTrace();
	    throw new Exception("停止或启用失败");
	}
    }

    @Override
    public List<JobInfoModel> queryJobInfoModels(JobInfoModel jobInfoModel){
	return jobDao.queryJobs(jobInfoModel);
    }
    
    @Override
    public void executorJob(Long id) throws JobException {
	JobInfoModel jobInfoModel = jobDao.queryJobById(id);
        String group = String.valueOf(jobInfoModel.getJobGroup());
        String name = String.valueOf(jobInfoModel.getId());
        
	try {
		JobDynamicScheduler.triggerJob(name, group);
	} catch (SchedulerException e) {
		e.printStackTrace();
		throw new JobException("执行任务异常："+e.getMessage());
	}
        
    }
    
}
