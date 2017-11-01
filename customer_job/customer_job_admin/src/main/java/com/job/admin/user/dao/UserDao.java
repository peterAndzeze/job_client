package com.job.admin.user.dao;

import com.job.admin.user.model.UserModel;

/**
 * 
 * @Title: UserDao.java
 * @Package: com.job.admin.user.dao
 * @Description: 数据库交互
 * @author: sunwei
 * @date: 2017年5月22日 上午10:55:04
 * @version: V1.0
 */
public interface UserDao {
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
