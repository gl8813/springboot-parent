package com.gl.springbootasync.utils;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

@Slf4j
public class JobSchedule {
    private static SchedulerFactory gSchedulerFactory = new StdSchedulerFactory();
    private static String JOB_GROUP_NAME = "JOB_GROUP_NAME";
    private static String TRIGGER_GROUP_NAME = "TRIGGER_GROUP_NAME";

    private JobSchedule(){

    }

    /**
     * @Description: 添加一个定时任务，使用默认的任务组名，触发器名，触发器组名
     *
     * @param jobName
     *            任务名
     * @param cls
     *            任务
     * @param cron
     *            时间设置，参考quartz说明文档
     * @throws SchedulerException
     *
     */
    public static void addJob(String jobName, Class cls, String cron)
            throws SchedulerException {
        Scheduler scheduler = gSchedulerFactory.getScheduler();

        // 用于描叙Job实现类及其他的一些静态信息，构建一个作业实例
        JobDetail jobDetail = JobBuilder.newJob(cls)
                .withIdentity(jobName, JOB_GROUP_NAME).build();

        // 构建一个触发器，规定触发的规则
        Trigger trigger = TriggerBuilder.newTrigger()// 创建一个新的TriggerBuilder来规范一个触发器
                .withIdentity(jobName, TRIGGER_GROUP_NAME)// 给触发器起一个名字和组名
                .startNow()// 立即执行
                .withSchedule(CronScheduleBuilder.cronSchedule(cron)) // 触发器的执行时间
                .build();// 产生触发器

        scheduler.scheduleJob(jobDetail, trigger);
        log.debug("添加任务:{},{},{}",jobName,cls,cron);
        // 启动
        if (!scheduler.isShutdown()) {
            scheduler.start();
        }
    }

    /**
     * @Description: 添加一个定时任务
     *
     * @param jobName
     *            任务名
     * @param jobGroupName
     *            任务组名
     * @param triggerName
     *            触发器名
     * @param triggerGroupName
     *            触发器组名
     *            任务
     * @param cron
     *            时间设置，参考quartz说明文档
     */
    public static void addJob(String jobName, String jobGroupName,
                              String triggerName, String triggerGroupName, Class cls, String cron)
            throws SchedulerException {

        Scheduler scheduler = gSchedulerFactory.getScheduler();
        // 用于描叙Job实现类及其他的一些静态信息，构建一个作业实例
        JobDetail jobDetail = JobBuilder.newJob(cls)
                .withIdentity(jobName, jobGroupName).build();

        // 构建一个触发器，规定触发的规则
        Trigger trigger = TriggerBuilder.newTrigger()// 创建一个新的TriggerBuilder来规范一个触发器
                .withIdentity(jobName, triggerGroupName)// 给触发器起一个名字和组名
                .startNow()// 立即执行
                .withSchedule(CronScheduleBuilder.cronSchedule(cron)) // 触发器的执行时间
                .build();// 产生触发器

        scheduler.scheduleJob(jobDetail, trigger);
        log.debug("添加任务:{},{},{},{},{},{}",jobName,jobGroupName,triggerName,triggerGroupName,cls,cron);
        // 启动
        if (!scheduler.isShutdown()) {
            scheduler.start();
        }

    }

    /**
     * @Description: 修改一个任务的触发时间(使用默认的任务组名，触发器名，触发器组名)
     *
     * @param jobName
     * @param cron
     * @throws SchedulerException
     *
     */
    public static void modifyJobTime(String jobName, String cron) throws SchedulerException {
        Scheduler scheduler = gSchedulerFactory.getScheduler();
        TriggerKey triggerKey = new TriggerKey(jobName, TRIGGER_GROUP_NAME);
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        if (trigger == null) {
            return;
        }
        String oldTime = trigger.getCronExpression();
        if (!oldTime.equalsIgnoreCase(cron)) {
            JobDetail jobDetail = scheduler.getJobDetail(new JobKey(jobName,
                    JOB_GROUP_NAME));
            Class objJobClass = jobDetail.getJobClass();
            removeJob(jobName);
            addJob(jobName, objJobClass, cron);
            log.debug("修改任务:{},{}",jobName,cron);
        }
    }

    /**
     * @Description: 移除一个任务(使用默认的任务组名，触发器名，触发器组名)
     *
     * @param jobName
     *
     * @throws SchedulerException
     */
    public static void removeJob(String jobName) throws SchedulerException {
        Scheduler scheduler = gSchedulerFactory.getScheduler();

        JobKey jobKey = new JobKey(jobName, TRIGGER_GROUP_NAME);
        // 停止触发器
        scheduler.pauseJob(jobKey);
        scheduler.unscheduleJob(new TriggerKey(jobName, TRIGGER_GROUP_NAME));// 移除触发器
        scheduler.deleteJob(jobKey);// 删除任务
        log.debug("移除任务:{}",jobName);
    }

    /**
     * 移除任务
     *
     * @param jobName
     * @param jobGroupName
     * @param triggerName
     * @param triggerGroupName
     * @throws SchedulerException
     */
    public static void removeJob(String jobName, String jobGroupName,
                                 String triggerName, String triggerGroupName)
            throws SchedulerException {
        Scheduler scheduler = gSchedulerFactory.getScheduler();
        JobKey jobKey = new JobKey(jobName, jobGroupName);
        // 停止触发器
        scheduler.pauseJob(jobKey);
        scheduler.unscheduleJob(new TriggerKey(jobName, triggerGroupName));// 移除触发器
        scheduler.deleteJob(jobKey);// 删除任务
        log.debug("移除任务:{},{},{},{},{},{}",jobName,jobGroupName,triggerName,triggerGroupName);
    }

    /**
     * 启动所有任务
     *
     * @throws SchedulerException
     */
    public static void startJobs() throws SchedulerException {
        Scheduler scheduler = gSchedulerFactory.getScheduler();
        scheduler.start();
        log.debug("启动所有任务");
    }

    /**
     * 关闭所有定时任务
     *
     * @throws SchedulerException
     *
     */
    public static void shutdownJobs() throws SchedulerException {
        Scheduler scheduler = gSchedulerFactory.getScheduler();
        if (!scheduler.isShutdown()) {
            scheduler.shutdown();
            log.debug("关闭所有任务");
        }
    }

}


