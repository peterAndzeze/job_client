package com.job.core.thread;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.job.core.biz.AdminBiz;
import com.job.core.biz.model.RegistryParam;
import com.job.core.biz.model.ReturnModel;
import com.job.core.enums.RegistryConfig;
import com.job.core.enums.RegistryConfig.RegistType;
import com.job.core.executor.JobExecutor;
import com.job.core.util.IpUtil;
/**
 * 
 * @Title: ExecuRegistThread.java
 * @Package: com.job.core.thread
 * @Description: 执行注册线程
 * @author: sunwei
 * @date: 2017年5月10日 下午3:18:47
 * @version: V1.0
 */
public class ExecutRegistThread  extends Thread{
    private Logger logger=LoggerFactory.getLogger(ExecutRegistThread.class);
    private static  ExecutRegistThread execuRegistThread=new ExecutRegistThread();
    public static ExecutRegistThread getExecutRegistThread(){
	return execuRegistThread;
    }
    private Thread registThread;
    private boolean toStop=false;//
    /**
     * 
     * @Title: start
     * @Description:执行关系注册
     * @author:sunwei
     * @createTime:2017年5月10日下午3:32:26
     * @param ip
     * @param port
     * @param appName
     * @param registHandlerHelper
     */
//    public void start(final String ip,final int port,final String appName, final RegistHandlerHelper registHandlerHelper){
//	if(null==registHandlerHelper ||null==ip || appName.trim().length()==0){
//	    return;
//	}
//	registThread=new Thread(new Runnable() {
//	    @Override
//	    public void run() {
//		while(!toStop){
//		    try {
//                        // generate addredd = ip:port
//                        String address = null;
//                        if (ip != null && ip.trim().length()>0) {
//                            address = ip.trim().concat(":").concat(String.valueOf(port));
//                        } else {
//                            address = IpUtil.getIpPort(port);
//                        }
//                        registHandlerHelper.registry(RegistHandlerHelper.RegistType.EXECUTOR.name(), appName, address);
//                        TimeUnit.SECONDS.sleep(RegistHandlerHelper.TIMEOUT);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//		}
//	});
//	//守护线程的优先级比较低，用于为系统中的其它对象和线程提供服务。设置：通过setDaemon(true)来设置线程为“守护线程”
//	//example: 垃圾回收线程就是一个经典的守护线程，当我们的程序中不再有任何运行的Thread,程序就不会再产生垃圾，垃圾回收器也就无事可做
//	//，所以当垃圾回收线程是JVM上仅剩的线程时，垃圾回收线程会自动离开。它始终在低级别的状态中运行，用于实时监控和管理系统中的可回收资源。
//	//生命周期：守护进程（Daemon）是运行在后台的一种特殊进程。它独立于控制终端并且周期性地执行某种任务或等待处理某些发生的事件。也就是说守护线程不依赖于终端，但是依赖于系统，与系统“同生共死”。
//	//那Java的守护线程是什么样子的呢。当JVM中所有的线程都是守护线程的时候，JVM就可以退出了；如果还有一个或以上的非守护线程则JVM不会退出。
//	registThread.setDaemon(true);
//	registThread.start();
//    }
    public void start(final int port, final String ip, final String appName){

        // valid
        if (appName==null || appName.trim().length()==0) {
            logger.warn(">>>>>>>>>>>> customer_job, executor registry config fail, appName is null.");
            return;
        }
        if (JobExecutor.getAdminBizList() == null) {
            logger.warn(">>>>>>>>>>>> customer_job, executor registry config fail, adminAddresses is null.");
            return;
        }

        // executor address (generate addredd = ip:port)
        final String executorAddress;
        if (ip != null && ip.trim().length()>0) {
            executorAddress = ip.trim().concat(":").concat(String.valueOf(port));
        } else {
            executorAddress = IpUtil.getIpPort(port);
        }

        registThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!toStop) {
                    try {
                        RegistryParam registryParam = new RegistryParam(RegistType.EXECUTOR.name(), appName, executorAddress);
                        for (AdminBiz adminBiz: JobExecutor.getAdminBizList()) {
                            try {
                                ReturnModel<String> registryResult = adminBiz.registry(registryParam);
                                if (registryResult!=null && ReturnModel.SUCCESS_CODE == registryResult.getCode()) {
                                    registryResult = ReturnModel.SUCCESS;
                                    logger.info(">>>>>>>>>>> customer_job registry success, registryParam:{}, registryResult:{}", new Object[]{registryParam, registryResult});
                                    break;
                                } else {
                                    logger.info(">>>>>>>>>>> customer_job registry fail, registryParam:{}, registryResult:{}", new Object[]{registryParam, registryResult});
                                }
                            } catch (Exception e) {
                                logger.info(">>>>>>>>>>> customer_job registry error, registryParam:{}", registryParam, e);
                            }

                        }
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }

                    try {
                        TimeUnit.SECONDS.sleep(RegistryConfig.BEAT_TIMEOUT);
                    } catch (InterruptedException e) {
                        logger.error(e.getMessage(), e);
                    }
                }
            }
        });
        registThread.setDaemon(true);
        registThread.start();
    }
    /**
     * 
     * @Title: toStop
     * @Description:停止
     * @author:sunwei
     * @createTime:2017年5月10日下午3:46:50
     */
    public void toStop() {
        toStop = true;
    }
}
