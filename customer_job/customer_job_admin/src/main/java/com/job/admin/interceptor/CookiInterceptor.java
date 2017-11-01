package com.job.admin.interceptor;

import java.util.HashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class CookiInterceptor extends HandlerInterceptorAdapter {
    @Override
    public void postHandle(HttpServletRequest request,HttpServletResponse response, Object handler,ModelAndView modelAndView) throws Exception {
	if (modelAndView != null && ArrayUtils.isNotEmpty(request.getCookies())) {
	    HashMap<String, Cookie> cookieMap = new HashMap<String, Cookie>();
	    for (Cookie ck : request.getCookies()) {
		cookieMap.put(ck.getName(), ck);
	    }
	    modelAndView.addObject("cookieMap", cookieMap);
	}
	super.postHandle(request, response, handler, modelAndView);
    }
}
