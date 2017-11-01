package com.job.admin.controller;
import java.io.IOException;
import java.io.OutputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.job.admin.controller.annotation.PermessionLimit;
import com.job.admin.framework.springmvc.BaseController;
import com.job.admin.user.model.UserModel;
import com.job.admin.user.service.UserService;
import com.job.core.biz.AdminBiz;
import com.job.core.rpc.NetComServerFactory;
import com.job.core.rpc.codec.RpcRequest;
import com.job.core.rpc.codec.RpcResponse;
import com.job.core.rpc.serialize.HessianSerializer;
import com.job.core.util.HttpClientUtil;
/**
 * 
 * @Title: LoginController.java
 * @Package: com.job.admin.controller
 * @Description: 首页收录信息
 * @author: sunwei
 * @date: 2017年5月20日 下午1:10:58
 * @version: V1.0
 */
@Controller("/")
public class IndexController extends BaseController {
    private Logger logger=LoggerFactory.getLogger(IndexController.class);
    @Resource
    private UserService userService;
    @RequestMapping("/login")
    public String login(HttpServletRequest request,HttpServletResponse response,UserModel model){
	return "login/login";
    }
    @RequestMapping("/toLogin")
    @ResponseBody
    public String toLogin(HttpServletRequest request,HttpServletResponse response,UserModel model){
	UserModel userModel=userService.getUserModel(model);
	if(null==userModel || StringUtils.isEmpty(model.getUserName()) || StringUtils.isEmpty(model.getUserPassword())){
	    return jsonStrAndState(null, false, "用户名或密码错误");
	}
//	if(!PermissionInterceptor.ifLogin(request, userModel)){//如果没有登陆过
//	    boolean ifRemeber=false;
//	    if(model.isIfRemeber()){//记住登陆
//		ifRemeber=true;
//	    }
//	    PermissionInterceptor.login(response, userModel, ifRemeber);
//	}
	return  jsonStrAndState("page/main.jsp", true, "登陆成功");
    }
    @RequestMapping("/")
    @Override
    public String getPath(HttpServletRequest request) {
	return "index";
    }
    
    @RequestMapping(value="api",method={RequestMethod.POST,RequestMethod.GET})
    @PermessionLimit(limit=false)
    public void api(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // invoke
        RpcResponse rpcResponse = doInvoke(request);

        // serialize response
        byte[] responseBytes = HessianSerializer.serialize(rpcResponse);

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        //baseRequest.setHandled(true);

        OutputStream out = response.getOutputStream();
        out.write(responseBytes);
        out.flush();
    }
    private RpcResponse doInvoke(HttpServletRequest request) {
        try {
            // deserialize request
            byte[] requestBytes = HttpClientUtil.readBytes(request);
            if (requestBytes == null || requestBytes.length==0) {
                RpcResponse rpcResponse = new RpcResponse();
                rpcResponse.setError("RpcRequest byte[] is null");
                return rpcResponse;
            }
            RpcRequest rpcRequest = (RpcRequest) HessianSerializer.deserialize(requestBytes, RpcRequest.class);

            // invoke
            RpcResponse rpcResponse = NetComServerFactory.invokeService(rpcRequest, null);
            return rpcResponse;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);

            RpcResponse rpcResponse = new RpcResponse();
            rpcResponse.setError("Server-error:" + e.getMessage());
            return rpcResponse;
        }
    }
}
