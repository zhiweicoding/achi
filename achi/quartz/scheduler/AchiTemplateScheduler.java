package com.deta.achi.quartz.scheduler;

import com.deta.achi.quartz.job.AchiTemplateJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @author DJWang
 */
public class AchiTemplateScheduler {
    /**
     * 创建任务调度器的方法
     */
    public Scheduler getScheduler() throws SchedulerException {
        SchedulerFactory schedulerFactory=new StdSchedulerFactory();
        return schedulerFactory.getScheduler();
    }

    /**
     * 定义任务方法
     */
    public void schedulerJob() throws SchedulerException {
        //创建任务
        JobDetail jobDetail= JobBuilder.newJob(AchiTemplateJob.class)
                .withIdentity("updateAchiTemplate","achiTemplate").build();
        //每天凌晨执行一次
        String cron="0 0 12 * * ?";
//        String cron="0 */1 * * * ?";
        //产生trigger对象
        CronTrigger trigger=TriggerBuilder.newTrigger()
                .withIdentity("updateAchiTemplate","achiTemplate")
                .withSchedule(CronScheduleBuilder.cronSchedule(cron)).build();
        Scheduler scheduler=getScheduler();
        //调度任务
        scheduler.scheduleJob(jobDetail,trigger);
        //开始任务
        scheduler.start();
    }
}
