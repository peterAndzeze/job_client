package com.customer.json;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
/**
 * 
 * @Title: JsonUtil.java
 * @Package: com.job.admin.util
 * @Description: 处理json数据工具类
 * @author: sunwei
 * @date: 2017年5月20日 下午1:20:48
 * @version: V1.0
 */
public class JsonUtil {
    /**
     * 
     * @Title: objectToJson
     * @Description:数据转为jsons
     * @author:sunwei
     * @createTime:2017年5月20日下午1:27:33
     * @param value
     */
    public static String objectToJson(Object value) {
	if (null == value) {
	    return "";
	}
	return JSON.toJSONString(value);
    }

    /**
     * 
     * @Title: objectToJsonExcludeProperty
     * @Description:转排除某个属性
     * @author:sunwei
     * @createTime:2017年5月20日下午1:39:48
     * @param object
     * @param properties
     *            属性
     * @return
     */
    public static String objectToJsonExcludeProperty(Object object,
	    String... properties) {
	JSONObject jsonObject = objectToJSONObject(object);
	if (null != jsonObject) {
	    if (null != properties) {
		for (String property : properties) {
		    jsonObject.remove(property);
		}
	    }
	}
	return jsonObject.toString();
    }

    /**
     * 
     * @Title: objectToJSONObject
     * @Description:返回jsonObject
     * @author:sunwei
     * @createTime:2017年5月20日下午1:44:39
     * @param object
     * @return
     */
    public static JSONObject objectToJSONObject(Object object) {
	if (null != object) {
	    String jsonStr = objectToJson(object);
	    return JSONObject.parseObject(jsonStr);
	}
	return null;
    }

    /**
     * 
     * @Title: objectToJSONOArr
     * @Description:转为json数组
     * @author:sunwei
     * @createTime:2017年5月20日下午1:54:29
     * @param object
     * @return
     */
    public static JSONArray objectToJSONOArr(Object object) {
	if (null != object) {
	    String jsonStr = objectToJson(object);
	    return JSONArray.parseArray(jsonStr);
	}
	return null;
    }

    /**
     * 
     * @Title: jsonToObject
     * @Description:json数据转为对象
     * @author:sunwei
     * @createTime:2017年5月20日下午2:02:07
     * @param jsonStr
     * @param className
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static Object jsonToObject(String jsonStr, Class className) {
	if (null != jsonStr) {
	    return JSON.parseObject(jsonStr, className);
	}
	return null;
    }
    /**
     * 
     * @Title: jsonToObject
     * @Description:json数据转为对象
     * @author:sunwei
     * @createTime:2017年5月20日下午2:02:07
     * @param jsonStr
     * @param className
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static Object jsonToList(String jsonStr, Class className) {
	if (null != jsonStr) {
	    return JSONArray.parseArray(jsonStr, className);
	}
	return null;
    }
    
    /**
     * 
     * @Title: jsonToMapObject
     * @Description:json 转为map<String,javaBean>
     * @author:sunwei
     * @createTime:2017年5月22日上午9:15:13
     * @param jsonStr
     * @param className map.class
     * @param javaBean javeBean.class
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static Object jsonToMapObject(String jsonStr, Class className,Class javaBean) {
	if (null != jsonStr) {
	    JSONObject jsonObject= JSONObject.parseObject(jsonStr);
	    Map<String, Object> map=(Map<String, Object>) JSONObject.toJavaObject(jsonObject, className);
	    for (Map.Entry<String, Object> entry:map.entrySet()) {
		if(null!=entry.getValue()){
		    Object object=jsonToObject(objectToJson(entry.getValue()), javaBean);
		    map.put(entry.getKey(), object);
		}
	    }
	    return map;
	}
	return null;
    }
    /**
     * 
     * @Title: jsonToMapList
     * @Description:
     * @author:sunwei
     * @createTime:2017年5月22日上午9:17:34
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static Object jsonToMapList(String jsonStr, Class className,Class javaBean){
	if (null != jsonStr) {
	    JSONObject jsonObject= JSONObject.parseObject(jsonStr);
	    Map<String, Object> map=(Map<String, Object>) JSONObject.toJavaObject(jsonObject, className);
	    for (Map.Entry<String, Object> entry:map.entrySet()) {
		if(null!=entry.getValue()){
		    List<Object> object=(List<Object>) jsonToList(objectToJson(entry.getValue()), javaBean);
		    map.put(entry.getKey(), object);
		}
	    }
	    return map;
	}
	return null;
    }
    
    
}
