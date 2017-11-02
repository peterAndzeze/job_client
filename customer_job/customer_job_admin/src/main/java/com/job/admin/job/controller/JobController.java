package com.job.admin.job.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.customer.uuid.CoreUtil;
import com.job.admin.common.PageModel;
import com.job.admin.framework.springmvc.BaseController;
import com.job.admin.job.model.JobInfoModel;
import com.job.admin.job.service.JobService;
import com.job.admin.jobGroup.model.JobGroupModel;
import com.job.admin.jobGroup.service.JobGroupService;
import com.job.admin.jobLogGlue.dao.JobLogGlueDao;
import com.job.admin.jobLogGlue.model.JobLogGlue;

@Controller
@RequestMapping("/jobInfo")
public class JobController extends BaseController{
    @Resource
    private JobService jobService;
    @Resource
    private JobLogGlueDao jobLogGlueDao;
    @RequestMapping("/main")
    @Override
    public String getPath(HttpServletRequest request) {
	request.getServletContext().setAttribute("uuid",
		CoreUtil.genUUIDString());
	request.getServletContext().setAttribute("timeStr",
		System.currentTimeMillis());
	return "/page/job/main";
    }
    
    
    /**
     * 
     * @Title: saveOrUpdate
     * @Description:修改或者保存
     * @author:sunwei
     * @createTime:2017年6月12日下午4:54:16
     * @param request
     * @param response
     * @param jobInfoModel
     * @return
     */
    @RequestMapping("/saveOrUpdateJob")
    @ResponseBody
    public String saveOrUpdate(HttpServletRequest request,HttpServletResponse response,JobInfoModel jobInfoModel){
	try {
	    if(null==jobInfoModel.getId() || "".equals(jobInfoModel.getId())){//新增
		jobService.saveJobInfoModel(jobInfoModel);
	    }else{
		jobService.updateJobIndoModel(jobInfoModel);
	    }
	    return jsonStrAndState("", true, "操作成功");
	} catch (Exception e) {
	    return jsonStrAndState("", false, e.getMessage());
	}
    }
    /***
     * 
     * @Title: queryPageList
     * @Description:分页
     * @author:sunwei
     * @createTime:2017年6月12日下午5:29:23
     * @param pageModel
     * @param jobInfoModel
     * @return
     */
    @RequestMapping("/queryPageList")
    @ResponseBody
    public String queryPageList(PageModel pageModel,JobInfoModel jobInfoModel){
	PageModel model=jobService.queryPageList(pageModel, jobInfoModel);
	return jsonStrListData(model);
    }
    /**
     * 
     * @Title: queryJobById
     * @Description:明细
     * @author:sunwei
     * @createTime:2017年6月13日下午2:24:35
     * @return
     */
    @RequestMapping("/queryJobById")
    @ResponseBody
    public String queryJobById(Long id){
	try {
	    JobInfoModel infoModel=jobService.queryJobById(id);
	    return jsonStrData(infoModel);
	} catch (Exception e) {
	    e.printStackTrace();
	    return jsonStrAndState("", false, "查询异常！！");
	}
    }
    @RequestMapping("/deleteJobById")
    @ResponseBody
    public String deleteJobById(Long id){
	try {
	    jobService.deleteById(id);
	    return jsonStrAndState(null, true, "删除成功！");
	} catch (Exception e) {
	    e.printStackTrace();
	    return jsonStrAndState("", false, "删除异常！！");
	}
    }
    /**
     * 
     * @Title: updateJobState
     * @Description:暂停
     * @author:sunwei
     * @createTime:2017年8月23日下午1:18:40
     * @param jobInfoModel
     * @return
     */
    @RequestMapping("/updatePauseAndNormalJob")
    @ResponseBody
    public String updatePauseAndNormalJob(JobInfoModel jobInfoModel){
	try {
	    jobService.updatePauseAndNormalJob(jobInfoModel);
	    return jsonStrAndState(null, true, "修改成功！");
	} catch (Exception e) {
	    e.printStackTrace();
	    return jsonStrAndState("", false, "修改异常！！");
	}
    }
    
    @RequestMapping("queryJobDictionary")
    @ResponseBody
    public String queryJobDictionary(JobInfoModel jobInfoModel){
	try {
	    List<JobInfoModel> infoModels=jobService.queryJobInfoModels(jobInfoModel);
	    return jsonStrListData(infoModels);
	} catch (Exception e) {
	    e.printStackTrace();
	    return jsonStrAndState("", false, "查询异常！！");
	}
    }
    /**
     * 
     * @Title: executorJob
     * @Description:执行job
     * @author:sunwei
     * @createTime:2017年8月2日下午12:44:11
     * @param id
     * @return
     */
    @RequestMapping("executorJob")
    @ResponseBody
    public String executorJob(@RequestParam(value="id",required=false) Long id){
	try {
	    jobService.executorJob(id);
	    return jsonStrAndState(null, true, "执行成功！");
	} catch (Exception e) {
	    e.printStackTrace();
	    return jsonStrAndState(null, false, "执行失败！");
	}
    }
    
    @RequestMapping("/saveGlue")
    @ResponseBody
    public String saveGlue(HttpServletRequest request,HttpServletResponse response,JobInfoModel jobInfoModel){
	try {
	 // log old code
	    JobInfoModel model= jobService.queryJobById(jobInfoModel.getId());
	    model.setGlueSource(jobInfoModel.getGlueSource());
	    jobService.updateJobIndoModel(model);
	    JobLogGlue jobLogGlue = new JobLogGlue();
	    jobLogGlue.setJobId(Integer.valueOf(model.getId().toString()));
	    jobLogGlue.setGlueType(model.getGlueType());
	    jobLogGlue.setGlueSource(model.getGlueSource());
	    jobLogGlue.setGlueRemark(jobInfoModel.getGlueRemark()==null?model.getGlueRemark():jobInfoModel.getGlueRemark());
	    jobLogGlueDao.save(jobLogGlue);
	    // remove code backup more than 30
	    jobLogGlueDao.removeOld(model.getId(), 30);
	    return jsonStrAndState("", true, "操作成功");
	} catch (Exception e) {
	    return jsonStrAndState("", false, e.getMessage());
	}
    }
}

