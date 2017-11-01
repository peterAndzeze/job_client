package com.job.admin.log.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.customer.uuid.CoreUtil;
import com.job.admin.common.PageModel;
import com.job.admin.framework.springmvc.BaseController;
import com.job.admin.log.model.JobLogModel;
import com.job.admin.log.service.LogService;
@Controller
@RequestMapping("/logInfo")
public class LogController extends BaseController {
    static{
	System.out.println("到底有没有");
    }
    @Resource
    private LogService logService;
    @RequestMapping("/main")
    @Override
    public String getPath(HttpServletRequest request) {
	request.getServletContext().setAttribute("uuid",
		CoreUtil.genUUIDString());
	request.getServletContext().setAttribute("timeStr",
		System.currentTimeMillis());
	return "/page/log/main";
    }
    /**
     * 
     * @Title: getLogPageList
     * @Description:
     * @author:sunwei
     * @createTime:2017年7月31日下午4:21:36
     * @param request
     * @param response
     * @param pageModel
     * @param jobLogModel
     * @return
     */
    @RequestMapping(value="/logModels")
    @ResponseBody
    public String getLogPageList(JobLogModel jobLogModel,PageModel pageModel){
	try {
	    PageModel model=logService.getPageList(pageModel, jobLogModel);
	    return jsonStrData(model);
	} catch (Exception e) {
	    e.printStackTrace();
	    return jsonStrAndState(null, false, "查询异常！");
	}
    }
   
}

