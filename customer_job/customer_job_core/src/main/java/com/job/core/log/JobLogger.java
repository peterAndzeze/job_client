package com.job.core.log;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 
 * @Title: JobLogger.java
 * @Package: com.job.core.log
 * @Description: job记录log日志
 * @author: sunwei
 * @date: 2017年5月10日 下午1:31:10
 * @version: V1.0
 */
public class JobLogger {
    private static Logger logger=LoggerFactory.getLogger("customer job");
    private static SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd hh24:mm:ss");
    /**
     * 
     * @Title: log
     * @Description:日志记录
     * @author:sunwei
     * @createTime:2017年5月10日下午1:45:05
     * @param appendLog 追加log日志
     */
    public static void log(String appendLog) {

        // "yyyy-MM-dd HH:mm:ss [ClassName]-[MethodName]-[LineNumber]-[ThreadName] log";
        StackTraceElement[] stackTraceElements = new Throwable().getStackTrace();
        StackTraceElement callInfo = stackTraceElements[1];

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(simpleDateFormat.format(new Date())).append(" ")
            .append("["+ callInfo.getClassName() +"]").append("-")
            .append("["+ callInfo.getMethodName() +"]").append("-")
            .append("["+ callInfo.getLineNumber() +"]").append("-")
            .append("["+ Thread.currentThread().getName() +"]").append(" ")
            .append(appendLog!=null?appendLog:"");
        String formatAppendLog = stringBuffer.toString();
        // appendlog
        String logFileName = JobLogAppender.contextHolder.get();
        JobLogAppender.appendLog(logFileName, formatAppendLog);
        logger.warn("[{}]: {}", logFileName, formatAppendLog);
    }
    
    
    
}
