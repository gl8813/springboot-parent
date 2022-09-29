package com.gl.springbootasync.service.Impl;

import com.gl.springbootasync.HelloJob;
import com.gl.springbootasync.utils.JobSchedule;
import org.quartz.SchedulerException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Component
public class JobServiceImpl implements CommandLineRunner {

    /**
     * 项目启动时，初始化定时器
     * @throws Exception
     */

    @Override
    public void run(String... args) throws Exception {
        String job_name = "动态任务调度";
        System.out.println("【系统启动】开始(每10秒输出一次)...");
        JobSchedule.addJob(job_name, HelloJob.class, "*/10 * * * * ?");
    }
}
