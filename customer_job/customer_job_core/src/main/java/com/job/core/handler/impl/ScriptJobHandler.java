package com.job.core.handler.impl;

import com.job.core.biz.model.ReturnModel;
import com.job.core.executor.JobExecutor;
import com.job.core.glue.GlueTypeEnum;
import com.job.core.handler.BaseJobHandler;
import com.job.core.log.JobLogAppender;
import com.job.core.log.JobLogger;
import com.job.core.util.ScriptUtil;

/**
 * Created by xuxueli on 17/4/27.
 */
public class ScriptJobHandler extends BaseJobHandler {

    private Long jobId;
    private long glueUpdatetime;
    private String gluesource;
    private GlueTypeEnum glueType;

    public ScriptJobHandler(Long jobId, long glueUpdatetime, String gluesource, GlueTypeEnum glueType){
        this.jobId = jobId;
        this.glueUpdatetime = glueUpdatetime;
        this.gluesource = gluesource;
        this.glueType = glueType;
    }

    public long getGlueUpdatetime() {
        return glueUpdatetime;
    }
    @Override
    public ReturnModel<String> executor(String...params) throws Exception {

        // cmd + script-file-name
        String cmd = "bash";
        String scriptFileName = null;
        if (GlueTypeEnum.GLUE_SHELL == glueType) {
            cmd = "bash";
            scriptFileName = JobLogAppender.logPath.concat("gluesource/").concat(String.valueOf(jobId)).concat("_").concat(String.valueOf(glueUpdatetime)).concat(".sh");
        } else if (GlueTypeEnum.GLUE_PYTHON == glueType) {
            cmd = "python";
            scriptFileName = JobLogAppender.logPath.concat("gluesource/").concat(String.valueOf(jobId)).concat("_").concat(String.valueOf(glueUpdatetime)).concat(".py");
        }

        // make script file
        ScriptUtil.markScriptFile(scriptFileName, gluesource);

        // log file
        String logFileName = JobLogAppender.logPath.concat(JobLogAppender.contextHolder.get());

        // invoke
        JobLogger.log("----------- script file:"+ scriptFileName +" -----------");
        int exitValue = ScriptUtil.execToFile(cmd, scriptFileName, logFileName, params);
        ReturnModel<String> result = (exitValue==0)?ReturnModel.SUCCESS:new ReturnModel<String>(ReturnModel.FAILUE_CODE, "script exit value("+exitValue+") is failed");
        return result;
    }

   


}
