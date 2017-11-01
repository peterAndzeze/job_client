package com.job.core.rpc;

import com.job.core.biz.model.ReturnModel;
import com.job.core.rpc.codec.RpcRequest;
import com.job.core.rpc.codec.RpcResponse;
import com.job.core.rpc.jetty.server.JettyServer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.reflect.FastClass;
import org.springframework.cglib.reflect.FastMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @Title: NetComServerFactory.java
 * @Package: com.job.core.rpc
 * @Description: 初始化服务
 * @author: sunwei
 * @date: 2017年8月6日 下午9:24:59
 * @version: V1.0
 */
public class NetComServerFactory  {
    private static final Logger logger = LoggerFactory.getLogger(NetComServerFactory.class);

	// ---------------------- server start ----------------------
	JettyServer server = new JettyServer();
	public void start(int port, String ip, String appName) throws Exception {
		server.start(port, ip, appName);
	}

	// ---------------------- server destroy ----------------------
	public void destroy(){
		server.destroy();
	}

	// ---------------------- server instance ----------------------
	/**
	 * init local rpc service map
	 */
	private static Map<String, Object> serviceMap = new HashMap<String, Object>();
	private static String accessToken;
	public static void putService(Class<?> iface, Object serviceBean){
		serviceMap.put(iface.getName(), serviceBean);
	}
	public static void setAccessToken(String accessToken) {
		NetComServerFactory.accessToken = accessToken;
	}
	public static RpcResponse invokeService(RpcRequest request, Object serviceBean) {
		if (serviceBean==null) {
			serviceBean = serviceMap.get(request.getClassName());
		}
		if (serviceBean == null) {
			// TODO
		}

		RpcResponse response = new RpcResponse();

		if (System.currentTimeMillis() - request.getCreateMillisTime() > 180000) {
			response.setResult(new ReturnModel<String>(ReturnModel.FAILUE_CODE, "The timestamp difference between admin and executor exceeds the limit."));
			return response;
		}
		if (accessToken!=null && accessToken.trim().length()>0 && !accessToken.trim().equals(request.getAccessToken())) {
			response.setResult(new ReturnModel<String>(ReturnModel.FAILUE_CODE, "The access token[" + request.getAccessToken() + "] is wrong."));
			return response;
		}

		try {
			Class<?> serviceClass = serviceBean.getClass();
			String methodName = request.getMethodName();
			Class<?>[] parameterTypes = request.getParameterTypes();
			Object[] parameters = request.getParameters();

			FastClass serviceFastClass = FastClass.create(serviceClass);
			FastMethod serviceFastMethod = serviceFastClass.getMethod(methodName, parameterTypes);

			Object result = serviceFastMethod.invoke(serviceBean, parameters);

			response.setResult(result);
		} catch (Throwable t) {
			t.printStackTrace();
			response.setError(t.getMessage());
		}

		return response;
	}
}
