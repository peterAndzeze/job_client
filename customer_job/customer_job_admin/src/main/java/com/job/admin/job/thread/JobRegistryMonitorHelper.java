package com.job.admin.job.thread;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.job.admin.job.enmus.RegistryConfig;
import com.job.admin.job.schedule.JobDynamicScheduler;
import com.job.admin.jobGroup.model.JobGroupModel;
import com.job.admin.jobregister.model.JobRegisterModel;

/**
 * 
 * @Title: JobRegistryMonitorHelper.java
 * @Package: com.job.admin.job.thread
 * @Description: job注册服务
 * @author: sunwei
 * @date: 2017年8月6日 上午11:41:48
 * @version: V1.0
 */
public class JobRegistryMonitorHelper {
    private static Logger logger = LoggerFactory.getLogger(JobRegistryMonitorHelper.class);

	private static JobRegistryMonitorHelper instance = new JobRegistryMonitorHelper();
	public static JobRegistryMonitorHelper getInstance(){
		return instance;
	}

	private Thread registryThread;
	private volatile boolean toStop = false;
	public void start(){
	    registryThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (!toStop) {
					try {
					    // auto registry group
					    List<JobGroupModel> groupList = JobDynamicScheduler.jobGroupDao.findByAddressType(0);
					    if (CollectionUtils.isNotEmpty(groupList)) {
							// remove dead address (admin/executor)
							JobDynamicScheduler.jobRegisterDao.deleteDead(RegistryConfig.DEAD_TIMEOUT);
							// fresh online address (admin/executor)
							HashMap<String, List<String>> appAddressMap = new HashMap<String, List<String>>();
							List<JobRegisterModel> list = JobDynamicScheduler.jobRegisterDao.findAll(RegistryConfig.DEAD_TIMEOUT);
							if (list != null) {
								for (JobRegisterModel item: list) {
									if (RegistryConfig.RegistType.EXECUTOR.name().equals(item.getRegistryGroup())) {
										String appName = item.getRegistryKey();
										List<String> registryList = appAddressMap.get(appName);
										if (registryList == null) {
											registryList = new ArrayList<String>();
										}
										if (!registryList.contains(item.getRegistryValue())) {
											registryList.add(item.getRegistryValue());
										}
										appAddressMap.put(appName, registryList);
									}
								}
							}

							// fresh group address
							for (JobGroupModel group: groupList) {
								List<String> registryList = appAddressMap.get(group.getAppName());
								String addressListStr = null;
								if (CollectionUtils.isNotEmpty(registryList)) {
									Collections.sort(registryList);
									addressListStr = StringUtils.join(registryList, ",");
								}
								group.setAddressList(addressListStr);
								JobDynamicScheduler.jobGroupDao.updateJobGroup(group);
							}
						}
					} catch (Exception e) {
						logger.error("job registry instance error:{}", e);
					}
					try {
						TimeUnit.SECONDS.sleep(RegistryConfig.BEAT_TIMEOUT);
					} catch (InterruptedException e) {
						logger.error("job registry instance error:{}", e);
					}
				}
			}
		});
		registryThread.setDaemon(true);
		registryThread.start();
	}

	public void toStop(){
		toStop = true;
		//registryThread.interrupt();
	}
	
}
