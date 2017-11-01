package com.job.admin.user.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.job.admin.user.dao.UserDao;
import com.job.admin.user.model.UserModel;
import com.job.admin.user.service.UserService;
/**
 * 
 * @Title: UserServiceImpl.java
 * @Package: com.job.admin.user.service.impl
 * @Description: 用户信息
 * @author: sunwei
 * @date: 2017年5月22日 上午10:25:14
 * @version: V1.0
 */
@Service
public class UserServiceImpl implements UserService{
    static{
   	System.out.println("service层初始化*****");
       }
    @Resource
    private UserDao userDao;
    /**
     * 
     * @Title: getUserModel
     * @Description:查询用户信息
     * @author:sunwei
     * @createTime:2017年5月22日上午10:25:30
     * @see com.job.admin.user.service.UserService#getUserModel(com.job.admin.user.model.UserModel)
     * @param model
     * @return
     */
    @Override
    public UserModel getUserModel(UserModel model) {
	return userDao.getUserModel(model);
    }

}
