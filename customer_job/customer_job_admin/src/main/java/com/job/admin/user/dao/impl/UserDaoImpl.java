package com.job.admin.user.dao.impl;

import org.springframework.stereotype.Repository;

import com.job.admin.framework.mybatis.MybatisDao;
import com.job.admin.user.dao.UserDao;
import com.job.admin.user.model.UserModel;
/**
 * 
 * @Title: UserDaoImpl.java
 * @Package: com.job.admin.user.dao.impl
 * @Description: 用户
 * @author: sunwei
 * @date: 2017年5月22日 上午11:11:29
 * @version: V1.0
 */
@Repository("userDao")
public class UserDaoImpl extends MybatisDao<UserModel> implements UserDao{
    @Override
    public UserModel getUserModel(UserModel model) {
	return queryModel("UserModelMapper.getUserModelByNameAndPass", model);
    }

}
