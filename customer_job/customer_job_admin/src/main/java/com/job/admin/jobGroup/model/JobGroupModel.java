package com.job.admin.jobGroup.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.job.admin.common.BaseModel;
/**
 * 
 * @Title: JobGroupModel.java
 * @Package: com.job.admin.job.model
 * @Description: 执行器信息
 * @author: sunwei
 * @date: 2017年6月2日 下午2:05:09
 * @version: V1.0
 */
public class JobGroupModel extends BaseModel {
    private String appName;
    private String title;
    private Integer order;
    private Integer addressType;
    private String addressList;
    // registry list
    private List<String> registryList;  // 执行器地址列表(系统注册)
    
    public List<String> getRegistryList() {
	if (StringUtils.isNotBlank(addressList)) {
	    registryList = new ArrayList<String>(Arrays.asList(addressList.split(",")));
	}
	return registryList;
    }
    public void setRegistryList(List<String> registryList) {
        this.registryList = registryList;
    }
    public String getAppName() {
        return appName;
    }
    public void setAppName(String appName) {
        this.appName = appName;
    }
   
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Integer getOrder() {
        return order;
    }
    public void setOrder(Integer order) {
        this.order = order;
    }
    public Integer getAddressType() {
        return addressType;
    }
    public void setAddressType(Integer addressType) {
        this.addressType = addressType;
    }
    public String getAddressList() {
        return addressList;
    }
    public void setAddressList(String addressList) {
        this.addressList = addressList;
    }
    
}
