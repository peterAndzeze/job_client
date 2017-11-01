package com.job.core.glue;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import javax.annotation.Resource;

import groovy.lang.GroovyClassLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.AnnotationUtils;

import com.job.core.executor.JobExecutor;
import com.job.core.handler.BaseJobHandler;
/**
 * 
 * @Title: GlueFactory.java
 * @Package: com.job.core.glue
 * @Description: 运行环境工厂
 * @author: sunwei
 * @date: 2017年5月15日 下午3:14:57
 * @version: V1.0
 */
public class GlueFactory {
    private static Logger logger=LoggerFactory.getLogger(GlueFactory.class);
    private GroovyClassLoader groovyClassLoader=new GroovyClassLoader();
    private static GlueFactory glueFactory=new GlueFactory(); 
    public static GlueFactory getGlueFactory(){
	return glueFactory;
    }
    /**
     * 
     * @Title: injectService
     * @Description: inject action of spring  spring注入
     * @author:sunwei
     * @createTime:2017年5月15日下午3:20:37
     * @param instance
     */
    public void injectService(Object instance){
	if (instance==null) {
		return;
	}
	Field[] fields = instance.getClass().getDeclaredFields();
	for (Field field : fields) {
		if (Modifier.isStatic(field.getModifiers())) {
			continue;
		}
		Object fieldBean = null;
		// with bean-id, bean could be found by both @Resource and @Autowired, or bean could only be found by @Autowired
		if (AnnotationUtils.getAnnotation(field, Resource.class) != null) {
			try {
				Resource resource = AnnotationUtils.getAnnotation(field, Resource.class);
				if (resource.name()!=null && resource.name().length()>0){
					fieldBean = JobExecutor.applicationContext.getBean(resource.name());
				} else {
					fieldBean = JobExecutor.applicationContext.getBean(field.getName());
				}
			} catch (Exception e) {
			}
			if (fieldBean==null ) {
				fieldBean = JobExecutor.applicationContext.getBean(field.getType());
			}
		} else if (AnnotationUtils.getAnnotation(field, Autowired.class) != null) {
			Qualifier qualifier = AnnotationUtils.getAnnotation(field, Qualifier.class);
			if (qualifier!=null && qualifier.value()!=null && qualifier.value().length()>0) {
				fieldBean = JobExecutor.applicationContext.getBean(qualifier.value());
			} else {
				fieldBean = JobExecutor.applicationContext.getBean(field.getType());
			}
		}
		
		if (fieldBean!=null) {
			field.setAccessible(true);
			try {
				field.set(instance, fieldBean);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
    }
 // ----------------------------- load instance -----------------------------
 	// load new instance, prototype
 	public BaseJobHandler loadNewInstance(String codeSource) throws Exception{
 		if (codeSource!=null && codeSource.trim().length()>0) {
 			Class<?> clazz = groovyClassLoader.parseClass(codeSource);
 			if (clazz != null) {
 				Object instance = clazz.newInstance();
 				if (instance!=null) {
 					if (instance instanceof BaseJobHandler) {
 						this.injectService(instance);
 						return (BaseJobHandler) instance;
 					} else {
 						throw new IllegalArgumentException(">>>>>>>>>>> xxl-glue, loadNewInstance error, "
 								+ "cannot convert from instance["+ instance.getClass() +"] to IJobHandler");
 					}
 				}
 			}
 		}
 		throw new IllegalArgumentException(">>>>>>>>>>> xxl-glue, loadNewInstance error, instance is null");
 	}
    
}
