package com.deta.achi.quartz.start;

import com.deta.achi.quartz.scheduler.AchiTemplateScheduler;
import org.quartz.SchedulerException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author DJWang
 */
@Component
public class StartAchiTemplateJob implements CommandLineRunner {
    @Override
    public void run(String... args) throws SchedulerException {
        AchiTemplateScheduler achiTemplateScheduler=new AchiTemplateScheduler();
        achiTemplateScheduler.schedulerJob();
    }
}
