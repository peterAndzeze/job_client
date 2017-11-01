package com.job.admin.framework.glue;


/**
 * 
 * @Title: GlueTypeEnum.java
 * @Package: com.job.admin.framework.glue
 * @Description: 运行模式枚举
 * @author: sunwei
 * @date: 2017年6月14日 下午5:30:46
 * @version: V1.0
 */
public enum GlueTypeEnum {
    BEAN("BEAN模式","0"),
    GLUE_GROOVY("GLUE模式(Java)","1"),
    GLUE_SHELL("GLUE模式(Shell)","2"),
    GLUE_PYTHON("GLUE模式(Python)","3");
    private String desc;
    private String value;
    
    private GlueTypeEnum(String desc,String value) {
        this.desc = desc;
        this.value=value;
    }
    public String getDesc() {
        return desc;
    }

    public String getValue() {
        return value;
    }
    
    private GlueTypeEnum []  getGlueTypeEnum(){
	return GlueTypeEnum.values();
    }
    /**
     * 
     * @Title: getEumValue
     * @Description:
     * @author:sunwei
     * @createTime:2017年6月19日下午1:09:37
     * @param desc
     * @return
     */
    public String getEumValue(String desc){
	for(GlueTypeEnum glueTypeEnum: getGlueTypeEnum()){
	    if(glueTypeEnum.getDesc().equals(desc)){
		return glueTypeEnum.getValue();
	    }
	}
	return null;
    } 
   
    
}
