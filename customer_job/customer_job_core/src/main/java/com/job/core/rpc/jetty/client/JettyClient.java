package com.job.core.rpc.jetty.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.customer.json.JsonUtil;
import com.job.core.rpc.codec.RpcRequest;
import com.job.core.rpc.codec.RpcResponse;
import com.job.core.rpc.serialize.HessianSerializer;
import com.job.core.util.HttpClientUtil;

/**
 * 
 * @Title: JettyClient.java
 * @Package: com.job.core.rpc.jetty.client
 * @Description: jetty客户端
 * @author: sunwei
 * @date: 2017年5月15日 上午11:11:10
 * @version: V1.0
 */
public class JettyClient {
    private static Logger logger = LoggerFactory.getLogger(JettyClient.class);

    public RpcResponse send(RpcRequest request) throws Exception {
	try {
	    // serialize request
	    byte[] requestBytes = HessianSerializer.serialize(request);

	    // reqURL
	    String reqURL = request.getServerAddress();
	    if (reqURL != null && reqURL.toLowerCase().indexOf("http") == -1) {
		reqURL = "http://" + request.getServerAddress() + "/"; // IP:PORT,// need  // parse// to url
	    }
	    // remote invoke
	    byte[] responseBytes = HttpClientUtil.postRequest(reqURL,requestBytes);
	    if (responseBytes == null || responseBytes.length == 0) {
		RpcResponse rpcResponse = new RpcResponse();
		rpcResponse.setError("RpcResponse byte[] is null");
		return rpcResponse;
	    }
	    // deserialize response
	    RpcResponse response=(RpcResponse)HessianSerializer.deserialize(responseBytes, RpcResponse.class);
//	    String responseStr=JsonUtil.objectToJson(new String(requestBytes));
//	    RpcResponse response=(RpcResponse) JsonUtil.jsonToObject(responseStr, RpcResponse.class);
	     return response;
	} catch (Exception e) {
	    logger.error(e.getMessage(), e);

	    RpcResponse rpcResponse = new RpcResponse();
	    rpcResponse.setError("Client-error:" + e.getMessage());
	    return rpcResponse;
	}
    }

}
