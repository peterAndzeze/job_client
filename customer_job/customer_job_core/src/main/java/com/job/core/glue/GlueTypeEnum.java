package com.job.core.glue;
/**
 * 
 * @Title: GlueTypeEnum.java
 * @Package: com.job.core.glue
 * @Description: 程序执行运行方式枚举
 * @author: sunwei
 * @date: 2017年5月15日 下午3:10:34
 * @version: V1.0
 */
public enum GlueTypeEnum {

    BEAN("BEAN模式"),
    GLUE_GROOVY("GLUE模式(JAVA)"),
    GLUE_SHELL("GLUE模式(Shell)"),
    GLUE_PYTHON("GLUE模式(Python)");

    private String desc;
    private GlueTypeEnum(String desc) {
        this.desc = desc;
    }
    public String getDesc() {
        return desc;
    }

    public static GlueTypeEnum match(String name){
        for (GlueTypeEnum item: GlueTypeEnum.values()) {
            if (item.name().equals(name)) {
                return item;
            }
        }
        return null;
    }
}