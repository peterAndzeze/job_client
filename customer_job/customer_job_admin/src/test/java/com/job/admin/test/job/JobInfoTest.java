package com.job.admin.test.job;

import javax.annotation.Resource;

import org.junit.Test;

import com.job.admin.common.PageModel;
import com.job.admin.job.model.JobInfoModel;
import com.job.admin.job.service.JobService;
import com.job.admin.test.util.BaseJunit;

public class JobInfoTest extends BaseJunit {
    @Resource
    private  JobService jobService;
    @Test
    public void page(){
	PageModel pageModel=new PageModel();
	pageModel.setCountSql("JobInfoMapper.pageListCount");
	pageModel.setLimit(10);
	pageModel.setStart(0);
	JobInfoModel jobInfoModel=new JobInfoModel();
	pageModel =jobService.queryPageList(pageModel, jobInfoModel);
	System.out.println(pageModel.getRecords().size());
    }
}
