package com.job.core.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.job.core.biz.AdminBiz;
import com.job.core.biz.ExecutorBiz;
import com.job.core.biz.impl.ExecutorBizImpl;
import com.job.core.handler.BaseJobHandler;
import com.job.core.handler.annotation.JobHandler;
import com.job.core.log.JobLogAppender;
import com.job.core.rpc.NetComClientProxy;
import com.job.core.rpc.NetComServerFactory;
import com.job.core.thread.JobThread;
/**
 * 
 * @Title: JobExecutor.java
 * @Package: com.job.core.executor
 * @Description: job执行
 * @author: sunwei
 * @date: 2017年5月15日 上午11:30:48
 * @version: V1.0
 */
public class JobExecutor implements ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(JobExecutor.class);

    private String ip;
    private int port = 9999;
    private String appName;
    private String adminAddresses;
    private String accessToken;
    private String logPath;

    public String getAdminAddresses() {
        return adminAddresses;
    }
    public void setAdminAddresses(String adminAddresses) {
        this.adminAddresses = adminAddresses;
    }
    public String getAccessToken() {
        return accessToken;
    }
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    public String getIp() {
        return ip;
    }
    public int getPort() {
        return port;
    }
    public String getAppName() {
        return appName;
    }
    
    public String getLogPath() {
        return logPath;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
    public void setPort(int port) {
        this.port = port;
    }
    public void setAppName(String appName) {
        this.appName = appName;
    }
    public void setLogPath(String logPath) {
        this.logPath = logPath;
    }
    
    public static ApplicationContext applicationContext;
    
    /*******************job server**********************************/
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
    
    // ---------------------- start + stop ----------------------
    public void start() throws Exception {
        // init admin-client
        initAdminBizList(adminAddresses, accessToken);

        // init executor-jobHandlerRepository
        if (applicationContext != null) {
            initJobHandlerRepository(applicationContext);
        }

        // init logpath
        if (logPath!=null && logPath.trim().length()>0) {
            JobLogAppender.logPath = logPath;
        }

        // init executor-server
        initExecutorServer(port, ip, appName, accessToken);
    }
    public void destroy(){
        // destory JobThreadRepository
        if (jobThreadRepository.size() > 0) {
            for (Map.Entry<Long, JobThread> item: jobThreadRepository.entrySet()) {
                removeJobThread(item.getKey(), "Web容器销毁终止");
            }
            jobThreadRepository.clear();
        }

        // destory executor-server
        stopExecutorServer();
    }
    private void stopExecutorServer() {
        serverFactory.destroy();    // jetty + registry + callback
    }
    // ---------------------- admin-client ----------------------
    private static List<AdminBiz> adminBizList;
    
    
    public static List<AdminBiz> getAdminBizList() {
        return adminBizList;
    }
    public static void setAdminBizList(List<AdminBiz> adminBizList) {
        JobExecutor.adminBizList = adminBizList;
    }
    private static void initJobHandlerRepository(ApplicationContext applicationContext){
        // init job handler action
        Map<String, Object> serviceBeanMap = applicationContext.getBeansWithAnnotation(JobHandler.class);
        System.out.println("注册进来的jobbans："+serviceBeanMap.toString());
        if (serviceBeanMap!=null && serviceBeanMap.size()>0) {
            for (Object serviceBean : serviceBeanMap.values()) {
                if (serviceBean instanceof BaseJobHandler){
                    String name = serviceBean.getClass().getAnnotation(JobHandler.class).value();
                    BaseJobHandler handler = (BaseJobHandler) serviceBean;
                    if (loadJobHandler(name) != null) {
                        throw new RuntimeException("customer_job jobhandler naming conflicts.");
                    }
                    registJobHandler(name, handler);
                }
            }
        }
    }
  //执行期注册中
    private static ConcurrentHashMap<String, BaseJobHandler> jobHandlerRepository = new ConcurrentHashMap<String, BaseJobHandler>();
    public static BaseJobHandler registJobHandler(String name, BaseJobHandler jobHandler){
        logger.info("customer-job register jobhandler success, name:{}, jobHandler:{}", name, jobHandler);
        return jobHandlerRepository.put(name, jobHandler);
    }
    
    
    
    private static void initAdminBizList(String adminAddresses, String accessToken) throws Exception {
        if (adminAddresses!=null && adminAddresses.trim().length()>0) {
            for (String address: adminAddresses.trim().split(",")) {
                if (address!=null && address.trim().length()>0) {
                    String addressUrl = address.concat(AdminBiz.MAPPING);
                    AdminBiz adminBiz = (AdminBiz) new NetComClientProxy(AdminBiz.class, addressUrl, accessToken).getObject();
                    if (adminBizList == null) {
                        adminBizList = new ArrayList<AdminBiz>();
                    }
                    adminBizList.add(adminBiz);
                }
            }
        }
    }
    private NetComServerFactory serverFactory = new NetComServerFactory();
    private void initExecutorServer(int port, String ip, String appName, String accessToken) throws Exception {
        NetComServerFactory.putService(ExecutorBiz.class, new ExecutorBizImpl());   // rpc-service, base on jetty
        NetComServerFactory.setAccessToken(accessToken);
        serverFactory.start(port, ip, appName); // jetty + registry
    }
    /**
     * 
     * @Title: loadJobHandler
     * @Description:根据handler名称获取类实例
     * @author:sunwei
     * @createTime:2017年5月15日下午2:22:06
     * @param className handler注解名称
     * @return handler处理实例
     */
    public static BaseJobHandler loadJobHandler(String className){
	 logger.info("handler annotation name is 【"+className+"】获取实例");
	 return jobHandlerRepository.get(className);
    }
    
    /*******************jobThread存储和获取********************************/
    //job线程存储
    private static ConcurrentHashMap<Long, JobThread> jobThreadRepository = new ConcurrentHashMap<Long, JobThread>();
    /**
     * 
     * @Title: registryJobThread
     * @Description:将具体job放入执行器中
     * @author:sunwei
     * @createTime:2017年5月15日下午2:26:52
     * @param jobId 任务编号
     * @param jobHandler 执行器
     * @return job执行线程
     */
    public static JobThread registryJobThread(Long jobId,BaseJobHandler jobHandler, String removeOldReason){
	JobThread newJobThread=new JobThread(jobId,jobHandler);//将执行器放入线程
	newJobThread.start();
        logger.info(">>>>>>>>>>> customer_job regist JobThread success, jobId:{}, handler:{}", new Object[]{jobId, jobHandler});
        JobThread oldJobThread = jobThreadRepository.put(jobId, newJobThread);	// putIfAbsent | oh my god, map's put method return the old value!!!
        if (oldJobThread != null) {
            oldJobThread.toStop(removeOldReason);
            oldJobThread.interrupt();
        }

        return newJobThread;
    }
    /**
     * 
     * @Title: loadJobThread
     * @Description:根据jobId 获取job任务执行线程
     * @author:sunwei
     * @createTime:2017年5月15日下午2:31:23
     * @param jobId job任务编号
     * @return job执行器线程
     */
    public static JobThread loadJobThread(Long jobId){
	return jobThreadRepository.get(jobId);
    }
    /**
     * 
     * @Title: removeJobThread
     * @Description:删除job任务线程
     * @author:sunwei
     * @createTime:2017年5月15日下午2:33:12
     * @param jobId job任务编号
     */
    public static void removeJobThread(Long jobId,String removeOldReason){
	JobThread oldJobThread =jobThreadRepository.remove(jobId);
	if (oldJobThread != null) {
	       oldJobThread.toStop(removeOldReason);
	       oldJobThread.interrupt();
	}
	logger.info("remove jobId:{} is success!",new Object []{jobId});
    }
    
    
}
