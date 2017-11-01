package com.job.admin.jobregister.model;

import com.job.admin.common.BaseModel;

public class JobRegisterModel extends BaseModel{
    private String registryGroup;
    private String registryKey; 
    private String registryValue; 
    private String updateTime;
    public String getRegistryGroup() {
        return registryGroup;
    }
    public void setRegistryGroup(String registryGroup) {
        this.registryGroup = registryGroup;
    }
    public String getRegistryKey() {
        return registryKey;
    }
    public void setRegistryKey(String registryKey) {
        this.registryKey = registryKey;
    }
    public String getRegistryValue() {
        return registryValue;
    }
    public void setRegistryValue(String registryValue) {
        this.registryValue = registryValue;
    }
    public String getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
    
}
