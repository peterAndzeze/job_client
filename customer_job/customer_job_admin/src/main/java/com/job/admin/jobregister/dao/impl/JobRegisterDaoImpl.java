package com.job.admin.jobregister.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.job.admin.framework.mybatis.MybatisDao;
import com.job.admin.jobregister.dao.JobRegisterDao;
import com.job.admin.jobregister.model.JobRegisterModel;
@Repository
public class JobRegisterDaoImpl extends MybatisDao<JobRegisterModel> implements JobRegisterDao{
    @Override
    public boolean deleteDead(int timeout) {
        return deleteModel("JobRegistryMapper.removeDead", createParamter("timeout", timeout));
    }

    @Override
    public List<JobRegisterModel> findAll(int timeout) {
        return queryModels("JobRegistryMapper.findAll", createParamter("timeout", timeout));
    }
    
    @Override
    public int updateRegistry(String registryGroup, String registryKey, String registryValue) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("registryGroup", registryGroup);
        params.put("registryKey", registryKey);
        params.put("registryValue", registryValue);
        return updateSqlReturn("JobRegistryMapper.registryUpdate", params);
    }

    @Override
    public boolean saveRegistry(String registryGroup, String registryKey, String registryValue) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("registryGroup", registryGroup);
        params.put("registryKey", registryKey);
        params.put("registryValue", registryValue);

        return saveModel("JobRegistryMapper.registrySave", params);
    }


}
