package com.gl.springbootasync;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Calendar;

/**
 * 执行定时任务的类 任务内容在此执行
 */
@Slf4j
public class HelloJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //            log.info(jobExecutionContext.getScheduler().getSchedulerName());
        String jobParam = (String) jobExecutionContext.getJobDetail().getJobDataMap().get("jobParam");
        if (jobParam != null) {
            log.info(jobParam);
        }
        log.info("执行定时任务 ^_^ , 时间：" + Calendar.getInstance().get(Calendar.SECOND) + "秒");
        log.info("现在是北京时间:" + DateUtil.date() + " - HelloJob任务执行");
    }
}
