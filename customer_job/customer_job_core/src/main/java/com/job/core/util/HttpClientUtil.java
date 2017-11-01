package com.job.core.util;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * @Title: HttpClientUtil.java
 * @Package: com.job.core.util
 * @Description: 客户端请求工具
 * @author: sunwei
 * @date: 2017年5月10日 上午9:26:18
 * @version: V1.0
 */
public class HttpClientUtil {
    private static Logger logger=LoggerFactory.getLogger(HttpClientUtil.class);
    /**
     * 
     * @Title: postRequest
     * @Description: post请求
     * @author:sunwei
     * @createTime:2017年5月10日上午9:40:49
     * @param requestUrl 请求地址
     * @param data 请求数据
     * @return 返回信息
     */
    public static byte[] postRequest(String requestUrl,byte [] data){
	byte [] responsebytes=null;
	//创建http post请求
	HttpPost httpPost=new HttpPost(requestUrl);
	//建立客户端
	CloseableHttpClient httpClient = HttpClients.custom().disableAutomaticRetries().build();	// disable retry
	try {
        	//设置请求基本要求 超时时间
        	RequestConfig requestConfig= RequestConfig.custom()
        		.setConnectionRequestTimeout(10000)
                        .setSocketTimeout(10000)
                        .setConnectTimeout(10000)
                        .build();
        	httpPost.setConfig(requestConfig);
        	if(null!=data){
        	    httpPost.setEntity(new ByteArrayEntity(data,ContentType.DEFAULT_BINARY));

        	}
        	
        	HttpResponse response=httpClient.execute(httpPost);
        	HttpEntity responseEntity=response.getEntity();
        	if(null!=responseEntity){
        	    responsebytes=EntityUtils.toByteArray(responseEntity);
        	    EntityUtils.consume(responseEntity);
        	}
	} catch (IOException e) {
	    logger.error("post请求异常：",e);
	    e.printStackTrace();
	}finally{
	    httpPost.releaseConnection();
	    try {
		httpClient.close();
	    } catch (IOException e) {
		logger.error("post请求关闭信息httpClient异常："+e.getMessage());
		e.printStackTrace();
	    }
	}
	return responsebytes;
    }
    /**
     * 
     * @Title: requestRequest
     * @Description:处理request请求
     * @author:sunwei
     * @createTime:2017年5月10日上午9:55:49
     * @param url
     * @param data
     * @return
     * @throws IOException 
     */
    public static byte[] readBytes(HttpServletRequest request) throws IOException{
	request.setCharacterEncoding("UTF-8");
	InputStream inputStream=request.getInputStream();
	int contentLen=request.getContentLength();
	if (contentLen > 0) {
		int readLen = 0;
		int readLengthThisTime = 0;
		byte[] message = new byte[contentLen];
		try {
			while (readLen != contentLen) {
				readLengthThisTime = inputStream.read(message, readLen, contentLen - readLen);
				if (readLengthThisTime == -1) {
					break;
				}
				readLen += readLengthThisTime;
			}
			return message;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
	}
	return new byte[] {};
    }
    
    
}
