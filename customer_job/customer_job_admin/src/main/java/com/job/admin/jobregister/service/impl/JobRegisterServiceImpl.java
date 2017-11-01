package com.job.admin.jobregister.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.job.admin.jobregister.dao.JobRegisterDao;
import com.job.admin.jobregister.model.JobRegisterModel;
import com.job.admin.jobregister.service.JobRegisterService;
@Service
public class JobRegisterServiceImpl implements JobRegisterService {
    @Autowired
    private JobRegisterDao jobRegisterDao;
    
    @Override
    public boolean deleteDead(int timeout) {
	return jobRegisterDao.deleteDead(timeout);
    }

    @Override
    public List<JobRegisterModel> findAll(int timeout) {
	return jobRegisterDao.findAll(timeout);
    }
    @Transactional(propagation=Propagation.REQUIRES_NEW,readOnly=false,rollbackFor=RuntimeException.class)
    @Override
    public int updateRegistry(String registryGroup, String registryKey,
	    String registryValue) {
	int flag= jobRegisterDao.updateRegistry(registryGroup, registryKey, registryValue);
//	int i=1/0;
	return flag;
    }

    @Override
    public boolean saveRegistry(String registryGroup, String registryKey,
	    String registryValue) {
	return jobRegisterDao.saveRegistry(registryGroup, registryKey, registryValue);
    }

}
