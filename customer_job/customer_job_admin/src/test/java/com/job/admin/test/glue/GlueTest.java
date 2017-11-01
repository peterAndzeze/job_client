package com.job.admin.test.glue;

import com.job.admin.framework.glue.GlueTypeEnum;

public class GlueTest {
    public static void main(String[] args) {
	for (GlueTypeEnum glueTypeEnum:GlueTypeEnum.values()) {
	    System.out.println(glueTypeEnum.getValue()+glueTypeEnum.getDesc());
	}
    }
    
}
