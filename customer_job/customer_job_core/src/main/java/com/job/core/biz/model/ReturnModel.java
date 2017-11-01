package com.job.core.biz.model;

import java.io.Serializable;

/**
 * 
 * @Title: ReturnModel.java
 * @Package: com.job.core.biz.model
 * @Description: TODO
 * @author: sunwei
 * @date: 2017年5月10日 下午1:02:35
 * @version: V1.0
 */
public class ReturnModel<T> implements Serializable {

    /**
     *
     * @Fields serialVersionUID : TODO
     */
    private static final long serialVersionUID = -7821769030734386472L;
    public static final int SUCCESS_CODE=200;//成功code
    public static final int FAILUE_CODE=500;//失败code
    
    public static final ReturnModel<String> SUCCESS =new ReturnModel<String>(null);
    public static final ReturnModel<String> FAILUE=new ReturnModel<String>(FAILUE_CODE, null);
    private int code;//执行响应code
    private String msg;//执行响应信息
    private T content;//执行响应内容
    
    public ReturnModel(int code,String msg){
	this.code=code;
	this.msg=msg;
    }
    
    public ReturnModel(T content){
	this.code=SUCCESS_CODE;
	this.content=content;
    }
    
    
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public T getContent() {
        return content;
    }
    public void setContent(T content) {
        this.content = content;
    }
    
    
    
    
    
    
    
    

}
