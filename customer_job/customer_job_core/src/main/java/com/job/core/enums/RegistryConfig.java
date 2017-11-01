package com.job.core.enums;

/**
 * 
 * @Title: RegistryConfig.java
 * @Package: com.job.admin.job.enmus
 * @Description:job 注册服务枚举
 * @author: sunwei
 * @date: 2017年8月6日 上午11:53:24
 * @version: V1.0
 */
public class RegistryConfig {

    public static final int BEAT_TIMEOUT = 30;
    public static final int DEAD_TIMEOUT = BEAT_TIMEOUT * 3;

    public enum RegistType{ EXECUTOR, ADMIN }

}
