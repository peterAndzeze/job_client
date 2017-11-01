package com.job.admin.jobGroup.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.customer.uuid.CoreUtil;
import com.job.admin.common.PageModel;
import com.job.admin.framework.springmvc.BaseController;
import com.job.admin.jobGroup.model.JobGroupModel;
import com.job.admin.jobGroup.service.JobGroupService;
@Controller
@RequestMapping("/jobGroup")
public class JobGroupController extends BaseController {
    @Resource
    private JobGroupService jobGroupService;
    
    @RequestMapping("/main")
    @Override
    public String getPath(HttpServletRequest request) {
	request.getServletContext().setAttribute("uuid",
		CoreUtil.genUUIDString());
	request.getServletContext().setAttribute("timeStr",
		System.currentTimeMillis());
	return "page/jobgroup/main";
    }
    /**
     * 
     * @Title: queryJobGroup
     * @Description:分页数据
     * @author:sunwei
     * @createTime:2017年6月7日下午3:28:32
     * @param request
     * @param response
     * @param jobGroupModel
     * @return
     */
    @RequestMapping("/queryJobGroup")
    @ResponseBody
    public String queryJobGroup(HttpServletRequest request,HttpServletResponse response,JobGroupModel jobGroupModel,PageModel pageModel){
	PageModel model=jobGroupService.queryPageList(pageModel, jobGroupModel);
	return jsonStrData(model);
    }
    /**
     * 
     * @Title: queryJobGoupDetail
     * @Description:查询明细
     * @author:sunwei
     * @createTime:2017年6月8日下午3:14:56
     * @param jobGroupModel
     * @return
     */
    @RequestMapping("/queryJobGoupDetail")
    @ResponseBody
    public String queryJobGoupDetail(JobGroupModel jobGroupModel){
	JobGroupModel model=jobGroupService.queryJobGoupDetail(jobGroupModel);
	return jsonStrData(model);
    }
    /**
     * 
     * @Title: saveOrUpdate
     * @Description:保存或者修改
     * @author:sunwei
     * @createTime:2017年6月9日下午3:46:07
     * @param jobGroupModel
     * @return
     */
    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public String saveOrUpdate(HttpServletRequest request,@ModelAttribute("jobGroupModel")JobGroupModel jobGroupModel){
	try {
	    if(null==jobGroupModel.getId()){//为空
	        jobGroupService.saveJobGroup(jobGroupModel);
	    }else{
	        jobGroupService.updateJobGroup(jobGroupModel);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	    return jsonStrAndState(null, false, e.getMessage());
	}
	return jsonStrAndState(null, true, "操作成功");
    }
    /**
     * 
     * @Title: deleteJobGroup
     * @Description:删除
     * @author:sunwei
     * @createTime:2017年6月9日下午4:19:29
     * @param jobGroupModel
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public String deleteJobGroup(JobGroupModel jobGroupModel){
	try {
	    jobGroupService.deleteJobGroup(jobGroupModel);
	    return jsonStrAndState(null, true, "操作成功");
	} catch (Exception e) {
	    e.printStackTrace();
	    return jsonStrAndState(null, false, e.getMessage());
	}
    }
    @RequestMapping("/jobGroupModels")
    @ResponseBody
    public String jobGroupModels(JobGroupModel jobGroupModel){
	List<JobGroupModel> groupModels=jobGroupService.jobGroupModels(jobGroupModel);
	return jsonStrListData(groupModels);
    }
}
