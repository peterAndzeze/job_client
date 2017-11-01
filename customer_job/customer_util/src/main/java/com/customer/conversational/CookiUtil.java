package com.customer.conversational;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @Title: CookiUtil.java
 * @Package: com.job.admin.util
 * @Description: cooki 工具类
 * @author: sunwei
 * @date: 2017年5月19日 下午4:56:28
 * @version: V1.0
 */
public class CookiUtil {
    private static final int COOKI_MAX_AGE=60*60*2;//最大时间
    private static final String PATH="/";//路径
    /**
     * 
     * @Title: setCooki
     * @Description: 新增cooki
     * @author:sunwei
     * @createTime:2017年5月19日下午5:01:28
     * @param httpServletResponse
     * @param key key
     * @param value value值
     * @param ifRemeber
     */
    public static void setCooki(HttpServletResponse httpServletResponse,String key,String value,boolean ifRemeber){
	int age=COOKI_MAX_AGE;
	if(!ifRemeber){//记录过
	    age=-1;
	}
	Cookie cookie=new Cookie(key, value);
	cookie.setMaxAge(age);
	cookie.setPath(PATH);
	httpServletResponse.addCookie(cookie);
    }
    /**
     * 
     * @Title: saveCooki
     * @Description:
     * @author:sunwei
     * @createTime:2017年5月20日上午9:28:07
     * @param response
     * @param key
     * @param value
     * @param maxAge
     * @param path
     */
    private static void saveCooki(HttpServletResponse response,String key,String value,int maxAge,String path){
	Cookie cookie=new Cookie(key, value);
	cookie.setMaxAge(maxAge);
	cookie.setPath(path);
	response.addCookie(cookie);
    }
    /**
     * 
     * @Title: getValue
     * @Description:获取cooki值
     * @author:sunwei
     * @createTime:2017年5月20日上午9:29:43
     * @param request
     * @param key
     * @return
     */
    public static String getValue(HttpServletRequest request,String key){
	Cookie cookie=getCooki(request, key);
	if(null!=cookie){
	    return cookie.getValue();
	}
	return null;
    }
    /**
     * 
     * @Title: getCooki
     * @Description:得到cooki
     * @author:sunwei
     * @createTime:2017年5月20日上午9:30:56
     * @param request
     * @param key
     * @return
     */
    private static Cookie getCooki(HttpServletRequest request,String key){
	Cookie [] cookis=request.getCookies();
	if(null!=cookis){
	    for (Cookie cookie : cookis) {
		if(key.equals(cookie.getName())){
		    return cookie;
		}
	    }
	}
	return null;
    }
    
    /**
     * 
     * @Title: removeCooki
     * @Description:删除cooki
     * @author:sunwei
     * @createTime:2017年5月20日上午9:34:29
     * @param request
     * @param key
     */
    public static void removeCooki(HttpServletRequest request,HttpServletResponse response,String key){
	Cookie cookie=getCooki(request, key);
	if(null!=cookie){
	    saveCooki(response, key, "", 0,PATH);
	}
    }
    
}
