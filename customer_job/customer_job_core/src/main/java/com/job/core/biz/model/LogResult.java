package com.job.core.biz.model;

import java.io.Serializable;
/**
 * 
 * @Title: LogResult.java
 * @Package: com.job.core.biz.model
 * @Description: log对应的model
 * @author: sunwei
 * @date: 2017年5月10日 下午1:49:53
 * @version: V1.0
 */
public class LogResult implements Serializable {
    private static final long serialVersionUID = 42L;
    private int fromLineNum;
    private int toLineNum;
    private String logContent;
    private boolean isEnd;
    /**
     * 
     * @Description:
     * @param fromLineNum 开始行号
     * @param toLineNum 结束行号
     * @param logContent 日志内容 
     * @param isEnd 是否结束
     */
    public LogResult(int fromLineNum, int toLineNum, String logContent, boolean isEnd) {
        this.fromLineNum = fromLineNum;
        this.toLineNum = toLineNum;
        this.logContent = logContent;
        this.isEnd = isEnd;
    }
    public int getFromLineNum() {
        return fromLineNum;
    }

    public void setFromLineNum(int fromLineNum) {
        this.fromLineNum = fromLineNum;
    }

    public int getToLineNum() {
        return toLineNum;
    }

    public void setToLineNum(int toLineNum) {
        this.toLineNum = toLineNum;
    }

    public String getLogContent() {
        return logContent;
    }

    public void setLogContent(String logContent) {
        this.logContent = logContent;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean end) {
        isEnd = end;
    }
}
