package com.customer.uuid;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.UUID;

public class CoreUtil {
	
	public static final int LINUX = 1;
	
	public static final int WINDOWS = 2;
	/**
	 * 无参构造方法
	 */
	private CoreUtil(){}
	
	/**
	 * 生成排序号
	 * @return 排序号
	 */
	public static Long generateSortIdx(){
		return System.currentTimeMillis();
	}
	/**
	 * 生成当前日间戳
	 * @return 当前日间戳
	 */
	public static Timestamp generateTimestamp(){
		return new java.sql.Timestamp(System.currentTimeMillis());
	}
	/**
	 * 生成字符串型的UUID
	 * @return 字符串型的UUID
	 */
	public static String genUUIDString(){
		return UUID.randomUUID().toString();
	}
	
	/**
	 * @author zhaodekun
	 * 获取系统类型
	 * @return LINUX = 1 & WINDOWS = 2 
	 */
	public static int getServerOSType(){
		int resultCode = 0;
		String osTag = System.getProperty("os.name");
		if(osTag.indexOf("Linux")!=-1||osTag.indexOf("Mac")!=-1){
			resultCode = LINUX;
		}else{
			resultCode = WINDOWS;
		}
		return resultCode ; 
	}
	
	/**
	 * @author dekunzhao
	 * 获取系统当前用户名
	 * @return 系统当前用户名
	 */
	public static String getServerUser(){
		return System.getProperty("user.name");
	}
	
	/**
	 * @author dekunzhao
	 * 获取Local IP
	 * @return IPAdress
	 */
	public static String getLocalIP(){  
		InetAddress addr = null;  
        try {  
            addr = InetAddress.getLocalHost();  
        } catch (UnknownHostException e) {  
            e.printStackTrace();  
                        return null;  
        }  
        byte[] ipAddr = addr.getAddress();  
        String ipAddrStr = "";  
        for (int i = 0; i < ipAddr.length; i++) {  
            if (i > 0) {  
                ipAddrStr += ".";  
            }  
            ipAddrStr += ipAddr[i] & 0xFF;  
        }  
        //System.out.println(ipAddrStr);  
        return ipAddrStr;  
	}
}