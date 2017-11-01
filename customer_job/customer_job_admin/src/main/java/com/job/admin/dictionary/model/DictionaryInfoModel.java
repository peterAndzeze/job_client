package com.job.admin.dictionary.model;

import com.job.admin.common.BaseModel;
/**
 * 
 * @Title: DictionaryInfoModel.java
 * @Package: com.job.admin.dictionary.model
 * @Description: 字典项
 * @author: sunwei
 * @date: 2017年7月31日 下午8:20:21
 * @version: V1.0
 */
public class DictionaryInfoModel extends BaseModel {
    private String key;	        
    private String display;	    
    private String value;	    
    private String value1;	    
    private Long parentId;
    private String isLeaf;
    
    public String getIsLeaf() {
        return isLeaf;
    }
    public void setIsLeaf(String isLeaf) {
        this.isLeaf = isLeaf;
    }
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public String getDisplay() {
        return display;
    }
    public void setDisplay(String display) {
        this.display = display;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public String getValue1() {
        return value1;
    }
    public void setValue1(String value1) {
        this.value1 = value1;
    }
    public Long getParentId() {
        return parentId;
    }
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
    
}
