package com.job.core.biz.model;

import java.io.Serializable;

/**
 * 
 * @Title: RegistryParam.java
 * @Package: com.job.core.biz.model
 * @Description: 注册model
 * @author: sunwei
 * @date: 2017年8月16日 下午2:59:55
 * @version: V1.0
 */
public class RegistryParam implements Serializable {
    private static final long serialVersionUID = 42L;

    private String registGroup;
    private String registryKey;
    private String registryValue;

    public RegistryParam(){}
    public RegistryParam(String registGroup, String registryKey, String registryValue) {
        this.registGroup = registGroup;
        this.registryKey = registryKey;
        this.registryValue = registryValue;
    }

    public String getRegistGroup() {
        return registGroup;
    }

    public void setRegistGroup(String registGroup) {
        this.registGroup = registGroup;
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

    @Override
    public String toString() {
        return "RegistryParam{" +
                "registGroup='" + registGroup + '\'' +
                ", registryKey='" + registryKey + '\'' +
                ", registryValue='" + registryValue + '\'' +
                '}';
    }
}
