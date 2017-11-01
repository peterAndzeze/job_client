package com.job.admin.user.service;

import com.job.admin.user.model.UserModel;

/**
 * 
 * @Title: UserService.java
 * @Package: com.job.admin.user.service
 * @Description: 用户信息维护接口
 * @author: sunwei
 * @date: 2017年5月22日 上午10:20:26
 * @version: V1.0
 */
public interface UserService {
    /**
     * 
     * @Title: getUserModel
     * @Description: 查询用户信息（）
     * @author:sunwei
     * @createTime:2017年5月22日上午10:23:59
     * @param model 封装对象 组合各个参数查询
     * @return
     */
    public UserModel getUserModel(UserModel model);
}
