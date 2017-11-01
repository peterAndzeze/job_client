package com.job.admin.user.model;

import com.job.admin.common.BaseModel;
/**
 * 
 * @Title: UserModel.java
 * @Package: com.job.admin.user.model
 * @Description: 用户
 * @author: sunwei
 * @date: 2017年5月22日 上午10:18:30
 * @version: V1.0
 */
public class UserModel extends BaseModel{
    private String userName;
    private String userPassword;
    private String sex;
    private String phoneNumber;
    private boolean ifRemeber;
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserPassword() {
        return userPassword;
    }
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public boolean isIfRemeber() {
        return ifRemeber;
    }
    public void setIfRemeber(boolean ifRemeber) {
        this.ifRemeber = ifRemeber;
    }
    
}
