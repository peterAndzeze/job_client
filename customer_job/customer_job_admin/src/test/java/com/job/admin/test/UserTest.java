package com.job.admin.test;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.customer.json.JsonUtil;
import com.job.admin.test.util.BaseJunit;
import com.job.admin.user.model.UserModel;
import com.job.admin.user.service.UserService;

public class UserTest extends BaseJunit{
    @Resource
    private UserService userService;
    @Test
    public void getUser(){
	UserModel u=new UserModel();
	u.setUserName("章三");
	u.setUserPassword("11222");
	UserModel model=userService.getUserModel(u);
//	System.out.println(model.getSex());
    }
    
    public static void main(String[] args) {
	UserModel u=new UserModel();
	u.setUserName("章三");
	u.setUserPassword("11222");
	List<UserModel> list=new ArrayList<UserModel>();
	list.add(u);
	String str=JsonUtil.objectToJson(list);
	System.out.println(str);
    }
    
    
    
}
