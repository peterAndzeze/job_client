package com.job.admin.test;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.customer.json.JsonUtil;
import com.job.admin.common.PageModel;
import com.job.admin.log.dao.LogDao;
import com.job.admin.log.model.JobLogModel;
import com.job.admin.log.service.LogService;
import com.job.admin.menu.util.DateUtil;
import com.job.admin.test.util.BaseJunit;

public class LogTest  extends BaseJunit{
    @Autowired
    private LogService logService;
    @Autowired
    private LogDao logDao;

    @Test
    public void getPageList(){
	PageModel pageModel=new PageModel();
	pageModel.setStart(0);
	pageModel.setLimit(10);
	pageModel.setCountSql("JobLogMapper.pageListCount");
	JobLogModel jobLogModel=new JobLogModel();
	jobLogModel.setJobGroup(1L);
	jobLogModel.setJobId(3L);
	logService.getPageList(pageModel, jobLogModel);
    }
    @Test
    public void load(){
//	JobLogModel jobLogModel=logDao.load(1111L);
//	System.out.println(jobLogModel.getId());
    }
    
    
    @Test
    public void save(){
	JobLogModel jobLogModel=new JobLogModel();
	jobLogModel.setJobId(3L);
	jobLogModel.setJobGroup(12L);
	System.out.println(jobLogModel.getId());
    }
    
    
    @Test
    public void triggerCountByHandleCode(){
//	int count=logDao.triggerCountByHandleCode(2);
//	System.out.println(count+"*********");
    }
    @Test
    public void triggerCountByDay() throws Exception{
//	Date from =DateUtil.str2Date("2013-10-12", DateUtil.DEFAULT_DATE_FORMAT);
//	Date to =DateUtil.str2Date("2018-10-12", DateUtil.DEFAULT_DATE_FORMAT);
//	List<Map<String, Object>> list=logDao.triggerCountByDay(from, to,2);
//	System.out.println(JsonUtil.objectToJson(list));
    }
    
}
