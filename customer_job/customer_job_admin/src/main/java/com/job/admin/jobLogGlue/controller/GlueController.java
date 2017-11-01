
package com.job.admin.jobLogGlue.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.job.admin.framework.springmvc.BaseController;
import com.job.admin.jobLogGlue.model.JobLogGlue;
import com.job.admin.jobLogGlue.service.JobGlueService;
@Controller
@RequestMapping("/glue")
public class GlueController extends BaseController{
    @Resource
    private JobGlueService jobGlueService;
    /**
     * 
     * @Title: getGlue
     * @Description:获取信息
     * @author:sunwei
     * @createTime:2017年10月31日下午4:06:53
     * @param jobId
     * @return
     */
    @RequestMapping("glues")
    @ResponseBody
    public String getGlue(Long jobId){
	List<JobLogGlue> jobLogGlues=jobGlueService.getJobLogGlueByJobId(jobId);
	return jsonStrListData(jobLogGlues);
    }
    
    @Override
    public String getPath(HttpServletRequest request) {
	return null;
    }

}
