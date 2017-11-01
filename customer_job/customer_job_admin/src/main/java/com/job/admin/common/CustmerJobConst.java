package com.job.admin.common;
/**
 * 
 * @Title: CustmerJobConst.java
 * @Package: com.job.admin.common
 * @Description: 常量类
 * @author: sunwei
 * @date: 2017年5月25日 下午4:26:25
 * @version: V1.0
 */
public final class CustmerJobConst {
    /**子节点**/
    public static final String IS_LEAF="0";
    /**非子节点***/
    public static final String IS_NOT_LEAF="1";
    public static final Integer TREE_NORMAL_LEV = 2;//一次取树的常规级数
    
    public static final Long PARENT_ID=10001L;
    /**执行成功**/
    public static final int SUCCESS_CODE = 200;
    /**执行失败***/
    public static final int FAIL_CODE = 500;
    /**handler 执行进行中状态0**/
    public static final int HANDLER_EXECUTORING_CODE=0;
    

}
