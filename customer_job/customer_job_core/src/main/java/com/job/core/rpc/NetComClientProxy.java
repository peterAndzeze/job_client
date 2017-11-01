package com.job.core.rpc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;

import com.job.core.rpc.codec.RpcRequest;
import com.job.core.rpc.codec.RpcResponse;
import com.job.core.rpc.jetty.client.JettyClient;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 
 * @Title: NetComClientProxy.java
 * @Package: com.job.core.rpc
 * @Description: 网络链接客户端
 * @author: sunwei
 * @date: 2017年5月15日 上午11:08:50
 * @version: V1.0
 */
public class NetComClientProxy implements FactoryBean<Object> {
    private static final Logger logger = LoggerFactory.getLogger(NetComClientProxy.class);

	// ---------------------- config ----------------------
	private Class<?> iface;
	private String serverAddress;
	private String accessToken;
	private JettyClient client = new JettyClient();
	public NetComClientProxy(Class<?> iface, String serverAddress, String accessToken) {
		this.iface = iface;
		this.serverAddress = serverAddress;
		this.accessToken = accessToken;
	}

	@Override
	public Object getObject() throws Exception {
		return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[] { iface },
		new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			    // request
			    RpcRequest request = new RpcRequest();
	                    request.setServerAddress(serverAddress);
	                    request.setCreateMillisTime(System.currentTimeMillis());
	                    request.setAccessToken(accessToken);
	                    request.setClassName(method.getDeclaringClass().getName());
	                    request.setMethodName(method.getName());
	                    request.setParameterTypes(method.getParameterTypes());
	                    request.setParameters(args);
	                    // send
	                    RpcResponse response = client.send(request);
	                    // valid response
	                    if (response == null) {
				logger.error(">>>>>>>>>>> xxl-rpc netty response not found.");
				throw new Exception(">>>>>>>>>>> xxl-rpc netty response not found.");
	                    }
	                    if (response.isError()) {
	                        throw new RuntimeException(response.getError());
	                    } else {
	                        return response.getResult();
	                    }
	                   
			}
		});
	}
	@Override
	public Class<?> getObjectType() {
		return iface;
	}
	@Override
	public boolean isSingleton() {
		return false;
	}

}
