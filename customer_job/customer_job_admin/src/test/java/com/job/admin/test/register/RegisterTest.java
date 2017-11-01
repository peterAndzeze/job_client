package com.job.admin.test.register;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.job.admin.jobregister.model.JobRegisterModel;
import com.job.admin.jobregister.service.JobRegisterService;
import com.job.admin.test.util.BaseJunit;

public class RegisterTest extends BaseJunit{
    @Autowired
    private JobRegisterService jobRegisterService;
    @Test
    public void delete(){
	boolean  flag=jobRegisterService.deleteDead(1);
	System.out.println(flag);
    }
    
    
    @Test
    public void finaALl(){
	List<JobRegisterModel> jobRegisterModels=jobRegisterService.findAll(1);
	System.out.println(jobRegisterModels.size());
    }
    
    @Test
    public void update(){
	int flag=jobRegisterService.updateRegistry("ADMIN", "ADMIN", "10.128.78.140:8888");
	System.out.println(flag);
    }
}
