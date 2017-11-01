package com.job.admin.common;

import java.util.Date;

/**
 * 
 * @Title: BaseModel.java
 * @Package: com.job.admin.common
 * @Description: 基础工具类
 * @author: sunwei
 * @date: 2017年5月22日 上午10:16:43
 * @version: V1.0
 */
public class BaseModel {
    private Long id;
    private Date createTime;
    private Date modifyTime;
    private String creator;
    private String operator;
    private String remark;
    private String state;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Date getModifyTime() {
        return modifyTime;
    }
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
    public String getCreator() {
        return creator;
    }
    public void setCreator(String creator) {
        this.creator = creator;
    }
    public String getOperator() {
        return operator;
    }
    public void setOperator(String operator) {
        this.operator = operator;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
}
