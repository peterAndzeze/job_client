package com.job.core.handler.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @Title: JobHandler.java
 * @Package: com.job.core.biz.annotation
 * @Description: jobhandler注解
 * @author: sunwei
 * @date: 2017年5月10日 下午1:05:50
 * @version: V1.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface JobHandler {
    String value() default "";
}
