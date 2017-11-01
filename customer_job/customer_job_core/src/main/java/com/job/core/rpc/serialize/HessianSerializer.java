package com.job.core.rpc.serialize;

import com.caucho.hessian.client.HessianProxyFactory;
import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import com.caucho.hessian.io.HessianSerializerInput;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * 
 * @Title: HessianSerializer.java
 * @Package: com.job.core.rpc.serialize
 * @Description: 序列化？？？？？？？？？？？
 * @author: sunwei
 * @date: 2017年5月15日 上午11:20:12
 * @version: V1.0
 */
public class HessianSerializer {
    

    public static  <T> byte[] serialize(T obj) {
	ByteArrayOutputStream os = new ByteArrayOutputStream();
	HessianOutput ho = new HessianOutput(os);
	try {
	    ho.writeObject(obj);
	} catch (IOException e) {
	    throw new IllegalStateException(e.getMessage(), e);
	}
	return os.toByteArray();
    }

    public static  <T> Object deserialize(byte[] bytes, Class<T> clazz) {
	ByteArrayInputStream is = new ByteArrayInputStream(bytes);
	HessianInput hi = new HessianInput(is);
	try {
	    return hi.readObject();
	} catch (IOException e) {
	    throw new IllegalStateException(e.getMessage(), e);
	} finally {
	    try {
		if (null != hi)
		    hi.close();
	    } catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
    }

}
