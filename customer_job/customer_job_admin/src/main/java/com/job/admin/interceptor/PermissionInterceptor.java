package com.job.admin.interceptor;

import java.math.BigInteger;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.customer.conversational.CookiUtil;
import com.job.admin.user.model.UserModel;
import com.job.admin.user.service.UserService;
import com.job.admin.user.service.impl.UserServiceImpl;

/**
 * 
 * @Title: PermissionInterceptor.java
 * @Package: com.job.admin.interceptor
 * @Description: 权限验证
 * @author: sunwei
 * @date: 2017年5月22日 上午10:12:42
 * @version: V1.0
 */
public class PermissionInterceptor extends HandlerInterceptorAdapter {
    private static final UserService userService=new UserServiceImpl();

    /**
     * 
     * @Title: login
     * @Description:用户登陆
     * @author:sunwei
     * @createTime:2017年5月22日上午11:46:08
     * @param request
     *            请求
     * @param userModel
     *            用户信息
     * @param ifRemeber
     *            是否记住用户
     */
    public static void login(HttpServletResponse response, UserModel userModel,boolean ifRemeber) {
	String loginKey = userModel.getUserName() + ","+ userModel.getUserPassword();
	String loginToken = new BigInteger(1, loginKey.getBytes()).toString(16);
	CookiUtil.setCooki(response, loginKey, loginToken, ifRemeber);
    }

    /**
     * 
     * @Title: ifLogin
     * @Description:验证用户是否登陆
     * @author:sunwei
     * @createTime:2017年5月22日上午11:40:25
     * @param request
     */
    public static boolean ifLogin(HttpServletRequest request, UserModel userModel) {
	if(null==userModel){
	    return false;
	}
	String loginKey = userModel.getUserName() + ","+ userModel.getUserPassword();
	String indentityInfo = CookiUtil.getValue(request, loginKey);
	String loginToken = new BigInteger(1, loginKey.getBytes()).toString(16);
	if (null != indentityInfo && indentityInfo.equals(loginToken)) {
	    return true;
	}
	return false;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {

	if (!(handler instanceof HandlerMethod)) {
	    return super.preHandle(request, response, handler);
	}
	Cookie[] cookies=request.getCookies();//拿到请求的cookis
	if(null==cookies){
	    response.sendRedirect(request.getContextPath() + "/");
	    return false;
	}
	return super.preHandle(request, response, handler);
    }

}
